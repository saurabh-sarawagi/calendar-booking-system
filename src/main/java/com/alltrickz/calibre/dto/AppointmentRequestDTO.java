package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentRequestDTO {

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotBlank(message = "Invitee Name is required")
    private String inviteeName;

    @NotBlank(message = "Invitee Email is required")
    @Email(message = "invitee Invalid email format")
    private String inviteeEmail;

    @NotNull(message = "Appointment Date is required")
    private LocalDate date;

    @NotNull(message = "Start time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "Start time must be in the format HH:00 (e.g., 10:00)")
    private String startTime;

    @NotNull(message = "End time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):00$", message = "End time must be in the format HH:00 (e.g., 17:00)")
    private String endTime;
}
