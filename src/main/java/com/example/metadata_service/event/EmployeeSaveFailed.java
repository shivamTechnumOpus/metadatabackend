package com.example.metadata_service.event;

import lombok.Getter;

@Getter
public class EmployeeSaveFailed {
    private final Integer tenantId;
    private final String errorMessage;

    public EmployeeSaveFailed(Integer tenantId, String errorMessage) {
        this.tenantId = tenantId;
        this.errorMessage = errorMessage;
    }
}