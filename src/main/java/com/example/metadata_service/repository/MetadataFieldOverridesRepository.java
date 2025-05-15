package com.example.metadata_service.repository;
import com.example.metadata_service.entity.MetadataFieldOverrides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface MetadataFieldOverridesRepository extends JpaRepository<MetadataFieldOverrides, Integer> {
    @Query("SELECT o FROM MetadataFieldOverrides o WHERE o.tenant.tenantId = :tenantId " +
            "AND o.app.appId = :appId AND o.table.tableId = :tableId AND o.field.fieldId = :fieldId")
    List<MetadataFieldOverrides> findAllByTenantIdAndAppIdAndTableIdAndFieldId(
            @Param("tenantId") Integer tenantId,
            @Param("appId") Integer appId,
            @Param("tableId") Integer tableId,
            @Param("fieldId") Integer fieldId);
}