package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "metadata_group_field_mapping")
@Data
public class MetadataGroupFieldMapping {
    @Id
    @Column(name = "mapping_id")
    private Integer mappingId;

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

    @Column(name = "group_id", nullable = false)
    private Integer groupId;


}