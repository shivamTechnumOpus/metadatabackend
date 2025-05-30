//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "metadata_relationships")
//@Data
//public class MetadataRelationships {
//    @Id
//    @Column(name = "relationship_id")
//    private Integer relationshipId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
//    private MetadataApp app;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    private MetadataTenants tenant;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
//    private MetadataTables table;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "source_table_id", referencedColumnName = "table_id")
//    private MetadataTables sourceTable;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields sourceField;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "target_table_id", referencedColumnName = "table_id")
//    private MetadataTables targetTable;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "target_field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields targetField;
//
//    @Column(name = "relationship_type", nullable = false)
//    private String relationshipType;
//
//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive;
//
//    @Column(name = "description")
//    private String description;
//
//}


package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_relationships")
@Getter
@Setter
public class MetadataRelationships extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "relationship_id", updatable = false, nullable = false)
    private UUID relationshipId;

    @Column(name = "relationship_type")
    private String relationshipType;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    private MetadataTables tableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "source_table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    private MetadataTables sourceTableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields sourceField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "target_table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    private MetadataTables targetTableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields targetField;

    @Override
    public String toString() {
        return "MetadataRelationship{" +
                "relationshipId=" + relationshipId +
                ", relationshipType='" + relationshipType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}