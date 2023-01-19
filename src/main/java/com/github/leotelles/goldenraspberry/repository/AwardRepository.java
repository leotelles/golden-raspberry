package com.github.leotelles.goldenraspberry.repository;

import com.github.leotelles.goldenraspberry.domain.AwardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AwardRepository extends JpaRepository<AwardModel, Integer> {

    @Query(value = "SELECT producers FROM AwardModel WHERE winner = 'yes' GROUP BY producers")
    Optional<List<String>> findAllProducersByWinnerMovies();

    @Query(value = "SELECT count(id) FROM AwardModel WHERE winner = 'yes' AND producers LIKE CONCAT('%', ?1, '%')")
    Integer countProducerWins(String producer);

    List<AwardModel> findAllByProducersContainingAndWinnerOrderByYearAsc(String producer, String winner);

}
