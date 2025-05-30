//package com.example.metadata_service.entity;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "employees")
//@Getter
//@Setter
//public class Employee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "employee_id")
//    private Integer employeeId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    @JsonManagedReference
//    private MetadataTenants tenant;
//
//    @Column(name = "first_name")
//    private String firstName;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "password")
//    private String password;
//
//    @Column(name = "phone_number")
//    private Long phoneNumber;
//
//    @Column(name = "street_name")
//    private String streetName;
//
//    @Column(name = "city")
//    private String city;
//
//
//    @Override
//    public String toString() {
//        return "Employee{employeeId=" + employeeId + ", firstName='" + firstName + "', lastName='" + lastName + "'}";
//    }
//
//}