package com.alltrickz.calibre.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeSlotResponseDTO {

    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime end;
}
