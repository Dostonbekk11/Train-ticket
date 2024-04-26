package org.example.railwayticketbooking.service;

import lombok.RequiredArgsConstructor;
import org.example.railwayticketbooking.config.exception.BookingNotFoundException;
import org.example.railwayticketbooking.config.exception.TrainNotFoundException;
import org.example.railwayticketbooking.config.exception.UserNotFoundException;
import org.example.railwayticketbooking.config.exception.WagonNotFoundException;
import org.example.railwayticketbooking.dto.TrainDto;
import org.example.railwayticketbooking.model.Booking;
import org.example.railwayticketbooking.model.Train;
import org.example.railwayticketbooking.model.User;
import org.example.railwayticketbooking.model.Wagon;
import org.example.railwayticketbooking.repository.BookingRepository;
import org.example.railwayticketbooking.repository.TrainRepository;
import org.example.railwayticketbooking.repository.UserRepository;
import org.example.railwayticketbooking.repository.WagonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {
    private final UserRepository userRepository;
    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;
    private final WagonRepository wagonRepository;

    public Train saveTrain(TrainDto trainDto) {
        Train train = new Train();
        train.setName(trainDto.getName());
        train.setSource(trainDto.getSource());
        train.setDestination(trainDto.getDestination());
        train.setWagons(trainDto.getWagons());

        return trainRepository.save(train);
    }

    public Booking bookTrainForUser(Long userId, Long trainId, Long wagonId, int seats) throws WagonNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new TrainNotFoundException("Train not found"));

        Wagon wagon = wagonRepository.findById(Math.toIntExact(wagonId))
                .orElseThrow(() -> new WagonNotFoundException("Wagon not found"));



        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTrain(train);
        booking.setWagon(wagon);
        booking.setSeats(seats);

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        bookingRepository.delete(booking);
    }

    public List<Train> getTrains() {
        return trainRepository.findAll();
    }
}
