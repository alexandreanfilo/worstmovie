package com.texoit.worstmovie.Movie;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.texoit.worstmovie.Producer.Producer;
import com.texoit.worstmovie.Studio.Studio;

@Entity
@Table(name = "movies")
public class Movie {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @Column(name = "title", nullable = false)
   private String title;

   @Column(name = "year", nullable = false)
   private int year;

   @Column(name = "winner", nullable = false)
   private boolean winner;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Studio> studios;

   @ManyToMany(fetch = FetchType.EAGER)
   private Set<Producer> producers;

   public Movie() {
   }

   public Movie(int year, String title, boolean winner) {
      this.title = title;
      this.year = year;
      this.winner = winner;
      this.studios = new HashSet<Studio>();
      this.producers = new HashSet<Producer>();
   }

   public int getYear() {
      return year;
   }

   public String getTitle() {
      return title;
   }

   public boolean isWinner() {
      return winner;
   }

   public void addStudio(Studio s) {
      this.studios.add(s);
   }

   public void addProducer(Producer p) {
      this.producers.add(p);
   }

}
