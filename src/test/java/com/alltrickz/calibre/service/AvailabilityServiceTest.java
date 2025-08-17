package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.AvailabilityExceptionRuleRepository;
import com.alltrickz.calibre.dao.AvailabilityWeeklyRuleRepository;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @Mock
    private AvailabilityWeeklyRuleRepository weeklyRepo;

    @Mock
    private AvailabilityExceptionRuleRepository exceptionRepo;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private AvailabilityService availabilityService;

    private Owner owner;

    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john@example.com");
        owner.setPhoneNumber("1234567890");
    }

    @Test
    void testSetWeeklyAvailabilitySuccess() throws Exception {
        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);

        AvailabilityWeeklyRuleRequestDTO dto = new AvailabilityWeeklyRuleRequestDTO();
        dto.setDayOfWeek(DayOfWeek.MONDAY);
        dto.setStartTime("10:00");
        dto.setEndTime("17:00");
        dto.setIsAvailable(true);

        when(weeklyRepo.saveAll(anyList())).thenAnswer(i -> i.getArgument(0));

        List<AvailabilityWeeklyRuleResponseDTO> result = availabilityService.setWeeklyAvailability(1L, List.of(dto));
        assertEquals(1, result.size());
        assertEquals(DayOfWeek.MONDAY, result.get(0).getDayOfWeek());
    }

    @Test
    void testSetWeeklyAvailabilityDuplicateDay() {
        AvailabilityWeeklyRuleRequestDTO dto1 = new AvailabilityWeeklyRuleRequestDTO();
        dto1.setDayOfWeek(DayOfWeek.MONDAY);
        dto1.setIsAvailable(true);
        dto1.setStartTime("10:00");
        dto1.setEndTime("11:00");

        AvailabilityWeeklyRuleRequestDTO dto2 = new AvailabilityWeeklyRuleRequestDTO();
        dto2.setDayOfWeek(DayOfWeek.MONDAY);
        dto2.setIsAvailable(true);
        dto2.setStartTime("12:00");
        dto2.setEndTime("13:00");

        Exception ex = assertThrows(Exception.class,
                () -> availabilityService.setWeeklyAvailability(1L, List.of(dto1, dto2)));

        assertEquals("Duplicate day found: MONDAY", ex.getMessage());
    }

    @Test
    void testAddExceptionRulesSuccess() throws Exception {
        when(ownerService.validateAndGetOwner(1L)).thenReturn(owner);
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setStartTime("10:00");
        dto.setEndTime("11:00");
        dto.setIsAvailable(true);

        when(exceptionRepo.findByOwnerIdAndDate(1L, dto.getDate())).thenReturn(null);
        when(exceptionRepo.saveAll(anyList())).thenAnswer(i -> i.getArgument(0));

        List<AvailabilityExceptionRuleResponseDTO> result = availabilityService.addExceptionRules(1L, List.of(dto));
        assertEquals(1, result.size());
        assertEquals(dto.getDate(), result.get(0).getDate());
    }

    @Test
    void testAddExceptionRulesNullTime() {
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setDate(LocalDate.now().minusDays(1));
        dto.setIsAvailable(true);

        Exception ex = assertThrows(Exception.class,
                () -> availabilityService.addExceptionRules(1L, List.of(dto)));

        assertEquals("Start and end time cannot be empty for available rule", ex.getMessage());
    }

    @Test
    void testAddExceptionRulesPastDate() {
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setStartTime("10:00");
        dto.setEndTime("11:00");
        dto.setDate(LocalDate.now().minusDays(1));
        dto.setIsAvailable(true);

        Exception ex = assertThrows(Exception.class,
                () -> availabilityService.addExceptionRules(1L, List.of(dto)));

        assertEquals("Exceptions for past dates are not accepted.", ex.getMessage());
    }

    @Test
    void testDeleteExceptionRulesSuccess() throws Exception {
        when(exceptionRepo.existsById(1L)).thenReturn(true);
        doNothing().when(exceptionRepo).deleteById(1L);

        assertDoesNotThrow(() -> availabilityService.deleteExceptionRules(1L));
    }

    @Test
    void testDeleteExceptionRulesNotFound() {
        when(exceptionRepo.existsById(1L)).thenReturn(false);
        Exception ex = assertThrows(Exception.class,
                () -> availabilityService.deleteExceptionRules(1L));

        assertEquals("Owner Exception not found", ex.getMessage());
    }

}
