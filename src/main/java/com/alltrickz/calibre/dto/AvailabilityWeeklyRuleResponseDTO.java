package com.alltrickz.calibre.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * DTO returned after persisting or retrieving weekly availability rules.
 */
@Data
@AllArgsConstructor
public class AvailabilityWeeklyRuleResponseDTO {

    /** Unique identifier of the rule */
    private Long id;

    /** Owner for whom this rule belongs */
    private Long ownerId;

    /** Day of the week (e.g., MONDAY) */
    private DayOfWeek dayOfWeek;

    /** Start time of availability */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    /** End time of availability */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    /** Whether the owner is available */
    private Boolean isAvailable;
}
