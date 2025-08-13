package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AvailabilityRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityResponseDTO;
import com.alltrickz.calibre.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PostMapping("/set")
    public ResponseEntity<AvailabilityResponseDTO> setAvailability(@Valid @RequestBody AvailabilityRequestDTO availabilityRequestDTO) throws Exception {
        return ResponseEntity.ok(availabilityService.setAvailability(availabilityRequestDTO));
    }

}
