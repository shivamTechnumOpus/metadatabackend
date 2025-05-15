package com.example.metadata_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FieldOptionsDTO {
    private Integer fieldId;
    private String fieldName;
    private List<Option> options;

    @Getter
    @Setter
    @ToString
    public static class Option {
        private String value;
        private String displayName;
        private Integer sortOrder;
    }
}