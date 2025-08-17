package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityExceptionRuleResponseDTOTest {

    @Test
    void testResponseDtoCreation() {
        AvailabilityExceptionRuleResponseDTO dto = new AvailabilityExceptionRuleResponseDTO(
                10L,
                200L,
                LocalDate.of(2025, 8, 20),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                false,
                "Holiday"
        );

        assertEquals(10L, dto.getId());
        assertEquals(200L, dto.getOwnerId());
        assertEquals(LocalDate.of(2025, 8, 20), dto.getDate());
        assertEquals(LocalTime.of(10, 0), dto.getStartTime());
        assertEquals(LocalTime.of(12, 0), dto.getEndTime());
        assertFalse(dto.getIsAvailable());
        assertEquals("Holiday", dto.getDescription());
    }

}
