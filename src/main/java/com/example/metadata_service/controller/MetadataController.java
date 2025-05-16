package com.example.metadata_service.controller;

//import com.example.metadata_service.dto.EmployeeDTO;
//import com.example.metadata_service.dto.TableSchemaDTO;
//import com.example.metadata_service.entity.Employee;
//import com.example.metadata_service.exception.ResourceNotFoundException;
//import com.example.metadata_service.exception.ValidationException;
//import com.example.metadata_service.service.MetadataService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//@CrossOrigin
//@RestController
//@RequestMapping("/api")
//public class MetadataController {
//
//    @Autowired
//    private MetadataService metadataService;
//
//    @GetMapping("/metadata/{tenantId}/{tableId}")
//    public ResponseEntity<TableSchemaDTO> getTableSchema(
//            @PathVariable Integer tenantId,
//            @PathVariable Integer tableId,
//            @RequestParam Integer appId) {
//
//            TableSchemaDTO schema = metadataService.getTableSchema(tenantId, tableId, appId);
//            return ResponseEntity.ok(schema);
//
//    }
//
//    @PostMapping("/data/{tenantId}")
//    public ResponseEntity<EmployeeDTO> saveEmployee(
//            @PathVariable Integer tenantId,
//            @RequestParam Integer tableId,
//            @RequestParam Integer appId,
//            @RequestBody Map<String, Object> data) {
//
//            EmployeeDTO employee = metadataService.saveEmployee(tenantId, tableId, appId, data);
//            return ResponseEntity.status(HttpStatus.CREATED).body(employee);
//
//    }
//
//    @GetMapping("/autofill")
//    public ResponseEntity<Map<String, String>> autofillField(
//            @RequestParam Integer tenantId,
//            @RequestParam Integer tableId,
//            @RequestParam Integer appId,
//            @RequestParam String fieldName,
//            @RequestParam String value) {
//
//            Map<String, String> results = metadataService.autofillField(tenantId, tableId, appId, fieldName, value);
//            return ResponseEntity.ok(results);
//
//    }
//    @GetMapping("/employees/{tenantId}")
//    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable Integer tenantId) {
//        List<EmployeeDTO> employees = metadataService.getAllEmployees(tenantId);
//        return ResponseEntity.ok(employees);
//    }
//
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
//    }
//}





/// ////////////////////////////////////



import com.example.metadata_service.dto.EmployeeDTO;
import com.example.metadata_service.dto.FieldOptionsDTO;
import com.example.metadata_service.dto.TableSchemaDTO;
import com.example.metadata_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class MetadataController {

    @Autowired private SchemaService schemaService;
    @Autowired private EmployeeService employeeService;
    @Autowired private FieldOptionsService fieldOptionsService;
    @Autowired private AutofillService autofillService;

    @PostMapping("/data/{tenantId}")
    public ResponseEntity<EmployeeDTO> saveEmployee(
            @PathVariable Integer tenantId,
            @RequestParam Integer tableId,
            @RequestParam Integer appId,
            @RequestBody Map<String, Object> data) {
        employeeService.initiateSaveEmployee(tenantId, tableId, appId, data);
        // Note: In a real Saga, the response might be async or polled.
        // Here, we assume synchronous response for simplicity.
        return ResponseEntity.ok(new EmployeeDTO()); // Placeholder; adjust based on Saga outcome
    }

    @GetMapping("/employees/{tenantId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable Integer tenantId) {
        List<EmployeeDTO> employees = employeeService.getAllEmployees(tenantId);
        return ResponseEntity.ok(employees);
    }



    @GetMapping("/schema/{tenantId}/{tableId}/{appId}")
    public ResponseEntity<TableSchemaDTO> getTableSchema(
            @PathVariable Integer tenantId,
            @PathVariable Integer tableId,
            @PathVariable Integer appId) {
        TableSchemaDTO schema = schemaService.getTableSchema(tenantId, tableId, appId);
        return ResponseEntity.ok(schema);
    }

    @GetMapping("/autofill/{tenantId}/{tableId}/{appId}/{sourceFieldName}")
    public ResponseEntity<Map<String, String>> autofillField(
            @PathVariable Integer tenantId,
            @PathVariable Integer tableId,
            @PathVariable Integer appId,
            @PathVariable String sourceFieldName,
            @RequestParam String sourceValue) {
        Map<String, String> results = autofillService.autofillField(tenantId, tableId, appId, sourceFieldName, sourceValue);
        return ResponseEntity.ok(results);
    }
}


