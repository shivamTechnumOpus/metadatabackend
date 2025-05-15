package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadata_field_api_relationship")
@Data
public class MetadataFieldApiRelationship {
    @Id
    @Column(name = "api_fieldrelation_id")
    private Integer apiFieldRelationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "standard_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields standardField;

    @Column(name = "onified_api_id", nullable = false)
    private Integer onifiedApiId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private MetadataApp app;


}