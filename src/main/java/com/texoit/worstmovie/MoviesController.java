package com.texoit.worstmovie;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MoviesController {

   @Autowired
   private CSVService csv;
   
   @GetMapping("/movies/max-min-intervals")
   public String hello() {
      this.csv.importMovies();
      return new String("max-min-intervals...");
   }


   
}
