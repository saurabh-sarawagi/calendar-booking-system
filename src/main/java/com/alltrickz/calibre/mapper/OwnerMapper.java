package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.entity.Owner;

/**
 * Mapper class for converting between Owner entity and DTOs.
 * All methods are static and stateless.
 */
public class OwnerMapper {

    /**
     * Converts OwnerRequestDTO to Owner entity.
     *
     * @param ownerRequestDTO the request DTO
     * @return mapped Owner entity
     */
    public static Owner mapToEntity(OwnerRequestDTO ownerRequestDTO) {
        Owner owner = new Owner();
        owner.setFullName(ownerRequestDTO.getFullName());
        owner.setEmail(ownerRequestDTO.getEmail());
        owner.setPhoneNumber(ownerRequestDTO.getPhoneNumber());
        return owner;
    }

    /**
     * Converts Owner entity to OwnerResponseDTO.
     *
     * @param owner the entity
     * @return mapped response DTO
     */
    public static OwnerResponseDTO mapToResponse(Owner owner) {
        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFullName(),
                owner.getEmail(),
                owner.getPhoneNumber()
        );
    }

    /**
     * Updates an existing Owner entity using OwnerRequestDTO.
     *
     * @param owner the existing entity
     * @param ownerRequestDTO   the request DTO
     */
    public static void updateEntity(Owner owner, OwnerRequestDTO ownerRequestDTO) {
        owner.setFullName(ownerRequestDTO.getFullName());
        owner.setEmail(ownerRequestDTO.getEmail());
        owner.setPhoneNumber(ownerRequestDTO.getPhoneNumber());
    }
}
