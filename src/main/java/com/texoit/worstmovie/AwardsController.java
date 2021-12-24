package com.texoit.worstmovie;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AwardsController {

   @GetMapping("/awards/max-min-intervals")
   public String hello() {
      return new String("max-min-intervals...");
   }


   
}
