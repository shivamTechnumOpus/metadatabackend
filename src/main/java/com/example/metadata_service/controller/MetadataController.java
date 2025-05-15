package com.example.metadata_service.controller;

import com.example.metadata_service.dto.EmployeeDTO;
import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.entity.Employee;
import com.example.metadata_service.exception.ResourceNotFoundException;
import com.example.metadata_service.exception.ValidationException;
import com.example.metadata_service.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @GetMapping("/metadata/{tenantId}/{tableId}")
    public ResponseEntity<TableSchemaDTO> getTableSchema(
            @PathVariable Integer tenantId,
            @PathVariable Integer tableId,
            @RequestParam Integer appId) {

            TableSchemaDTO schema = metadataService.getTableSchema(tenantId, tableId, appId);
            return ResponseEntity.ok(schema);

    }

    @PostMapping("/data/{tenantId}")
    public ResponseEntity<EmployeeDTO> saveEmployee(
            @PathVariable Integer tenantId,
            @RequestParam Integer tableId,
            @RequestParam Integer appId,
            @RequestBody Map<String, Object> data) {

            EmployeeDTO employee = metadataService.saveEmployee(tenantId, tableId, appId, data);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee);

    }

    @GetMapping("/autofill")
    public ResponseEntity<Map<String, String>> autofillField(
            @RequestParam Integer tenantId,
            @RequestParam Integer tableId,
            @RequestParam Integer appId,
            @RequestParam String fieldName,
            @RequestParam String value) {

            Map<String, String> results = metadataService.autofillField(tenantId, tableId, appId, fieldName, value);
            return ResponseEntity.ok(results);

    }
    @GetMapping("/employees/{tenantId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable Integer tenantId) {
        List<EmployeeDTO> employees = metadataService.getAllEmployees(tenantId);
        return ResponseEntity.ok(employees);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}