package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AvailabilityRequestDTO {

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotNull(message = "Start time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    @NotNull(message = "End time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;
}
