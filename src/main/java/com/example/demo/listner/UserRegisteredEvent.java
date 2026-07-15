package com.example.demo.listner;

import lombok.Getter;

@Getter
public class UserRegisteredEvent {

	
	private final String email;

	public UserRegisteredEvent(String email) {
		this.email = email;
	}

}
