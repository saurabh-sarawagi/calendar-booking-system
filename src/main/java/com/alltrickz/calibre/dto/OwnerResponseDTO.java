package com.alltrickz.calibre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
}
