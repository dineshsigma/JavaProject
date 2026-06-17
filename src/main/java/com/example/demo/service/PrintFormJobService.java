package com.example.demo.service;

import com.example.demo.entity.PrintFormJob;
import com.example.demo.repository.PrintFormJobRepository;
import com.example.demo.dto.PrintFormJobRequest;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.example.demo.model.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.UUID;
	
@Service   // ✅ VERY IMPORTANT
public class PrintFormJobService {

    private final PrintFormJobRepository repository;

    public PrintFormJobService(PrintFormJobRepository repository) {
        this.repository = repository;
    }

    public Page<PrintFormJob> getAllJobs(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public PrintFormJob getJobById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
    
    public PrintFormJob createJob(PrintFormJobRequest req) {

        PrintFormJob job = new PrintFormJob();
        
        job.setId(UUID.randomUUID());
        job.setIan(req.getIan());
        job.setVersion(req.getVersion());
        job.setInspectionReportNo(req.getInspectionReportNo());
        job.setInspectionType(req.getInspectionType());
        job.setInspectionTemplate(req.getInspectionTemplate());
        job.setStatus(Status.valueOf(req.getStatus().toUpperCase().trim()));
        job.setPreview(req.getIsPreview());
        job.setRequestedBy(req.getRequestedBy());
        job.setRequestedByEmail(req.getRequestedByEmail());
        job.setFileName(req.getFileName());
        job.setFilePath(req.getFilePath());
        job.setFileSize(req.getFileSize());
        job.setRequestBody(req.getRequestBody());

        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        return repository.save(job);

    	
    }
}