package com.example.demo.service;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import javax.sql.DataSource;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class CsvJobService {
	private final JobLauncher jobLauncher;
	private final Job importJob;
	private final DataSource dataSource;

	@Async("csvExecutor")
	public void processCsv(String filePath) throws Exception {
		JobParameters params = new JobParametersBuilder().addString("filePath", filePath)
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(importJob, params);
	}

	@Async
	public void uploadProcessCsv(Path csvPath) {

	    try (Connection connection = dataSource.getConnection()) {

	        connection.setAutoCommit(false);

	        // Remove existing data
	        try (PreparedStatement ps =
	                connection.prepareStatement("TRUNCATE TABLE admin_details")) {

	            ps.executeUpdate();
	        }

	        CopyManager copyManager =
	                new CopyManager(connection.unwrap(BaseConnection.class));

	        String copySql = """
	                COPY admin_details
	                (
	                    id,
	                    first_name,
	                    last_name,
	                    email,
	                    mobile_number
	                )
	                FROM STDIN
	                WITH (
	                    FORMAT CSV,
	                    HEADER TRUE
	                )
	                """;

	        long startTime = System.currentTimeMillis();

	        try (Reader reader = Files.newBufferedReader(csvPath)) {

	            long count = copyManager.copyIn(copySql, reader);

	            long endTime = System.currentTimeMillis();

	            System.out.println("Inserted Records : " + count);
	            System.out.println("Time Taken : "
	                    + (endTime - startTime) + " ms");
	        }

	        connection.commit();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}