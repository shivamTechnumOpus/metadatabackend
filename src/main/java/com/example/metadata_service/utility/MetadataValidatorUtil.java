package com.example.metadata_service.utility;

import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.Employee;
import com.example.metadata_service.entity.FieldOptions;
import com.example.metadata_service.exception.ValidationException;
import com.example.metadata_service.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//
//public class MetadataValidatorUtil {
//    public static List<String> validateEmployeeData(Map<String, Object> data, TableSchemaDTO schema, EmployeeRepository employeeRepository) {
//        List<String> errors = new ArrayList<>();
//
//        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
//            String fieldName = field.getFieldName();
//            if (field.getIsRequired() && !data.containsKey(fieldName)) {
//                errors.add(fieldName + " is required");
//            }
//            if (data.containsKey(fieldName)) {
//                Object value = data.get(fieldName);
//                if (value == null) {
//                    errors.add(fieldName + " cannot be null");
//                } else if (!isValidType(value, field.getDatatype())) {
//                    errors.add(fieldName + " has invalid type");
//                }
//                if (field.getIsUnique() && fieldName.equals("first_name")) {
//                    employeeRepository.findByTenantIdAndFirstName(schema.getTenantId(), (String) value)
//                            .ifPresent(e -> errors.add(fieldName + " must be unique"));
//                }
//                if (field.getPattern() != null && value instanceof String && !((String) value).matches(field.getPattern())) {
//                    errors.add(fieldName + " does not match pattern");
//                }
//                if (field.getMinValue() != null && value instanceof Number && ((Number) value).doubleValue() < field.getMinValue()) {
//                    errors.add(fieldName + " is below minimum value");
//                }
//                if (field.getMaxValue() != null && value instanceof Number && ((Number) value).doubleValue() > field.getMaxValue()) {
//                    errors.add(fieldName + " exceeds maximum value");
//                }
//            }
//        }
//
//        return errors;
//    }
//
//    private static boolean isValidType(Object value, String datatype) {
//        return switch (datatype.toLowerCase()) {
//            case "string" -> value instanceof String;
//            case "integer", "long" -> value instanceof Number;
//            default -> false;
//        };
//    }
//}









/// ///////////////////////////////////
//
//public class MetadataValidatorUtil {
//    public static List<String> validateEmployeeData(Map<String, Object> data, TableSchemaDTO schema, EmployeeRepository employeeRepository) {
//        List<String> errors = new ArrayList<>();
//
//        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
//            String fieldName = field.getFieldName();
//            Object value = data.get(fieldName);
//
//            // Check isActive
//            boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();
//            if (!isActive) {
//                if (value != null) {
//                    errors.add(fieldName + " is not active for tenant " + schema.getTenantId());
//                }
//                continue; // Skip further validation for inactive fields
//            }
//
//            // Use overridden validation properties if available
//            boolean isRequired = field.getOverriddenIsRequired() != null ? field.getOverriddenIsRequired() : field.getIsRequired();
//            boolean isUnique = field.getOverriddenIsUnique() != null ? field.getOverriddenIsUnique() : field.getIsUnique();
//            String pattern = field.getOverriddenPattern() != null ? field.getOverriddenPattern() : field.getPattern();
//            Double minValue = field.getOverriddenMinValue() != null ? field.getOverriddenMinValue() : field.getMinValue();
//            Double maxValue = field.getOverriddenMaxValue() != null ? field.getOverriddenMaxValue() : field.getMaxValue();
//
//            // Check required fields
//            if (isRequired && (value == null || (value instanceof String && ((String) value).isEmpty()))) {
//                errors.add(fieldName + " is required for tenant " + schema.getTenantId());
//            }
//
//            // Check datatype and other validations only if value is provided
//            if (value != null) {
//                // Check datatype
//                switch (field.getDatatype().toLowerCase()) {
//                    case "string":
//                        if (!(value instanceof String)) {
//                            errors.add(fieldName + " must be a string for tenant " + schema.getTenantId());
//                        }
//                        break;
//                    case "number":
//                        if (!(value instanceof Number)) {
//                            errors.add(fieldName + " must be a number for tenant " + schema.getTenantId());
//                        }
//                        break;
//                }
//
//                // Check pattern for strings
//                if (pattern != null && value instanceof String && !((String) value).matches(pattern)) {
//                    errors.add(fieldName + " does not match pattern " + pattern + " for tenant " + schema.getTenantId());
//                }
//
//                // Check min/max for numbers
//                if (value instanceof Number) {
//                    double numValue = ((Number) value).doubleValue();
//                    if (minValue != null && numValue < minValue) {
//                        errors.add(fieldName + " must be at least " + minValue + " for tenant " + schema.getTenantId());
//                    }
//                    if (maxValue != null && numValue > maxValue) {
//                        errors.add(fieldName + " must not exceed " + maxValue + " for tenant " + schema.getTenantId());
//                    }
//                }
//
//                // Check uniqueness
//                if (isUnique && value != null && fieldName.equals("first_name")) {
//                    List<Employee> existing = employeeRepository.findAllByTenantIdAndFirstName(schema.getTenantId(), (String) value);
//                    if (!existing.isEmpty()) {
//                        errors.add(fieldName + " must be unique for tenant " + schema.getTenantId());
//                    }
//                }
//            }
//        }
//
//        return errors;
//    }
//}





