package com.example.demo.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "pickup_date")
    private LocalDate pickupDate;

    @Column(name = "opening_hours") // New field for opening hours
    private String openingHours;

    @Column(name = "created")
    private LocalDateTime created;


    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }
}
