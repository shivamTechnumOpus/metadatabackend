package com.example.metadata_service.event;

import lombok.Getter;

@Getter
public class EmployeeValidationFailed {
    private final Integer tenantId;
    private final String errorMessage;

    public EmployeeValidationFailed(Integer tenantId, String errorMessage) {
        this.tenantId = tenantId;
        this.errorMessage = errorMessage;
    }
}