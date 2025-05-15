package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataRelationships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataRelationshipsRepository extends JpaRepository<MetadataRelationships, Integer> {
    @Query("SELECT r FROM MetadataRelationships r WHERE r.table.tableId = :tableId AND r.sourceField.fieldId = :sourceFieldId AND r.tenant.tenantId = :tenantId")
    Optional<MetadataRelationships> findByTableIdAndSourceFieldIdAndTenantId(
            @Param("tableId") Integer tableId,
            @Param("sourceFieldId") Integer sourceFieldId,
            @Param("tenantId") Integer tenantId);

    @Query("SELECT r FROM MetadataRelationships r WHERE r.table.tableId = :tableId AND r.sourceField.fieldId = :sourceFieldId AND r.relationshipType = :relationshipType AND r.tenant.tenantId = :tenantId")
    Optional<MetadataRelationships> findByTableIdAndSourceFieldIdAndRelationshipTypeAndTenantId(
            @Param("tableId") Integer tableId,
            @Param("sourceFieldId") Integer sourceFieldId,
            @Param("relationshipType") String relationshipType,
            @Param("tenantId") Integer tenantId);

    @Query("SELECT r FROM MetadataRelationships r WHERE r.tenant.tenantId = :tenantId AND r.table.tableId = :tableId AND r.isActive = true")
    List<MetadataRelationships> findByTenantIdAndTableIdAndIsActiveTrue(
            @Param("tenantId") Integer tenantId,
            @Param("tableId") Integer tableId);
}