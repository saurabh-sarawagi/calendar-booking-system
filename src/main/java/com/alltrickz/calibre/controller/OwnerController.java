package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/new")
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody OwnerRequestDTO ownerRequestDTO) {
        return ResponseEntity.ok(ownerService.createOwner(ownerRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequestDTO ownerRequestDTO) throws Exception {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerRequestDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }
}
