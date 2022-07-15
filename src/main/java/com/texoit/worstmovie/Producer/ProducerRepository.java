package com.texoit.worstmovie.Producer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

   public Producer findByName(String name);

   @Query(nativeQuery = true, value = ""
         + "SELECT t.producer,"
         + "       CASEWHEN(following_producer = producer_id,"
         + "                abs(following_win - previous_win),"
         + "               -1"
         + "       ) as diff_interval,"
         + "       t.previous_win,"
         + "       t.following_win"
         + "  FROM ("
         + "     SELECT p.id   as producer_id,"
         + "            p.name as producer,"
         + "            m.year as previous_win,"
         + "            (lead(p.id)   over (order by p.id, m.year)) as following_producer,"
         + "            (lead(m.year) over (order by p.id, m.year)) as following_win"
         + "       FROM movies m"
         + "      INNER JOIN movies_producers mp"
         + "         ON mp.movie_id = m.id"
         + "      INNER JOIN producers p"
         + "         ON p.id = mp.producers_id"
         + "      WHERE m.winner = true"
         + "      ORDER BY p.id, m.year"
         + "  ) t"
         + " WHERE t.producer_id = t.following_producer"
         + " ORDER BY diff_interval")
   public List<Object[]> findConsecutiveAwards();

}
