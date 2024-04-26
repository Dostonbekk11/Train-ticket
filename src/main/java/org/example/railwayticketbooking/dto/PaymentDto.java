package org.example.railwayticketbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class PaymentDto {
    private Long userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String transactionId;
}
