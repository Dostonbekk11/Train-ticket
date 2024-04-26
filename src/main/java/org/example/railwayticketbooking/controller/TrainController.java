package org.example.railwayticketbooking.controller;

import lombok.RequiredArgsConstructor;
import org.example.railwayticketbooking.config.exception.BookingNotFoundException;
import org.example.railwayticketbooking.config.exception.WagonNotFoundException;
import org.example.railwayticketbooking.dto.TrainDto;
import org.example.railwayticketbooking.model.Booking;
import org.example.railwayticketbooking.model.Train;
import org.example.railwayticketbooking.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {
    private final TrainService trainService;



    @PostMapping
    public ResponseEntity<Train> saveTrain(@RequestBody TrainDto trainDto) {
        Train savedTrain = trainService.saveTrain(trainDto);
        return ResponseEntity.ok(savedTrain);
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> bookTrainForUser(@RequestParam Long userId,
                                                    @RequestParam Long trainId,
                                                    @RequestParam Long wagonId,
                                                    @RequestParam int seats) {
        try {
            Booking bookedSeats = trainService.bookTrainForUser(userId, trainId, wagonId, seats);
            return ResponseEntity.ok(bookedSeats);
        } catch (WagonNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        try {
            trainService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        } catch (BookingNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Train>> getTrains() {
        List<Train> trains = trainService.getTrains();
        return ResponseEntity.ok(trains);
    }
}
