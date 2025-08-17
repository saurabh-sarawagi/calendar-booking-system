package com.alltrickz.calibre.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityExceptionRuleRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validExceptionRule_shouldPassValidation() {
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setDate(LocalDate.now());
        dto.setStartTime("10:00");
        dto.setEndTime("12:00");
        dto.setIsAvailable(false);
        dto.setDescription("Public Holiday");

        Set<ConstraintViolation<AvailabilityExceptionRuleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void missingDate_shouldFailValidation() {
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setStartTime("10:00");
        dto.setEndTime("12:00");
        dto.setIsAvailable(true);

        Set<ConstraintViolation<AvailabilityExceptionRuleRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidTimeFormat_shouldFailValidation() {
        AvailabilityExceptionRuleRequestDTO dto = new AvailabilityExceptionRuleRequestDTO();
        dto.setDate(LocalDate.now());
        dto.setStartTime("10:30AM");
        dto.setEndTime("abc");
        dto.setIsAvailable(true);

        Set<ConstraintViolation<AvailabilityExceptionRuleRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.size() >= 1);
    }

}
