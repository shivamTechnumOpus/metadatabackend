package com.example.metadata_service.controller;

import com.example.metadata_service.dto.FieldOptionsConfigDTO;
import com.example.metadata_service.dto.FieldOptionsDTO;
import com.example.metadata_service.dto.FieldOptionsValuesDTO;
import com.example.metadata_service.service.FieldOptionsService;
//import com.example.metadata_service.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class OptionsController {
//    @Autowired
//    private MetadataService metadataService;

    @Autowired private FieldOptionsService fieldOptionsService;

//    @GetMapping("/{tenantId}/{tableId}/{appId}/{fieldId}")
//    public ResponseEntity<FieldOptionsDTO> getFieldOptions(
//            @PathVariable Integer tenantId,
//            @PathVariable Integer tableId,
//            @PathVariable Integer appId,
//            @PathVariable Integer fieldId,
//            @RequestParam(defaultValue = "false") boolean refresh) {
//        FieldOptionsDTO options = metadataService.getFieldOptions(tenantId, appId, tableId, fieldId, refresh);
//        return ResponseEntity.ok(options);
//    }


    @GetMapping("/options/{tenantId}/{tableId}/{appId}/{fieldId}")
    public ResponseEntity<FieldOptionsDTO> getFieldOptions(
            @PathVariable Integer tenantId,
            @PathVariable Integer tableId,
            @PathVariable Integer appId,
            @PathVariable Integer fieldId,
            @RequestParam(defaultValue = "false") boolean refresh) {
        FieldOptionsDTO options = fieldOptionsService.getFieldOptions(tenantId, appId, tableId, fieldId, refresh);
        return ResponseEntity.ok(options);
    }

}