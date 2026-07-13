package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Builder;
@Data
@Builder
public class PersonalDetailsDTO {

    @NotBlank(message = "Aadhar Number is required")
    @Pattern(
        regexp = "\\d{12}",
        message = "Aadhar Number must contain 12 digits"
    )
    private String aadharNumber;

    @NotBlank(message = "PAN Card is required")
    @Pattern(
        regexp = "[A-Z]{5}[0-9]{4}[A-Z]",
        message = "Invalid PAN Card format"
    )
    private String panCard;

    @NotBlank(message = "Address is required")
    private String address;
}
