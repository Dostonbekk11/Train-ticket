package org.example.railwayticketbooking.repository;

import jakarta.transaction.Transactional;
import org.example.railwayticketbooking.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    @Query(nativeQuery = true, value = "select public.train_status.status_id as st_id,status.name as status, " +
            "train.from_place,train.to_place, train.enabled,train.date,train.departure_time,train.arrival_time,train.num_place,train.id,train.price from train inner join " +
            "train_status  on train.id = train_status.train_id inner join status on train_status.status_id = status.id where train.from_place=:fPlace and " +
            "train.to_place=:tPlace and train.date=:date and train.departure_time=:time and public.train.enabled=true")
    Optional<List<Train>> showTrains(
            @Param("fPlace") String fPlace, @Param("tPlace") String tPlace,
            @Param("date") LocalDate date, @Param("time") LocalTime time
    );

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update train set enabled=false where id=:tId")
    void updateTrainForBooking(@Param("tId") Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update train set enabled=true where id=:tId")
    void updateTrainForCancelBooking(@Param("tId") Long id);
}