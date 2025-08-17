package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentResponseDTOTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(11, 0);

        AppointmentResponseDTO dto = new AppointmentResponseDTO(
                1L, 2L, "Jane Doe", "jane@example.com", date, start, end
        );

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getOwnerId());
        assertEquals("Jane Doe", dto.getInviteeName());
        assertEquals("jane@example.com", dto.getInviteeEmail());
        assertEquals(date, dto.getDate());
        assertEquals(start, dto.getStartTime());
        assertEquals(end, dto.getEndTime());
    }

}
