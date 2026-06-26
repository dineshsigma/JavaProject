package com.example.demo.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.example.demo.validator.ValidSalary;

@Data
public class EmployeeRequestDTO {
	

	@NotBlank(message = "Employee name is required")
    private String empName;

    @NotBlank(message = "Employee ID is required")
    private String empId;

    @ValidSalary
    private Object empSalary;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String empMobileNumber;
    
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String empEmail;

}
