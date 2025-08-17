package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

public class AvailabilityExceptionRuleMapper {

    public static AvailabilityExceptionRule mapToEntity(AvailabilityExceptionRuleRequestDTO availabilityExceptionRuleRequestDTO, Owner owner) {
        AvailabilityExceptionRule availabilityExceptionRule = new AvailabilityExceptionRule();
        availabilityExceptionRule.setOwner(owner);
        availabilityExceptionRule.setDate(availabilityExceptionRuleRequestDTO.getDate());
        availabilityExceptionRule.setStartTime(LocalTime.parse(availabilityExceptionRuleRequestDTO.getStartTime()));
        availabilityExceptionRule.setEndTime(LocalTime.parse(availabilityExceptionRuleRequestDTO.getEndTime()));
        availabilityExceptionRule.setIsActive(availabilityExceptionRuleRequestDTO.getIsActive());
        availabilityExceptionRule.setDescription(availabilityExceptionRuleRequestDTO.getDescription());
        return availabilityExceptionRule;
    }

    public static AvailabilityExceptionRuleResponseDTO mapToResponse(AvailabilityExceptionRule availabilityExceptionRule) {
        return new AvailabilityExceptionRuleResponseDTO(
                availabilityExceptionRule.getId(),
                availabilityExceptionRule.getOwner().getId(),
                availabilityExceptionRule.getDate(),
                availabilityExceptionRule.getStartTime(),
                availabilityExceptionRule.getEndTime(),
                availabilityExceptionRule.getIsActive(),
                availabilityExceptionRule.getDescription()
        );
    }

    public static AvailabilityExceptionRule updateEntity(AvailabilityExceptionRule entity, AvailabilityExceptionRuleRequestDTO availabilityExceptionRuleRequestDTO) {
        // owner should not change on update
        entity.setDate(availabilityExceptionRuleRequestDTO.getDate());
        entity.setStartTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getStartTime()) : null);
        entity.setEndTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getEndTime()) : null);
        entity.setIsActive(availabilityExceptionRuleRequestDTO.getIsActive());
        entity.setDescription(availabilityExceptionRuleRequestDTO.getDescription());
        return entity;
    }
}
