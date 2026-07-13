package com.example.demo.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.FileUploadResponseDTO;
import com.example.demo.entity.ApiResponse;
import com.example.demo.entity.FileEntity;
import com.example.demo.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {

	private final FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse<FileUploadResponseDTO>> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		
		if (file == null) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(400, "File is required. Please select a file to upload.", null));
		}

		if (file.isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(400, "Uploaded file is empty. Please upload a valid file.", null));
		}
		
		if (file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty()) {

			return ResponseEntity.badRequest().body(new ApiResponse<>(400, "File name is missing.", null));
		}

		UUID fileId = fileService.uploadFile(file);

		FileUploadResponseDTO response = new FileUploadResponseDTO(fileId, file.getOriginalFilename(),
				file.getContentType());

		return ResponseEntity.ok(new ApiResponse<>(200, "File Uploaded Successfully", response));
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<byte[]> viewImage(@PathVariable UUID id) {

		FileEntity file = fileService.getFile(id);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getFileType())).body(file.getFileData());
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {

		FileEntity file = fileService.getFile(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
				.contentType(MediaType.parseMediaType(file.getFileType())).body(file.getFileData());
	}

}
