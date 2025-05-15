package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataOptionsKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataOptionsKeyRepository extends JpaRepository<MetadataOptionsKey, Integer> {
}