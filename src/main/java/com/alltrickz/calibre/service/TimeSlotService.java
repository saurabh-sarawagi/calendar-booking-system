package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;

    public List<TimeSlotResponseDTO> getAvailableTimeSlots(Long ownerId, LocalDate date) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        AvailabilityRule availabilityRule = availabilityRepository.findByOwner(owner);

        if (ObjectUtils.isEmpty(availabilityRule)) {
            throw new Exception("Owner has not defined any Availability Rule.");
        }

        // Parse start and end time
        LocalTime start = availabilityRule.getStartTime();
        LocalTime end = availabilityRule.getEndTime();

        List<TimeSlotResponseDTO> slots = new ArrayList<>();

        // Generate hourly slots
        LocalTime current = start;
        while (!current.plusHours(1).isAfter(end)) {
            LocalTime slotEnd = current.plusHours(1);
            slots.add(new TimeSlotResponseDTO(current.toString(), slotEnd.toString()));
            current = slotEnd;
        }

        // todo filter out booked slots once appointment table exists
        return slots;
    }

}
