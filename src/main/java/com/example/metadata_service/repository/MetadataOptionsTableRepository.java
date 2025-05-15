package com.example.metadata_service.repository;
import com.example.metadata_service.entity.MetadataOptionsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetadataOptionsTableRepository extends JpaRepository<MetadataOptionsTable, Integer> {
    Optional<MetadataOptionsTable> findByOptionsTableIdAndSourceFieldIdAndOptionKeyId(Integer optionsTableId, Integer sourceFieldId, Integer optionKeyId);
}