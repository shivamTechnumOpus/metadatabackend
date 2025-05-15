package com.example.metadata_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FieldOptionsValuesDTO {
    private Integer optionGroupId;
    private Integer tenantId;
    private Integer appId;
    private Integer tableId;
    private Integer fieldId;
    private List<Option> options;

    @Getter
    @Setter
    @ToString
    public static class Option {
        private String optionValue;
        private String displayName;
        private Boolean isActive;
        private Integer sortOrder;
    }
}