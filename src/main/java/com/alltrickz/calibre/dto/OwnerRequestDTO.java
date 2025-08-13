package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OwnerRequestDTO {

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[A-Za-z]+\\s+[A-Za-z]+$", message = "Full name must include first and last name")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}
