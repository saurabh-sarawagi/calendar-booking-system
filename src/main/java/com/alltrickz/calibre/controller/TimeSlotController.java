package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @GetMapping("/get")
    public ResponseEntity<List<TimeSlotResponseDTO>> getSlots(@RequestParam Long ownerId, @RequestParam LocalDate date) throws Exception {
        return ResponseEntity.ok(timeSlotService.getAvailableTimeSlots(ownerId, date));
    }

}
