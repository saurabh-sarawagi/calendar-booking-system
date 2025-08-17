package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.entity.Owner;

public class OwnerMapper {
    public static Owner mapToEntity(OwnerRequestDTO ownerRequestDTO) {
        Owner owner = new Owner();
        owner.setFullName(ownerRequestDTO.getFullName());
        owner.setEmail(ownerRequestDTO.getEmail());
        owner.setPhoneNumber(ownerRequestDTO.getPhoneNumber());
        return owner;
    }

    public static OwnerResponseDTO mapToResponse(Owner owner) {
        return new OwnerResponseDTO(
                owner.getId(),
                owner.getFullName(),
                owner.getEmail(),
                owner.getPhoneNumber()
        );
    }

    public static void updateEntity(Owner owner, OwnerRequestDTO ownerRequestDTO) {
        owner.setFullName(ownerRequestDTO.getFullName());
        owner.setEmail(ownerRequestDTO.getEmail());
        owner.setPhoneNumber(ownerRequestDTO.getPhoneNumber());
    }
}
