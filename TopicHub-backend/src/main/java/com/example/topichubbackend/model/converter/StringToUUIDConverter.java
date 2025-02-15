package com.example.topichubbackend.model.converter;

import jakarta.persistence.*;

import java.util.*;

// Перед ревью не используемые классы, полностью закомменченные, наверн лучше вообще тогда убирать,
// но мб он тебе еще нужен будет, тогда все ок

//@Converter(autoApply = false)
//public class StringToUUIDConverter implements AttributeConverter<String, UUID> {
//    @Override
//    public UUID convertToDatabaseColumn(String attribute) {
//        if (attribute == null) {
//            return null;
//        }
//        return UUID.fromString(attribute);
//    }
//    @Override
//    public String convertToEntityAttribute(UUID dbData) {
//        if (dbData == null) {
//            return null;
//        }
//        return dbData.toString();
//    }
//}
