package com.example.demo.service;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.example.demo.repository.AdminBatchRepository;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminBatchRepository repository;

	private final ObjectMapper objectMapper;

	@Transactional(readOnly = true)
	public void streamAdmins(OutputStream outputStream) throws Exception {
		JsonGenerator generator = objectMapper.getFactory().createGenerator(outputStream);
		generator.writeStartObject();
		generator.writeStringField("status", "SUCCESS");
		generator.writeStringField("message", "Data Getting Success");
		generator.writeFieldName("data");
		generator.writeStartArray();
		repository.streamAllAdmins(rs -> {
			try {
				
				generator.writeStartObject();
				generator.writeStringField("id", rs.getString("id"));
				generator.writeStringField("firstName", rs.getString("first_name"));
				generator.writeStringField("lastName", rs.getString("last_name"));
				generator.writeStringField("email", rs.getString("email"));
				generator.writeStringField("mobileNumber", rs.getString("mobile_number"));
				generator.writeEndObject();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		generator.writeEndArray();
		generator.flush();
		generator.close();

	}

}
