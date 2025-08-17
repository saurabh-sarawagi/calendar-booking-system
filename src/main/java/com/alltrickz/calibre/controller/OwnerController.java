package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing APIs to manage owner details.
 */
@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
@Tag(name = "Owner APIs", description = "Endpoint(s) for creating, getting, updating owner details")
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Create a new owner.
     *
     * @param ownerRequestDTO request body with owner details
     * @return saved owner details
     */
    @Operation(summary = "Create Owner", description = "Creates a new owner with full name, email, and phone number.")
    @PostMapping("/new")
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody OwnerRequestDTO ownerRequestDTO) {
        return ResponseEntity.ok(ownerService.createOwner(ownerRequestDTO));
    }

    /**
     * Update an existing owner.
     *
     * @param id ID of the owner
     * @param ownerRequestDTO updated owner details
     * @return updated owner details
     * @throws Exception if owner not found
     */
    @Operation(summary = "Update Owner", description = "Updates owner details by ID.")
    @PutMapping("/update/{id}")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerRequestDTO ownerRequestDTO) throws Exception {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerRequestDTO));
    }

    /**
     * Get all owners.
     *
     * @return list of owners
     */
    @Operation(summary = "Get All Owners", description = "Retrieves a list of all owners.")
    @GetMapping("/get")
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    /**
     * Get an owner by ID.
     *
     * @param id ID of the owner
     * @return owner details
     * @throws Exception if owner not found
     */
    @Operation(summary = "Get Owner By ID", description = "Retrieves owner details by ID.")
    @GetMapping("get/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }
}
