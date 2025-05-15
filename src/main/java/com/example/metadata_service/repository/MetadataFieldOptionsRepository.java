package com.example.metadata_service.repository;
import com.example.metadata_service.entity.MetadataFieldOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MetadataFieldOptionsRepository extends JpaRepository<MetadataFieldOptions, Integer> {
    @Query("SELECT m FROM MetadataFieldOptions m " +
            "WHERE m.tenant.tenantId = :tenantId " +
            "AND m.app.appId = :appId " +
            "AND m.table.tableId = :tableId " +
            "AND m.field.fieldId = :fieldId")
    Optional<MetadataFieldOptions> findByTenantIdAndAppIdAndTableIdAndFieldId(
            @Param("tenantId") Integer tenantId,
            @Param("appId") Integer appId,
            @Param("tableId") Integer tableId,
            @Param("fieldId") Integer fieldId);
}