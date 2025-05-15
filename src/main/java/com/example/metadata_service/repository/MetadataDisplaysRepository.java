package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataDisplays;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetadataDisplaysRepository extends JpaRepository<MetadataDisplays, Integer> {
    Optional<MetadataDisplays> findByTenantIdAndAppIdAndTableIdAndFieldId(Integer tenantId, Integer appId, Integer tableId, Integer fieldId);
}