package com.example.metadata_service.service;

import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.MetadataStandardFields;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.repository.MetadataStandardFieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AutofillService {

    @Autowired private SchemaService schemaService;
    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;

    public Map<String, String> autofillField(Integer tenantId, Integer tableId, Integer appId, String sourceFieldName, String sourceValue) {
        TableSchemaDTO schema = schemaService.getTableSchema(tenantId, tableId, appId);
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

    private String fetchCityByPinCode(String pinCode, Integer apiId) {
        // Placeholder for API call
        return "New York";
    }
}
