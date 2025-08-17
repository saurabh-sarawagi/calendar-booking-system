package com.alltrickz.calibre.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.Mockito.*;

public class AvailabilityWeeklyRuleRepositoryTest {

    @Mock
    private AvailabilityWeeklyRuleRepository repository;

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
    void testDeleteByOwnerId() {
        Long ownerId = 1L;
        repository.deleteByOwnerId(ownerId);
        verify(repository, times(1)).deleteByOwnerId(ownerId);
    }

    @Test
    void testFindByOwnerIdAndDayOfWeek() {
        Long ownerId = 1L;
        DayOfWeek day = DayOfWeek.MONDAY;
        repository.findByOwnerIdAndDayOfWeek(ownerId, day);
        verify(repository, times(1)).findByOwnerIdAndDayOfWeek(ownerId, day);
    }

}
