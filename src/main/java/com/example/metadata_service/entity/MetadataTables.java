//package com.example.metadata_service.entity;
////
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import jakarta.persistence.*;
////import lombok.Data;
////import lombok.Getter;
////import lombok.Setter;
////
////import java.util.List;
////
////@Entity
////@Table(name = "metadata_tables")
////@Getter
////@Setter
////public class MetadataTables {
////    @Id
////    @Column(name = "table_id")
////    private Integer tableId;
////
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
////    private MetadataApp app;
////
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
////    private MetadataTenants tenant;
////
////    @Column(name = "table_name", nullable = false)
////    private String tableName;
////
////    @Column(name = "is_active", nullable = false)
////    private Boolean isActive;
////
////    @Column(name = "description")
////    private String description;
////
////    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataTableStructure> tableStructures;
////
////    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataFieldOverrides> fieldOverrides;
////
////    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataConstraints> constraints;
////
////    @OneToMany(mappedBy = "sourceTable", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataDependencies> sourceDependencies;
////
////    @OneToMany(mappedBy = "targetTable", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataDependencies> targetDependencies;
////
////    @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataRelationships> relationships;
////
////    @OneToMany(mappedBy = "sourceTable", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataRelationships> sourceRelationships;
////
////    @OneToMany(mappedBy = "targetTable", fetch = FetchType.LAZY)
////    @JsonIgnore
////    private List<MetadataRelationships> targetRelationships;
////
////
////    @Override
////    public String toString() {
////        return "MetadataTables{" +
////                "tableId=" + tableId +
////                ", tableName='" + tableName + '\'' +
////                ", isActive=" + isActive +
////                ", description='" + description + '\'' +
////                '}';
////    }
////
////}



//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.util.UUID;
//@Entity
//@Table(name = "metadata_tables")
//@Getter
//@Setter
//public class MetadataTables extends BaseMetaDataEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "table_id", nullable = false, updatable = false)
//    private UUID tableId;
//
//    @Column(name = "tablecode", nullable = false)
//    private String tableCode;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "app_id", nullable = false)
//    private MetadataApp app;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "tenant_id", nullable = false)
//    private MetadataTenants tenant;
//
//    @Column(name = "table_name", nullable = false)
//    private String tableName;
//
//    @Override
//    public String toString() {
//        return "MetadataTables{" +
//                "tableId=" + tableId +
//                ", tableCode='" + tableCode + '\'' +
//                ", appId=" + (app != null ? app.getAppId() : null) +
//                ", tenantId=" + (tenant != null ? tenant.getTenantId() : null) +
//                ", tableName='" + tableName + '\'' +
//                ", createdAt=" + getCreatedAt() +
//                ", createdBy='" + getCreatedBy() + '\'' +
//                ", updatedAt=" + getUpdatedAt() +
//                ", updatedBy='" + getUpdatedBy() + '\'' +
//                ", deletedAt=" + getDeletedAt() +
//                ", deletedBy='" + getDeletedBy() + '\'' +
//                ", isActive=" + getIsActive() +
//                ", isDeleted=" + getIsDeleted() +
//           '}';
//}
//}

package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_tables",
        uniqueConstraints = @UniqueConstraint(columnNames = {"table_code", "app_code"}))
@Getter
@Setter
public class MetadataTables extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "table_id", updatable = false, nullable = false)
    private UUID tableId;

    @Column(name = "table_code", nullable = false)
    private String tableCode;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @OneToMany(mappedBy = "tableCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataTableStructure> tableStructures = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataTable{" +
                "tableId=" + tableId +
                ", tableCode='" + tableCode + '\'' +
                ", tableName='" + tableName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


