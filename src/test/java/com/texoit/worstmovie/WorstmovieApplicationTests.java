package com.texoit.worstmovie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.csv.CSVRecord;

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
		this.mockMvc.perform(get("/producers/consecutive-awards"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.min", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$.max", hasSize(greaterThan(0))));
	}

}
