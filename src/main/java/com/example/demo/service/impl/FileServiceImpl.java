package com.example.demo.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.FileEntity;

import com.example.demo.exception.NoDataFoundException;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public UUID uploadFile(MultipartFile file)
            throws IOException {

        FileEntity entity = new FileEntity();

        entity.setId(UUID.randomUUID());
        entity.setFileName(file.getOriginalFilename());
        entity.setFileType(file.getContentType());
        entity.setFileSize(file.getSize());
        entity.setFileData(file.getBytes());

        fileRepository.save(entity);

        return entity.getId();
    }

    @Override
    public FileEntity getFile(UUID id) {

        return fileRepository.findById(id)
                .orElseThrow(() ->
                        new NoDataFoundException(
                                "File not found : " + id));
    }
}