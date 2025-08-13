package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "startTime cannot be blank")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotBlank(message = "endTime cannot be blank")
    @Column(nullable = false)
    private LocalTime endTime;
}
