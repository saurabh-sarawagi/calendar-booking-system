package com.alltrickz.calibre.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnerResponseDTOTest {

    @Test
    void testConstructorAndGetters() {
        OwnerResponseDTO dto = new OwnerResponseDTO(1L, "John Doe", "john@example.com", "1234567890");

        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getFullName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("1234567890", dto.getPhoneNumber());
    }

}
