package com.example.demo.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
	

	@NotBlank(message = "Employee name is required")
    private String empName;

    @NotBlank(message = "Employee ID is required")
    private String empId;

    @NotBlank(message = "Salary is required")
    private String empSalary;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String empMobileNumber;

    @Email(message = "Invalid email format")
    private String empEmail;


}
