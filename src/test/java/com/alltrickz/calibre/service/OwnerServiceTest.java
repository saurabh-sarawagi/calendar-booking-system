package com.alltrickz.calibre.service;

import com.alltrickz.calibre.dao.OwnerRepository;
import com.alltrickz.calibre.dto.OwnerRequestDTO;
import com.alltrickz.calibre.dto.OwnerResponseDTO;
import com.alltrickz.calibre.entity.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;
    private OwnerRequestDTO ownerRequestDTO;

    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john@example.com");
        owner.setPhoneNumber("1234567890");

        ownerRequestDTO = new OwnerRequestDTO();
        ownerRequestDTO.setFullName("Jane Smith");
        ownerRequestDTO.setEmail("jane@example.com");
        ownerRequestDTO.setPhoneNumber("0987654321");
    }

    @Test
    void testCreateOwner() {
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerResponseDTO response = ownerService.createOwner(ownerRequestDTO);
        assertEquals(owner.getId(), response.getId());
        assertEquals(owner.getFullName(), response.getFullName());
    }

    @Test
    void testUpdateOwnerSuccess() throws Exception {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerResponseDTO response = ownerService.updateOwner(1L, ownerRequestDTO);
        assertEquals(owner.getId(), response.getId());
    }

    @Test
    void testUpdateOwnerNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> ownerService.updateOwner(1L, ownerRequestDTO));
        assertEquals("Owner not found with id: 1", ex.getMessage());
    }

    @Test
    void testGetAllOwners() {
        when(ownerRepository.findAll()).thenReturn(List.of(owner));
        List<OwnerResponseDTO> owners = ownerService.getAllOwners();
        assertEquals(1, owners.size());
        assertEquals(owner.getFullName(), owners.get(0).getFullName());
    }

    @Test
    void testGetOwnerByIdSuccess() throws Exception {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        OwnerResponseDTO response = ownerService.getOwnerById(1L);
        assertEquals(owner.getFullName(), response.getFullName());
    }

    @Test
    void testGetOwnerByIdNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> ownerService.getOwnerById(1L));
        assertEquals("Owner not found with id: 1", ex.getMessage());
    }

    @Test
    void testValidateAndGetOwnerSuccess() throws Exception {
        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));
        Owner result = ownerService.validateAndGetOwner(1L);
        assertEquals(owner, result);
    }

    @Test
    void testValidateAndGetOwnerNotFound() {
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(Exception.class, () -> ownerService.validateAndGetOwner(1L));
        assertEquals("Owner not found with id: 1", ex.getMessage());
    }

}
