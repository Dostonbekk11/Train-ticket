package org.example.railwayticketbooking.config.exception;

public class EmailAlreadyExistsExceptions extends RuntimeException {
    public EmailAlreadyExistsExceptions(String email) {
        super("Email already exists: " + email);
    }
}
