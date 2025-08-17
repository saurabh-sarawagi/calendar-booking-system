package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

/**
 * Mapper class for converting between AvailabilityWeeklyRule entity and DTOs.
 * All methods are static and stateless.
 */
public class AvailabilityWeeklyRuleMapper {

    /**
     * Converts AvailabilityWeeklyRuleRequestDTO to AvailabilityWeeklyRule entity.
     *
     * @param availabilityWeeklyRuleRequestDTO   the request DTO
     * @param owner the owner entity
     * @return mapped AvailabilityWeeklyRule entity
     */
    public static AvailabilityWeeklyRule mapToEntity(AvailabilityWeeklyRuleRequestDTO availabilityWeeklyRuleRequestDTO, Owner owner) {
        AvailabilityWeeklyRule availabilityWeeklyRule = new AvailabilityWeeklyRule();
        availabilityWeeklyRule.setOwner(owner);
        availabilityWeeklyRule.setDayOfWeek(availabilityWeeklyRuleRequestDTO.getDayOfWeek());
        availabilityWeeklyRule.setStartTime(availabilityWeeklyRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityWeeklyRuleRequestDTO.getStartTime()) : null);
        availabilityWeeklyRule.setEndTime(availabilityWeeklyRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityWeeklyRuleRequestDTO.getEndTime()) : null);
        availabilityWeeklyRule.setIsAvailable(availabilityWeeklyRuleRequestDTO.getIsAvailable());
        return availabilityWeeklyRule;
    }

    /**
     * Converts AvailabilityWeeklyRule entity to AvailabilityWeeklyRuleResponseDTO.
     *
     * @param availabilityWeeklyRule the entity
     * @return mapped response DTO
     */
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
