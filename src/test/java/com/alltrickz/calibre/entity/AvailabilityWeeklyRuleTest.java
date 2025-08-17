package com.alltrickz.calibre.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityWeeklyRuleTest {

    private AvailabilityWeeklyRule weeklyRule;
    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(2L);
        owner.setFullName("Alice Smith");
        owner.setEmail("alice.smith@example.com");
        owner.setPhoneNumber("1234567890");

        weeklyRule = new AvailabilityWeeklyRule();
        weeklyRule.setId(200L);
        weeklyRule.setOwner(owner);
        weeklyRule.setDayOfWeek(DayOfWeek.MONDAY);
        weeklyRule.setStartTime(LocalTime.of(9, 0));
        weeklyRule.setEndTime(LocalTime.of(17, 0));
        weeklyRule.setIsAvailable(true);
    }

    @Test
    void testEntityFields() {
        assertEquals(200L, weeklyRule.getId());
        assertEquals(owner, weeklyRule.getOwner());
        assertEquals(DayOfWeek.MONDAY, weeklyRule.getDayOfWeek());
        assertEquals(LocalTime.of(9, 0), weeklyRule.getStartTime());
        assertEquals(LocalTime.of(17, 0), weeklyRule.getEndTime());
        assertTrue(weeklyRule.getIsAvailable());
    }

    @Test
    void testUnavailableDay() {
        weeklyRule.setIsAvailable(false);
        assertFalse(weeklyRule.getIsAvailable(), "Availability should be false");
    }

    @Test
    void testAllDayRuleWithoutTimes() {
        weeklyRule.setStartTime(null);
        weeklyRule.setEndTime(null);
        assertNull(weeklyRule.getStartTime(), "Start time can be null for all-day rules");
        assertNull(weeklyRule.getEndTime(), "End time can be null for all-day rules");
    }

    @Test
    void testToStringContainsKeyFields() {
        String toString = weeklyRule.toString();
        assertTrue(toString.contains("MONDAY"));
        assertTrue(toString.contains("09:00"));
    }

}
