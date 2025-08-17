package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dao.AvailabilityRepository;
import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.AvailabilityRule;
import com.alltrickz.calibre.entity.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final AvailabilityRepository availabilityRepository;
    private final OwnerRepository ownerRepository;
    private final AppointmentRepository appointmentRepository;

    public List<TimeSlotResponseDTO> getAvailableTimeSlots(Long ownerId, LocalDate date) throws Exception {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new Exception("Owner Not Found"));
        Optional<AvailabilityRule> availabilityRuleOptional = availabilityRepository.findByOwner(owner);

        if (availabilityRuleOptional.isEmpty()) {
            return new ArrayList<>();
        }

        // If the requested date is in the past, return empty list
        if (date.isBefore(LocalDate.now())) {
            return new ArrayList<>();
        }

        // Parse start and end time
        LocalTime start = availabilityRuleOptional.get().getStartTime();
        LocalTime end = availabilityRuleOptional.get().getEndTime();

        List<TimeSlotResponseDTO> allSlots = new ArrayList<>();

        // Generate hourly slots
        LocalTime current = start;
        while (!current.plusHours(1).isAfter(end) && !current.plusHours(1).equals(LocalTime.MIDNIGHT)) {
            LocalTime slotEnd = current.plusHours(1);
            if (date.isEqual(LocalDate.now()) && current.isBefore(LocalTime.now())) {
                current = slotEnd;
                continue;
            }
            allSlots.add(new TimeSlotResponseDTO(current, slotEnd));
            current = slotEnd;
        }

        List<Appointment> appointments = appointmentRepository.findByOwnerIdAndDate(ownerId, date);
        return allSlots.stream().filter(slot -> appointments.stream().noneMatch(booking -> booking.getStartTime().equals(slot.getStart()) && booking.getEndTime().equals(slot.getEnd()))).toList();
    }

}
