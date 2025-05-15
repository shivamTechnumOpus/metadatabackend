package com.example.metadata_service.service;

import com.example.metadata_service.dto.*;
import com.example.metadata_service.entity.*;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.exception.ValidationException;
import com.example.metadata_service.repository.*;
import com.example.metadata_service.utility.MetadataValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Service
//public class MetadataService {
//    @Autowired private MetadataTablesRepository tablesRepository;
//    @Autowired private MetadataTableStructureRepository tableStructureRepository;
//    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
//    @Autowired private MetadataFieldOverridesRepository fieldOverridesRepository;
//    @Autowired private MetadataConstraintsRepository constraintsRepository;
//    @Autowired private MetadataDependenciesRepository dependenciesRepository;
//    @Autowired private MetadataFieldApiRelationshipRepository apiRelationshipRepository;
//    @Autowired private EmployeeRepository employeeRepository;
//    @Autowired private MetadataTenantsRepository tenantsRepository;
//
//    public TableSchemaDTO getTableSchema(Integer tenantId, Integer tableId, Integer appId) {
//        MetadataTables table = tablesRepository.findById(tableId)
//                .filter(t -> t.getTenant().getTenantId().equals(tenantId) && t.getApp().getAppId().equals(appId) && t.getIsActive())
//                .orElseThrow(() -> new ResourceNotFoundException("Table not found or inactive for tenantId=" + tenantId + ", tableId=" + tableId));
//
//        TableSchemaDTO schema = new TableSchemaDTO();
//        schema.setTableId(tableId);
//        schema.setTableName(table.getTableName());
//        schema.setTenantId(tenantId);
//        schema.setAppId(appId);
//
//        List<TableSchemaDTO.FieldSchemaDTO> fields = new ArrayList<>();
//        List<MetadataTableStructure> structures = tableStructureRepository.findByTenantIdAndTableId(tenantId, tableId);
//
//        for (MetadataTableStructure structure : structures) {
//            MetadataStandardFields field = standardFieldsRepository.findById(structure.getField().getFieldId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldId=" + structure.getField().getFieldId()));
//
//            TableSchemaDTO.FieldSchemaDTO fieldSchema = new TableSchemaDTO.FieldSchemaDTO();
//            fieldSchema.setFieldId(field.getFieldId());
//            fieldSchema.setFieldName(field.getFieldName());
//            fieldSchema.setDatatype(field.getDatatype());
//            fieldSchema.setDisplayName(field.getDisplayName());
//            fieldSchema.setHelpText(field.getHelpText());
//            fieldSchema.setPlaceholder(field.getPlaceholder());
//            fieldSchema.setDefaultValue(field.getDefaultValue());
//            fieldSchema.setIsRequired(field.getIsRequired());
//            fieldSchema.setIsUnique(field.getIsUnique());
//            fieldSchema.setPattern(field.getPattern());
//            fieldSchema.setIsActive(field.getIsActive());
//
//            fieldOverridesRepository.findByTenantIdAndTableIdAndFieldId(tenantId, tableId, field.getFieldId())
//                    .ifPresent(override -> {
//                        fieldSchema.setOverriddenField(override.getOverriddenField());
//                        fieldSchema.setOverrideValue(override.getOverrideValue());
//                        switch (override.getOverriddenField()) {
//                            case "display_name":
//                                fieldSchema.setDisplayName(override.getOverrideValue());
//                                break;
//                            case "placeholder":
//                                fieldSchema.setPlaceholder(override.getOverrideValue());
//                                break;
//                            case "default_value":
//                                fieldSchema.setDefaultValue(override.getOverrideValue());
//                                break;
//                        }
//                    });
//
//            constraintsRepository.findByTenantIdAndTableIdAndFieldId(tenantId, tableId, field.getFieldId())
//                    .ifPresent(constraint -> {
//                        fieldSchema.setMinValue(constraint.getMinValue());
//                        fieldSchema.setMaxValue(constraint.getMaxValue());
//                    });
//
//            apiRelationshipRepository.findByTenantIdAndAppIdAndStandardFieldId(tenantId, appId, field.getFieldId())
//                    .ifPresent(api -> fieldSchema.setApiId(api.getOnifiedApiId()));
//
//            List<TableSchemaDTO.DependencyDTO> dependencies = dependenciesRepository
//                    .findBySourceTableIdAndSourceFieldIdAndTenantId(tableId, field.getFieldId(), tenantId)
//                    .stream()
//                    .map(dep -> {
//                        TableSchemaDTO.DependencyDTO depDTO = new TableSchemaDTO.DependencyDTO();
//                        depDTO.setDependencyId(dep.getDependencyId());
//                        depDTO.setTargetFieldId(dep.getTargetField().getFieldId());
//                        depDTO.setConditionType(dep.getConditionType());
//                        depDTO.setConditionValue(dep.getConditionValue());
//                        depDTO.setConditionExpression(dep.getConditionExpression());
//                        return depDTO;
//                    })
//                    .toList();
//            fieldSchema.setDependencies(dependencies);
//
//            fields.add(fieldSchema);
//        }
//
//        schema.setFields(fields);
//        return schema;
//    }
//
//    @Transactional
//    public EmployeeDTO saveEmployee(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
//        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
//        List<String> errors = MetadataValidatorUtil.validateEmployeeData(data, schema, employeeRepository);
//        if (!errors.isEmpty()) {
//            throw new ValidationException("Validation failed: " + String.join(", ", errors));
//        }
//
//        MetadataTenants tenant = tenantsRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for tenantId=" + tenantId));
//
//        Employee employee = new Employee();
//        employee.setTenant(tenant);
//        employee.setFirstName((String) data.get("first_name"));
//        employee.setLastName((String) data.get("last_name"));
//        employee.setPassword((String) data.get("password"));
//        Object phoneNumber = data.get("phone_number");
//        employee.setPhoneNumber(phoneNumber instanceof Number ? ((Number) phoneNumber).longValue() : null);
//        employee.setStreetName((String) data.get("street_name"));
//        employee.setCity((String) data.get("city"));
//
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        EmployeeDTO dto = new EmployeeDTO();
//        dto.setEmployeeId(savedEmployee.getEmployeeId());
//        dto.setTenantId(savedEmployee.getTenant().getTenantId());
//        dto.setFirstName(savedEmployee.getFirstName());
//        dto.setLastName(savedEmployee.getLastName());
//        dto.setPassword(savedEmployee.getPassword());
//        dto.setPhoneNumber(savedEmployee.getPhoneNumber());
//        dto.setStreetName(savedEmployee.getStreetName());
//        dto.setCity(savedEmployee.getCity());
//
//        return dto;
//    }
//
//    public Map<String, String> autofillField(Integer tenantId, Integer tableId, Integer appId, String sourceFieldName, String sourceValue) {
//        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
//        TableSchemaDTO.FieldSchemaDTO sourceField = schema.getFields().stream()
//                .filter(f -> f.getFieldName().equals(sourceFieldName))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldName=" + sourceFieldName));
//
//        Map<String, String> results = new HashMap<>();
//        for (TableSchemaDTO.DependencyDTO dep : sourceField.getDependencies()) {
//            if (dep.getConditionType().equals("autofill") && sourceField.getApiId() != null) {
//                String targetFieldName = standardFieldsRepository.findById(dep.getTargetFieldId())
//                        .map(MetadataStandardFields::getFieldName)
//                        .orElseThrow(() -> new ResourceNotFoundException("Target field not found for fieldId=" + dep.getTargetFieldId()));
//                String targetValue = fetchCityByPinCode(sourceValue, sourceField.getApiId());
//                results.put(targetFieldName, targetValue);
//            }
//        }
//        return results;
//    }
//
//    private String fetchCityByPinCode(String pinCode, Integer apiId) {
//        // TODO: Implement real API call using apiId (e.g., via RestTemplate)
//        return "New York"; // Placeholder
//    }
//}



















 /////////////////////////////////////
