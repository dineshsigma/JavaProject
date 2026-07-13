package com.example.demo.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.CustomerRegistrationRequest;
import com.example.demo.dto.PersonalDetailsDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.PersonalDetails;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.PersonalDetailsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final PersonalDetailsRepository personalDetailsRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	@Transactional(rollbackFor = Exception.class)
	public String registerCustomer(CustomerRegistrationRequest request) {

		try {

			UUID customerId = UUID.randomUUID();

			Customer customer = new Customer();
			customer.setId(customerId);
			customer.setEmail(request.getEmail());
			customer.setPassword(passwordEncoder.encode(request.getPassword()));

			customer.setMobileNumber(request.getMobileNumber());
			
			Role role = roleRepository.findById(request.getRoleId())
					.orElseThrow(() -> new RuntimeException("Role not found"));

			customer.setRole(role);

			customerRepository.save(customer);

			validatePersonalDetails(request.getPersonalDetails());

			PersonalDetails details = new PersonalDetails();

			details.setId(UUID.randomUUID());
			details.setCustomerId(customerId);
			details.setAadhar_number(request.getPersonalDetails().getAadharNumber());

			details.setAddress(request.getPersonalDetails().getAddress());

			details.setPanCard(request.getPersonalDetails().getPanCard());

			personalDetailsRepository.save(details);

			return "Customer Registered Successfully";

		} catch (IllegalArgumentException ex) {
			throw ex;

		} catch (Exception ex) {
			throw ex;
		}

	}

	private void validatePersonalDetails(PersonalDetailsDTO dto) {

		if (dto == null) {
			throw new IllegalArgumentException("Personal Details are mandatory");
		}

		if (dto.getPanCard() == null || dto.getPanCard().trim().isEmpty()) {

			throw new IllegalArgumentException("PAN Card is required");
		}

		if (!dto.getPanCard().matches("[A-Z]{5}[0-9]{4}[A-Z]")) {

			throw new IllegalArgumentException("Invalid PAN Card Format");
		}

		if (!dto.getAadharNumber().matches("\\d{12}")) {

			throw new IllegalArgumentException("Aadhar Number must contain 12 digits");
		}
	}

}
