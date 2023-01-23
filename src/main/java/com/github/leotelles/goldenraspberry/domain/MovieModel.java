package com.github.leotelles.goldenraspberry.domain;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Entity
@Table(name = "movies")
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "release_year")
    private String year;

    private String title;
    private String studios;
    private String producers;

    @Nullable
    private String winner;

}
