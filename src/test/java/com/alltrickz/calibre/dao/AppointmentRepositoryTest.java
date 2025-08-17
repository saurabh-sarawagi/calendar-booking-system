package com.alltrickz.calibre.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class AppointmentRepositoryTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByOwnerIdAndDate() {
        Long ownerId = 1L;
        LocalDate date = LocalDate.now();

        when(appointmentRepository.findByOwnerIdAndDate(ownerId, date)).thenReturn(List.of());

        List<?> result = appointmentRepository.findByOwnerIdAndDate(ownerId, date);
        verify(appointmentRepository, times(1)).findByOwnerIdAndDate(ownerId, date);
    }

    @Test
    void testFindUpcomingAppointments() {
        Long ownerId = 1L;
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        when(appointmentRepository.findUpcomingAppointments(eq(ownerId), eq(today), eq(now), any(PageRequest.class)))
                .thenReturn(List.of());

        appointmentRepository.findUpcomingAppointments(ownerId, today, now, PageRequest.of(0, 10));
        verify(appointmentRepository, times(1))
                .findUpcomingAppointments(eq(ownerId), eq(today), eq(now), any(PageRequest.class));
    }

}
