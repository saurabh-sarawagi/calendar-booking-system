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

    public OwnerResponseDTO createOwner(OwnerRequestDTO ownerRequestDTO) {
        Owner owner = ownerRepository.save(OwnerMapper.mapToEntity(ownerRequestDTO));
        return OwnerMapper.mapToResponse(owner);
    }

    public OwnerResponseDTO updateOwner(Long id, OwnerRequestDTO ownerRequestDTO) throws Exception {
        Owner existingOwner = ownerRepository.findById(id).orElseThrow(() -> new Exception("Owner not found with id: " + id));
        OwnerMapper.updateEntity(existingOwner, ownerRequestDTO);
        Owner updatedOwner = ownerRepository.save(existingOwner);
        return OwnerMapper.mapToResponse(updatedOwner);
    }


    public List<OwnerResponseDTO> getAllOwners() {
        return ownerRepository.findAll().stream().map(OwnerMapper::mapToResponse).toList();
    }

    public OwnerResponseDTO getOwnerById(Long id) throws Exception {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new Exception("Owner not found with id: " + id));
        return OwnerMapper.mapToResponse(owner);
    }
}
