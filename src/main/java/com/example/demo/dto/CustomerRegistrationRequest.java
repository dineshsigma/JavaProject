package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class CustomerRegistrationRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Mobile Number is required")
    private String mobileNumber;
    

    @NotNull(message = "Role ID is required")
    private UUID roleId;


    @Valid
    private PersonalDetailsDTO personalDetails;
}

