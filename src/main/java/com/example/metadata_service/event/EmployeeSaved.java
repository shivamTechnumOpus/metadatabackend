package com.example.metadata_service.event;
import com.example.metadata_service.dto.EmployeeDTO;
import lombok.Getter;

@Getter
public class EmployeeSaved {
    private final Integer tenantId;
    private final EmployeeDTO employeeDTO;

    public EmployeeSaved(Integer tenantId, EmployeeDTO employeeDTO) {
        this.tenantId = tenantId;
        this.employeeDTO = employeeDTO;
    }
}
