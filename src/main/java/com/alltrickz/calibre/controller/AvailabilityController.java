package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    // endpoint to be used to set or update the availability rule for the owner
    @PostMapping("/set/{ownerId}")
    public ResponseEntity<AvailabilityRule> setAvailability(@PathVariable Long ownerId, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime) throws Exception {
        return ResponseEntity.ok(availabilityService.setAvailability(ownerId, startTime, endTime));
    }

}
