package org.example.railwayticketbooking.repository;

import org.example.railwayticketbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
