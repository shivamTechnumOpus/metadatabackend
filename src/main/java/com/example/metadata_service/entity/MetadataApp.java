package com.example.metadata_service.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "metadata_app")
//@Getter
//@Setter
//public class MetadataApp {
//    @Id
//    @Column(name = "app_id")
//    private Integer appId;
//
//    @Column(name = "app_name", nullable = false)
//    private String appName;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataTables> tables;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataTableStructure> tableStructures;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldOverrides> fieldOverrides;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataFieldApiRelationship> apiRelationships;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataConstraints> constraints;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataDependencies> dependencies;
//
//    @OneToMany(mappedBy = "app", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<MetadataRelationships> relationships;
//
//
//    @Override
//    public String toString() {
//        return "MetadataApp{" +
//                "appId=" + appId +
//                ", appName='" + appName + '\'' +
//                '}';
//    }
//
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
//@Table(name = "metadata_app")
//@Getter
//@Setter
//public class MetadataApp extends BaseMetaDataEntity {
//
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "app_id", columnDefinition = "UUID")
//    private UUID appId;
//
//    @Column(name = "app_code", nullable = false, unique = true)
//    private String appCode;
//
//    @Column(name = "app_name", nullable = false)
//    private String appName;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "version")
//    private String version;
//
//    @Override
//    public String toString() {
//        return "MetadataApp{" +
//                "appId=" + appId +
//                ", appCode='" + appCode + '\'' +
//                ", appName='" + appName + '\'' +
//                ", description='" + description + '\'' +
//                ", version='" + version + '\'' +
//                '}';
//    }
//}
//




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_app", indexes = {
    @Index(name = "idx_app_code", columnList = "app_code")
})
@Getter
@Setter
public class MetadataApp extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "app_id", updatable = false, nullable = false)
    private UUID appId;

    @NotNull
    @Size(max = 50)
    @Column(name = "app_code", unique = true, nullable = false)
    private String appCode;

    @Size(max = 100)
    @Column(name = "app_name")
    private String appName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Size(max = 20)
    @Column(name = "status")
    private String status;

    @Size(max = 20)
    @Column(name = "version")
    private String version;

    @OneToMany(mappedBy = "appCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataTables> tables = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("appId", appId)
                .append("appCode", appCode)
                .append("appName", appName)
                .append("status", status)
                .append("isActive", getIsActive())
                .build();
    }
}

