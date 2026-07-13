package com.example.demo.entity;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

	private UUID  id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;

}
