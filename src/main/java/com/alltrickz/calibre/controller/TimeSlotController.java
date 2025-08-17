package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.service.TimeSlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller exposing APIs to get available time slots
 */
@RestController
@RequestMapping("/api/timeslots")
@RequiredArgsConstructor
@Tag(name = "TimeSlot APIs", description = "Endpoint(s) for getting available time slots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    /**
     * Get all available time slots for a given owner and date.
     *
     * @param ownerId ID of the calendar owner
     * @param date the date to check availability
     * @return list of available time slots
     * @throws Exception if owner is invalid or service fails
     */
    @Operation(summary = "Get Available Time Slots", description = "Retrieves the list of all available hourly time slots for the given owner on a specific date.")
    @GetMapping("/get")
    public ResponseEntity<List<TimeSlotResponseDTO>> getSlots(@RequestParam Long ownerId, @RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(timeSlotService.getAvailableTimeSlots(ownerId, date));
    }

}
