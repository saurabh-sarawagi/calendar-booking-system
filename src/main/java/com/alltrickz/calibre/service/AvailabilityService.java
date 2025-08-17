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
    private final OwnerRepository ownerRepository;

    @Transactional
    public List<AvailabilityWeeklyRuleResponseDTO> setWeeklyAvailability(Long ownerId, List<AvailabilityWeeklyRuleRequestDTO> availabilityWeeklyRuleRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
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

        // Ensure no duplicate days - although it'll be validated by db too
        Set<DayOfWeek> seenDays = new HashSet<>();
        for (AvailabilityWeeklyRuleRequestDTO dto : availabilityWeeklyRuleRequestDTO) {
            if (!seenDays.add(dto.getDayOfWeek())) {
                throw new Exception("Duplicate day found: " + dto.getDayOfWeek());
            }
        }

        availabilityWeeklyRuleRepository.deleteByOwnerId(ownerId);
        availabilityWeeklyRuleRepository.flush();

        List<AvailabilityWeeklyRule> toBeSaved = availabilityWeeklyRuleRequestDTO.stream().map(dto -> AvailabilityWeeklyRuleMapper.mapToEntity(dto, owner)).toList();
        return availabilityWeeklyRuleRepository.saveAll(toBeSaved).stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).toList();
    }

    public List<AvailabilityWeeklyRuleResponseDTO> getWeeklyAvailability(Long ownerId) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        return availabilityWeeklyRuleRepository.findByOwnerId(ownerId).stream().map(AvailabilityWeeklyRuleMapper::mapToResponse).collect(Collectors.toList());
    }

    public List<AvailabilityExceptionRuleResponseDTO> addExceptionRules(Long ownerId, List<AvailabilityExceptionRuleRequestDTO> availabilityExceptionRuleRequestDTO) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
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