//
//import com.example.metadata_service.dto.EmployeeDTO;
//import com.example.metadata_service.dto.TableSchemaDTO;
//import com.example.metadata_service.entity.*;
//import com.example.metadata_service.exception.ResourceNotFoundException;
//import com.example.metadata_service.exception.ValidationException;
//import com.example.metadata_service.repository.*;
//import com.example.metadata_service.utility.MetadataValidatorUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class MetadataService {
//    @Autowired private MetadataTablesRepository tablesRepository;
//    @Autowired private MetadataTableStructureRepository tableStructureRepository;
//    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
//    @Autowired private MetadataFieldOverridesRepository fieldOverridesRepository;
//    @Autowired private MetadataConstraintsRepository constraintsRepository;
//    @Autowired private MetadataDependenciesRepository dependenciesRepository;
//    @Autowired private MetadataFieldApiRelationshipRepository apiRelationshipRepository;
//    @Autowired private EmployeeRepository employeeRepository;
//    @Autowired private MetadataTenantsRepository tenantsRepository;
//
//    public TableSchemaDTO getTableSchema(Integer tenantId, Integer tableId, Integer appId) {
//        MetadataTables table = tablesRepository.findById(tableId)
//                .filter(t -> t.getTenant().getTenantId().equals(tenantId) && t.getApp().getAppId().equals(appId) && t.getIsActive())
//                .orElseThrow(() -> new ResourceNotFoundException("Table not found or inactive for tenantId=" + tenantId + ", tableId=" + tableId));
//
//        TableSchemaDTO schema = new TableSchemaDTO();
//        schema.setTableId(tableId);
//        schema.setTableName(table.getTableName());
//        schema.setTenantId(tenantId);
//        schema.setAppId(appId);
//
//        List<TableSchemaDTO.FieldSchemaDTO> fields = new ArrayList<>();
//        List<MetadataTableStructure> structures = tableStructureRepository.findByTenantIdAndTableId(tenantId, tableId);
//
//        for (MetadataTableStructure structure : structures) {
//            MetadataStandardFields field = standardFieldsRepository.findById(structure.getField().getFieldId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldId=" + structure.getField().getFieldId()));
//
//            TableSchemaDTO.FieldSchemaDTO fieldSchema = new TableSchemaDTO.FieldSchemaDTO();
//            fieldSchema.setFieldId(field.getFieldId());
//            fieldSchema.setFieldName(field.getFieldName());
//            fieldSchema.setDatatype(field.getDatatype());
//            fieldSchema.setDisplayName(field.getDisplayName());
//            fieldSchema.setHelpText(field.getHelpText());
//            fieldSchema.setPlaceholder(field.getPlaceholder());
//            fieldSchema.setDefaultValue(field.getDefaultValue());
//            fieldSchema.setIsRequired(field.getIsRequired());
//            fieldSchema.setIsUnique(field.getIsUnique());
//            fieldSchema.setPattern(field.getPattern());
//            fieldSchema.setIsActive(field.getIsActive());
//
//            // Fetch all active overrides for this field
//            List<MetadataFieldOverrides> overrides = fieldOverridesRepository
//                    .findAllByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, field.getFieldId());
//            for (MetadataFieldOverrides override : overrides) {
//                switch (override.getOverriddenField()) {
//                    case "display_name":
//                        fieldSchema.setOverriddenDisplayName(override.getOverrideValue());
//                        fieldSchema.setDisplayName(override.getOverrideValue());
//                        break;
//                    case "help_text":
//                        fieldSchema.setOverriddenHelpText(override.getOverrideValue());
//                        fieldSchema.setHelpText(override.getOverrideValue());
//                        break;
//                    case "placeholder":
//                        fieldSchema.setOverriddenPlaceholder(override.getOverrideValue());
//                        fieldSchema.setPlaceholder(override.getOverrideValue());
//                        break;
//                    case "default_value":
//                        fieldSchema.setOverriddenDefaultValue(override.getOverrideValue());
//                        fieldSchema.setDefaultValue(override.getOverrideValue());
//                        break;
//                    case "is_required":
//                        fieldSchema.setOverriddenIsRequired(Boolean.parseBoolean(override.getOverrideValue()));
//                        fieldSchema.setIsRequired(Boolean.parseBoolean(override.getOverrideValue()));
//                        break;
//                    case "is_unique":
//                        fieldSchema.setOverriddenIsUnique(Boolean.parseBoolean(override.getOverrideValue()));
//                        fieldSchema.setIsUnique(Boolean.parseBoolean(override.getOverrideValue()));
//                        break;
//                    case "pattern":
//                        fieldSchema.setOverriddenPattern(override.getOverrideValue());
//                        fieldSchema.setPattern(override.getOverrideValue());
//                        break;
//                    case "is_active":
//                        fieldSchema.setOverriddenIsActive(Boolean.parseBoolean(override.getOverrideValue()));
//                        fieldSchema.setIsActive(Boolean.parseBoolean(override.getOverrideValue()));
//                        break;
//                    case "min_value":
//                        fieldSchema.setOverriddenMinValue(Double.parseDouble(override.getOverrideValue()));
//                        fieldSchema.setMinValue(Double.parseDouble(override.getOverrideValue()));
//                        break;
//                    case "max_value":
//                        fieldSchema.setOverriddenMaxValue(Double.parseDouble(override.getOverrideValue()));
//                        fieldSchema.setMaxValue(Double.parseDouble(override.getOverrideValue()));
//                        break;
//                }
//            }
//
//            constraintsRepository.findByTenantIdAndTableIdAndFieldId(tenantId, tableId, field.getFieldId())
//                    .ifPresent(constraint -> {
//                        if (fieldSchema.getMinValue() == null) {
//                            fieldSchema.setMinValue(constraint.getMinValue());
//                        }
//                        if (fieldSchema.getMaxValue() == null) {
//                            fieldSchema.setMaxValue(constraint.getMaxValue());
//                        }
//                    });
//
//            apiRelationshipRepository.findByTenantIdAndAppIdAndStandardFieldId(tenantId, appId, field.getFieldId())
//                    .ifPresent(api -> fieldSchema.setApiId(api.getOnifiedApiId()));
//
//            List<TableSchemaDTO.DependencyDTO> dependencies = dependenciesRepository
//                    .findBySourceTableIdAndSourceFieldIdAndTenantId(tableId, field.getFieldId(), tenantId)
//                    .stream()
//                    .map(dep -> {
//                        TableSchemaDTO.DependencyDTO depDTO = new TableSchemaDTO.DependencyDTO();
//                        depDTO.setDependencyId(dep.getDependencyId());
//                        depDTO.setTargetFieldId(dep.getTargetField().getFieldId());
//                        depDTO.setConditionType(dep.getConditionType());
//                        depDTO.setConditionValue(dep.getConditionValue());
//                        depDTO.setConditionExpression(dep.getConditionExpression());
//                        return depDTO;
//                    })
//                    .toList();
//            fieldSchema.setDependencies(dependencies);
//
//            fields.add(fieldSchema);
//        }
//
//        schema.setFields(fields);
//        return schema;
//    }
//
//    @Transactional
//    public EmployeeDTO saveEmployee(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
//        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
//        List<String> errors = MetadataValidatorUtil.validateEmployeeData(data, schema, employeeRepository);
//        if (!errors.isEmpty()) {
//            throw new ValidationException("Validation failed: " + String.join(", ", errors));
//        }
//
//        MetadataTenants tenant = tenantsRepository.findById(tenantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for tenantId=" + tenantId));
//
//        Employee employee = new Employee();
//        employee.setTenant(tenant);
//
//        // Apply field values, using defaultValue for missing fields if isActive=true
//        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
//            String fieldName = field.getFieldName();
//            Object value = data.get(fieldName);
//            boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();
//
//            if (!isActive) {
//                // Skip inactive fields
//                continue;
//            }
//
//            String defaultValue = field.getOverriddenDefaultValue() != null ? field.getOverriddenDefaultValue() : field.getDefaultValue();
//
//            switch (fieldName) {
//                case "first_name":
//                    employee.setFirstName(value != null ? (String) value : defaultValue);
//                    break;
//                case "last_name":
//                    employee.setLastName(value != null ? (String) value : defaultValue);
//                    break;
//                case "password":
//                    employee.setPassword(value != null ? (String) value : defaultValue);
//                    break;
//                case "phone_number":
//                    if (value instanceof Number) {
//                        employee.setPhoneNumber(((Number) value).longValue());
//                    } else if (defaultValue != null) {
//                        try {
//                            employee.setPhoneNumber(Long.parseLong(defaultValue));
//                        } catch (NumberFormatException e) {
//                            // Ignore invalid default
//                        }
//                    }
//                    break;
//                case "street_name":
//                    employee.setStreetName(value != null ? (String) value : defaultValue);
//                    break;
//                case "city":
//                    employee.setCity(value != null ? (String) value : defaultValue);
//                    break;
//            }
//        }
//
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        EmployeeDTO dto = new EmployeeDTO();
//        dto.setEmployeeId(savedEmployee.getEmployeeId());
//        dto.setTenantId(savedEmployee.getTenant().getTenantId());
//        dto.setFirstName(savedEmployee.getFirstName());
//        dto.setLastName(savedEmployee.getLastName());
//        dto.setPassword(savedEmployee.getPassword());
//        dto.setPhoneNumber(savedEmployee.getPhoneNumber());
//        dto.setStreetName(savedEmployee.getStreetName());
//        dto.setCity(savedEmployee.getCity());
//
//        return dto;
//    }
//
//    public Map<String, String> autofillField(Integer tenantId, Integer tableId, Integer appId, String sourceFieldName, String sourceValue) {
//        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
//        TableSchemaDTO.FieldSchemaDTO sourceField = schema.getFields().stream()
//                .filter(f -> f.getFieldName().equals(sourceFieldName))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldName=" + sourceFieldName));
//
//        Map<String, String> results = new HashMap<>();
//        for (TableSchemaDTO.DependencyDTO dep : sourceField.getDependencies()) {
//            if (dep.getConditionType().equals("autofill") && sourceField.getApiId() != null) {
//                String targetFieldName = standardFieldsRepository.findById(dep.getTargetFieldId())
//                        .map(MetadataStandardFields::getFieldName)
//                        .orElseThrow(() -> new ResourceNotFoundException("Target field not found for fieldId=" + dep.getTargetFieldId()));
//                String targetValue = fetchCityByPinCode(sourceValue, sourceField.getApiId());
//                results.put(targetFieldName, targetValue);
//            }
//        }
//        return results;
//    }
//
//    private String fetchCityByPinCode(String pinCode, Integer apiId) {
//        // TODO: Implement real API call using apiId (e.g., via RestTemplate)
//        return "New York"; // Placeholder
//    }
//}
//

