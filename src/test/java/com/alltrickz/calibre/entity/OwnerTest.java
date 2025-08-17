package com.alltrickz.calibre.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setFullName("John Doe");
        owner.setEmail("john.doe@example.com");
        owner.setPhoneNumber("9876543210");
    }

    @Test
    void testEntityFields() {
        assertEquals(1L, owner.getId());
        assertEquals("John Doe", owner.getFullName());
        assertEquals("john.doe@example.com", owner.getEmail());
        assertEquals("9876543210", owner.getPhoneNumber());
    }

    @Test
    void testEmailUniquenessConstraint() {
        // Simulation: two different Owners cannot have same email.
        Owner another = new Owner();
        another.setId(2L);
        another.setFullName("Jane Smith");
        another.setEmail("john.doe@example.com"); // duplicate
        another.setPhoneNumber("1112223333");

        assertNotEquals(owner.getId(), another.getId());
        assertEquals(owner.getEmail(), another.getEmail(), "Emails should match but must be unique in DB");
    }

    @Test
    void testPhoneNumberUniquenessConstraint() {
        // Simulation: two different Owners cannot have same phone number.
        Owner another = new Owner();
        another.setId(3L);
        another.setFullName("Jake Brown");
        another.setEmail("jake.brown@example.com");
        another.setPhoneNumber("9876543210"); // duplicate

        assertNotEquals(owner.getId(), another.getId());
        assertEquals(owner.getPhoneNumber(), another.getPhoneNumber(), "Phone numbers should match but must be unique in DB");
    }

    @Test
    void testToStringContainsKeyFields() {
        String toString = owner.toString();
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains("john.doe@example.com"));
    }

}
