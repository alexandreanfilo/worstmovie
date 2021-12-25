package com.texoit.worstmovie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

   public List<Movie> findByWinner(boolean winner);

   @Query(nativeQuery = true, value = "select p.name as producer"
         + "    from movies m"
         + "   inner join movies_producers mp"
         + "      on mp.movie_id = m.id"
         + "   inner join producers p"
         + "      on p.id = mp.producers_id"
         + "   where m.winner = true"
         + "   group by p.name"
         + "  having count(*) > 1")
   public List<ConsecutiveAwardsStatistic> findConsecutiveAwards();

}
