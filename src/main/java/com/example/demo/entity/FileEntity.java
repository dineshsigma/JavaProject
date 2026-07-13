package com.example.demo.entity;


import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "files")
@Data
public class FileEntity {

@Id
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;


}
