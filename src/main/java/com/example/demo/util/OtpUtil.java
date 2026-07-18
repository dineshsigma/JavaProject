package com.example.demo.util;

import java.security.SecureRandom;

public class OtpUtil {

	private static final SecureRandom RANDOM = new SecureRandom();

	private OtpUtil() {
	}

	public static String generateOtp() {

		return String.format("%06d", RANDOM.nextInt(1000000));
	}

}
