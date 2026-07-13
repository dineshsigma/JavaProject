package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
public class Customer {

	@Id
	private UUID id;

	private String email;

	private String password;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

}
