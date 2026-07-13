package com.example.demo.controller;

import com.example.demo.service.CsvJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {
	private final CsvJobService csvJobService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) throws Exception {
		String filePath = "uploads/" + System.currentTimeMillis() + ".csv";
		Path path = Paths.get(filePath);
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		csvJobService.processCsv(filePath);
		return ResponseEntity.ok(Map.of("message", "File Uploaded Successfully. Processing Started."));
	}
}