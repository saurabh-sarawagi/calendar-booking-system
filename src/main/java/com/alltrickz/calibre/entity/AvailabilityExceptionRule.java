package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing an exception to the regular weekly availability of an Owner.
 * Example use cases:
 * - Marking a specific date as unavailable (holiday, personal leave, etc.)
 * - Adding custom availability times for a specific date
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "date"}))
public class AvailabilityExceptionRule {

    /**
     * Primary key for AvailabilityExceptionRule.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The owner to whom this availability exception belongs.
     * Many exceptions can belong to one owner.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Owner owner;

    /**
     * The date on which this exception applies.
     * This is mandatory and combined with owner_id to enforce uniqueness.
     */
    @Column(nullable = false)
    private LocalDate date;

    /**
     * Optional start time for availability on this date.
     * Can be Null if isAvailable is false
     */
    private LocalTime startTime;

    /**
     * Optional end time for availability on this date.
     * Can be Null if isAvailable is false
     */
    private LocalTime endTime;

    /**
     * Whether the owner is available on this date/time range.
     * true = available, false = not available
     */
    @Column(nullable = false)
    private Boolean isAvailable;

    /**
     * Optional description for this exception rule.
     * Example: "Diwali Holiday", "Personal Leave", etc.
     */
    private String description;
}
