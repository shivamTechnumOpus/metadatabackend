package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MetadataOptionsRepository extends JpaRepository<MetadataOptions, Integer> {
    List<MetadataOptions> findByTenantIdAndAppIdAndTableIdAndOptionKeyIdOrderByDisplayOrder(Integer tenantId, Integer appId, Integer tableId, Integer optionKeyId);
}
