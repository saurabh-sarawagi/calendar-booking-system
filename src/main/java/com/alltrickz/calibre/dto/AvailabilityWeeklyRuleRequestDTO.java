package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.DayOfWeek;

@Data
public class AvailabilityWeeklyRuleRequestDTO {

    @NotNull(message = "Day is required")
    private DayOfWeek dayOfWeek;

    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;

    @NotNull
    private Boolean isAvailable;
}
