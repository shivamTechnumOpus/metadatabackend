package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataDependencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetadataDependenciesRepository extends JpaRepository<MetadataDependencies, Integer> {
    @Query("SELECT d FROM MetadataDependencies d WHERE d.sourceTable.tableId = :sourceTableId AND d.sourceField.fieldId = :sourceFieldId AND d.tenant.tenantId = :tenantId")
    List<MetadataDependencies> findBySourceTableIdAndSourceFieldIdAndTenantId(
            @Param("sourceTableId") Integer sourceTableId,
            @Param("sourceFieldId") Integer sourceFieldId,
            @Param("tenantId") Integer tenantId);
}