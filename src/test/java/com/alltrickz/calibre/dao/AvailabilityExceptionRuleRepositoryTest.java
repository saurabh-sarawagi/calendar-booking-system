package com.alltrickz.calibre.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

public class AvailabilityExceptionRuleRepositoryTest {

    @Mock
    private AvailabilityExceptionRuleRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByOwnerId() {
        Long ownerId = 1L;
        when(repository.findByOwnerId(ownerId)).thenReturn(List.of());
        repository.findByOwnerId(ownerId);
        verify(repository, times(1)).findByOwnerId(ownerId);
    }

    @Test
    void testFindByOwnerIdAndDate() {
        Long ownerId = 1L;
        LocalDate date = LocalDate.now();
        repository.findByOwnerIdAndDate(ownerId, date);
        verify(repository, times(1)).findByOwnerIdAndDate(ownerId, date);
    }

}
