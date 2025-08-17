package com.alltrickz.calibre.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityWeeklyRuleRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validWeeklyRule_shouldPassValidation() {
        AvailabilityWeeklyRuleRequestDTO dto = new AvailabilityWeeklyRuleRequestDTO();
        dto.setDayOfWeek(DayOfWeek.MONDAY);
        dto.setStartTime("09:00");
        dto.setEndTime("17:00");
        dto.setIsAvailable(true);

        Set<ConstraintViolation<AvailabilityWeeklyRuleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void missingDayOfWeek_shouldFailValidation() {
        AvailabilityWeeklyRuleRequestDTO dto = new AvailabilityWeeklyRuleRequestDTO();
        dto.setStartTime("09:00");
        dto.setEndTime("17:00");
        dto.setIsAvailable(true);

        Set<ConstraintViolation<AvailabilityWeeklyRuleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidTimeFormat_shouldFailValidation() {
        AvailabilityWeeklyRuleRequestDTO dto = new AvailabilityWeeklyRuleRequestDTO();
        dto.setDayOfWeek(DayOfWeek.MONDAY);
        dto.setStartTime("9 AM");
        dto.setEndTime("17:60");
        dto.setIsAvailable(true);

        Set<ConstraintViolation<AvailabilityWeeklyRuleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.size() >= 1);
    }
}
