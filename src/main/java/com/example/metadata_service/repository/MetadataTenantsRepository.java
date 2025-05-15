package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataTenants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataTenantsRepository extends JpaRepository<MetadataTenants, Integer> {
}