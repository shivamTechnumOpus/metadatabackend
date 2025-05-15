package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadata_field_overrides")
@Data
public class MetadataFieldOverrides {
    @Id

    @Column(name = "override_id")
    private Integer overrideId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private MetadataApp app;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
    private MetadataTables table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @Column(name = "overridden_field")
    private String overriddenField;

    @Column(name = "override_value")
    private String overrideValue;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


}