package com.alltrickz.calibre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO for creating or updating calendar owner details.
 */
@Data
public class OwnerRequestDTO {

    /** Full name of the owner (must contain first and last name) */
    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[A-Za-z]+\\s+[A-Za-z]+$", message = "Full name must include first and last name")
    private String fullName;

    /** Email of the owner */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /** Phone number of the owner (10 digits only) */
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
}
