package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
