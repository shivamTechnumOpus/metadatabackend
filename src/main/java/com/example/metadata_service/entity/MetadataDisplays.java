package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metadata_displays")
@Data
public class MetadataDisplays {
    @Id
    @Column(name = "display_id")
    private Integer displayId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "app_id", nullable = false)
    private Integer appId;

    @Column(name = "table_id", nullable = false)
    private Integer tableId;

    @Column(name = "field_id", nullable = false)
    private Integer fieldId;

    @Column(name = "options_source_id", nullable = false)
    private Integer optionsSourceId;

    @Column(name = "option_key_id")
    private Integer optionKeyId;

    @Column(name = "default_value")
    private String defaultValue;
}
