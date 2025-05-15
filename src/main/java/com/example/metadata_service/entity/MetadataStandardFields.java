package com.example.metadata_service.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "metadata_standard_fields")
@Getter
@Setter
public class MetadataStandardFields {
    @Id
    @Column(name = "field_id")
    private Integer fieldId;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "datatype", nullable = false)
    private String datatype;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "help_text")
    private String helpText;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Column(name = "is_unique", nullable = false)
    private Boolean isUnique;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataTableStructure> tableStructures;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldOverrides> fieldOverrides;

    @OneToMany(mappedBy = "standardField", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataFieldApiRelationship> apiRelationships;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataConstraints> constraints;

    @OneToMany(mappedBy = "sourceField", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataDependencies> sourceDependencies;

    @OneToMany(mappedBy = "targetField", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataDependencies> targetDependencies;

    @OneToMany(mappedBy = "sourceField", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> sourceRelationships;

    @OneToMany(mappedBy = "targetField", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MetadataRelationships> targetRelationships;


    @Override
    public String toString() {
        return "MetadataStandardFields{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                ", datatype='" + datatype + '\'' +
                ", displayName='" + displayName + '\'' +
                ", helpText='" + helpText + '\'' +
                ", placeholder='" + placeholder + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", isRequired=" + isRequired +
                ", isUnique=" + isUnique +
                ", pattern='" + pattern + '\'' +
                ", isActive=" + isActive +

                '}';
    }
}