package com.alltrickz.calibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private Long ownerId;
    private String inviteeName;
    private String inviteeEmail;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
