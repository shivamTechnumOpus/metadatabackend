package com.example.metadata_service.entity;

//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "metadata_tenants")
//@Getter
//@Setter
//public class MetadataTenants {
//    @Id
//    @Column(name = "tenant_id")
//    private Integer tenantId;
//
//    @Column(name = "tenant_name", nullable = false)
//    private String tenantName;
//
//    @Column(name = "industry_type")
//    private String industryType;
//
//    @Column(name = "contact_name")
//    private String contactName;
//
//    @Column(name = "contact_email")
//    private String contactEmail;
//
//    @Column(name = "contact_phone")
//    private String contactPhone;
//
//    @Column(name = "administrator_email")
//    private String administratorEmail;
//
//    @Column(name = "tenant_domain")
//    private String tenantDomain;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataTables> tables;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataTableStructure> tableStructures;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldOverrides> fieldOverrides;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldApiRelationship> apiRelationships;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataConstraints> constraints;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<MetadataDependencies> dependencies;
//
//    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<MetadataRelationships> relationships;
//
//
//    @Override
//    public String toString() {
//        return "MetadataTenants{tenantId=" + tenantId + ", tenantName='" + tenantName + "', tenantDomain='" + tenantDomain + "'}";
//    }
//
//}



//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "metadata_tenants")
//@Getter
//@Setter
//public class MetadataTenants extends BaseMetaDataEntity {
//
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "tenant_id", columnDefinition = "UUID")
//    private UUID tenantId;
//
//    @Column(name = "tenant_code", nullable = false, unique = true)
//    private String tenantCode;
//
//    @Column(name = "tenant_name", nullable = false)
//    private String tenantName;
//
//    @Column(name = "description")
//    private String description;
//
//    @Override
//    public String toString() {
//        return "MetadataTenant{" +
//                "tenantId=" + tenantId +
//                ", tenantCode='" + tenantCode + '\'' +
//                ", tenantName='" + tenantName + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }
//}



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_tenants", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code")
})
@Getter
@Setter
public class MetadataTenants extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tenant_id", updatable = false, nullable = false)
    private UUID tenantId;

    @NotNull
    @Size(max = 50)
    @Column(name = "tenant_code", unique = true, nullable = false)
    private String tenantCode;

    @Size(max = 100)
    @Column(name = "tenant_name")
    private String tenantName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Size(max = 20)
    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<DatabaseConnection> tenantConfigs = new HashSet<>();

    @OneToMany(mappedBy = "tenantCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataTables> tables = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("tenantId", tenantId)
                .append("tenantCode", tenantCode)
                .append("tenantName", tenantName)
                .append("status", status)
                .append("isActive", getIsActive())
                .build();
    }
}