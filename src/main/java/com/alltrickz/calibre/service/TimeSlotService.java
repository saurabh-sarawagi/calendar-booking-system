package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final OwnerService ownerService;
    private final AvailabilityService availabilityService;
    private final AppointmentRepository appointmentRepository;

    /**
     * Returns available one-hour time slots for a given owner on a specific date.
     *
     * @param ownerId Owner ID
     * @param date Appointment date
     * @return List of available time slots
     * @throws Exception if owner not found
     */
    public List<TimeSlotResponseDTO> getAvailableTimeSlots(Long ownerId, LocalDate date) throws Exception {
        // validate owner details
        ownerService.validateAndGetOwner(ownerId);

        // if the requested date is in the past, return empty list
        if (date.isBefore(LocalDate.now())) {
            return new ArrayList<>();
        }

        LocalTime start;
        LocalTime end;

        // check for exception rules first as they take precedence
        AvailabilityExceptionRule exceptionRule = availabilityService.findExceptionRuleByOwnerAndDate(ownerId, date);
        if (exceptionRule != null) {
            if (!exceptionRule.getIsAvailable()) {
                // exception rule exists and state that owner is not available meaning that no available slots
                return new ArrayList<>();
            }
            start = exceptionRule.getStartTime();
            end = exceptionRule.getEndTime();
        } else {
            // if exception not available for selected date then regular weekly rules to be evaluated
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            AvailabilityWeeklyRule weeklyRule = availabilityService.findWeeklyRuleByOwnerAndDate(ownerId, dayOfWeek);
            if (weeklyRule == null || !weeklyRule.getIsAvailable()) {
                // weekly rule don't exist or rule exist but availability is set to false
                return new ArrayList<>();
            }
            start = weeklyRule.getStartTime();
            end = weeklyRule.getEndTime();
        }

        List<TimeSlotResponseDTO> allSlots = new ArrayList<>();

        // Generate hourly slots
        LocalTime current = start;
        while (!current.plusHours(1).isAfter(end) && !current.plusHours(1).equals(LocalTime.MIDNIGHT)) {
            LocalTime slotEnd = current.plusHours(1);

            // skip past slots for current day
            if (date.isEqual(LocalDate.now()) && current.isBefore(LocalTime.now())) {
                current = slotEnd;
                continue;
            }
            allSlots.add(new TimeSlotResponseDTO(current, slotEnd));
            current = slotEnd;
        }

        // fetch already booked appointments and filter those slots out
        List<Appointment> appointments = appointmentRepository.findByOwnerIdAndDate(ownerId, date);
        return allSlots.stream().filter(slot -> appointments.stream().noneMatch(booking -> booking.getStartTime().equals(slot.getStart()) && booking.getEndTime().equals(slot.getEnd()))).toList();
    }
}
