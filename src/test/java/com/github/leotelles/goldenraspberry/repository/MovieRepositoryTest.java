package com.github.leotelles.goldenraspberry.repository;

import com.github.leotelles.goldenraspberry.domain.MovieModel;
import com.github.leotelles.goldenraspberry.util.CsvReader;
import com.github.leotelles.goldenraspberry.util.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import static com.github.leotelles.goldenraspberry.utils.FileData.getFileData;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        FileReader<MovieModel> fileReader = new CsvReader<>(MovieModel.class);
        Optional<List<MovieModel>> movieList = Optional.ofNullable(
                fileReader.read(
                        new StringReader(getFileData())));

        Assertions.assertTrue(movieList.isPresent());
        movieList.get().forEach(movie -> testEntityManager.persistAndFlush(movie));
    }

    @Test
    void shouldFindWinningProducers() {
        Optional<List<String>> movieList = movieRepository.findAllProducersByWinnerMovies();

        Assertions.assertTrue(movieList.isPresent());
        Assertions.assertTrue(movieList.get().stream().anyMatch(s -> s.contains("Allan Carr")));
        Assertions.assertTrue(movieList.get().stream().anyMatch(s -> s.contains("Steve Fargnoli")));
        Assertions.assertTrue(movieList.get().stream().anyMatch(s -> s.contains("Steven Perry")));
        Assertions.assertTrue(movieList.get().stream().anyMatch(s -> s.contains("Joel Silver")));
        Assertions.assertFalse(movieList.get().stream().anyMatch(s -> s.contains("Jerry Weintraub")));
    }

    @Test
    void shouldCountProducerWins() {
        Assertions.assertNotEquals(movieRepository.countProducerWins("Allan Carr"), 0);
        Assertions.assertEquals(movieRepository.countProducerWins("Allan Carr"), 1);
        Assertions.assertEquals(movieRepository.countProducerWins("Bo Derek"), 2);
        Assertions.assertEquals(movieRepository.countProducerWins("Steve Shagan"), 0);
    }

    @Test
    void shouldFindMoviesByWinningProducers() {
        List<MovieModel> movieList = movieRepository.findAllByProducersContainingAndWinnerOrderByYearAsc("Allan Carr", "yes");
        Assertions.assertTrue(movieList.stream().anyMatch(movie -> movie.getTitle().equals("Can't Stop the Music")));
        Assertions.assertTrue(movieList.stream().noneMatch(movie -> movie.getTitle().equals("Where the Boys Are '84")));

        movieList = movieRepository.findAllByProducersContainingAndWinnerOrderByYearAsc("Bo Derek", "yes");
        Assertions.assertTrue(movieList.stream().anyMatch(movie -> movie.getTitle().equals("Bolero")));
        Assertions.assertTrue(movieList.stream().anyMatch(movie -> movie.getTitle().equals("Ghosts Can't Do It")));
    }

}
