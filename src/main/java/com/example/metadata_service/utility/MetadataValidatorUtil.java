package com.example.metadata_service.utility;

import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.*;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.exception.ValidationException;
import com.example.metadata_service.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class MetadataValidatorUtil {

    @Autowired private MetadataDisplaysRepository displaysRepository;
    @Autowired private MetadataOptionsRepository optionsRepository;
    @Autowired private MetadataOptionsTableRepository optionsTableRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
    @Autowired private MetadataTablesRepository tablesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<String> validateEmployeeData(Map<String, Object> data, TableSchemaDTO schema, EmployeeRepository employeeRepository) {
        List<String> errors = new ArrayList<>();

        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
            String fieldName = field.getFieldName();
            Object value = data.get(fieldName);
            Boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();
            Boolean isRequired = field.getOverriddenIsRequired() != null ? field.getOverriddenIsRequired() : field.getIsRequired();
            Boolean isUnique = field.getOverriddenIsUnique() != null ? field.getOverriddenIsUnique() : field.getIsUnique();
            String pattern = field.getOverriddenPattern() != null ? field.getOverriddenPattern() : field.getPattern();
            Double minValue = field.getMinValue();
            Double maxValue = field.getMaxValue();
            Boolean isDropdown = field.getIsDropdown() != null && field.getIsDropdown();

            if (!isActive && value != null) {
                errors.add(fieldName + " is not active for tenant " + schema.getTenantId());
                continue;
            }

            if (isRequired && value == null && field.getDefaultValue() == null) {
                errors.add(fieldName + " is required for tenant " + schema.getTenantId());
            }

            if (value != null) {
                if (pattern != null && !Pattern.matches(pattern, value.toString())) {
                    errors.add(fieldName + " does not match pattern " + pattern + " for tenant " + schema.getTenantId());
                }

                if (minValue != null && field.getDatatype().equals("number")) {
                    try {
                        double numValue = ((Number) value).doubleValue();
                        if (numValue < minValue) {
                            errors.add(fieldName + " must be at least " + minValue + " for tenant " + schema.getTenantId());
                        }
                    } catch (ClassCastException | NumberFormatException e) {
                        errors.add(fieldName + " must be a valid number for tenant " + schema.getTenantId());
                    }
                }

                if (maxValue != null && field.getDatatype().equals("number")) {
                    try {
                        double numValue = ((Number) value).doubleValue();
                        if (numValue > maxValue) {
                            errors.add(fieldName + " must not exceed " + maxValue + " for tenant " + schema.getTenantId());
                        }
                    } catch (ClassCastException | NumberFormatException e) {
                        errors.add(fieldName + " must be a valid number for tenant " + schema.getTenantId());
                    }
                }

                if (isDropdown) {
                    try {
                        String stringValue = value.toString();
                        Set<String> validOptions = getValidOptions(
                                schema.getTenantId(), schema.getAppId(), schema.getTableId(), field);
                        if (!validOptions.contains(stringValue)) {
                            errors.add(fieldName + " has invalid value '" + stringValue + "' for tenant " + schema.getTenantId());
                        }
                    } catch (Exception e) {
                        errors.add(fieldName + " validation failed: " + e.getMessage());
                    }
                }
            }

            if (isUnique && value != null && fieldName.equals("first_name")) {
                List<Employee> existing = employeeRepository.findAllByTenantIdAndFirstName(schema.getTenantId(), (String) value);
                if (!existing.isEmpty()) {
                    errors.add(fieldName + " must be unique for tenant " + schema.getTenantId());
                }
            }
        }

        return errors;
    }

    private Set<String> getValidOptions(Integer tenantId, Integer appId, Integer tableId, TableSchemaDTO.FieldSchemaDTO field) {
        Integer optionsSourceId = field.getOptionsSourceId();
        Integer optionKeyId = field.getOptionKeyId();

        if (optionKeyId == null) {
            return Set.of();
        }

        if (optionsSourceId == 5001) { // internal (static options)
            List<MetadataOptions> options = optionsRepository
                    .findByTenantIdAndAppIdAndTableIdAndOptionKeyIdOrderByDisplayOrder(tenantId, appId, tableId, optionKeyId);
            return options.stream()
                    .map(MetadataOptions::getOptionValue)
                    .collect(Collectors.toSet());
        } else if (optionsSourceId == 5002) { // internal table (dynamic options)
            MetadataOptionsTable optionsTable = optionsTableRepository
                    .findByTableOptionId(optionKeyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Options table not found for tableOptionId=" + optionKeyId));

            // Get table and field names
            String tableName = tablesRepository.findById(optionsTable.getOptionsTableId())
                    .map(MetadataTables::getTableName)
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found for tableId=" + optionsTable.getOptionsTableId()));
            String fieldName = standardFieldsRepository.findById(field.getFieldId())
                    .map(MetadataStandardFields::getFieldName)
                    .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldId=" + field.getFieldId()));

            // Dynamic query for distinct values
            String queryStr = "SELECT DISTINCT " + fieldName + " FROM " + tableName + " WHERE tenant_id = :tenantId AND " + fieldName + " IS NOT NULL ORDER BY " + fieldName;
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter("tenantId", tenantId);
            List<String> values = query.getResultList();
            return values.stream()
                    .map(Object::toString)
                    .collect(Collectors.toSet());
        } else {
            throw new UnsupportedOperationException("Unsupported options source: " + optionsSourceId);
        }
    }
}






