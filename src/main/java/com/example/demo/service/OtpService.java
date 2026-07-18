package com.example.demo.service;

import com.example.demo.dto.GenerateOtpRequest;
import com.example.demo.entity.ApiResponse;

public interface OtpService {
	
	 ApiResponse generateOtp(GenerateOtpRequest request);

}
