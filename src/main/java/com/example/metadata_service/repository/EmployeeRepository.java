package com.example.metadata_service.repository;

import com.example.metadata_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
@Query("SELECT e FROM Employee e WHERE e.tenant.tenantId = :tenantId AND e.firstName = :firstName")
Optional<Employee> findByTenantIdAndFirstName(@Param("tenantId") Integer tenantId, @Param("firstName") String firstName);


    @Query("SELECT e FROM Employee e WHERE e.tenant.tenantId = :tenantId AND e.firstName = :firstName")
    List<Employee> findAllByTenantIdAndFirstName(@Param("tenantId") Integer tenantId, @Param("firstName") String firstName);

    @Query("SELECT e FROM Employee e WHERE e.tenant.tenantId = :tenantId ")
    List<Employee> findAllByTenantId(Integer tenantId);

    @Query("SELECT DISTINCT e.firstName FROM Employee e WHERE e.tenant.tenantId = :tenantId AND e.firstName IS NOT NULL")
    List<String> findDistinctFirstNameByTenantId(Integer tenantId);
}