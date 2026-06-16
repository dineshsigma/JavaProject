package com.example.demo.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.example.demo.model.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status != null ? status.name() : null;
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return dbData != null ? Status.valueOf(dbData) : null;
    }
}