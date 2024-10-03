package com.konlamp.rallyepulse.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;

import java.time.LocalTime;


/**
 * Converter to persist LocalDate and LocalDateTime with
 * JPA 2.1 and Hibernate older than 5.0 version
 **/

@Converter(autoApply = false)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        return (localTime == null ? null : localTime.toString());
    }

    @Override
    public LocalTime convertToEntityAttribute(String time) {
        return (time == null ? null : LocalTime.parse(time));
    }

}
