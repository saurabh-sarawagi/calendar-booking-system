package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.DayOfWeek;

/**
 * DTO for creating or updating weekly availability rules.
 * Example: "Every Monday, available from 09:00 to 17:00".
 */
@Data
public class AvailabilityWeeklyRuleRequestDTO {

    /**
     * Day of the week for the rule (e.g., MONDAY, TUESDAY).
     */
    @NotNull(message = "Day is required")
    private DayOfWeek dayOfWeek;

    /**
     * Start time of availability in format HH:00 (e.g., "09:00").
     */
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    /**
     * End time of availability in format HH:00 (e.g., "17:00").
     */
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;

    /**
     * Whether the owner is available on this day/time range.
     */
    @NotNull
    private Boolean isAvailable;
}
