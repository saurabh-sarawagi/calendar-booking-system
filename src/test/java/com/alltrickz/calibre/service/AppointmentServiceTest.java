package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AppointmentRepository;
import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private OwnerService ownerService;

    @Mock
    private TimeSlotService timeSlotService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Owner owner;
    private AppointmentRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john@example.com");
        owner.setPhoneNumber("1234567890");

        requestDTO = new AppointmentRequestDTO();
        requestDTO.setOwnerId(1L);
        requestDTO.setInviteeName("Alice");
        requestDTO.setInviteeEmail("alice@example.com");
        requestDTO.setDate(LocalDate.now().plusDays(1));
        requestDTO.setStartTime("10:00");
        requestDTO.setEndTime("11:00");
    }

    @Test
    void testBookAppointmentSuccess() throws Exception {
        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
        when(timeSlotService.getAvailableTimeSlots(eq(1L), any())).thenReturn(List.of(new TimeSlotResponseDTO(LocalTime.of(10,0), LocalTime.of(11,0))));
        when(appointmentRepository.saveAndFlush(any(Appointment.class))).thenAnswer(i -> {
            Appointment a = i.getArgument(0);
            a.setId(1L);
            return a;
        });

        AppointmentResponseDTO response = appointmentService.bookAppointment(requestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getInviteeName());
    }

    @Test
    void testBookAppointmentSlotUnavailable() throws Exception {
        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
        when(timeSlotService.getAvailableTimeSlots(eq(1L), any())).thenReturn(List.of());

        Exception ex = assertThrows(Exception.class, () -> appointmentService.bookAppointment(requestDTO));
        assertEquals("This Timeslot is not available for booking appointment.", ex.getMessage());
    }

    @Test
    void testUpdateAppointmentSuccess() throws Exception {
        Appointment existing = new Appointment();
        existing.setId(1L);
        existing.setOwner(owner);
        existing.setDate(LocalDate.now().plusDays(1));
        existing.setStartTime(LocalTime.of(9,0));
        existing.setEndTime(LocalTime.of(10,0));
        existing.setInviteeName("Old");
        existing.setInviteeEmail("old@example.com");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
        when(timeSlotService.getAvailableTimeSlots(eq(1L), any())).thenReturn(List.of(new TimeSlotResponseDTO(LocalTime.of(10,0), LocalTime.of(11,0))));
        when(appointmentRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        AppointmentResponseDTO updated = appointmentService.updateAppointment(1L, requestDTO);

        assertEquals("Alice", updated.getInviteeName());
        assertEquals(LocalTime.of(10,0), updated.getStartTime());
    }

    @Test
    void testDeleteAppointmentSuccess() throws Exception {
        Appointment existing = new Appointment();
        existing.setId(1L);
        existing.setOwner(owner);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(appointmentRepository).delete(existing);

        assertDoesNotThrow(() -> appointmentService.deleteAppointment(1L));
    }

    @Test
    void testGetUpcomingAppointments() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setOwner(owner);
        appointment.setDate(LocalDate.now().plusDays(1));
        appointment.setStartTime(LocalTime.of(10,0));
        appointment.setEndTime(LocalTime.of(11,0));
        appointment.setInviteeName("Alice");
        appointment.setInviteeEmail("alice@example.com");

        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
        when(appointmentRepository.findUpcomingAppointments(eq(1L), any(), any(), any())).thenReturn(List.of(appointment));

        List<AppointmentResponseDTO> result = appointmentService.getUpcomingAppointments(1L, 10);
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getInviteeName());
    }

}
