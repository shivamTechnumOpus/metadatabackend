package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metadata_options_category")
@Data
public class MetadataOptionsCategory {
    @Id
    @Column(name = "options_source_id")
    private Integer optionsSourceId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description")
    private String description;
}