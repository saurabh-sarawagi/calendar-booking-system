package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Entity representing a recurring weekly availability rule for an Owner.
 * Example use cases:
 * - Marking a particular weekday as available with specific time slots.
 * - Marking a weekday as completely unavailable.
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "day_of_week"}))
public class AvailabilityWeeklyRule {

    /**
     * Primary key for AvailabilityWeeklyRule.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The owner to whom this weekly availability rule belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Owner owner;

    /**
     * The day of the week this rule applies to (e.g., MONDAY, TUESDAY).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

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

}
