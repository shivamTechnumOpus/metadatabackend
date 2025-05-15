package com.example.metadata_service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "metadata_tables")
@Getter
@Setter
public class MetadataTables {
    @Id
    @Column(name = "table_id")
    private Integer tableId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private MetadataApp app;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTableStructure> tableStructures;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldOverrides> fieldOverrides;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataConstraints> constraints;

    @OneToMany(mappedBy = "sourceTable", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataDependencies> sourceDependencies;

    @OneToMany(mappedBy = "targetTable", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataDependencies> targetDependencies;

    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> relationships;

    @OneToMany(mappedBy = "sourceTable", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> sourceRelationships;

    @OneToMany(mappedBy = "targetTable", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> targetRelationships;


    @Override
    public String toString() {
        return "MetadataTables{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", isActive=" + isActive +
                ", description='" + description + '\'' +
                '}';
    }

}