package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDTO> bookAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentRequestDTO));
    }

    @PatchMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable Long appointmentId, @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws Exception {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentId, appointmentRequestDTO));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) throws Exception {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/upcoming/{ownerId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getUpcomingAppointments(@PathVariable Long ownerId, @RequestParam(defaultValue = "10") int size) throws Exception {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments(ownerId, size));
    }

}
