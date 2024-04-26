package org.example.railwayticketbooking.repository;

import org.example.railwayticketbooking.model.Train;
import org.example.railwayticketbooking.model.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WagonRepository extends JpaRepository<Wagon, Integer> {
    Train getById(Long wagonId);
}
