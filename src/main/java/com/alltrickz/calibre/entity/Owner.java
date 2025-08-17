package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;


/**
 * Entity representing the Calendar Owner.
 * An Owner is the person who manages availability and appointments.
 * Each Owner has unique email and phone number identifiers.
 */
@Entity
@Data
public class Owner {

    /**
     * Primary key for the Owner.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Full name of the Owner.
     */
    @Column(nullable = false)
    private String fullName;

    /**
     * Unique email of the Owner.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Unique phone number of the Owner.
     */
    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
