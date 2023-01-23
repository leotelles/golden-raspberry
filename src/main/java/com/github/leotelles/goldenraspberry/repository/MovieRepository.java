package com.github.leotelles.goldenraspberry.repository;

import com.github.leotelles.goldenraspberry.domain.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieModel, Integer> {

    @Query(value = "SELECT producers FROM MovieModel WHERE winner = 'yes' GROUP BY producers")
    Optional<List<String>> findAllProducersByWinnerMovies();

    @Query(value = "SELECT count(id) FROM MovieModel WHERE winner = 'yes' AND producers LIKE CONCAT('%', ?1, '%')")
    Integer countProducerWins(String producer);

    List<MovieModel> findAllByProducersContainingAndWinnerOrderByYearAsc(String producer, String winner);

}
