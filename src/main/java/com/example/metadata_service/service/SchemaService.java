package com.example.metadata_service.service;

import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.*;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchemaService {

    @Autowired private MetadataTablesRepository tablesRepository;
    @Autowired private MetadataTableStructureRepository tableStructureRepository;
    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
    @Autowired private MetadataFieldOverridesRepository fieldOverridesRepository;
    @Autowired private MetadataConstraintsRepository constraintsRepository;
    @Autowired private MetadataDependenciesRepository dependenciesRepository;
    @Autowired private MetadataFieldApiRelationshipRepository apiRelationshipRepository;
    @Autowired private MetadataDisplaysRepository displaysRepository;

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

            displaysRepository.findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, field.getFieldId())
                    .ifPresent(display -> {
                        fieldSchema.setIsDropdown(display.getOptionKeyId() != null);
                        fieldSchema.setOptionsSourceId(display.getOptionsSourceId());
                        fieldSchema.setOptionKeyId(display.getOptionKeyId());
                    });

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
}