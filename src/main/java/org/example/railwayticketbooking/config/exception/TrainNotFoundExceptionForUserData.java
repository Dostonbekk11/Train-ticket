package org.example.railwayticketbooking.config.exception;

public class TrainNotFoundExceptionForUserData extends RuntimeException{
    public TrainNotFoundExceptionForUserData() {
        super("Train not found for your data");
    }
}
