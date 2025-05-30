//package com.example.metadata_service.entity;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "metadata_standard_fields")
//@Getter
//@Setter
//public class MetadataStandardFields {
//    @Id
//    @Column(name = "field_id")
//    private Integer fieldId;
//
//    @Column(name = "field_name", nullable = false)
//    private String fieldName;
//
//    @Column(name = "datatype", nullable = false)
//    private String datatype;
//
//    @Column(name = "display_name")
//    private String displayName;
//
//    @Column(name = "help_text")
//    private String helpText;
//
//    @Column(name = "placeholder")
//    private String placeholder;
//
//    @Column(name = "default_value")
//    private String defaultValue;
//
//    @Column(name = "is_required", nullable = false)
//    private Boolean isRequired;
//
//    @Column(name = "is_unique", nullable = false)
//    private Boolean isUnique;
//
//    @Column(name = "pattern")
//    private String pattern;
//
//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive;
//
//    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataTableStructure> tableStructures;
//
//    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldOverrides> fieldOverrides;
//
//    @OneToMany(mappedBy = "standardField", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldApiRelationship> apiRelationships;
//
//    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataConstraints> constraints;
//
//    @OneToMany(mappedBy = "sourceField", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataDependencies> sourceDependencies;
//
//    @OneToMany(mappedBy = "targetField", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataDependencies> targetDependencies;
//
//    @OneToMany(mappedBy = "sourceField", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataRelationships> sourceRelationships;
//
//    @OneToMany(mappedBy = "targetField", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataRelationships> targetRelationships;
//
//
//    @Override
//    public String toString() {
//        return "MetadataStandardFields{" +
//                "fieldId=" + fieldId +
//                ", fieldName='" + fieldName + '\'' +
//                ", datatype='" + datatype + '\'' +
//                ", displayName='" + displayName + '\'' +
//                ", helpText='" + helpText + '\'' +
//                ", placeholder='" + placeholder + '\'' +
//                ", defaultValue='" + defaultValue + '\'' +
//                ", isRequired=" + isRequired +
//                ", isUnique=" + isUnique +
//                ", pattern='" + pattern + '\'' +
//                ", isActive=" + isActive +
//
//                '}';
//    }
//}



//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "metadata_standard_fields")
//@Getter
//@Setter
//public class MetadataStandardFields extends BaseMetaDataEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "field_id", nullable = false, updatable = false)
//    private UUID fieldId;
//
//    @Column(name = "field_name", nullable = false)
//    private String fieldName;
//
//    @Column(name = "display_name")
//    private String displayName;
//
//    @Column(name = "datatype")
//    private String datatype;
//
//    @Column(name = "field_type")
//    private String fieldType;
//
//    @Column(name = "help_text")
//    private String helpText;
//
//    @Column(name = "placeholder")
//    private String placeholder;
//
//    @Column(name = "default_value")
//    private String defaultValue;
//
//    @Column(name = "is_required")
//    private boolean isRequired;
//
//    @Column(name = "is_unique")
//    private boolean isUnique;
//
//    @Column(name = "is_active")
//    private boolean isActive;
//
//    @Override
//    public String toString() {
//        return "MetadataStandardFields{" +
//                "fieldId=" + fieldId +
//                ", fieldName='" + fieldName + '\'' +
//                ", displayName='" + displayName + '\'' +
//                ", datatype='" + datatype + '\'' +
//                ", fieldType='" + fieldType + '\'' +
//                ", helpText='" + helpText + '\'' +
//                ", placeholder='" + placeholder + '\'' +
//                ", defaultValue='" + defaultValue + '\'' +
//                ", isRequired=" + isRequired +
//                ", isUnique=" + isUnique +
//                ", isActive=" + isActive +
//                ", createdAt=" + getCreatedAt() +
//                ", createdBy='" + getCreatedBy() + '\'' +
//                ", updatedAt=" + getUpdatedAt() +
//                ", updatedBy='" + getUpdatedBy() + '\'' +
//                ", deletedAt=" + getDeletedAt() +
//                ", deletedBy='" + getDeletedBy() + '\'' +
//                ", isActive=" + getIsActive() +
//                ", isDeleted=" + getIsDeleted() +
//          '}';
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
@Table(name = "metadata_standard_fields")
@Getter
@Setter
public class MetadataStandardFields extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "field_id", updatable = false, nullable = false)
    private UUID fieldId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "datatype")
    private String datatype;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "help_text")
    private String helpText;

    @Column(name = "placeholder")
    private String placeholder;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "is_required")
    private Boolean isRequired = false;

    @Column(name = "is_unique")
    private Boolean isUnique = false;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataTableStructure> tableStructures = new HashSet<>();

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataGroupFieldMapping> groupFieldMappings = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataStandardField{" +
                "fieldId=" + fieldId +
                ", fieldName='" + fieldName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", datatype='" + datatype + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", isRequired=" + isRequired +
                ", isUnique=" + isUnique +
                '}';
    }
}


