package com.alltrickz.calibre.mapper;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerMapperTest {

    private OwnerRequestDTO requestDTO;
    private Owner owner;

    @BeforeEach
    void setUp() {
        requestDTO = new OwnerRequestDTO();
        requestDTO.setFullName("John Doe");
        requestDTO.setEmail("john.doe@example.com");
        requestDTO.setPhoneNumber("9876543210");

        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("Old Name");
        owner.setEmail("old@example.com");
        owner.setPhoneNumber("1234567890");
    }

    @Test
    void testMapToEntity() {
        Owner entity = OwnerMapper.mapToEntity(requestDTO);

        assertNotNull(entity);
        assertEquals("John Doe", entity.getFullName());
        assertEquals("john.doe@example.com", entity.getEmail());
        assertEquals("9876543210", entity.getPhoneNumber());
    }

    @Test
    void testMapToResponse() {
        OwnerResponseDTO responseDTO = OwnerMapper.mapToResponse(owner);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("Old Name", responseDTO.getFullName());
        assertEquals("old@example.com", responseDTO.getEmail());
        assertEquals("1234567890", responseDTO.getPhoneNumber());
    }

    @Test
    void testUpdateEntity() {
        OwnerMapper.updateEntity(owner, requestDTO);

        assertEquals("John Doe", owner.getFullName());
        assertEquals("john.doe@example.com", owner.getEmail());
        assertEquals("9876543210", owner.getPhoneNumber());
    }

}
