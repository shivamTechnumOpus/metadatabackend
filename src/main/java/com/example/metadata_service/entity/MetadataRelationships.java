package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadata_relationships")
@Data
public class MetadataRelationships {
    @Id
    @Column(name = "relationship_id")
    private Integer relationshipId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private MetadataApp app;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
    private MetadataTables table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_table_id", referencedColumnName = "table_id")
    private MetadataTables sourceTable;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields sourceField;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_table_id", referencedColumnName = "table_id")
    private MetadataTables targetTable;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields targetField;

    @Column(name = "relationship_type", nullable = false)
    private String relationshipType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "description")
    private String description;

}