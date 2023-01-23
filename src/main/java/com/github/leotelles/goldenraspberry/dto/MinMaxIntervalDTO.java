package com.github.leotelles.goldenraspberry.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MinMaxIntervalDTO {

    public MinMaxIntervalDTO() {
        min = new ArrayList<>();
        max = new ArrayList<>();
    }

    private List<ProducerDTO> min;
    private List<ProducerDTO> max;

}
