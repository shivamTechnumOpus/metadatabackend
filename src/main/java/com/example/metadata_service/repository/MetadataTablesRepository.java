package com.example.metadata_service.repository;


import com.example.metadata_service.entity.MetadataTables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataTablesRepository extends JpaRepository<MetadataTables, Integer> {
    Optional<MetadataTables> findById(Integer tableId);

    @Query("SELECT t FROM MetadataTables t WHERE t.tenant.tenantId = :tenantId AND t.app.appId = :appId")
    List<MetadataTables> findByTenantIdAndAppId(@Param("tenantId") Integer tenantId, @Param("appId") Integer appId);
}