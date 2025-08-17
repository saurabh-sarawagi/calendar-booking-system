package com.alltrickz.calibre.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    @Test
    void testAppointmentEntityFields() {
        // Arrange
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPhoneNumber("9876543210");

        Appointment appointment = new Appointment();
        appointment.setId(100L);
        appointment.setOwner(owner);
        appointment.setInviteeName("Alice");
        appointment.setInviteeEmail("alice@example.com");
        appointment.setDate(LocalDate.of(2025, 8, 16));
        appointment.setStartTime(LocalTime.of(10, 0));
        appointment.setEndTime(LocalTime.of(11, 0));

        // Assert
        assertEquals(100L, appointment.getId());
        assertEquals(owner, appointment.getOwner());
        assertEquals("Alice", appointment.getInviteeName());
        assertEquals("alice@example.com", appointment.getInviteeEmail());
        assertEquals(LocalDate.of(2025, 8, 16), appointment.getDate());
        assertEquals(LocalTime.of(10, 0), appointment.getStartTime());
        assertEquals(LocalTime.of(11, 0), appointment.getEndTime());
    }

    @Test
    void testAppointmentEqualsAndHashCode() {
        Owner owner = new Owner();
        owner.setId(1L);

        Appointment a1 = new Appointment();
        a1.setId(1L);
        a1.setOwner(owner);

        Appointment a2 = new Appointment();
        a2.setId(1L);
        a2.setOwner(owner);

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void testAppointmentToString() {
        Appointment appointment = new Appointment();
        appointment.setId(10L);
        appointment.setInviteeName("Bob");
        appointment.setInviteeEmail("bob@example.com");

        String str = appointment.toString();
        assertTrue(str.contains("Bob"));
        assertTrue(str.contains("bob@example.com"));
    }

}
