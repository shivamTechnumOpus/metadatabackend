package com.example.metadata_service.controller;

import com.example.metadata_service.dto.FieldOptionsConfigDTO;
import com.example.metadata_service.dto.FieldOptionsDTO;
import com.example.metadata_service.dto.FieldOptionsValuesDTO;
import com.example.metadata_service.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api/options")
public class OptionsController {
    @Autowired
    private MetadataService metadataService;

    @GetMapping("/{tenantId}/{tableId}/{appId}/{fieldId}")
    public ResponseEntity<FieldOptionsDTO> getFieldOptions(
            @PathVariable Integer tenantId,
            @PathVariable Integer tableId,
            @PathVariable Integer appId,
            @PathVariable Integer fieldId,
            @RequestParam(defaultValue = "false") boolean refresh) {
        FieldOptionsDTO options = metadataService.getFieldOptions(tenantId, appId, tableId, fieldId, refresh);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/config")
    public ResponseEntity<Map<String, Object>> saveFieldOptionsConfig(@RequestBody FieldOptionsConfigDTO dto) {
        Integer configId = metadataService.saveFieldOptionsConfig(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("optionConfigId", configId, "message", "Configuration saved"));
    }

    @PostMapping("/values")
    public ResponseEntity<Map<String, Object>> saveFieldOptionsValues(@RequestBody FieldOptionsValuesDTO dto) {
        List<Integer> optionIds = metadataService.saveFieldOptionsValues(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("optionIds", optionIds, "message", "Options saved"));
    }

    @DeleteMapping("/config/{optionConfigId}")
    public ResponseEntity<Map<String, String>> deleteFieldOptionsConfig(@PathVariable Integer optionConfigId) {
        metadataService.deleteFieldOptionsConfig(optionConfigId);
        return ResponseEntity.ok(Map.of("message", "Configuration deleted"));
    }
}