package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.AvailabilityRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.AvailabilityMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;

    public AvailabilityResponseDTO setAvailability(AvailabilityRequestDTO availabilityRequestDTO) throws Exception {
        if (availabilityRequestDTO.getEndTime().compareTo(availabilityRequestDTO.getStartTime()) <= 0) {
            throw new Exception("End Time must be greater than Start Time");
        }
        Owner owner = ownerRepository.findById(availabilityRequestDTO.getOwnerId()).orElseThrow(() -> new Exception("Owner Not Found"));
        AvailabilityRule availabilityRule = AvailabilityMapper.mapToEntity(availabilityRequestDTO, owner);
        AvailabilityRule availabilityRuleAfterSave = availabilityRepository.save(availabilityRule);
        return AvailabilityMapper.mapToResponse(availabilityRuleAfterSave);
    }

    public AvailabilityResponseDTO updateAvailability(AvailabilityRequestDTO availabilityRequestDTO) throws Exception {
        if (availabilityRequestDTO.getEndTime().compareTo(availabilityRequestDTO.getStartTime()) <= 0) {
            throw new Exception("End Time must be greater than Start Time");
        }
        Owner owner = ownerRepository.findById(availabilityRequestDTO.getOwnerId()).orElseThrow(() -> new Exception("Owner Not Found"));

        // checking if availability rule already exist for update
        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);
        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("No availability rule found for this owner to update.");
        }

        // only start and end time can be updated - owner won't be updated
        availabilityRule.setStartTime(LocalTime.parse(availabilityRequestDTO.getStartTime()));
        availabilityRule.setEndTime(LocalTime.parse(availabilityRequestDTO.getEndTime()));
        AvailabilityRule availabilityRuleAfterSave = availabilityRepository.save(availabilityRule);
        return AvailabilityMapper.mapToResponse(availabilityRuleAfterSave);

    }

}
