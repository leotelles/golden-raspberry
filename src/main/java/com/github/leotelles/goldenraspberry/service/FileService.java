package com.github.leotelles.goldenraspberry.service;

import com.github.leotelles.goldenraspberry.domain.MovieModel;
import com.github.leotelles.goldenraspberry.util.CsvReader;
import com.github.leotelles.goldenraspberry.util.FileReader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final MovieService movieService;

    public FileService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void loadAndPersistCsvFile(String path) {
        FileReader<MovieModel> fileReader = new CsvReader<>(MovieModel.class);
        Optional<List<MovieModel>> movieList = Optional.ofNullable(fileReader.read(path));
        movieList.ifPresent(movies -> movieService.save(movies));
    }

}
