package com.example.metadata_service.event;
import lombok.Getter;

import java.util.Map;

@Getter
public class EmployeeValidated {
    private final Integer tenantId;
    private final Integer tableId;
    private final Integer appId;
    private final Map<String, Object> data;

    public EmployeeValidated(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
        this.tenantId = tenantId;
        this.tableId = tableId;
        this.appId = appId;
        this.data = data;
    }
}