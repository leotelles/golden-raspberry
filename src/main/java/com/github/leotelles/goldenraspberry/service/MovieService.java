package com.github.leotelles.goldenraspberry.service;

import com.github.leotelles.goldenraspberry.domain.MovieModel;
import com.github.leotelles.goldenraspberry.dto.MinMaxIntervalDTO;
import com.github.leotelles.goldenraspberry.dto.ProducerDTO;
import com.github.leotelles.goldenraspberry.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void save(List<MovieModel> movieList) {
        movieRepository.saveAll(movieList);
    }

    public MinMaxIntervalDTO getMinMaxIntervalDTO() {
        return filterMinMaxIntervalDTO(
                getAllProducersDTO(
                        filterTwiceWinningProducers(
                                movieRepository.findAllProducersByWinnerMovies().orElseGet(ArrayList::new))));
    }

    private MinMaxIntervalDTO filterMinMaxIntervalDTO(List<ProducerDTO> producersDTO) {
        if (producersDTO.isEmpty()) {
            return new MinMaxIntervalDTO();
        }

        int minInterval = Collections.min(producersDTO, Comparator.comparingInt(ProducerDTO::getInterval)).getInterval();
        int maxInterval = Collections.max(producersDTO, Comparator.comparingInt(ProducerDTO::getInterval)).getInterval();

        MinMaxIntervalDTO minMaxIntervalDTO = new MinMaxIntervalDTO();
        minMaxIntervalDTO.setMin(producersDTO.stream().filter(producerDTO -> producerDTO.getInterval() == minInterval).collect(Collectors.toList()));
        minMaxIntervalDTO.setMax(producersDTO.stream().filter(producerDTO -> producerDTO.getInterval() == maxInterval).collect(Collectors.toList()));

        return minMaxIntervalDTO;
    }

    private List<ProducerDTO> getAllProducersDTO(List<String> twiceWinningProducers) {
        List<ProducerDTO> producersDTO = new ArrayList<>();

        twiceWinningProducers.forEach(producerName -> {
            List<MovieModel> producerMovies = movieRepository.findAllByProducersContainingAndWinnerOrderByYearAsc(producerName, "yes");
            producersDTO.addAll(getProducerDTO(producerMovies, producerName));
        });

        return producersDTO;
    }

    private List<String> filterTwiceWinningProducers(List<String> winnerProducers) {
        List<String> twiceWinningProducers = new ArrayList<>();

        winnerProducers.forEach(producers ->  {
            for (String producerName : producers.split(", | and ")) {
                if (twiceWinningProducers.contains(producerName)) {
                    continue;
                }
                if (movieRepository.countProducerWins(producerName) > 1) {
                    twiceWinningProducers.add(producerName);
                }
            }
        });

        return twiceWinningProducers;
    }

    private List<ProducerDTO> getProducerDTO(List<MovieModel> producerMovies, String producerName) {
        List<ProducerDTO> producersDTO = new ArrayList<>();
        for (int i = 0; i < producerMovies.size() - 1; i++) {
            MovieModel previousWin = producerMovies.get(i);
            MovieModel followingWin = producerMovies.get(i + 1);

            Integer interval = Integer.parseInt(followingWin.getYear()) - Integer.parseInt(previousWin.getYear());

            producersDTO.add(
                    new ProducerDTO(
                            producerName,
                            interval,
                            previousWin.getYear(),
                            followingWin.getYear()));
        }

        return producersDTO;
    }

}
