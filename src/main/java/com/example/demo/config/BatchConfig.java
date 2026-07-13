package com.example.demo.config;

import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.entity.Admin;
import com.example.demo.processor.AdminProcessor;
import com.example.demo.writer.AdminWriter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final AdminProcessor processor;

    private final AdminWriter writer;

    @Bean
    @StepScope
    public FlatFileItemReader<Admin> reader(
            @Value("#{jobParameters['filePath']}") String filePath) {

        FlatFileItemReader<Admin> reader =
                new FlatFileItemReader<>();

        reader.setResource(
                new FileSystemResource(filePath));

        reader.setLinesToSkip(1);

        reader.setLineMapper(lineMapper());

        return reader;
    }

    private LineMapper<Admin> lineMapper() {

        DefaultLineMapper<Admin> mapper =
                new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer =
                new DelimitedLineTokenizer();

        tokenizer.setNames(
                "id",
                "firstName",
                "lastName",
                "email",
                "mobileNumber");

        mapper.setLineTokenizer(tokenizer);

        mapper.setFieldSetMapper(fieldSet -> {

            Admin admin = new Admin();

            admin.setId(
                    UUID.fromString(
                            fieldSet.readString("id")
                                    .replace("\"", "")
                                    .trim()));

            admin.setFirstName(
                    fieldSet.readString("firstName")
                            .replace("\"", "")
                            .trim());

            admin.setLastName(
                    fieldSet.readString("lastName")
                            .replace("\"", "")
                            .trim());

            admin.setEmail(
                    fieldSet.readString("email")
                            .replace("\"", "")
                            .trim());

            admin.setMobileNumber(
                    fieldSet.readString("mobileNumber")
                            .replace("\"", "")
                            .trim());

            return admin;
        });

        return mapper;
    }

    @Bean
    public Step csvStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Admin> reader) {

        return new StepBuilder(
                "csvStep",
                jobRepository)
                .<Admin,Admin>chunk(
                        1000,
                        transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importJob(
            JobRepository jobRepository,
            Step csvStep) {

        return new JobBuilder(
                "importJob",
                jobRepository)
                .start(csvStep)
                .build();
    }
}