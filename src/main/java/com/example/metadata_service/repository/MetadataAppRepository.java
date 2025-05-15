package com.example.metadata_service.repository;


import com.example.metadata_service.entity.MetadataApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataAppRepository extends JpaRepository<MetadataApp, Integer> {
}