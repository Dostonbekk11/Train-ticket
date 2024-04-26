package org.example.railwayticketbooking.config.exception;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(Long id) {
        super("Train not found by id : "+id);
    }

    public TrainNotFoundException(String s) {
    }
}
