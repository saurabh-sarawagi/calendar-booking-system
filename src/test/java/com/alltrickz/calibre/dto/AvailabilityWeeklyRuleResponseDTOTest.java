package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvailabilityWeeklyRuleResponseDTOTest {

    @Test
    void testResponseDtoCreation() {
        AvailabilityWeeklyRuleResponseDTO dto = new AvailabilityWeeklyRuleResponseDTO(
                1L,
                100L,
                DayOfWeek.MONDAY,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                true
        );

        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getOwnerId());
        assertEquals(DayOfWeek.MONDAY, dto.getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), dto.getStartTime());
        assertEquals(LocalTime.of(17, 0), dto.getEndTime());
        assertTrue(dto.getIsAvailable());
    }

}
