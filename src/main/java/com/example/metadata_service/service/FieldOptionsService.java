package com.example.metadata_service.service;

import com.example.metadata_service.dto.FieldOptionsDTO;
import com.example.metadata_service.entity.*;

import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//
//@Service
//public class FieldOptionsService {
//
//    @Autowired private MetadataDisplaysRepository displaysRepository;
//    @Autowired private MetadataOptionsRepository optionsRepository;
//    @Autowired private MetadataOptionsTableRepository optionsTableRepository;
//    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
//    @Autowired private EmployeeRepository employeeRepository;
//
//    public FieldOptionsDTO getFieldOptions(Integer tenantId, Integer appId, Integer tableId, Integer fieldId, boolean refresh) {
//        MetadataDisplays display = displaysRepository.findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, fieldId)
//                .filter(d -> d.getOptionKeyId() != null)
//                .orElseThrow(() -> new ResourceNotFoundException("No dropdown configuration found for tenantId=" + tenantId + ", appId=" + appId + ", tableId=" + tableId + ", fieldId=" + fieldId));
//
//        FieldOptionsDTO dto = new FieldOptionsDTO();
//        dto.setTenantId(tenantId);
//        dto.setAppId(appId);
//        dto.setTableId(tableId);
//        dto.setFieldId(fieldId);
//        dto.setFieldName(standardFieldsRepository.findById(fieldId)
//                .map(MetadataStandardFields::getFieldName)
//                .orElse("unknown"));
//
//        List<FieldOptionsDTO.Option> options = new ArrayList<>();
//        if (display.getOptionsSourceId() == 5001) { // Internal source
//            MetadataOptionsTable optionsTable = optionsTableRepository
//                    .findByOptionsTableIdAndSourceFieldIdAndOptionKeyId(tableId, fieldId, display.getOptionKeyId())
//                    .orElse(null);
//            if (optionsTable != null && optionsTable.getOptionsTableId().equals(3004)) { // employees table
//                List<String> values = new ArrayList<>();
//                if (fieldId == 1) { // first_name
//                    values = employeeRepository.findDistinctFirstNameByTenantId(tenantId);
//                }
//                options = values.stream()
//                        .map(value -> {
//                            FieldOptionsDTO.Option option = new FieldOptionsDTO.Option();
//                            option.setValue(value);
//                            option.setDisplayName(value);
//                            return option;
//                        })
//                        .collect(Collectors.toList());
//            } else {
//                List<MetadataOptions> staticOptions = optionsRepository
//                        .findByTenantIdAndAppIdAndTableIdAndOptionKeyIdOrderByDisplayOrder(tenantId, appId, tableId, display.getOptionKeyId());
//                options = staticOptions.stream()
//                        .map(opt -> {
//                            FieldOptionsDTO.Option option = new FieldOptionsDTO.Option();
//                            option.setValue(opt.getOptionValue());
//                            option.setDisplayName(opt.getOptionValue());
//                            return option;
//                        })
//                        .collect(Collectors.toList());
//            }
//        }
//
//        dto.setOptions(options);
//        return dto;
//    }
//}



@Service
public class FieldOptionsService {

    @Autowired private MetadataDisplaysRepository displaysRepository;
    @Autowired private MetadataOptionsRepository optionsRepository;
    @Autowired private MetadataOptionsTableRepository optionsTableRepository;
    @Autowired private MetadataStandardFieldsRepository standardFieldsRepository;
    @Autowired private MetadataTablesRepository tablesRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public FieldOptionsDTO getFieldOptions(Integer tenantId, Integer appId, Integer tableId, Integer fieldId, boolean refresh) {
        // Step 1: Validate dropdown configuration
        MetadataDisplays display = displaysRepository.findByTenantIdAndAppIdAndTableIdAndFieldId(tenantId, appId, tableId, fieldId)
                .filter(d -> d.getOptionKeyId() != null)
                .orElseThrow(() -> new ResourceNotFoundException("No dropdown configuration found for tenantId=" + tenantId + ", appId=" + appId + ", tableId=" + tableId + ", fieldId=" + fieldId));

        // Step 2: Initialize DTO
        FieldOptionsDTO dto = new FieldOptionsDTO();
        dto.setTenantId(tenantId);
        dto.setAppId(appId);
        dto.setTableId(tableId);
        dto.setFieldId(fieldId);
        dto.setFieldName(standardFieldsRepository.findById(fieldId)
                .map(MetadataStandardFields::getFieldName)
                .orElse("unknown"));

        // Step 3: Fetch options based on source
        List<FieldOptionsDTO.Option> options = new ArrayList<>();
        Integer optionsSourceId = display.getOptionsSourceId();
        Integer optionKeyId = display.getOptionKeyId();

        if (optionsSourceId == 5001) { // internal (static options)
            List<MetadataOptions> staticOptions = optionsRepository
                    .findByTenantIdAndAppIdAndTableIdAndOptionKeyIdOrderByDisplayOrder(tenantId, appId, tableId, optionKeyId);
            options = staticOptions.stream()
                    .map(opt -> {
                        FieldOptionsDTO.Option option = new FieldOptionsDTO.Option();
                        option.setValue(opt.getOptionValue());
                        option.setDisplayName(opt.getOptionValue());
                        return option;
                    })
                    .collect(Collectors.toList());
        } else if (optionsSourceId == 5002) { // internal table (dynamic options)
            MetadataOptionsTable optionsTable = optionsTableRepository
                    .findByTableOptionId(optionKeyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Options table not found for tableOptionId=" + optionKeyId));

            // Get table and field names
            String tableName = tablesRepository.findById(optionsTable.getOptionsTableId())
                    .map(MetadataTables::getTableName)
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found for tableId=" + optionsTable.getOptionsTableId()));
            String fieldName = standardFieldsRepository.findById(fieldId)
                    .map(MetadataStandardFields::getFieldName)
                    .orElseThrow(() -> new ResourceNotFoundException("Field not found for fieldId=" + fieldId));

            // Dynamic query for distinct values
            String queryStr = "SELECT DISTINCT " + fieldName + " FROM " + tableName + " WHERE tenant_id = :tenantId AND " + fieldName + " IS NOT NULL ORDER BY " + fieldName;
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter("tenantId", tenantId);
            List<String> values = query.getResultList();
            options = values.stream()
                    .map(value -> {
                        FieldOptionsDTO.Option option = new FieldOptionsDTO.Option();
                        option.setValue(value.toString());
                        option.setDisplayName(value.toString());
                        return option;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new UnsupportedOperationException("Unsupported options source: " + optionsSourceId);
        }

        dto.setOptions(options);
        return dto;
    }
}