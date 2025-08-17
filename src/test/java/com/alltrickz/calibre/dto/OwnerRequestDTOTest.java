package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private OwnerRequestDTO createValidDTO() {
        OwnerRequestDTO dto = new OwnerRequestDTO();
        dto.setFullName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setPhoneNumber("1234567890");
        return dto;
    }

    @Test
    void testValidDTO_ShouldPassValidation() {
        OwnerRequestDTO dto = createValidDTO();
        Set<ConstraintViolation<OwnerRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidName_ShouldFailValidation() {
        OwnerRequestDTO dto = createValidDTO();
        dto.setFullName("John"); // Missing last name
        Set<ConstraintViolation<OwnerRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testInvalidPhone_ShouldFailValidation() {
        OwnerRequestDTO dto = createValidDTO();
        dto.setPhoneNumber("12345");
        Set<ConstraintViolation<OwnerRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

}
