package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataTableStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MetadataTableStructureRepository extends JpaRepository<MetadataTableStructure, Integer> {
    @Query("SELECT ts FROM MetadataTableStructure ts WHERE ts.tenant.tenantId = :tenantId AND ts.table.tableId = :tableId")
    List<MetadataTableStructure> findByTenantIdAndTableId(@Param("tenantId") Integer tenantId, @Param("tableId") Integer tableId);
}