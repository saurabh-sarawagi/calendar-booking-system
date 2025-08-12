package com.alltrickz.calibre.controller;


import com.alltrickz.calibre.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    // endpoint to be used to set or update the availability rule for the owner
    @PostMapping("/set")
    public String setAvailability(@RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        return availabilityService.setAvailability(startTime, endTime);
    }

}
