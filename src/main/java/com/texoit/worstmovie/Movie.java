package com.texoit.worstmovie;

public class Movie {
   
   private final String title;
   private final int year;
   private Producer[] producers;
   private Studio[] studios;

   public Movie(String title, int year) {
      this.title = title;
      this.year = year;
   }

   public int getYear() {
      return year;
   }

   public String getTitle() {
      return title;
   }

   public Producer[] getProducers() {
      return producers;
   }

   public void setProducer(Producer[] producer) {
      this.producers = producer;
   }

   public Studio[] getStudios() {
      return studios;
   }

   public void setStudios(Studio[] studios) {
      this.studios = studios;
   }

}
