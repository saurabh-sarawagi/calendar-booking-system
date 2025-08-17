package com.alltrickz.calibre.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for sending appointment details back in responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    /** Unique identifier for the appointment */
    private Long id;

    /** ID of the calendar owner */
    private Long ownerId;

    /** Name of the invitee */
    private String inviteeName;

    /** Email of the invitee */
    private String inviteeEmail;

    /** Appointment date */
    private LocalDate date;

    /** Appointment start time */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    /** Appointment end time */
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
