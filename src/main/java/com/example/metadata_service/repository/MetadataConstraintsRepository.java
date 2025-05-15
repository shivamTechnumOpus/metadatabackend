package com.example.metadata_service.repository;
import com.example.metadata_service.entity.MetadataConstraints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MetadataConstraintsRepository extends JpaRepository<MetadataConstraints, Integer> {
    @Query("SELECT c FROM MetadataConstraints c WHERE c.tenant.tenantId = :tenantId AND c.table.tableId = :tableId AND c.field.fieldId = :fieldId")
    Optional<MetadataConstraints> findByTenantIdAndTableIdAndFieldId(
            @Param("tenantId") Integer tenantId,
            @Param("tableId") Integer tableId,
            @Param("fieldId") Integer fieldId);
}