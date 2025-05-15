package com.example.metadata_service.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "metadata_tenants")
@Getter
@Setter
public class MetadataTenants {
    @Id
    @Column(name = "tenant_id")
    private Integer tenantId;

    @Column(name = "tenant_name", nullable = false)
    private String tenantName;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "administrator_email")
    private String administratorEmail;

    @Column(name = "tenant_domain")
    private String tenantDomain;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTables> tables;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTableStructure> tableStructures;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldOverrides> fieldOverrides;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldApiRelationship> apiRelationships;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataConstraints> constraints;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MetadataDependencies> dependencies;

    @OneToMany(mappedBy = "tenant", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<MetadataRelationships> relationships;


    @Override
    public String toString() {
        return "MetadataTenants{tenantId=" + tenantId + ", tenantName='" + tenantName + "', tenantDomain='" + tenantDomain + "'}";
    }

}