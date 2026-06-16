package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")   // ✅ null & empty
    @Email(message = "Invalid email format")   // ✅ email format
    private String email;

    @NotBlank(message = "Password is required")   // ✅ null & empty
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
