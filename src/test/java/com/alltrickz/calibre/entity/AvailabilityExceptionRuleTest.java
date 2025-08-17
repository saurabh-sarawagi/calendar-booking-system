package com.alltrickz.calibre.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
public class AvailabilityExceptionRuleTest {

    private AvailabilityExceptionRule exceptionRule;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPhoneNumber("9876543210");

        exceptionRule = new AvailabilityExceptionRule();
        exceptionRule.setId(100L);
        exceptionRule.setOwner(owner);
        exceptionRule.setDate(LocalDate.of(2025, 8, 20));
        exceptionRule.setStartTime(LocalTime.of(10, 0));
        exceptionRule.setEndTime(LocalTime.of(12, 0));
        exceptionRule.setIsAvailable(false);
        exceptionRule.setDescription("Team offsite event");
    }

    @Test
    void testEntityFields() {
        assertEquals(100L, exceptionRule.getId());
        assertEquals(owner, exceptionRule.getOwner());
        assertEquals(LocalDate.of(2025, 8, 20), exceptionRule.getDate());
        assertEquals(LocalTime.of(10, 0), exceptionRule.getStartTime());
        assertEquals(LocalTime.of(12, 0), exceptionRule.getEndTime());
        assertFalse(exceptionRule.getIsAvailable());
        assertEquals("Team offsite event", exceptionRule.getDescription());
    }

    @Test
    void testIsAvailableTrue() {
        exceptionRule.setIsAvailable(true);
        assertTrue(exceptionRule.getIsAvailable(), "Availability should be true");
    }

    @Test
    void testDescriptionOptional() {
        exceptionRule.setDescription(null);
        assertNull(exceptionRule.getDescription(), "Description can be null");
    }

    @Test
    void testToStringContainsKeyFields() {
        String toString = exceptionRule.toString();
        assertTrue(toString.contains("Team offsite event"));
        assertTrue(toString.contains("2025-08-20"));
    }

}
