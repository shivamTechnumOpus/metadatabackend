package com.example.metadata_service.repository;


import com.example.metadata_service.entity.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Integer> {
}
