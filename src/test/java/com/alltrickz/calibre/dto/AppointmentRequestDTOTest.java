package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private AppointmentRequestDTO createValidDTO() {
        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setOwnerId(1L);
        dto.setInviteeName("John Doe");
        dto.setInviteeEmail("john@example.com");
        dto.setDate(LocalDate.now());
        dto.setStartTime("10:00");
        dto.setEndTime("11:00");
        return dto;
    }

    @Test
    void testValidDTO_ShouldPassValidation() {
        AppointmentRequestDTO dto = createValidDTO();
        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testMissingOwnerId_ShouldFailValidation() {
        AppointmentRequestDTO dto = createValidDTO();
        dto.setOwnerId(null);
        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidEmail_ShouldFailValidation() {
        AppointmentRequestDTO dto = createValidDTO();
        dto.setInviteeEmail("invalid-email");
        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidTimeFormat_ShouldFailValidation() {
        AppointmentRequestDTO dto = createValidDTO();
        dto.setStartTime("10:30"); // Invalid format
        Set<ConstraintViolation<AppointmentRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

}
