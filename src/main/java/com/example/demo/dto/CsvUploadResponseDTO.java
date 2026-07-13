package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsvUploadResponseDTO {

	private String message;
	private long totalRecords;
	private long successRecords;
	private long failedRecords;

}
