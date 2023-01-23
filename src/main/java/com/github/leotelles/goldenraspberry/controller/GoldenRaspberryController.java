package com.github.leotelles.goldenraspberry.controller;

import com.github.leotelles.goldenraspberry.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/golden-raspberry-awards")
public class GoldenRaspberryController {

    private final MovieService movieService;

    public GoldenRaspberryController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/min-max-interval")
    public ResponseEntity<?> getMinMaxWinningInterval() {
        try {
            return ResponseEntity.ok(movieService.getMinMaxIntervalDTO());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("It was not possible to obtain the producers with the highest and lowest award range");
        }
    }

}
