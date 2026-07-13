package com.example.demo.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.FileEntity;

public interface FileService {

    UUID uploadFile(MultipartFile file)
            throws IOException;

    FileEntity getFile(UUID id);
}
