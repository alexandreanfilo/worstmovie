package com.texoit.worstmovie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorstmovieApplication {

	@Autowired
   private static CSVService csv;

	public static void main(String[] args) {
		SpringApplication.run(WorstmovieApplication.class, args);
	}

}