//////////////////////////////////////

@Service
public class MetadataService {
    @Autowired private MetadataTablesRepository tablesRepository;
    @Autowired private MetadataTableStructureRepository tableStructureRepository;
    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
    @Autowired private MetadataFieldOverridesRepository fieldOverridesRepository;
    @Autowired private MetadataConstraintsRepository constraintsRepository;
    @Autowired private MetadataDependenciesRepository dependenciesRepository;
    @Autowired private MetadataFieldApiRelationshipRepository apiRelationshipRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private MetadataTenantsRepository tenantsRepository;
    @Autowired private MetadataFieldOptionsRepository fieldOptionsConfigRepository;
    @Autowired private FieldOptionsRepository fieldOptionsRepository;
    @Autowired private MetadataValidatorUtil validatorUtil;

    public TableSchemaDTO getTableSchema(Integer tenantId, Integer tableId, Integer appId) {
        MetadataTables table = tablesRepository.findById(tableId)
                .filter(t -> t.getTenant().getTenantId().equals(tenantId) && t.getApp().getAppId().equals(appId) && t.getIsActive())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found or inactive for tenantId=" + tenantId + ", tableId=" + tableId));

        TableSchemaDTO schema = new TableSchemaDTO();
        schema.setTableId(tableId);
        schema.setTableName(table.getTableName());
        schema.setTenantId(tenantId);
        schema.setAppId(appId);

        List<TableSchemaDTO.FieldSchemaDTO> fields = new ArrayList<>();
        List<MetadataTableStructure> structures = tableStructureRepository.findByTenantIdAndTableId(tenantId, tableId);

        for (MetadataTableStructure structure : structures) {
            MetadataStandardFields field = standardFieldsRepository.findById(structure.getField().getFieldId())
                    .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldId=" + structure.getField().getFieldId()));

            TableSchemaDTO.FieldSchemaDTO fieldSchema = new TableSchemaDTO.FieldSchemaDTO();
            fieldSchema.setFieldId(field.getFieldId());
            fieldSchema.setFieldName(field.getFieldName());
            fieldSchema.setDatatype(field.getDatatype());
            fieldSchema.setDisplayName(field.getDisplayName());
            fieldSchema.setHelpText(field.getHelpText());
            fieldSchema.setPlaceholder(field.getPlaceholder());
            fieldSchema.setDefaultValue(field.getDefaultValue());
            fieldSchema.setIsRequired(field.getIsRequired());
            fieldSchema.setIsUnique(field.getIsUnique());
            fieldSchema.setPattern(field.getPattern());
            fieldSchema.setIsActive(field.getIsActive());

            // Check for dropdown configuration
            fieldOptionsConfigRepository.findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, field.getFieldId())
                    .ifPresent(config -> fieldSchema.setIsDropdown(config.getIsDropdown() && config.getIsActive()));

            // Fetch all active overrides for this field
            List<MetadataFieldOverrides> overrides = fieldOverridesRepository
                    .findAllByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, field.getFieldId());
            for (MetadataFieldOverrides override : overrides) {
                switch (override.getOverriddenField()) {
                    case "display_name":
                        fieldSchema.setOverriddenDisplayName(override.getOverrideValue());
                        fieldSchema.setDisplayName(override.getOverrideValue());
                        break;
                    case "help_text":
                        fieldSchema.setOverriddenHelpText(override.getOverrideValue());
                        fieldSchema.setHelpText(override.getOverrideValue());
                        break;
                    case "placeholder":
                        fieldSchema.setOverriddenPlaceholder(override.getOverrideValue());
                        fieldSchema.setPlaceholder(override.getOverrideValue());
                        break;
                    case "default_value":
                        fieldSchema.setOverriddenDefaultValue(override.getOverrideValue());
                        fieldSchema.setDefaultValue(override.getOverrideValue());
                        break;
                    case "is_required":
                        fieldSchema.setOverriddenIsRequired(Boolean.parseBoolean(override.getOverrideValue()));
                        fieldSchema.setIsRequired(Boolean.parseBoolean(override.getOverrideValue()));
                        break;
                    case "is_unique":
                        fieldSchema.setOverriddenIsUnique(Boolean.parseBoolean(override.getOverrideValue()));
                        fieldSchema.setIsUnique(Boolean.parseBoolean(override.getOverrideValue()));
                        break;
                    case "pattern":
                        fieldSchema.setOverriddenPattern(override.getOverrideValue());
                        fieldSchema.setPattern(override.getOverrideValue());
                        break;
                    case "is_active":
                        fieldSchema.setOverriddenIsActive(Boolean.parseBoolean(override.getOverrideValue()));
                        fieldSchema.setIsActive(Boolean.parseBoolean(override.getOverrideValue()));
                        break;
                    case "min_value":
                        fieldSchema.setOverriddenMinValue(Double.parseDouble(override.getOverrideValue()));
                        fieldSchema.setMinValue(Double.parseDouble(override.getOverrideValue()));
                        break;
                    case "max_value":
                        fieldSchema.setOverriddenMaxValue(Double.parseDouble(override.getOverrideValue()));
                        fieldSchema.setMaxValue(Double.parseDouble(override.getOverrideValue()));
                        break;
                }
            }

