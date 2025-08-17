package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.entity.Appointment;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentMapperTest {

    private AppointmentRequestDTO requestDTO;
    private Owner owner;

    @BeforeEach
    void setUp() {
        requestDTO = new AppointmentRequestDTO();
        requestDTO.setOwnerId(1L);
        requestDTO.setInviteeName("John Doe");
        requestDTO.setInviteeEmail("john@example.com");
        requestDTO.setDate(LocalDate.of(2025, 8, 17));
        requestDTO.setStartTime("10:00");
        requestDTO.setEndTime("11:00");

        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("Owner Name");
        owner.setEmail("owner@example.com");
        owner.setPhoneNumber("9876543210");
    }

    @Test
    void testMapToEntity() {
        Appointment appointment = AppointmentMapper.mapToEntity(requestDTO, owner);

        assertNotNull(appointment);
        assertEquals(owner, appointment.getOwner());
        assertEquals("John Doe", appointment.getInviteeName());
        assertEquals("john@example.com", appointment.getInviteeEmail());
        assertEquals(LocalDate.of(2025, 8, 17), appointment.getDate());
        assertEquals(LocalTime.of(10, 0), appointment.getStartTime());
        assertEquals(LocalTime.of(11, 0), appointment.getEndTime());
    }

    @Test
    void testMapToResponse() {
        Appointment appointment = AppointmentMapper.mapToEntity(requestDTO, owner);
        appointment.setId(100L);

        AppointmentResponseDTO responseDTO = AppointmentMapper.mapToResponse(appointment);

        assertNotNull(responseDTO);
        assertEquals(100L, responseDTO.getId());
        assertEquals(owner.getId(), responseDTO.getOwnerId());
        assertEquals("John Doe", responseDTO.getInviteeName());
        assertEquals("john@example.com", responseDTO.getInviteeEmail());
        assertEquals(LocalDate.of(2025, 8, 17), responseDTO.getDate());
        assertEquals(LocalTime.of(10, 0), responseDTO.getStartTime());
        assertEquals(LocalTime.of(11, 0), responseDTO.getEndTime());
    }

    @Test
    void testUpdateEntity() {
        Appointment appointment = new Appointment();
        appointment.setOwner(owner);

        AppointmentMapper.updateEntity(appointment, requestDTO);

        assertEquals(LocalDate.of(2025, 8, 17), appointment.getDate());
        assertEquals(LocalTime.of(10, 0), appointment.getStartTime());
        assertEquals(LocalTime.of(11, 0), appointment.getEndTime());
        assertEquals("John Doe", appointment.getInviteeName());
        assertEquals("john@example.com", appointment.getInviteeEmail());
        // owner should remain unchanged
        assertEquals(owner, appointment.getOwner());
    }

}
