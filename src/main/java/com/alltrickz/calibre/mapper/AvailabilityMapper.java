package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

public class AvailabilityMapper {

    public static AvailabilityRule mapToEntity(AvailabilityRequestDTO availabilityRequestDTO, Owner owner) {
        AvailabilityRule availabilityRule = new AvailabilityRule();
        availabilityRule.setOwner(owner);
        availabilityRule.setStartTime(LocalTime.parse(availabilityRequestDTO.getStartTime()));
        availabilityRule.setEndTime(LocalTime.parse(availabilityRequestDTO.getEndTime()));
        return availabilityRule;
    }

    public static AvailabilityResponseDTO mapToResponse(AvailabilityRule availabilityRule) {
        return new AvailabilityResponseDTO(
                availabilityRule.getId(),
                availabilityRule.getOwner().getId(),
                availabilityRule.getStartTime(),
                availabilityRule.getEndTime()
        );
    }

}
