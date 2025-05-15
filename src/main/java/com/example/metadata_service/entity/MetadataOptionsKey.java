package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metadata_options_key")
@Data
public class MetadataOptionsKey {
    @Id
    @Column(name = "option_key_id")
    private Integer optionKeyId;

    @Column(name = "key_name", nullable = false)
    private String keyName;

    @Column(name = "description")
    private String description;
}