package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.GenerateOtpRequest;
import com.example.demo.entity.ApiResponse;
import com.example.demo.service.OtpService;

import lombok.RequiredArgsConstructor;
import com.example.demo.util.OtpUtil;
import com.example.demo.model.OtpRecord;
import org.springframework.data.redis.core.RedisTemplate;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String OTP_PREFIX = "OTP:";

	private static final int OTP_EXPIRY_MINUTES = 5;

	private static final int MAX_RETRIES = 3;

	@Override
	public ApiResponse generateOtp(GenerateOtpRequest request) {

		String email = request.getEmail();

		String otp = OtpUtil.generateOtp();

		OtpRecord record = new OtpRecord();

		record.setOtp(otp);
		record.setRetries(0);
		record.setLockUntilEpoch(0);

		String key = OTP_PREFIX + email;
		
		System.out.println("email" + email);
		
		System.out.println("otp" + otp);

		//redisTemplate.opsForValue().set(key, record, Duration.ofMinutes(OTP_EXPIRY_MINUTES));

		return ApiResponse.builder().status(200).message("OTP Sent Successfully").build();

	}

}
