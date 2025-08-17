package com.alltrickz.calibre.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class OwnerRepositoryTest {

    @Mock
    private OwnerRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRepositoryCall() {
        repository.findAll();
        verify(repository, times(1)).findAll();
    }

}
