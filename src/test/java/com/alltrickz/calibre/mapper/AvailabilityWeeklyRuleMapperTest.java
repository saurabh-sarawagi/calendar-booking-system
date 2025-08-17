package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityWeeklyRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityWeeklyRule;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityWeeklyRuleMapperTest {

    private AvailabilityWeeklyRuleRequestDTO requestDTO;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("Owner Name");
        owner.setEmail("owner@example.com");
        owner.setPhoneNumber("9876543210");

        requestDTO = new AvailabilityWeeklyRuleRequestDTO();
        requestDTO.setDayOfWeek(DayOfWeek.MONDAY);
        requestDTO.setStartTime("10:00");
        requestDTO.setEndTime("18:00");
        requestDTO.setIsAvailable(true);
    }

    @Test
    void testMapToEntity() {
        AvailabilityWeeklyRule entity = AvailabilityWeeklyRuleMapper.mapToEntity(requestDTO, owner);

        assertNotNull(entity);
        assertEquals(owner, entity.getOwner());
        assertEquals(DayOfWeek.MONDAY, entity.getDayOfWeek());
        assertEquals(LocalTime.of(10, 0), entity.getStartTime());
        assertEquals(LocalTime.of(18, 0), entity.getEndTime());
        assertTrue(entity.getIsAvailable());
    }

    @Test
    void testMapToResponse() {
        AvailabilityWeeklyRule entity = AvailabilityWeeklyRuleMapper.mapToEntity(requestDTO, owner);
        entity.setId(100L);

        AvailabilityWeeklyRuleResponseDTO responseDTO = AvailabilityWeeklyRuleMapper.mapToResponse(entity);

        assertNotNull(responseDTO);
        assertEquals(100L, responseDTO.getId());
        assertEquals(owner.getId(), responseDTO.getOwnerId());
        assertEquals(DayOfWeek.MONDAY, responseDTO.getDayOfWeek());
        assertEquals(LocalTime.of(10, 0), responseDTO.getStartTime());
        assertEquals(LocalTime.of(18, 0), responseDTO.getEndTime());
        assertTrue(responseDTO.getIsAvailable());
    }

}
