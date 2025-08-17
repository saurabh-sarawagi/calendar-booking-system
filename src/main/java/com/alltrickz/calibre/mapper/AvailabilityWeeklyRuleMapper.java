package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

public class AvailabilityWeeklyRuleMapper {

    public static AvailabilityWeeklyRule mapToEntity(AvailabilityWeeklyRuleRequestDTO availabilityWeeklyRuleRequestDTO, Owner owner) {
        AvailabilityWeeklyRule availabilityWeeklyRule = new AvailabilityWeeklyRule();
        availabilityWeeklyRule.setOwner(owner);
        availabilityWeeklyRule.setDayOfWeek(availabilityWeeklyRuleRequestDTO.getDayOfWeek());
        availabilityWeeklyRule.setStartTime(LocalTime.parse(availabilityWeeklyRuleRequestDTO.getStartTime()));
        availabilityWeeklyRule.setEndTime(LocalTime.parse(availabilityWeeklyRuleRequestDTO.getEndTime()));
        availabilityWeeklyRule.setIsActive(availabilityWeeklyRuleRequestDTO.getIsActive());
        return availabilityWeeklyRule;
    }

    public static AvailabilityWeeklyRuleResponseDTO mapToResponse(AvailabilityWeeklyRule availabilityWeeklyRule) {
        return new AvailabilityWeeklyRuleResponseDTO(
                availabilityWeeklyRule.getId(),
                availabilityWeeklyRule.getOwner().getId(),
                availabilityWeeklyRule.getDayOfWeek(),
                availabilityWeeklyRule.getStartTime(),
                availabilityWeeklyRule.getEndTime(),
                availabilityWeeklyRule.getIsActive()
        );
    }

    public static AvailabilityWeeklyRule updateEntity(AvailabilityWeeklyRule entity, AvailabilityWeeklyRuleRequestDTO dto) {
        // owner should not change on update
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime() != null ? LocalTime.parse(dto.getStartTime()) : null);
        entity.setEndTime(dto.getEndTime() != null ? LocalTime.parse(dto.getEndTime()) : null);
        entity.setIsActive(dto.getIsActive());
        return entity;
    }
}
