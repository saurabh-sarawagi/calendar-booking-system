package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class AvailabilityExceptionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate localDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(nullable = false)
    private Boolean isAvailable;

    @ManyToOne
    private AvailabilityRuleSet availabilityRuleSet;
}
