package com.example.demo.entity;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Pagination {

    private int page;
    private int size;
    private long totalRecords;
}
