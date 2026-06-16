package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import com.example.demo.model.Status;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;




@Data
@Entity
@Table(name = "print_form_jobs", schema = "print-form")
public class PrintFormJob {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "ian")
    private String ian;

    @Column(name = "version")
    private String version;

    @Column(name = "inspection_report_no")
    private String inspectionReportNo;

    @Column(name = "inspection_type")
    private String inspectionType;

    @Column(name = "inspection_template")
    private String inspectionTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    	
    @Column(name = "is_preview")
    private Boolean preview;


    @Column(name = "requested_by")
    private String requestedBy;

    @Column(name = "requested_by_email")
    private String requestedByEmail;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ Generate getters and setters
}