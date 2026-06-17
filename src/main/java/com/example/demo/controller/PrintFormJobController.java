package com.example.demo.controller;

import com.example.demo.entity.PrintFormJob;
import com.example.demo.service.PrintFormJobService;
import com.example.demo.dto.PrintFormJobRequest;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/print-jobs")
@CrossOrigin
public class PrintFormJobController {

    private final PrintFormJobService service;

    public PrintFormJobController(PrintFormJobService service) {
        this.service = service;
    }

    // ✅ GET ALL
    @GetMapping
    public Page<PrintFormJob> getAllJobs(
    		@RequestParam(required = false) String inspectionReportNo,
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy
    		) {
    	Sort sort = Sort.by(sortBy).descending(); // default desc
    	System.out.println("inspectionReportNo" +  inspectionReportNo);
        return service.getAllJobs(inspectionReportNo,PageRequest.of(page, size , sort));
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public PrintFormJob getJobById(@PathVariable UUID id) {
        return service.getJobById(id);
    }
    
    // POST PRINT FORM JOB CONTROLLERS
    @PostMapping
    public PrintFormJob createJob(@Valid @RequestBody PrintFormJobRequest request) {
    	return service.createJob(request);
    }
    
    
}