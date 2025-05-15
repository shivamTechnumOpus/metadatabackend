package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadata_table_structure")
@Getter
@Setter
public class MetadataTableStructure {
    @Id
    @Column(name = "table_structure_id")
    private Integer tableStructureId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
    private MetadataTables table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    private MetadataApp app;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;
    @Override
    public String toString() {
        return "MetadataTableStructure{" +
                "tableStructureId=" + tableStructureId +
                '}';
    }



}