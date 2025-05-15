package com.example.metadata_service.repository;
import com.example.metadata_service.entity.FieldOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldOptionsRepository extends JpaRepository<FieldOptions, Integer> {
    @Query("SELECT f FROM FieldOptions f " +
            "WHERE f.tenant.tenantId = :tenantId " +
            "AND f.app.appId = :appId " +
            "AND f.table.tableId = :tableId " +
            "AND f.field.fieldId = :fieldId " +
            "AND f.optionGroupId = :optionGroupId " +
            "AND f.isActive = :isActive " +
            "ORDER BY f.sortOrder")
    List<FieldOptions> findByTenantIdAndAppIdAndTableIdAndFieldIdAndOptionGroupIdAndIsActive(
            @Param("tenantId") Integer tenantId,
            @Param("appId") Integer appId,
            @Param("tableId") Integer tableId,
            @Param("fieldId") Integer fieldId,
            @Param("optionGroupId") Integer optionGroupId,
            @Param("isActive") Boolean isActive);
}