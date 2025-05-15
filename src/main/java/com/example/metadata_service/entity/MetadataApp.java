package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "metadata_app")
@Getter
@Setter
public class MetadataApp {
    @Id
    @Column(name = "app_id")
    private Integer appId;

    @Column(name = "app_name", nullable = false)
    private String appName;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTables> tables;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTableStructure> tableStructures;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldOverrides> fieldOverrides;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldApiRelationship> apiRelationships;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataConstraints> constraints;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataDependencies> dependencies;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> relationships;

    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataGroupFieldMapping> groupFieldMappings;

    @Override
    public String toString() {
        return "MetadataApp{" +
                "appId=" + appId +
                ", appName='" + appName + '\'' +
                '}';
    }


}