            constraintsRepository.findByTenantIdAndTableIdAndFieldId(tenantId, tableId, field.getFieldId())
                    .ifPresent(constraint -> {
                        if (fieldSchema.getMinValue() == null) {
                            fieldSchema.setMinValue(constraint.getMinValue());
                        }
                        if (fieldSchema.getMaxValue() == null) {
                            fieldSchema.setMaxValue(constraint.getMaxValue());
                        }
                    });

            apiRelationshipRepository.findByTenantIdAndAppIdAndStandardFieldId(tenantId, appId, field.getFieldId())
                    .ifPresent(api -> fieldSchema.setApiId(api.getOnifiedApiId()));

            List<TableSchemaDTO.DependencyDTO> dependencies = dependenciesRepository
                    .findBySourceTableIdAndSourceFieldIdAndTenantId(tableId, field.getFieldId(), tenantId)
                    .stream()
                    .map(dep -> {
                        TableSchemaDTO.DependencyDTO depDTO = new TableSchemaDTO.DependencyDTO();
                        depDTO.setDependencyId(dep.getDependencyId());
                        depDTO.setTargetFieldId(dep.getTargetField().getFieldId());
                        depDTO.setConditionType(dep.getConditionType());
                        depDTO.setConditionValue(dep.getConditionValue());
                        depDTO.setConditionExpression(dep.getConditionExpression());
                        return depDTO;
                    })
                    .toList();
            fieldSchema.setDependencies(dependencies);

