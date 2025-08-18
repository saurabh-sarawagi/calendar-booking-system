package com.alltrickz.calibre.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for sending availability exception rules back in responses.
 */
@Data
@AllArgsConstructor
public class AvailabilityExceptionRuleResponseDTO {

    /** Unique identifier of the rule */
    private Long id;

    /** Owner for whom this rule belongs */
    private Long ownerId;

    /** Specific date for the exception */
    private LocalDate date;

    /** Start time for this exception */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    /** End time for this exception */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    /** Whether available on this date */
    private Boolean isAvailable;

    /** Description for the exception (e.g., "Vacation") */
    private String description;
}
