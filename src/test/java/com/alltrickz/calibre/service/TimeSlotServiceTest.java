package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TimeSlotServiceTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private AvailabilityService availabilityService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private TimeSlotService timeSlotService;

    private Owner owner;
    private LocalDate today;

    @BeforeEach
    void setup() throws Exception {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");

        today = LocalDate.now();

        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
    }

    @Test
    void testAvailableSlots_NoAppointments() throws Exception {
        LocalDate tomorrow = today.plusDays(1);
        AvailabilityWeeklyRule weeklyRule = new AvailabilityWeeklyRule();
        weeklyRule.setOwner(owner);
        weeklyRule.setDayOfWeek(tomorrow.getDayOfWeek());
        weeklyRule.setIsAvailable(true);
        weeklyRule.setStartTime(LocalTime.of(9, 0));
        weeklyRule.setEndTime(LocalTime.of(12, 0));

        when(availabilityService.findExceptionRuleByOwnerAndDate(1L, tomorrow)).thenReturn(null);
        when(availabilityService.findWeeklyRuleByOwnerAndDate(1L, tomorrow.getDayOfWeek())).thenReturn(weeklyRule);
        when(appointmentRepository.findByOwnerIdAndDate(1L, tomorrow)).thenReturn(List.of());

        List<TimeSlotResponseDTO> slots = timeSlotService.getAvailableTimeSlots(1L, tomorrow);
        assertEquals(3, slots.size());
        assertEquals(LocalTime.of(9,0), slots.get(0).getStart());
        assertEquals(LocalTime.of(10,0), slots.get(0).getEnd());
    }

    @Test
    void testAvailableSlots_WithExceptionRuleUnavailable() throws Exception {
        AvailabilityExceptionRule exceptionRule = new AvailabilityExceptionRule();
        exceptionRule.setIsAvailable(false);

        when(availabilityService.findExceptionRuleByOwnerAndDate(1L, today)).thenReturn(exceptionRule);

        List<TimeSlotResponseDTO> slots = timeSlotService.getAvailableTimeSlots(1L, today);
        assertTrue(slots.isEmpty());
    }

    @Test
    void testAvailableSlots_WithExceptionRuleAvailable() throws Exception {
        LocalDate tomorrow = today.plusDays(1);
        AvailabilityExceptionRule exceptionRule = new AvailabilityExceptionRule();
        exceptionRule.setIsAvailable(true);
        exceptionRule.setStartTime(LocalTime.of(13, 0));
        exceptionRule.setEndTime(LocalTime.of(15, 0));

        when(availabilityService.findExceptionRuleByOwnerAndDate(1L, tomorrow)).thenReturn(exceptionRule);
        when(appointmentRepository.findByOwnerIdAndDate(1L, tomorrow)).thenReturn(List.of());

        List<TimeSlotResponseDTO> slots = timeSlotService.getAvailableTimeSlots(1L, tomorrow);
        assertEquals(2, slots.size());
        assertEquals(LocalTime.of(13,0), slots.get(0).getStart());
        assertEquals(LocalTime.of(14,0), slots.get(0).getEnd());
    }

    @Test
    void testAvailableSlots_PastDateReturnsEmpty() throws Exception {
        LocalDate pastDate = today.minusDays(1);
        List<TimeSlotResponseDTO> slots = timeSlotService.getAvailableTimeSlots(1L, pastDate);
        assertTrue(slots.isEmpty());
    }

    @Test
    void testAvailableSlots_RemovesBookedAppointments() throws Exception {
        LocalDate tomorrow = today.plusDays(1);
        AvailabilityWeeklyRule weeklyRule = new AvailabilityWeeklyRule();
        weeklyRule.setOwner(owner);
        weeklyRule.setDayOfWeek(tomorrow.getDayOfWeek());
        weeklyRule.setIsAvailable(true);
        weeklyRule.setStartTime(LocalTime.of(9, 0));
        weeklyRule.setEndTime(LocalTime.of(11, 0));

        Appointment booked = new Appointment();
        booked.setStartTime(LocalTime.of(9, 0));
        booked.setEndTime(LocalTime.of(10, 0));

        when(availabilityService.findExceptionRuleByOwnerAndDate(1L, tomorrow)).thenReturn(null);
        when(availabilityService.findWeeklyRuleByOwnerAndDate(1L, tomorrow.getDayOfWeek())).thenReturn(weeklyRule);
        when(appointmentRepository.findByOwnerIdAndDate(1L, tomorrow)).thenReturn(List.of(booked));

        List<TimeSlotResponseDTO> slots = timeSlotService.getAvailableTimeSlots(1L, tomorrow);
        assertEquals(1, slots.size());
        assertEquals(LocalTime.of(10, 0), slots.get(0).getStart());
        assertEquals(LocalTime.of(11, 0), slots.get(0).getEnd());
    }

}
