package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing APIs to manage Availability.
 */
@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
@Tag(name = "Availability APIs", description = "Endpoint(s) for setting weekly and exception rules")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    /**
     * Set or update weekly availability rules for a given owner.
     * Example: Available on Mondays from 09:00 - 18:00
     *
     * @param ownerId ID of the calendar owner
     * @param availabilityWeeklyRuleRequestDTO list of weekly availability rules
     * @return list of saved weekly rules
     */
    @Operation(summary = "Set weekly availability", description = "Creates or updates weekly availability rules for the given owner.")
    @PostMapping("/weekly/set/{ownerId}")
    public ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> setWeeklyAvailability(@PathVariable Long ownerId, @Valid @RequestBody List<AvailabilityWeeklyRuleRequestDTO> availabilityWeeklyRuleRequestDTO) throws Exception {
        return ResponseEntity.ok(availabilityService.setWeeklyAvailability(ownerId, availabilityWeeklyRuleRequestDTO));
    }

    /**
     * Get weekly availability rules of the owner.
     *
     * @param ownerId ID of the calendar owner
     * @return list of weekly availability rules
     */
    @Operation(summary = "Get weekly availability", description = "Retrieves weekly availability rules for the given owner.")
    @GetMapping("/weekly/{ownerId}")
    public ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> getWeeklyAvailability(@PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(availabilityService.getWeeklyAvailability(ownerId));
    }

    /**
     * Add exception rules to override weekly availability.
     * Example: Mark 25th Dec as unavailable, even if it's a Monday.
     *
     * @param ownerId ID of the calendar owner
     * @param availabilityExceptionRuleRequestDTO list of exception rules
     * @return list of saved exception rules
     */
    @Operation(summary = "Add exception rules", description = "Adds exception rules (specific date overrides) for the given owner.")
    @PostMapping("/exception/add/{ownerId}")
    public ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> addExceptionRules(@PathVariable Long ownerId, @Valid @RequestBody List<AvailabilityExceptionRuleRequestDTO> availabilityExceptionRuleRequestDTO) throws Exception {
        return ResponseEntity.ok(availabilityService.addExceptionRules(ownerId, availabilityExceptionRuleRequestDTO));
    }

    /**
     * Get all exception rules for an owner.
     *
     * @param ownerId ID of the calendar owner
     * @return list of exception rules
     */
    @Operation(summary = "Add exception rules", description = "Adds exception rules (specific date overrides) for the given owner.")
    @GetMapping("/exception/{ownerId}")
    public ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> getExceptionRules(@PathVariable Long ownerId) throws Exception {
        return ResponseEntity.ok(availabilityService.getExceptionRules(ownerId));
    }

    /**
     * Delete an exception rule by its ID.
     *
     * @param id ID of the exception rule to delete
     * @return HTTP 204 No Content
     */
    @Operation(summary = "Delete exception rule", description = "Deletes an exception rule by its ID.")
    @DeleteMapping("/exception/delete/{id}")
    public ResponseEntity<Void> editExceptionRule(@PathVariable Long id) throws Exception {
        availabilityService.deleteExceptionRules(id);
        return ResponseEntity.noContent().build();
    }
}