////////////////////////////////////





import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.Employee;
import com.example.metadata_service.repository.EmployeeRepository;
import com.example.metadata_service.repository.FieldOptionsRepository;
import com.example.metadata_service.repository.MetadataFieldOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


//
//public class MetadataValidatorUtil {
//    public static List<String> validateEmployeeData(Map<String, Object> data, TableSchemaDTO schema, EmployeeRepository employeeRepository) {
//        List<String> errors = new ArrayList<>();
//
//        for (TableSchemaDTO.FieldSchemaDTO field : schema.getFields()) {
//            String fieldName = field.getFieldName();
//            Object value = data.get(fieldName);
//            Boolean isActive = field.getOverriddenIsActive() != null ? field.getOverriddenIsActive() : field.getIsActive();
//            Boolean isRequired = field.getOverriddenIsRequired() != null ? field.getOverriddenIsRequired() : field.getIsRequired();
//            Boolean isUnique = field.getOverriddenIsUnique() != null ? field.getOverriddenIsUnique() : field.getIsUnique();
//            String pattern = field.getOverriddenPattern() != null ? field.getOverriddenPattern() : field.getPattern();
//            Double minValue = field.getMinValue();
//            Double maxValue = field.getMaxValue();
//
//            if (!isActive && value != null) {
//                errors.add(fieldName + " is not active for tenant " + schema.getTenantId());
//                continue;
//            }
//
//            if (isRequired && value == null && field.getDefaultValue() == null) {
//                errors.add(fieldName + " is required for tenant " + schema.getTenantId());
//            }
//
//            if (value != null) {
//                if (pattern != null && !Pattern.matches(pattern, value.toString())) {
//                    errors.add(fieldName + " does not match pattern " + pattern + " for tenant " + schema.getTenantId());
//                }
//
//                if (minValue != null && field.getDatatype().equals("number")) {
//                    try {
//                        double numValue = ((Number) value).doubleValue();
//                        if (numValue < minValue) {
//                            errors.add(fieldName + " must be at least " + minValue + " for tenant " + schema.getTenantId());
//                        }
//                    } catch (ClassCastException | NumberFormatException e) {
//                        errors.add(fieldName + " must be a valid number for tenant " + schema.getTenantId());
//                    }
//                }
//
//                if (maxValue != null && field.getDatatype().equals("number")) {
//                    try {
//                        double numValue = ((Number) value).doubleValue();
//                        if (numValue > maxValue) {
//                            errors.add(fieldName + " must not exceed " + maxValue + " for tenant " + schema.getTenantId());
//                        }
//                    } catch (ClassCastException | NumberFormatException e) {
//                        errors.add(fieldName + " must be a valid number for tenant " + schema.getTenantId());
//                    }
//                }
//            }
//
//            if (isUnique && value != null && fieldName.equals("first_name")) {
//                List<Employee> existing = employeeRepository.findAllByTenantIdAndFirstName(schema.getTenantId(), (String) value);
//                if (!existing.isEmpty()) {
//                    errors.add(fieldName + " must be unique for tenant " + schema.getTenantId());
//                }
//            }
//        }
//
//        return errors;
//    }
//}



////////////////////////////////////////////////


@Component
public class MetadataValidatorUtil {
    private final FieldOptionsRepository fieldOptionsRepository;
    private final MetadataFieldOptionsRepository fieldOptionsConfigRepository;

    @Autowired
    public MetadataValidatorUtil(FieldOptionsRepository fieldOptionsRepository, MetadataFieldOptionsRepository fieldOptionsConfigRepository) {
        this.fieldOptionsRepository = fieldOptionsRepository;
        this.fieldOptionsConfigRepository = fieldOptionsConfigRepository;
    }

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
                        Integer optionGroupId = getOptionGroupId(schema.getTenantId(), schema.getAppId(), schema.getTableId(), field.getFieldId());
                        boolean isValid = fieldOptionsRepository
                                .findByTenantIdAndAppIdAndTableIdAndFieldIdAndOptionGroupIdAndIsActive(
                                        schema.getTenantId(), schema.getAppId(), schema.getTableId(), field.getFieldId(),
                                        optionGroupId, true)
                                .stream()
                                .anyMatch(opt -> opt.getOptionValue().equals(stringValue));
                        if (!isValid) {
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

    private Integer getOptionGroupId(Integer tenantId, Integer appId, Integer tableId, Integer fieldId) {
        return fieldOptionsConfigRepository
                .findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, fieldId)
                .filter(config -> config.getIsActive() && config.getIsDropdown())
                .map(config -> config.getOptionGroupId())
                .orElseThrow(() -> new ValidationException("No active dropdown configuration found for fieldId=" + fieldId));
    }
}







