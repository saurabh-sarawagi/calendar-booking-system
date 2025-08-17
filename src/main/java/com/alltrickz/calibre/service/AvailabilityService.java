package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityExceptionRuleRepository;
import com.alltrickz.calibre.dao.AvailabilityWeeklyRuleRepository;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import com.alltrickz.calibre.entity.Owner;
import com.alltrickz.calibre.mapper.AvailabilityExceptionRuleMapper;
import com.alltrickz.calibre.mapper.AvailabilityWeeklyRuleMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityWeeklyRuleRepository availabilityWeeklyRuleRepository;
    private final AvailabilityExceptionRuleRepository availabilityExceptionRuleRepository;
    private final OwnerService ownerService;

    /**
     * Sets the weekly availability rules for a given owner.
     * Validates time ranges and ensures no duplicate day entries.
     * Ignore the previous set availability and persist the new rules
     *
     * @param ownerId Owner ID
     * @param availabilityWeeklyRuleRequestDTO List of weekly rules
     * @return List of saved AvailabilityWeeklyRuleResponseDTO
     * @throws Exception for invalid input or duplicates
     */
    @Transactional
    public List<AvailabilityWeeklyRuleResponseDTO> setWeeklyAvailability(Long ownerId, List<AvailabilityWeeklyRuleRequestDTO> availabilityWeeklyRuleRequestDTO) throws Exception {
        // validate and fetch owner details
        Owner owner = ownerService.validateAndGetOwner(ownerId);

        // validating all passed weekly rules
        for(AvailabilityWeeklyRuleRequestDTO request : availabilityWeeklyRuleRequestDTO) {
            if (request.getIsAvailable()) {
                if (StringUtils.isEmpty(request.getStartTime()) || StringUtils.isEmpty(request.getEndTime())) {
                    throw new Exception("Start and end time cannot be empty for available rule");
                }

                if (request.getEndTime().compareTo(request.getStartTime()) <= 0) {
                    throw new Exception("End Time must be greater than Start Time");
                }
            }
        }

        // ensure no duplicate days - although it'll be validated by db too
        Set<DayOfWeek> seenDays = new HashSet<>();
        for (AvailabilityWeeklyRuleRequestDTO dto : availabilityWeeklyRuleRequestDTO) {
            if (!seenDays.add(dto.getDayOfWeek())) {
                throw new Exception("Duplicate day found: " + dto.getDayOfWeek());
            }
        }

        // remove existing rules immediately
        availabilityWeeklyRuleRepository.deleteByOwnerId(ownerId);
        availabilityWeeklyRuleRepository.flush();

        // map and save new rules
        List<AvailabilityWeeklyRule> toBeSaved = availabilityWeeklyRuleRequestDTO.stream().map(dto -> AvailabilityWeeklyRuleMapper.mapToEntity(dto, owner)).toList();
        return availabilityWeeklyRuleRepository.saveAll(toBeSaved).stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).toList();
    }

    /**
     * Fetches weekly availability rules for a given owner.
     *
     * @param ownerId Owner ID
     * @return List of AvailabilityWeeklyRuleResponseDTO
     * @throws Exception if owner not found
     */
    public List<AvailabilityWeeklyRuleResponseDTO> getWeeklyAvailability(Long ownerId) throws Exception {
        // validate owner
        ownerService.validateAndGetOwner(ownerId);
        return availabilityWeeklyRuleRepository.findByOwnerId(ownerId).stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Adds or updates exception rules for an owner.
     * Validates time ranges and prevents rules for past dates.
     *
     * @param ownerId Owner ID
     * @param availabilityExceptionRuleRequestDTO List of exception rules
     * @return List of saved AvailabilityExceptionRuleResponseDTO
     * @throws Exception for invalid inputs
     */
    public List<AvailabilityExceptionRuleResponseDTO> addExceptionRules(Long ownerId, List<AvailabilityExceptionRuleRequestDTO> availabilityExceptionRuleRequestDTO) throws Exception {
        // validate and fetch owner details
        Owner owner = ownerService.validateAndGetOwner(ownerId);

        // validating all passed exception rules
        for(AvailabilityExceptionRuleRequestDTO request : availabilityExceptionRuleRequestDTO) {
            if (request.getIsAvailable()) {
                if (StringUtils.isEmpty(request.getStartTime()) || StringUtils.isEmpty(request.getEndTime())) {
                    throw new Exception("Start and end time cannot be empty for available rule");
                }

                if (request.getEndTime().compareTo(request.getStartTime()) <= 0) {
                    throw new Exception("End Time must be greater than Start Time");
                }
            }

            if (request.getDate().isBefore(LocalDate.now())) {
                throw new Exception("Exceptions for past dates are not accepted.");
            }
        }

        List<AvailabilityExceptionRule> toBeSaved =  new ArrayList<>();
        for (AvailabilityExceptionRuleRequestDTO request : availabilityExceptionRuleRequestDTO) {
            // fetch existing exceptions - upsert logic
            AvailabilityExceptionRule availabilityExceptionRule = availabilityExceptionRuleRepository.findByOwnerIdAndDate(ownerId, request.getDate());
            if (availabilityExceptionRule != null) {
                AvailabilityExceptionRuleMapper.updateEntity(availabilityExceptionRule, request);
            } else {
                availabilityExceptionRule = AvailabilityExceptionRuleMapper.mapToEntity(request, owner);
            }
            toBeSaved.add(availabilityExceptionRule);
        }
        return availabilityExceptionRuleRepository.saveAll(toBeSaved).stream().map(AvailabilityExceptionRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Retrieves all exception rules for an owner.
     *
     * @param ownerId Owner ID
     * @return List of AvailabilityExceptionRuleResponseDTO
     * @throws Exception if owner not found
     */
    public List<AvailabilityExceptionRuleResponseDTO> getExceptionRules(Long ownerId) throws Exception {
        // validate owner details
        ownerService.validateAndGetOwner(ownerId);
        return availabilityExceptionRuleRepository.findByOwnerId(ownerId).stream().map(AvailabilityExceptionRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Deletes an exception rule by ID.
     *
     * @param id exception rule ID
     * @throws Exception if rule not found
     */
    public void deleteExceptionRules(Long id) throws Exception {
        // pre-check if exception rule exists
        if (!availabilityExceptionRuleRepository.existsById(id)) {
            throw new Exception("Owner Exception not found");
        }
        // delete exception
        availabilityExceptionRuleRepository.deleteById(id);
    }

    /** Helper methods to find rules directly and avoid repository dependency in other service class (used in internal logic) */
    public AvailabilityExceptionRule findExceptionRuleByOwnerAndDate(Long ownerId, LocalDate date) {
        return availabilityExceptionRuleRepository.findByOwnerIdAndDate(ownerId, date);
    }
    public AvailabilityWeeklyRule findWeeklyRuleByOwnerAndDate(Long ownerId, DayOfWeek dayOfWeek) {
        return availabilityWeeklyRuleRepository.findByOwnerIdAndDayOfWeek(ownerId, dayOfWeek);
    }
}
