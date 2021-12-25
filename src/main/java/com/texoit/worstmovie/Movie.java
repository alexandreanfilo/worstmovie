package com.texoit.worstmovie;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private int id;
   
   @Column(name = "title", nullable = false)
   private final String title;

   @Column(name = "year", nullable = false)
   private final int year;

   @Column(name = "winner", nullable = false)
   private final boolean winner;

   @ManyToMany
   private Set<Producer> producers;

   @ManyToMany
   private Set<Studio> studios;

   public Movie(String title, int year, boolean winner) {
      this.title = title;
      this.year = year;
      this.winner = winner;
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

}
