package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    /**
     * Creates a new owner in the system.
     *
     * @param ownerRequestDTO Owner request DTO containing fullName, email, phoneNumber
     * @return OwnerResponseDTO with generated ID and details
     */
    public OwnerResponseDTO createOwner(OwnerRequestDTO ownerRequestDTO) {
        // create owner entity for save
        Owner owner = ownerRepository.save(OwnerMapper.mapToEntity(ownerRequestDTO));
        return OwnerMapper.mapToResponse(owner);
    }

    /**
     * Updates an existing owner by ID.
     *
     * @param id Owner ID
     * @param ownerRequestDTO Updated owner details
     * @return Updated OwnerResponseDTO
     * @throws Exception if owner not found
     */
    public OwnerResponseDTO updateOwner(Long id, OwnerRequestDTO ownerRequestDTO) throws Exception {
        // fetch existing owner from DB
        Owner existingOwner = ownerRepository.findById(id).orElseThrow(() -> new Exception("Owner not found with id: " + id));

        // update existing owner entity with received DTO fields
        OwnerMapper.updateEntity(existingOwner, ownerRequestDTO);

        // updating owner details in db
        Owner updatedOwner = ownerRepository.save(existingOwner);
        return OwnerMapper.mapToResponse(updatedOwner);
    }

    /**
     * Retrieves all owners.
     *
     * @return List of OwnerResponseDTO
     */
    public List<OwnerResponseDTO> getAllOwners() {
        // fetch all owners from DB
        return ownerRepository.findAll().stream().map(OwnerMapper::mapToResponse).toList();
    }

    /**
     * Retrieves a single owner by ID.
     *
     * @param id Owner ID
     * @return OwnerResponseDTO
     * @throws Exception if owner not found
     */
    public OwnerResponseDTO getOwnerById(Long id) throws Exception {
        // fetch owner by id and map to DTO
        return OwnerMapper.mapToResponse(validateAndGetOwner(id));
    }

    /**
     * Validates that an owner exists and returns the Owner entity.
     *
     * @param id Owner ID
     * @return Owner entity
     * @throws Exception if owner not found
     */
    public Owner validateAndGetOwner(Long id) throws Exception {
        // fetch owner entity by id
        return ownerRepository.findById(id).orElseThrow(() -> new Exception("Owner not found with id: " + id));
    }
}
