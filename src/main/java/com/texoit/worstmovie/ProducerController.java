package com.texoit.worstmovie;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ProducerController {

   private final ProducerRepository producerRepository;
   private final MovieRepository movieRepository;

   @Autowired
   public ProducerController(ProducerRepository pR, MovieRepository mR) {
      this.producerRepository = pR;
      this.movieRepository = mR;
   }

   @GetMapping("/producers/all")
   public List<Producer> allProducers() {
      return this.producerRepository.findAll();
   }

}
