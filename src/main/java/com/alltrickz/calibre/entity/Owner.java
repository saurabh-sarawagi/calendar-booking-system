package com.alltrickz.calibre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "fullName cannot be blank")
    @Column(nullable = false, unique = true)
    private String fullName;

    @NotBlank(message = "email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "phoneNumber cannot be blank")
    @Column(nullable = false, unique = true)
    private String phoneNumber;
}
