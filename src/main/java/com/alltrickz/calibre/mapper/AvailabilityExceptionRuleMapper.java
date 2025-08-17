package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.Owner;

import java.time.LocalTime;

/**
 * Mapper class for converting between AvailabilityExceptionRule entity and DTOs.
 * All methods are static and stateless.
 */
public class AvailabilityExceptionRuleMapper {

    /**
     * Converts AvailabilityExceptionRuleRequestDTO to AvailabilityExceptionRule entity.
     *
     * @param availabilityExceptionRuleRequestDTO   the request DTO
     * @param owner the owner entity
     * @return mapped AvailabilityExceptionRule entity
     */
    public static AvailabilityExceptionRule mapToEntity(AvailabilityExceptionRuleRequestDTO availabilityExceptionRuleRequestDTO, Owner owner) {
        AvailabilityExceptionRule availabilityExceptionRule = new AvailabilityExceptionRule();
        availabilityExceptionRule.setOwner(owner);
        availabilityExceptionRule.setDate(availabilityExceptionRuleRequestDTO.getDate());
        availabilityExceptionRule.setStartTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getStartTime()) : null);
        availabilityExceptionRule.setEndTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getEndTime()) : null);
        availabilityExceptionRule.setIsAvailable(availabilityExceptionRuleRequestDTO.getIsAvailable());
        availabilityExceptionRule.setDescription(availabilityExceptionRuleRequestDTO.getDescription());
        return availabilityExceptionRule;
    }

    /**
     * Converts AvailabilityExceptionRule entity to AvailabilityExceptionRuleResponseDTO.
     *
     * @param availabilityExceptionRule the entity
     * @return mapped response DTO
     */
    public static AvailabilityExceptionRuleResponseDTO mapToResponse(AvailabilityExceptionRule availabilityExceptionRule) {
        return new AvailabilityExceptionRuleResponseDTO(
                availabilityExceptionRule.getId(),
                availabilityExceptionRule.getOwner().getId(),
                availabilityExceptionRule.getDate(),
                availabilityExceptionRule.getStartTime(),
                availabilityExceptionRule.getEndTime(),
                availabilityExceptionRule.getIsAvailable(),
                availabilityExceptionRule.getDescription()
        );
    }

    /**
     * Updates an existing AvailabilityExceptionRule entity from DTO values.
     * The owner is not updated.
     *
     * @param availabilityExceptionRule the entity to update
     * @param availabilityExceptionRuleRequestDTO    the DTO containing new values
     */
    public static void updateEntity(AvailabilityExceptionRule availabilityExceptionRule, AvailabilityExceptionRuleRequestDTO availabilityExceptionRuleRequestDTO) {
        // owner should not change on update
        availabilityExceptionRule.setDate(availabilityExceptionRuleRequestDTO.getDate());
        availabilityExceptionRule.setStartTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getStartTime()) : null);
        availabilityExceptionRule.setEndTime(availabilityExceptionRuleRequestDTO.getStartTime() != null ? LocalTime.parse(availabilityExceptionRuleRequestDTO.getEndTime()) : null);
        availabilityExceptionRule.setIsAvailable(availabilityExceptionRuleRequestDTO.getIsAvailable());
        availabilityExceptionRule.setDescription(availabilityExceptionRuleRequestDTO.getDescription());
    }
}
