package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeSlotResponseDTOTest {

    @Test
    void testConstructorAndGetters() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);

        TimeSlotResponseDTO dto = new TimeSlotResponseDTO(start, end);

        assertEquals(start, dto.getStart());
        assertEquals(end, dto.getEnd());
    }

}
