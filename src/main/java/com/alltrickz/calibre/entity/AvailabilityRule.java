package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class AvailabilityRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Owner owner;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}
