package com.texoit.worstmovie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioController {

   private final StudioRepository stusioRepository;

   @Autowired
   public StudioController(StudioRepository sR) {
      this.stusioRepository = sR;
   }

   @GetMapping("/studios/all")
   public List<Studio> allProducers() {
      return this.stusioRepository.findAll();
   }

}
