package org.example.railwayticketbooking.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.railwayticketbooking.model.Wagon;

import java.util.List;

@Getter
@Setter
public class TrainDto {
    private Long trainId;
    private String name;
    private String source;
    private String destination;
    private List<Wagon> wagons;
}
