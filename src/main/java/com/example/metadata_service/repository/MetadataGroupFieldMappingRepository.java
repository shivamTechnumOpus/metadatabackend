package com.example.metadata_service.repository;
import com.example.metadata_service.entity.MetadataGroupFieldMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MetadataGroupFieldMappingRepository extends JpaRepository<MetadataGroupFieldMapping, Integer> {
    @Query("SELECT m FROM MetadataGroupFieldMapping m WHERE m.tenant.tenantId = :tenantId AND m.table.tableId = :tableId AND m.groupId = :groupId")
    List<MetadataGroupFieldMapping> findByTenantIdAndTableIdAndGroupId(
            @Param("tenantId") Integer tenantId,
            @Param("tableId") Integer tableId,
            @Param("groupId") Integer groupId);

    @Query("SELECT m FROM MetadataGroupFieldMapping m WHERE m.tenant.tenantId = :tenantId AND m.app.appId = :appId")
    List<MetadataGroupFieldMapping> findByTenantIdAndAppId(
            @Param("tenantId") Integer tenantId,
            @Param("appId") Integer appId);
}