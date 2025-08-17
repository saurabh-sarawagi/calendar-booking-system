package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for creating exception rules (specific dates when availability differs).
 * Example: "On 2025-08-20, unavailable all day".
 */
@Data
public class AvailabilityExceptionRuleRequestDTO {

    /** Specific date for the exception rule */
    @NotNull(message = "Exception Date is required")
    private LocalDate date;

    /** Start time for this exception (HH:00 format) */
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    /** End time for this exception (HH:00 format) */
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;

    /** Whether available on this date */
    @NotNull
    private Boolean isAvailable;

    /** Optional description for the exception (e.g., "Public Holiday") */
    private String description;
}
