package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CsvJobService {
	private final JobLauncher jobLauncher;
	private final Job importJob;

	@Async("csvExecutor")
	public void processCsv(String filePath) throws Exception {
		JobParameters params = new JobParametersBuilder().addString("filePath", filePath)
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		jobLauncher.run(importJob, params);
	}
}