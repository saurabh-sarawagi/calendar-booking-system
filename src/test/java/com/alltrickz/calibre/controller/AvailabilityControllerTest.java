package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.service.AvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilityControllerTest {

    @Mock
    private AvailabilityService availabilityService;

    @InjectMocks
    private AvailabilityController availabilityController;

    private List<AvailabilityWeeklyRuleRequestDTO> weeklyRequestDTO;
    private List<AvailabilityWeeklyRuleResponseDTO> weeklyResponseDTO;
    private List<AvailabilityExceptionRuleRequestDTO> exceptionRequestDTO;
    private List<AvailabilityExceptionRuleResponseDTO> exceptionResponseDTO;

    @BeforeEach
    void setup() {
        // Weekly DTOs
        AvailabilityWeeklyRuleRequestDTO weeklyReq = new AvailabilityWeeklyRuleRequestDTO();
        weeklyReq.setDayOfWeek(DayOfWeek.MONDAY);
        weeklyReq.setStartTime("09:00");
        weeklyReq.setEndTime("18:00");
        weeklyReq.setIsAvailable(true);
        weeklyRequestDTO = List.of(weeklyReq);

        AvailabilityWeeklyRuleResponseDTO weeklyResp = new AvailabilityWeeklyRuleResponseDTO(
                1L, 1L, DayOfWeek.MONDAY,
                LocalTime.of(9,0), LocalTime.of(18,0), true
        );
        weeklyResponseDTO = List.of(weeklyResp);

        // Exception DTOs
        AvailabilityExceptionRuleRequestDTO exReq = new AvailabilityExceptionRuleRequestDTO();
        exReq.setDate(LocalDate.now().plusDays(1));
        exReq.setStartTime("10:00");
        exReq.setEndTime("11:00");
        exReq.setIsAvailable(false);
        exReq.setDescription("Holiday");
        exceptionRequestDTO = List.of(exReq);

        AvailabilityExceptionRuleResponseDTO exResp = new AvailabilityExceptionRuleResponseDTO(
                1L, 1L, exReq.getDate(),
                LocalTime.of(10,0), LocalTime.of(11,0),
                false, "Holiday"
        );
        exceptionResponseDTO = List.of(exResp);
    }

    @Test
    void testSetWeeklyAvailability() throws Exception {
        when(availabilityService.setWeeklyAvailability(1L, weeklyRequestDTO)).thenReturn(weeklyResponseDTO);

        ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> response = availabilityController.setWeeklyAvailability(1L, weeklyRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(DayOfWeek.MONDAY, response.getBody().get(0).getDayOfWeek());
    }

    @Test
    void testGetWeeklyAvailability() throws Exception {
        when(availabilityService.getWeeklyAvailability(1L)).thenReturn(weeklyResponseDTO);

        ResponseEntity<List<AvailabilityWeeklyRuleResponseDTO>> response = availabilityController.getWeeklyAvailability(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testAddExceptionRules() throws Exception {
        when(availabilityService.addExceptionRules(1L, exceptionRequestDTO)).thenReturn(exceptionResponseDTO);

        ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> response = availabilityController.addExceptionRules(1L, exceptionRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Holiday", response.getBody().get(0).getDescription());
    }

    @Test
    void testGetExceptionRules() throws Exception {
        when(availabilityService.getExceptionRules(1L)).thenReturn(exceptionResponseDTO);

        ResponseEntity<List<AvailabilityExceptionRuleResponseDTO>> response = availabilityController.getExceptionRules(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testDeleteExceptionRule() throws Exception {
        doNothing().when(availabilityService).deleteExceptionRules(1L);

        ResponseEntity<Void> response = availabilityController.editExceptionRule(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
