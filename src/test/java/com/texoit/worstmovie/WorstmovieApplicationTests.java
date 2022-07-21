package com.texoit.worstmovie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WorstmovieApplicationTests {

	private final String fileName = "movielist.csv";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CSVService csvService;

	@Test
	public void shouldReturnAllMovies() throws Exception {
		Set<String> movies = new HashSet<String>();

		ResultActions result = this.mockMvc.perform(get("/movies/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		for (CSVRecord row : this.csvService.getCSVRecords(this.fileName)) {
			String title = this.csvService.findStringValue(row, "title");

			if (!movies.contains(title)) {
				movies.add(title);
			}
		}

		result.andExpect(jsonPath("$", hasSize(movies.size())));

		for (int i = 0; i < movies.size(); i++) {
			result.andExpect(jsonPath("$[" + i + "].title", in(movies)));
		}
	}

	@Test
	public void shouldReturnAllProducers() throws Exception {
		Set<String> producers = new HashSet<String>();

		ResultActions result = this.mockMvc.perform(get("/producers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		for (CSVRecord row : this.csvService.getCSVRecords(this.fileName)) {
			List<String> producersNames = this.csvService.findSplittedValues(row, "producers", ",| and ");

			for (String name : producersNames) {
				if (!producers.contains(name)) {
					producers.add(name);
				}
			}
		}

		result.andExpect(jsonPath("$", hasSize(producers.size())));

		for (int i = 0; i < producers.size(); i++) {
			result.andExpect(jsonPath("$[" + i + "].name", in(producers)));
		}
	}

	@Test
	public void shouldReturnAllStudios() throws Exception {
		Set<String> studios = new HashSet<String>();

		ResultActions result = this.mockMvc.perform(get("/studios/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		for (CSVRecord row : this.csvService.getCSVRecords(this.fileName)) {
			List<String> studiosNames = this.csvService.findSplittedValues(row, "studios", ",| and ");

			for (String name : studiosNames) {
				if (!studios.contains(name)) {
					studios.add(name);
				}
			}
		}

		result.andExpect(jsonPath("$", hasSize(studios.size())));

		for (int i = 0; i < studios.size(); i++) {
			result.andExpect(jsonPath("$[" + i + "].name", in(studios)));
		}
	}

	@Test
	public void shouldReturnConsecutiveAwards() throws Exception {
		ResultActions result = this.mockMvc.perform(get("/producers/consecutive-awards"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		HashMap<String, ArrayList<Integer>> awards = new HashMap<String, ArrayList<Integer>>();

		for (CSVRecord row : this.csvService.getCSVRecords(this.fileName)) {
			Integer year = this.csvService.findIntegerValue(row, "year");
			List<String> producersNames = this.csvService.findSplittedValues(row, "producers", ",| and ");
			Boolean winner = this.csvService.findBooleanValue(row, "winner", "yes");

			if (winner) {
				for (String name : producersNames) {
					if (!awards.containsKey(name)) {
						awards.put(name, new ArrayList<Integer>());
					}
					awards.get(name).add(year);
				}
			}
		}

		List<ConsecutiveAwardsDTO> minMap = new ArrayList<ConsecutiveAwardsDTO>();
		List<ConsecutiveAwardsDTO> maxMap = new ArrayList<ConsecutiveAwardsDTO>();

		awards.forEach((producer, years) -> {
			if (years.size() > 1) {
				years.sort((year1, year2) -> year1 - year2);

				int min = 0;
				int max = 0;
				for (int i = 0; i < years.size() - 1; i++) {
					int interval = years.get(i + 1) - years.get(i);
					if (i == 0) {
						min = interval;
						max = interval;
					} else {
						if (interval < min) {
							min = interval;
						}
						if (interval > max) {
							max = interval;
						}
					}
				}

				if (min > 0) {
					if (minMap.size() == 0) {
						minMap.add(new ConsecutiveAwardsDTO(producer, min));
					} else {
						if (min < minMap.get(0).getInterval()) {
							minMap.clear();
							minMap.add(new ConsecutiveAwardsDTO(producer, min));
						} else if (min == minMap.get(0).getInterval()) {
							minMap.add(new ConsecutiveAwardsDTO(producer, min));
						}
					}
				}

				if (max > 0) {
					if (maxMap.size() == 0) {
						maxMap.add(new ConsecutiveAwardsDTO(producer, max));
					} else {
						if (max > maxMap.get(0).getInterval()) {
							maxMap.clear();
							maxMap.add(new ConsecutiveAwardsDTO(producer, max));
						} else if (max == maxMap.get(0).getInterval()) {
							maxMap.add(new ConsecutiveAwardsDTO(producer, max));
						}
					}
				}
			}
		});

		for (int i = 0; i < minMap.size(); i++) {
			result.andExpect(jsonPath("$.min[" + i + "].producer", is(minMap.get(i).getProducer())))
				.andExpect(jsonPath("$.min[" + i + "].interval", is(minMap.get(i).getInterval())));
		}

		for (int i = 0; i < maxMap.size(); i++) {
			result.andExpect(jsonPath("$.max[" + i + "].producer", is(maxMap.get(i).getProducer())))
				.andExpect(jsonPath("$.max[" + i + "].interval", is(maxMap.get(i).getInterval())));
		}
	}

}
