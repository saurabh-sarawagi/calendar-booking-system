package com.alltrickz.calibre.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

/**
 * DTO representing a single available timeslot for booking.
 */
@Data
@AllArgsConstructor
public class TimeSlotResponseDTO {

    /** Start time of the slot */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;

    /** End time of the slot */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime end;
}
