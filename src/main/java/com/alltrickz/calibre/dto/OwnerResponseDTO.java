package com.alltrickz.calibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for sending calendar owner details in responses.
 */
@Data
@AllArgsConstructor
public class OwnerResponseDTO {

    /** Unique identifier for the owner */
    private Long id;

    /** Full name of the owner (would contain first and last name) */
    private String fullName;

    /** Email of the owner */
    private String email;

    /** Phone number of the owner (10 digits only) */
    private String phoneNumber;
}
