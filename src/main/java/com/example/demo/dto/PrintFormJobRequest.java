package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PrintFormJobRequest {

    @NotBlank(message = "IAN is required")
    private String ian;

    @NotBlank(message = "Version is required")
    private String version;

    @NotBlank(message = "Inspection Report No is required")
    private String inspectionReportNo;

    @NotBlank(message = "Inspection Type is required")
    private String inspectionType;

    @NotBlank(message = "Inspection Template is required")
    private String inspectionTemplate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "isPreview is required")
    private Boolean isPreview;

    @NotBlank(message = "Requested By is required")
    private String requestedBy;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Requested By Email is required")
    private String requestedByEmail;

    @NotBlank(message = "File Name is required")
    private String fileName;

    @NotBlank(message = "File Path is required")
    private String filePath;

    @NotNull(message = "File Size is required")
    private Long fileSize;

    @NotBlank(message = "Request Body is required")
    private String requestBody;
}