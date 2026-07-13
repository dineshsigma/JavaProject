package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;

@Service
public class FailedRecordService {

    public synchronized void save(
            String row,
            String error) {

        try (FileWriter writer =
                     new FileWriter(
                             "failed-records.csv",
                             true)) {

            writer.write(
                    row + "," +
                    error +
                    "\n");

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}