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
            if (request.getIsActive() && request.getEndTime().compareTo(request.getStartTime()) <= 0) {
                throw new Exception("End Time must be greater than Start Time");
            }
        }

        // fetch existing rules for the owner
        List<AvailabilityWeeklyRule> existingRules = availabilityWeeklyRuleRepository.findByOwnerId(ownerId);
        Map<DayOfWeek, AvailabilityWeeklyRule> existingWeeklyRuleMap = existingRules.stream().collect(Collectors.toMap(AvailabilityWeeklyRule::getDayOfWeek, rule->rule));

        Set<DayOfWeek> requestDays = availabilityWeeklyRuleRequestDTO.stream().map(AvailabilityWeeklyRuleRequestDTO::getDayOfWeek).collect(Collectors.toSet());
        List<AvailabilityWeeklyRule> toBeSaved = new ArrayList<>();

        for (AvailabilityWeeklyRuleRequestDTO availabilityWeeklyRuleDTO : availabilityWeeklyRuleRequestDTO) {
            AvailabilityWeeklyRule availabilityWeeklyRule;
            if (existingWeeklyRuleMap.containsKey(availabilityWeeklyRuleDTO.getDayOfWeek())) {
                availabilityWeeklyRule = AvailabilityWeeklyRuleMapper.updateEntity(existingWeeklyRuleMap.get(availabilityWeeklyRuleDTO.getDayOfWeek()), availabilityWeeklyRuleDTO);
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
            if (request.getIsActive() && request.getEndTime().compareTo(request.getStartTime()) <= 0) {
                throw new Exception("End Time must be greater than Start Time");
            }

            if (request.getDate().isBefore(LocalDate.now())) {
                throw new Exception("Exceptions for past dates are not accepted.");
            }
        }

        List<AvailabilityExceptionRule> toBeSaved =  new ArrayList<>();
        for (AvailabilityExceptionRuleRequestDTO request : availabilityExceptionRuleRequestDTO) {
            Optional<AvailabilityExceptionRule> existing = availabilityExceptionRuleRepository.findByOwnerIdAndDate(ownerId, request.getDate());
            AvailabilityExceptionRule entity = (existing.isPresent()) ? AvailabilityExceptionRuleMapper.updateEntity(existing.get(), request) : AvailabilityExceptionRuleMapper.mapToEntity(request, owner);
            toBeSaved.add(entity);
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
