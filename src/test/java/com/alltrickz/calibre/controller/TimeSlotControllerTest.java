package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.TimeSlotResponseDTO;
import com.alltrickz.calibre.service.TimeSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TimeSlotControllerTest {

    @Mock
    private TimeSlotService timeSlotService;

    @InjectMocks
    private TimeSlotController timeSlotController;

    private List<TimeSlotResponseDTO> timeSlots;

    @BeforeEach
    void setup() {
        timeSlots = List.of(
                new TimeSlotResponseDTO(LocalTime.of(9, 0), LocalTime.of(10, 0)),
                new TimeSlotResponseDTO(LocalTime.of(10, 0), LocalTime.of(11, 0))
        );
    }

    @Test
    void testGetSlots() throws Exception {
        when(timeSlotService.getAvailableTimeSlots(1L, LocalDate.of(2025, 8, 20)))
                .thenReturn(timeSlots);

        ResponseEntity<List<TimeSlotResponseDTO>> response = timeSlotController.getSlots(1L, LocalDate.of(2025, 8, 20));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(LocalTime.of(9, 0), response.getBody().get(0).getStart());
    }

    @Test
    void testGetSlots_EmptyList() throws Exception {
        when(timeSlotService.getAvailableTimeSlots(2L, LocalDate.of(2025, 8, 21)))
                .thenReturn(new ArrayList<>());

        ResponseEntity<List<TimeSlotResponseDTO>> response = timeSlotController.getSlots(2L, LocalDate.of(2025, 8, 21));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetSlots_Exception() throws Exception {
        when(timeSlotService.getAvailableTimeSlots(3L, LocalDate.of(2025, 8, 22)))
                .thenThrow(new Exception("Owner not found"));

        Exception exception = assertThrows(Exception.class, () ->
                timeSlotController.getSlots(3L, LocalDate.of(2025, 8, 22)));

        assertEquals("Owner not found", exception.getMessage());
    }

}
