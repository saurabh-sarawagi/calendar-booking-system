package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing APIs to manage Appointments.
 */
@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Tag(name = "Appointment APIs", description = "Endpoint(s) for booking, updating, cancelling and retrieving appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Book a new appointment for a given owner
     *
     * @param appointmentRequestDTO Request body containing appointment details.
     * @return The booked appointment details.
     * @throws Exception if validation fails or owner is not found or time slot already booked.
     */
    @Operation(summary = "Book an appointment", description = "Creates a new appointment for the provided owner and timeslot.")
    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDTO> bookAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentRequestDTO));
    }

    /**
     * Update an existing appointment.
     *
     * @param appointmentId         ID of the appointment to update.
     * @param appointmentRequestDTO New appointment details.
     * @return The updated appointment details.
     * @throws Exception if validation fails or appointment not found.
     */
    @Operation(summary = "Update an appointment", description = "Updates details of an existing appointment by ID.")
    @PatchMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long appointmentId, @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentId, appointmentRequestDTO));
    }

    /**
     * Cancel an existing appointment by its ID.
     *
     * @param id ID of the appointment to cancel.
     * @return Empty response with HTTP 204 status if successful.
     * @throws Exception if the appointment is not found.
     */
    @Operation(summary = "Cancel an appointment", description = "Deletes/cancels an appointment by ID.")
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) throws Exception {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve upcoming appointments for a given owner.
     *
     * @param ownerId ID of the owner whose appointments should be retrieved.
     * @param size    Maximum number of appointments to return (default 10).
     * @return List of upcoming appointments.
     * @throws Exception if the owner is not found.
     */
    @Operation(summary = "Get upcoming appointments", description = "Fetches a list of upcoming appointments for a given owner.")
    @GetMapping("/upcoming/{ownerId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getUpcomingAppointments(@PathVariable Long ownerId, @RequestParam(defaultValue = "10") int size) throws Exception {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments(ownerId, size));
    }

}
