package com.example.metadata_service.repository;


import com.example.metadata_service.entity.MetadataStandardFields;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetadataStandardFieldsRepository extends JpaRepository<MetadataStandardFields, Integer> {
    Optional<MetadataStandardFields> findById(Integer fieldId);
}