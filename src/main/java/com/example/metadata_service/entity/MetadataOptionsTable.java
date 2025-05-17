package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metadata_options_table")
@Data
public class MetadataOptionsTable {
    @Id
    @Column(name = "table_option_id")
    private Integer tableOptionId;

    @Column(name = "options_table_id", nullable = false)
    private Integer optionsTableId;

//    @Column(name = "source_field_id")
//    private Integer sourceFieldId;
//
//    @Column(name = "option_key_id")
//    private Integer optionKeyId;

    @ManyToOne
    @JoinColumn(name = "options_source_id", nullable = false)
    private MetadataOptionsCategory optionsSource;

    @Column(name = "description")
    private String description;
}