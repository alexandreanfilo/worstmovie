package com.texoit.worstmovie;

public class ConsecutiveAwardsDTO {

   private String producer;
   private int interval;
   private int previousWin;
   private int followingWin;

   public ConsecutiveAwardsDTO(String producer, int interval, int previousWin, int followingWin) {
      this.producer = producer;
      this.interval = interval;
      this.previousWin = previousWin;
      this.followingWin = followingWin;
   }

   public ConsecutiveAwardsDTO(String producer, int interval) {
      this.producer = producer;
      this.interval = interval;
   }

   public String getProducer() {
      return producer;
   }

   public int getInterval() {
      return interval;
   }

   public int getFollowingWin() {
      return followingWin;
   }

   public int getPreviousWin() {
      return previousWin;
   }

}
