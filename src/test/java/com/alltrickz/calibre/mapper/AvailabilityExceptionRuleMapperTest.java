package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.AvailabilityExceptionRuleRequestDTO;
import com.alltrickz.calibre.dto.AvailabilityExceptionRuleResponseDTO;
import com.alltrickz.calibre.entity.AvailabilityExceptionRule;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityExceptionRuleMapperTest {

    private AvailabilityExceptionRuleRequestDTO requestDTO;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("Owner Name");
        owner.setEmail("owner@example.com");
        owner.setPhoneNumber("9876543210");

        requestDTO = new AvailabilityExceptionRuleRequestDTO();
        requestDTO.setDate(LocalDate.of(2025, 8, 17));
        requestDTO.setStartTime("10:00");
        requestDTO.setEndTime("11:00");
        requestDTO.setIsAvailable(true);
        requestDTO.setDescription("Special Day");
    }

    @Test
    void testMapToEntity() {
        AvailabilityExceptionRule entity = AvailabilityExceptionRuleMapper.mapToEntity(requestDTO, owner);

        assertNotNull(entity);
        assertEquals(owner, entity.getOwner());
        assertEquals(LocalDate.of(2025, 8, 17), entity.getDate());
        assertEquals(LocalTime.of(10, 0), entity.getStartTime());
        assertEquals(LocalTime.of(11, 0), entity.getEndTime());
        assertTrue(entity.getIsAvailable());
        assertEquals("Special Day", entity.getDescription());
    }

    @Test
    void testMapToResponse() {
        AvailabilityExceptionRule entity = AvailabilityExceptionRuleMapper.mapToEntity(requestDTO, owner);
        entity.setId(100L);

        AvailabilityExceptionRuleResponseDTO responseDTO = AvailabilityExceptionRuleMapper.mapToResponse(entity);

        assertNotNull(responseDTO);
        assertEquals(100L, responseDTO.getId());
        assertEquals(owner.getId(), responseDTO.getOwnerId());
        assertEquals(LocalDate.of(2025, 8, 17), responseDTO.getDate());
        assertEquals(LocalTime.of(10, 0), responseDTO.getStartTime());
        assertEquals(LocalTime.of(11, 0), responseDTO.getEndTime());
        assertTrue(responseDTO.getIsAvailable());
        assertEquals("Special Day", responseDTO.getDescription());
    }

    @Test
    void testUpdateEntity() {
        AvailabilityExceptionRule entity = new AvailabilityExceptionRule();
        entity.setOwner(owner);

        AvailabilityExceptionRuleMapper.updateEntity(entity, requestDTO);

        assertEquals(LocalDate.of(2025, 8, 17), entity.getDate());
        assertEquals(LocalTime.of(10, 0), entity.getStartTime());
        assertEquals(LocalTime.of(11, 0), entity.getEndTime());
        assertTrue(entity.getIsAvailable());
        assertEquals("Special Day", entity.getDescription());
        // owner should not change
        assertEquals(owner, entity.getOwner());
    }

}
