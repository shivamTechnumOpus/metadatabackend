package com.example.metadata_service.repository;

import com.example.metadata_service.entity.MetadataOptionsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MetadataOptionsCategoryRepository extends JpaRepository<MetadataOptionsCategory, Integer> {
}
