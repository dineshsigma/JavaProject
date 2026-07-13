package com.example.demo.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponseDTO {

    private UUID fileId;

    private String fileName;

    private String fileType;
}