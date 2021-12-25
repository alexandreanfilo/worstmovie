package com.texoit.worstmovie;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MovieController {

   private final MovieRepository movieRepository;

   @Autowired
   public MovieController(MovieRepository mR) {
      this.movieRepository = mR;
   }

   @GetMapping("/movies/all")
   public List<Movie> allMovies() {
      return this.movieRepository.findAll();
   }

   @GetMapping("/movies/winners")
   public List<Movie> winnerMovies() {
      return this.movieRepository.findByWinner(true);
   }

}
