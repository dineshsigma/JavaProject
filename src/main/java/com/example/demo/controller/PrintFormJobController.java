package com.example.demo.controller;

import com.example.demo.entity.PrintFormJob;
import com.example.demo.service.PrintFormJobService;
import com.example.demo.dto.PrintFormJobRequest;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import jakarta.validation.Valid;

import java.util.List;

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
    public List<PrintFormJob> getAllJobs() {
        return service.getAllJobs();
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