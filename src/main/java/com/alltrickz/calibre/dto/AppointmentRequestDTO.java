package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for handling appointment creation requests.
 * Contains validation annotations to ensure required fields are provided and properly formatted.
 */
@Data
public class AppointmentRequestDTO {

    /** ID of the calendar owner with whom the appointment is booked */
    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    /** Name of the invitee booking the appointment */
    @NotBlank(message = "Invitee Name is required")
    private String inviteeName;

    /** Email of the invitee booking the appointment */
    @NotBlank(message = "Invitee Email is required")
    @Email(message = "invitee Invalid email format")
    private String inviteeEmail;

    /** Date of the appointment */
    @NotNull(message = "Appointment Date is required")
    private LocalDate date;

    /** Start time of the appointment in HH:00 format */
    @NotNull(message = "Start time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    /** End time of the appointment in HH:00 format */
    @NotNull(message = "End time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;
}
