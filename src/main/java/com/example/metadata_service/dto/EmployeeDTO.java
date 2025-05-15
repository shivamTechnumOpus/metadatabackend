package com.example.metadata_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {
    private Integer employeeId;
    private Integer tenantId;
    private String firstName;
    private String lastName;
    private String password;
    private Long phoneNumber;
    private String streetName;
    private String city;
}