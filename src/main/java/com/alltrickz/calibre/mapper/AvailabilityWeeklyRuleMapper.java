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
        availabilityWeeklyRule.setStartTime(availabilityWeeklyRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityWeeklyRuleRequestDTO.getStartTime()) : null);
        availabilityWeeklyRule.setEndTime(availabilityWeeklyRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityWeeklyRuleRequestDTO.getEndTime()) : null);
        availabilityWeeklyRule.setIsAvailable(availabilityWeeklyRuleRequestDTO.getIsAvailable());
        return availabilityWeeklyRule;
    }

    public static AvailabilityWeeklyRuleResponseDTO mapToResponse(AvailabilityWeeklyRule availabilityWeeklyRule) {
        return new AvailabilityWeeklyRuleResponseDTO(
                availabilityWeeklyRule.getId(),
                availabilityWeeklyRule.getOwner().getId(),
                availabilityWeeklyRule.getDayOfWeek(),
                availabilityWeeklyRule.getStartTime(),
                availabilityWeeklyRule.getEndTime(),
                availabilityWeeklyRule.getIsAvailable()
        );
    }
}
