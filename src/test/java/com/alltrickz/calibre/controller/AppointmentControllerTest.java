package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.AppointmentRequestDTO;
import com.alltrickz.calibre.dto.AppointmentResponseDTO;
import com.alltrickz.calibre.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    private AppointmentRequestDTO requestDTO;
    private AppointmentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new AppointmentRequestDTO();
        requestDTO.setOwnerId(1L);
        requestDTO.setDate(LocalDate.parse(LocalDate.now().toString()));
        requestDTO.setStartTime("10:00");
        requestDTO.setEndTime("11:00");

        responseDTO = new AppointmentResponseDTO();
        responseDTO.setId(100L);
        responseDTO.setOwnerId(1L);
        responseDTO.setDate(LocalDate.parse(LocalDate.now().toString()));
        responseDTO.setStartTime(LocalTime.parse("10:00"));
        responseDTO.setEndTime(LocalTime.parse("11:00"));
    }

    @Test
    void bookAppointment_Success() throws Exception {
        when(appointmentService.bookAppointment(any(AppointmentRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<AppointmentResponseDTO> response = appointmentController.bookAppointment(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(appointmentService, times(1)).bookAppointment(requestDTO);
    }

    @Test
    void bookAppointment_ServiceThrowsException() throws Exception {
        when(appointmentService.bookAppointment(any(AppointmentRequestDTO.class)))
                .thenThrow(new Exception("Booking failed"));

        Exception ex = assertThrows(Exception.class, () -> appointmentController.bookAppointment(requestDTO));
        assertEquals("Booking failed", ex.getMessage());
    }

    @Test
    void updateAppointment_Success() throws Exception {
        when(appointmentService.updateAppointment(anyLong(), any(AppointmentRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<AppointmentResponseDTO> response = appointmentController.updateAppointment(100L, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(appointmentService, times(1)).updateAppointment(100L, requestDTO);
    }

    @Test
    void updateAppointment_ServiceThrowsException() throws Exception {
        when(appointmentService.updateAppointment(anyLong(), any(AppointmentRequestDTO.class)))
                .thenThrow(new Exception("Update failed"));

        Exception ex = assertThrows(Exception.class, () -> appointmentController.updateAppointment(100L, requestDTO));
        assertEquals("Update failed", ex.getMessage());
    }

    @Test
    void deleteAppointment_Success() throws Exception {
        doNothing().when(appointmentService).deleteAppointment(100L);

        ResponseEntity<Void> response = appointmentController.deleteAppointment(100L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(appointmentService, times(1)).deleteAppointment(100L);
    }

    @Test
    void deleteAppointment_ServiceThrowsException() throws Exception {
        doThrow(new Exception("Delete failed")).when(appointmentService).deleteAppointment(100L);

        Exception ex = assertThrows(Exception.class, () -> appointmentController.deleteAppointment(100L));
        assertEquals("Delete failed", ex.getMessage());
    }

    @Test
    void getUpcomingAppointments_Success() throws Exception {
        List<AppointmentResponseDTO> responseList = Arrays.asList(responseDTO);
        when(appointmentService.getUpcomingAppointments(anyLong(), anyInt())).thenReturn(responseList);

        ResponseEntity<List<AppointmentResponseDTO>> response = appointmentController.getUpcomingAppointments(1L, 5);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDTO, response.getBody().get(0));
        verify(appointmentService, times(1)).getUpcomingAppointments(1L, 5);
    }

    @Test
    void getUpcomingAppointments_EmptyList() throws Exception {
        when(appointmentService.getUpcomingAppointments(anyLong(), anyInt())).thenReturn(Collections.emptyList());

        ResponseEntity<List<AppointmentResponseDTO>> response = appointmentController.getUpcomingAppointments(1L, 5);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getUpcomingAppointments_ServiceThrowsException() throws Exception {
        when(appointmentService.getUpcomingAppointments(anyLong(), anyInt()))
                .thenThrow(new Exception("Fetch failed"));

        Exception ex = assertThrows(Exception.class, () -> appointmentController.getUpcomingAppointments(1L, 5));
        assertEquals("Fetch failed", ex.getMessage());
    }
}
