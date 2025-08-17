package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing an Appointment booked with an Owner.
 * Each appointment has a unique constraint on (owner_id, date, start_time, end_time)
 * to avoid double-booking for the same owner and time slot.
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "date", "start_time", "end_time"}))
public class Appointment {

    /** Primary key - auto-generated unique identifier for each appointment */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The owner with whom the appointment is booked.
     * Single owner can have Many appointments.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Owner owner;

    /** Name of the invitee (person booking the appointment) */
    @Column(nullable = false)
    private String inviteeName;

    /** Email of the invitee */
    @Column(nullable = false)
    private String inviteeEmail;

    /** Date of the appointment (yyyy-MM-dd) */
    @Column(nullable = false)
    private LocalDate date;

    /** Start time of the appointment (HH:mm) */
    @Column(nullable = false)
    private LocalTime startTime;

    /** End time of the appointment (HH:mm) */
    @Column(nullable = false)
    private LocalTime endTime;
}
