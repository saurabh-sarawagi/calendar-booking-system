package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.*;
import com.alltrickz.calibre.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/weekly/set/{ownerId}")
    public ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> setWeeklyAvailability(@PathVariable Long ownerId, @Valid @RequestBody List<AvailabilityWeeklyRuleRequestDTO> availabilityWeeklyRuleRequestDTO) throws Exception {
        return ResponseEntity.ok(availabilityService.setWeeklyAvailability(ownerId, availabilityWeeklyRuleRequestDTO));
    }

    @GetMapping("/weekly/{ownerId}")
    public ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> getWeeklyAvailability(@PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(availabilityService.getWeeklyAvailability(ownerId));
    }

    @PostMapping("/exception/add/{ownerId}")
    public ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> addExceptionRules(@PathVariable Long ownerId, @Valid @RequestBody List<AvailabilityExceptionRuleRequestDTO> availabilityExceptionRuleRequestDTO) throws Exception {
        return ResponseEntity.ok(availabilityService.addExceptionRules(ownerId, availabilityExceptionRuleRequestDTO));
    }

    @GetMapping("/exception/{ownerId}")
    public ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> getExceptionRules(@PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(availabilityService.getExceptionRules(ownerId));
    }

    @DeleteMapping("/exception/delete/{id}")
    public ResponseEntity<Void> editExceptionRule(@PathVariable Long id) throws Exception {
        availabilityService.deleteExceptionRules(id);
        return ResponseEntity.noContent().build();
    }
}
