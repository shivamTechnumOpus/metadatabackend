package com.example.metadata_service.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "metadata_options")
@Data
public class MetadataOptions {
    @Id
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "app_id", nullable = false)
    private Integer appId;

    @Column(name = "table_id", nullable = false)
    private Integer tableId;

    @Column(name = "option_key_id", nullable = false)
    private Integer optionKeyId;

    @Column(name = "option_value", nullable = false)
    private String optionValue;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}