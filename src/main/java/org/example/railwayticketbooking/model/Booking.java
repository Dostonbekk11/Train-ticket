package org.example.railwayticketbooking.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     @ManyToOne
     @JoinColumn(name = "train_id", nullable = false)
     private Train train;
     @ManyToOne
     @JoinColumn(name = "users_id", nullable = false)
     private User user;
     @ManyToOne
     @JoinColumn(name = "wagon_id",nullable = false)
     private Wagon wagon;
     @Column(nullable = false)
     private int seats;
    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

}