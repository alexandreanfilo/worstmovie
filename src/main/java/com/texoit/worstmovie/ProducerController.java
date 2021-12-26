package com.texoit.worstmovie;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ProducerController {

   private final ProducerRepository producerRepository;

   @Autowired
   public ProducerController(ProducerRepository pR) {
      this.producerRepository = pR;
   }

   @GetMapping("/producers/all")
   public List<Producer> allProducers() {
      return this.producerRepository.findAll();
   }

   @GetMapping("/producers/consecutive-awards")
   public Map<String, Object> consecutiveAwards() {
      List<Object[]> consecutiveAwards = this.producerRepository.findConsecutiveAwards();

      List<ConsecutiveAwardsDTO> minArray = new ArrayList<ConsecutiveAwardsDTO>();
      List<ConsecutiveAwardsDTO> maxArray = new ArrayList<ConsecutiveAwardsDTO>();

      ConsecutiveAwardsDTO current = null;

      for (Object[] consecutiveAwardValues : consecutiveAwards) {
         ConsecutiveAwardsDTO consecutiveAward = new ConsecutiveAwardsDTO(
               consecutiveAwardValues[0].toString(),                    // producer
               Integer.parseInt(consecutiveAwardValues[1].toString()),  // interval
               Integer.parseInt(consecutiveAwardValues[2].toString()),  // previous
               Integer.parseInt(consecutiveAwardValues[3].toString())); // following

         if (current == null) {
            current = consecutiveAward;
         }

         if (consecutiveAward.getInterval() <= current.getInterval()) {
            minArray.add(consecutiveAward);
         }

         if (consecutiveAward.getInterval() == current.getInterval()) {
            maxArray.add(consecutiveAward);
         }

         if (consecutiveAward.getInterval() > current.getInterval()) {
            maxArray.remove(current);
            maxArray.add(consecutiveAward);
         }

         current = consecutiveAward;
      }

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("min", minArray);
      responseMap.put("max", maxArray);

      return responseMap;
   }

}
