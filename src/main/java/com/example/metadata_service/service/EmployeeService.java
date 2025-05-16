package com.example.metadata_service.service;
import com.example.metadata_service.dto.EmployeeDTO;
import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.Employee;
import com.example.metadata_service.entity.MetadataTenants;
import com.example.metadata_service.event.*;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.exception.ValidationException;
import com.example.metadata_service.repository.EmployeeRepository;
import com.example.metadata_service.repository.MetadataTenantsRepository;
import com.example.metadata_service.utility.MetadataValidatorUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private MetadataTenantsRepository tenantsRepository;
    @Autowired private MetadataValidatorUtil validatorUtil;
    @Autowired private SchemaService schemaService;
    @Autowired private ApplicationEventPublisher eventPublisher;

    public void initiateSaveEmployee(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
        eventPublisher.publishEvent(new EmployeeValidationRequested(tenantId, tableId, appId, data));
    }

    @EventListener
    @Transactional
    public void handleEmployeeValidation(EmployeeValidationRequested event) {
        try {
            TableSchemaDTO schema = schemaService.getTableSchema(event.getTenantId(), event.getTableId(), event.getAppId());
            List<String> errors = validatorUtil.validateEmployeeData(event.getData(), schema, employeeRepository);
            if (!errors.isEmpty()) {
                eventPublisher.publishEvent(new EmployeeValidationFailed(event.getTenantId(), String.join(", ", errors)));
                return;
            }
            eventPublisher.publishEvent(new EmployeeValidated(event.getTenantId(), event.getTableId(), event.getAppId(), event.getData()));
        } catch (Exception e) {
            eventPublisher.publishEvent(new EmployeeValidationFailed(event.getTenantId(), e.getMessage()));
        }
    }

    @EventListener
    @Transactional
    public void handleEmployeePersistence(EmployeeValidated event) {
        try {
            EmployeeDTO employeeDTO = saveEmployee(event.getTenantId(), event.getTableId(), event.getAppId(), event.getData());
            eventPublisher.publishEvent(new EmployeeSaved(event.getTenantId(), employeeDTO));
        } catch (Exception e) {
            eventPublisher.publishEvent(new EmployeeSaveFailed(event.getTenantId(), e.getMessage()));
        }
    }

    private EmployeeDTO saveEmployee(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
        TableSchemaDTO schema = schemaService.getTableSchema(tenantId, tableId, appId);
        MetadataTenants tenant = tenantsRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for tenantId=" + tenantId));

        Employee employee = new Employee();
        employee.setTenant(tenant);

        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
            String fieldName = field.getFieldName();
            Object value = data.get(fieldName);
            boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();
            String defaultValue = field.getOverriddenDefaultValue() != null ? field.getOverriddenDefaultValue() : field.getDefaultValue();

            if (!isActive) {
                continue;
            }

            switch (fieldName) {
                case "first_name":
                    employee.setFirstName(value != null ? (String) value : defaultValue);
                    break;
                case "last_name":
                    employee.setLastName(value != null ? (String) value : defaultValue);
                    break;
                case "password":
                    employee.setPassword(value != null ? (String) value : defaultValue);
                    break;
                case "phone_number":
                    if (value instanceof Number) {
                        employee.setPhoneNumber(((Number) value).longValue());
                    } else if (defaultValue != null) {
                        try {
                            employee.setPhoneNumber(Long.parseLong(defaultValue));
                        } catch (NumberFormatException e) {
                            // Ignore invalid default
                        }
                    }
                    break;
                case "street_name":
                    employee.setStreetName(value != null ? (String) value : defaultValue);
                    break;
                case "city":
                    employee.setCity(value != null ? (String) value : defaultValue);
                    break;
            }
        }

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(savedEmployee.getEmployeeId());
        dto.setTenantId(savedEmployee.getTenant().getTenantId());
        dto.setFirstName(savedEmployee.getFirstName());
        dto.setLastName(savedEmployee.getLastName());
        dto.setPassword(savedEmployee.getPassword());
        dto.setPhoneNumber(savedEmployee.getPhoneNumber());
        dto.setStreetName(savedEmployee.getStreetName());
        dto.setCity(savedEmployee.getCity());

        return dto;
    }

    @EventListener
    public void handleEmployeeValidationFailed(EmployeeValidationFailed event) {
        // Compensating action: Log failure or notify
        throw new ValidationException("Employee validation failed: " + event.getErrorMessage());
    }

    @EventListener
    public void handleEmployeeSaveFailed(EmployeeSaveFailed event) {
        // Compensating action: Log failure or notify
        throw new RuntimeException("Employee save failed: " + event.getErrorMessage());
    }

    public List<EmployeeDTO> getAllEmployees(Integer tenantId) {
        tenantsRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for tenantId=" + tenantId));

        List<Employee> employees = employeeRepository.findAllByTenantId(tenantId);

        return employees.stream().map(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmployeeId(employee.getEmployeeId());
            dto.setTenantId(employee.getTenant().getTenantId());
            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());
            dto.setPassword(employee.getPassword());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setStreetName(employee.getStreetName());
            dto.setCity(employee.getCity());
            return dto;
        }).collect(Collectors.toList());
    }
}