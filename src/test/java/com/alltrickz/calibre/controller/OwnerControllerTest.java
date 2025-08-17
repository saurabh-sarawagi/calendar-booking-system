package com.alltrickz.calibre.controller;

import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private OwnerRequestDTO ownerRequestDTO;
    private OwnerResponseDTO ownerResponseDTO;

    @BeforeEach
    void setup() {
        ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setFullName("John Doe");
        ownerRequestDTO.setEmail("john@example.com");
        ownerRequestDTO.setPhoneNumber("9876543210");

        ownerResponseDTO = new OwnerResponseDTO(1L, "John Doe", "john@example.com", "9876543210");
    }

    @Test
    void testCreateOwner() {
        when(ownerService.createOwner(ownerRequestDTO)).thenReturn(ownerResponseDTO);

        ResponseEntity<OwnerResponseDTO> response = ownerController.createOwner(ownerRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getFullName());
    }

    @Test
    void testUpdateOwner() throws Exception {
        when(ownerService.updateOwner(1L, ownerRequestDTO)).thenReturn(ownerResponseDTO);

        ResponseEntity<OwnerResponseDTO> response = ownerController.updateOwner(1L, ownerRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    @Test
    void testGetAllOwners() {
        when(ownerService.getAllOwners()).thenReturn(List.of(ownerResponseDTO));

        ResponseEntity<List<OwnerResponseDTO>> response = ownerController.getAllOwners();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetOwnerById() throws Exception {
        when(ownerService.getOwnerById(1L)).thenReturn(ownerResponseDTO);

        ResponseEntity<OwnerResponseDTO> response = ownerController.getOwnerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("9876543210", response.getBody().getPhoneNumber());
    }

}
