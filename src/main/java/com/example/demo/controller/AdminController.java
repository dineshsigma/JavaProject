package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.demo.service.AdminService;
import com.example.demo.service.CsvJobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
	private final CsvJobService csvJobService;
	private final AdminService adminService;

//	THIS IS THE CSV UPLOAD FILE USING @ASYNC +  JDBC TEMPLATE + USING INSERT QUERY
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) throws Exception {
		String filePath = "uploads/" + System.currentTimeMillis() + ".csv";
		Path path = Paths.get(filePath);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		csvJobService.processCsv(filePath);
		return ResponseEntity.ok(Map.of("message", "File Uploaded Successfully. Processing Started."));
	}

	@GetMapping(value = "/stream", produces = MediaType.APPLICATION_JSON_VALUE)
	public StreamingResponseBody streamAdmins() {
		return outputStream -> {
			try {
				adminService.streamAdmins(outputStream);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	@PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
		Path tempFile = Files.createTempFile("admins-", ".csv");
	    file.transferTo(tempFile);
	    csvJobService.uploadProcessCsv(tempFile);
		return ResponseEntity.ok(Map.of("message", "File Uploaded Successfully. Processing Started."));

	}

}