            fields.add(fieldSchema);
        }

        schema.setFields(fields);
        return schema;
    }

    @Transactional
    public EmployeeDTO saveEmployee(Integer tenantId, Integer tableId, Integer appId, Map<String, Object> data) {
        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
        List<String> errors = validatorUtil.validateEmployeeData(data, schema, employeeRepository);
        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed: " + String.join(", ", errors));
        }

        MetadataTenants tenant = tenantsRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found for tenantId=" + tenantId));

        Employee employee = new Employee();
        employee.setTenant(tenant);

        // Apply field values, using defaultValue for missing fields if isActive=true
        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
            String fieldName = field.getFieldName();
            Object value = data.get(fieldName);
            boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();

            if (!isActive) {
                continue;
            }

            String defaultValue = field.getOverriddenDefaultValue() != null ? field.getOverriddenDefaultValue() : field.getDefaultValue();

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

    public Map<String, String> autofillField(Integer tenantId, Integer tableId, Integer appId, String sourceFieldName, String sourceValue) {
        TableSchemaDTO schema = getTableSchema(tenantId, tableId, appId);
        TableSchemaDTO.FieldSchemaDTO sourceField = schema.getFields().stream()
                .filter(f -> f.getFieldName().equals(sourceFieldName))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldName=" + sourceFieldName));

        Map<String, String> results = new HashMap<>();
        for (TableSchemaDTO.DependencyDTO dep : sourceField.getDependencies()) {
            if (dep.getConditionType().equals("autofill") && sourceField.getApiId() != null) {
                String targetFieldName = standardFieldsRepository.findById(dep.getTargetFieldId())
                        .map(MetadataStandardFields::getFieldName)
                        .orElseThrow(() -> new ResourceNotFoundException("Target field not found for fieldId=" + dep.getTargetFieldId()));
                String targetValue = fetchCityByPinCode(sourceValue, sourceField.getApiId());
                results.put(targetFieldName, targetValue);
            }
        }
        return results;
    }

    public FieldOptionsDTO getFieldOptions(Integer tenantId, Integer appId, Integer tableId, Integer fieldId, boolean refresh) {
        MetadataFieldOptions config = fieldOptionsConfigRepository
                .findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, fieldId)
                .filter(c -> c.getIsDropdown() && c.getIsActive())
                .orElseThrow(() -> new ResourceNotFoundException("No dropdown configuration found for tenantId=" + tenantId + ", appId=" + appId + ", tableId=" + tableId + ", fieldId=" + fieldId));

        // TODO: Add caching with Redis
        // if (!refresh) { check cache }

        List<FieldOptions> options = fieldOptionsRepository
                .findByTenantIdAndAppIdAndTableIdAndFieldIdAndOptionGroupIdAndIsActive(
                        tenantId, appId, tableId, fieldId, config.getOptionGroupId(), true);

        FieldOptionsDTO dto = new FieldOptionsDTO();
        dto.setFieldId(fieldId);
        dto.setFieldName(standardFieldsRepository.findById(fieldId)
                .map(MetadataStandardFields::getFieldName)
                .orElse("unknown"));
        dto.setOptions(options.stream().map(opt -> {
            FieldOptionsDTO.Option option = new FieldOptionsDTO.Option();
            option.setValue(opt.getOptionValue());
            option.setDisplayName(opt.getDisplayName());
            option.setSortOrder(opt.getSortOrder());
            return option;
        }).collect(Collectors.toList()));

        // TODO: Cache result
        return dto;
    }

    @Transactional
    public Integer saveFieldOptionsConfig(FieldOptionsConfigDTO dto) {
        validateConfig(dto);

        MetadataFieldOptions config = fieldOptionsConfigRepository
                .findByTenantIdAndAppIdAndTableIdAndFieldId(
                        dto.getTenantId(), dto.getAppId(), dto.getTableId(), dto.getFieldId())
                .orElse(new MetadataFieldOptions());

        config.setTenant(tenantsRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + dto.getTenantId())));
        config.setApp(tablesRepository.findById(dto.getTableId())
                .map(MetadataTables::getApp)
                .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + dto.getTableId())));
        config.setTable(tablesRepository.findById(dto.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + dto.getTableId())));
        config.setField(standardFieldsRepository.findById(dto.getFieldId())
                .orElseThrow(() -> new ResourceNotFoundException("Field not found: " + dto.getFieldId())));
        config.setIsDropdown(dto.getIsDropdown());
        config.setOptionGroupId(dto.getIsDropdown() ? dto.getOptionGroupId() : null);
        config.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        config.setUpdatedAt(LocalDateTime.now());

        MetadataFieldOptions saved = fieldOptionsConfigRepository.save(config);
        // TODO: Invalidate cache
        return saved.getOptionConfigId();
    }

    @Transactional
    public List<Integer> saveFieldOptionsValues(FieldOptionsValuesDTO dto) {
        validateValues(dto);

        List<FieldOptions> existingOptions = fieldOptionsRepository
                .findByTenantIdAndAppIdAndTableIdAndFieldIdAndOptionGroupIdAndIsActive(
                        dto.getTenantId(), dto.getAppId(), dto.getTableId(), dto.getFieldId(), dto.getOptionGroupId(), true);

        List<FieldOptions> toSave = new ArrayList<>();
        for (FieldOptionsValuesDTO.Option opt : dto.getOptions()) {
            FieldOptions option = existingOptions.stream()
                    .filter(e -> e.getOptionValue().equals(opt.getOptionValue()))
                    .findFirst()
                    .orElse(new FieldOptions());

            option.setOptionGroupId(dto.getOptionGroupId());
            option.setTenant(tenantsRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + dto.getTenantId())));
            option.setApp(tablesRepository.findById(dto.getTableId())
                    .map(MetadataTables::getApp)
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + dto.getTableId())));
            option.setTable(tablesRepository.findById(dto.getTableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found: " + dto.getTableId())));
            option.setField(standardFieldsRepository.findById(dto.getFieldId())
                    .orElseThrow(() -> new ResourceNotFoundException("Field not found: " + dto.getFieldId())));
            option.setOptionValue(opt.getOptionValue());
            option.setDisplayName(opt.getDisplayName());
            option.setIsActive(opt.getIsActive() != null ? opt.getIsActive() : true);
            option.setSortOrder(opt.getSortOrder() != null ? opt.getSortOrder() : 0);
            option.setUpdatedAt(LocalDateTime.now());

            toSave.add(option);
        }

        // Deactivate removed options
        existingOptions.stream()
                .filter(e -> dto.getOptions().stream().noneMatch(o -> o.getOptionValue().equals(e.getOptionValue())))
                .forEach(e -> {
                    e.setIsActive(false);
                    e.setUpdatedAt(LocalDateTime.now());
                    toSave.add(e);
                });

        List<FieldOptions> saved = fieldOptionsRepository.saveAll(toSave);
        // TODO: Invalidate cache
        return saved.stream().map(FieldOptions::getOptionId).collect(Collectors.toList());
    }

    @Transactional
    public void deleteFieldOptionsConfig(Integer optionConfigId) {
        MetadataFieldOptions config = fieldOptionsConfigRepository.findById(optionConfigId)
                .orElseThrow(() -> new ResourceNotFoundException("Config not found: " + optionConfigId));
        config.setIsActive(false);
        config.setUpdatedAt(LocalDateTime.now());
        fieldOptionsConfigRepository.save(config);
        // TODO: Invalidate cache
    }

    private void validateConfig(FieldOptionsConfigDTO dto) {
        if (dto.getTenantId() == null || dto.getAppId() == null || dto.getTableId() == null || dto.getFieldId() == null) {
            throw new ValidationException("TenantId, appId, tableId, and fieldId are required");
        }
        if (dto.getIsDropdown() && dto.getOptionGroupId() == null) {
            throw new ValidationException("optionGroupId is required when isDropdown is true");
        }
        if (dto.getIsDropdown()) {
            List<FieldOptions> options = fieldOptionsRepository
                    .findByTenantIdAndAppIdAndTableIdAndFieldIdAndOptionGroupIdAndIsActive(
                            dto.getTenantId(), dto.getAppId(), dto.getTableId(), dto.getFieldId(), dto.getOptionGroupId(), true);
            if (options.isEmpty()) {
                throw new ValidationException("No active options found for optionGroupId: " + dto.getOptionGroupId());
            }
        }
    }

    private void validateValues(FieldOptionsValuesDTO dto) {
        if (dto.getOptionGroupId() == null || dto.getTenantId() == null || dto.getAppId() == null ||
                dto.getTableId() == null || dto.getFieldId() == null || dto.getOptions() == null) {
            throw new ValidationException("optionGroupId, tenantId, appId, tableId, fieldId, and options are required");
        }
        for (FieldOptionsValuesDTO.Option opt : dto.getOptions()) {
            if (opt.getOptionValue() == null || opt.getOptionValue().isEmpty() ||
                    opt.getDisplayName() == null || opt.getDisplayName().isEmpty()) {
                throw new ValidationException("optionValue and displayName are required for all options");
            }
        }
        long uniqueValues = dto.getOptions().stream().map(FieldOptionsValuesDTO.Option::getOptionValue).distinct().count();
        if (uniqueValues != dto.getOptions().size()) {
            throw new ValidationException("Duplicate optionValue found in options");
        }
    }

    private String fetchCityByPinCode(String pinCode, Integer apiId) {
        // Placeholder for API call
        return "New York";
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

