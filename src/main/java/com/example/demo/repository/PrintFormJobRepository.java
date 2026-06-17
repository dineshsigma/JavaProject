package com.example.demo.repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.PrintFormJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PrintFormJobRepository extends JpaRepository<PrintFormJob, UUID> {

	Page<PrintFormJob> findByInspectionReportNo(
        String inspectionReportNo,
        Pageable pageable
			);

}