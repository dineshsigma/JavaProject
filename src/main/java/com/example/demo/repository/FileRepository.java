package com.example.demo.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.FileEntity;

public interface FileRepository
        extends JpaRepository<FileEntity, UUID> {

}

