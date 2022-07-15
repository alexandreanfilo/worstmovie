package com.texoit.worstmovie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WorstmovieApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		System.out.println("contextLoads...");
	}

	@Test
	public void shouldReturnAllMovies() throws Exception {
		// todo: Ler arquivo e validar se todos os MOVIES retornados pela API batem com do arquivo

		this.mockMvc.perform(get("/movies/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(206)))
				.andExpect(jsonPath("$[0].title", is("Can't Stop the Music")));
	}

	@Test
	public void shouldReturnAllProducers() throws Exception {
		// todo: Ler arquivo e validar se todos os PRODUCERS retornados pela API batem com do arquivo

		this.mockMvc.perform(get("/producers/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(359)))
				.andExpect(jsonPath("$[0].name", is("Allan Carr")));
	}

	@Test
	public void shouldReturnAllStudios() throws Exception {
		// todo: Ler arquivo e validar se todos os STUDIOS retornados pela API batem com do arquivo

		this.mockMvc.perform(get("/studios/all"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(59)))
				.andExpect(jsonPath("$[0].name", is("Associated Film Distribution")));
	}

	@Test
	public void shouldReturnConsecutiveAwards() throws Exception {
		// todo: Refazer calculo e validar se bate com os valores retornados pela API		

		this.mockMvc.perform(get("/producers/consecutive-awards"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.min", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$.max", hasSize(greaterThan(0))));
	}

}
