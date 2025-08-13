package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TimeSlotRequestDTO {

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotNull(message = "Date is required")
    private LocalDate date;
}
