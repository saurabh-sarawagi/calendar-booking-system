package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityExceptionRuleRepository;
import com.alltrickz.calibre.dao.AvailabilityWeeklyRuleRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityWeeklyRuleRepository availabilityWeeklyRuleRepository;
    private final AvailabilityExceptionRuleRepository availabilityExceptionRuleRepository;
    private final OwnerRepository ownerRepository;

    @Transactional
    public List<AvailabilityWeeklyRuleResponseDTO> setWeeklyAvailability(Long ownerId, List<AvailabilityWeeklyRuleRequestDTO> availabilityWeeklyRuleRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        for(AvailabilityWeeklyRuleRequestDTO request : availabilityWeeklyRuleRequestDTO) {
            if (request.getIsActive()) {
                if (StringUtils.isEmpty(request.getStartTime()) || StringUtils.isEmpty(request.getEndTime())) {
                    throw new Exception("Start and end time cannot be empty for active rule");
                }

                if (request.getEndTime().compareTo(request.getStartTime()) <= 0) {
                    throw new Exception("End Time must be greater than Start Time");
                }
            }
        }

        // fetch existing rules for the owner
        List<AvailabilityWeeklyRule> existingRules = availabilityWeeklyRuleRepository.findByOwnerId(ownerId);
        Map<DayOfWeek, AvailabilityWeeklyRule> existingWeeklyRuleMap = existingRules.stream().collect(Collectors.toMap(AvailabilityWeeklyRule::getDayOfWeek, rule->rule));

        Set<DayOfWeek> requestDays = availabilityWeeklyRuleRequestDTO.stream().map(AvailabilityWeeklyRuleRequestDTO::getDayOfWeek).collect(Collectors.toSet());
        List<AvailabilityWeeklyRule> toBeSaved = new ArrayList<>();

        for (AvailabilityWeeklyRuleRequestDTO availabilityWeeklyRuleDTO : availabilityWeeklyRuleRequestDTO) {
            AvailabilityWeeklyRule availabilityWeeklyRule = existingWeeklyRuleMap.getOrDefault(availabilityWeeklyRuleDTO.getDayOfWeek(), new AvailabilityWeeklyRule());
            if (existingWeeklyRuleMap.containsKey(availabilityWeeklyRuleDTO.getDayOfWeek())) {
                AvailabilityWeeklyRuleMapper.updateEntity(availabilityWeeklyRule, availabilityWeeklyRuleDTO);
            } else {
                availabilityWeeklyRule = AvailabilityWeeklyRuleMapper.mapToEntity(availabilityWeeklyRuleDTO, owner);
            }
            toBeSaved.add(availabilityWeeklyRule);
        }

        List<AvailabilityWeeklyRule> toBeDeleted = existingRules.stream().filter(rule -> !requestDays.contains(rule.getDayOfWeek())).collect(Collectors.toList());
        if (!toBeDeleted.isEmpty()) {
            availabilityWeeklyRuleRepository.deleteAllInBatch(toBeDeleted);
        }

        List<AvailabilityWeeklyRule> saved = availabilityWeeklyRuleRepository.saveAll(toBeSaved);
        return saved.stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    public List<AvailabilityWeeklyRuleResponseDTO> getWeeklyAvailability(Long ownerId) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        return availabilityWeeklyRuleRepository.findByOwnerId(ownerId).stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    public List<AvailabilityExceptionRuleResponseDTO> addExceptionRules(Long ownerId, List<AvailabilityExceptionRuleRequestDTO> availabilityExceptionRuleRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        for(AvailabilityExceptionRuleRequestDTO request : availabilityExceptionRuleRequestDTO) {
            if (request.getIsActive()) {
                if (StringUtils.isEmpty(request.getStartTime()) || StringUtils.isEmpty(request.getEndTime())) {
                    throw new Exception("Start and end time cannot be empty for active rule");
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

    public List<AvailabilityExceptionRuleResponseDTO> getExceptionRules(Long ownerId) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        return availabilityExceptionRuleRepository.findByOwnerId(ownerId).stream().map(AvailabilityExceptionRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    public void deleteExceptionRules(Long id) throws Exception {
        if (!availabilityExceptionRuleRepository.existsById(id)) {
            throw new Exception("Owner Exception not found");
        }
        availabilityExceptionRuleRepository.deleteById(id);
    }

}
