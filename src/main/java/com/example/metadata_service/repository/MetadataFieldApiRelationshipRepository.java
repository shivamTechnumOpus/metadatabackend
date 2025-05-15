package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataFieldApiRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataFieldApiRelationshipRepository extends JpaRepository<MetadataFieldApiRelationship, Integer> {
    @Query("SELECT ar FROM MetadataFieldApiRelationship ar WHERE ar.tenant.tenantId = :tenantId AND ar.app.appId = :appId")
    List<MetadataFieldApiRelationship> findByTenantIdAndAppId(@Param("tenantId") Integer tenantId, @Param("appId") Integer appId);

    @Query("SELECT ar FROM MetadataFieldApiRelationship ar WHERE ar.tenant.tenantId = :tenantId AND ar.app.appId = :appId AND ar.standardField.fieldId = :fieldId")
    Optional<MetadataFieldApiRelationship> findByTenantIdAndAppIdAndStandardFieldId(
            @Param("tenantId") Integer tenantId,
            @Param("appId") Integer appId,
            @Param("fieldId") Integer fieldId);
}