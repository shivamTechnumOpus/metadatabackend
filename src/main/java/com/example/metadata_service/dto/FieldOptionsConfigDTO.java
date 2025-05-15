package com.example.metadata_service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FieldOptionsConfigDTO {
    private Integer tenantId;
    private Integer appId;
    private Integer tableId;
    private Integer fieldId;
    private Boolean isDropdown;
    private Integer optionGroupId;
    private Boolean isActive;
}