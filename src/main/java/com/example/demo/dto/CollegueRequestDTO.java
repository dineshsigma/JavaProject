package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
@Data
@Builder
public class CollegueRequestDTO {

	@NotBlank(message = "CollegueName is Mandatory")
	@Size(min = 3, max = 50,message = "Colleague Name must be between 3 and 50 characters")
	private String collegueName;
	
	private String collegueId;

	@NotBlank(message = "Email is Mandatory")
	@Email(message = "Invalid Email Format")
	private String email;

	@NotBlank(message = "MobileNumber is Mandatory")
	@Pattern(regexp = "^[6-9][0-9]{9}$",  message = "Mobile Number must contain 10 digits and start with 6,7,8,9")
	private String mobileNumber;

	@NotBlank(message = "Department is Mandatory")
	private String department;

	@NotBlank(message = "Designation is Mandatory")
	private String designation;

	@NotNull(message = "Salary is Mandatory")
	@Positive(message = "Salary must be greater than 0")
	private BigDecimal salary;

	@NotNull(message = "Date Of Joining is Mandatory")
	private LocalDate dateOfJoining;

	@NotBlank(message = "Status is Mandatory")
	private String status;

}
