package com.alltrickz.calibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AvailabilityResponseDTO {
    private Long id;
    private Long ownerId;
    private LocalTime startTime;
    private LocalTime endTime;
}
