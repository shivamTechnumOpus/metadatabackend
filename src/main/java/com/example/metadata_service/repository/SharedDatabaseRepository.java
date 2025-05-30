package com.example.metadata_service.repository;

import com.example.metadata_service.entity.SharedDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface  SharedDatabaseRepository extends JpaRepository<SharedDatabase, UUID> {
}
