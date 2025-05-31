//package com.example.metadata_service.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "database_connections")
//@Getter
//@Setter
//public class DatabaseConnection extends BaseMetaDataEntity {
//
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "database_id", columnDefinition = "UUID")
//    private UUID databaseId;
//
//    @Column(name = "tenant_code")
//    private String tenantCode;
//
//    @Column(name = "data_location_type")
//    private String dataLocationType;
//
//    @Column(name = "database_host")
//    private String databaseHost;
//
//    @Column(name = "database_port")
//    private Integer databasePort;
//
//    @Column(name = "database_name")
//    private String databaseName;
//
//    @Column(name = "database_username")
//    private String databaseUsername;
//
//    @Column(name = "database_password")
//    private String databasePassword;
//
//    @Column(name = "schema_name")
//    private String schemaName;
//
//    @ManyToOne
//    @JoinColumn(name = "shared_database_id")
//    private SharedDatabase sharedDatabase;
//}


package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;



@Entity
@Table(name = "metadata_tenant_configs", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code")
})
@Getter
@Setter
public class DatabaseConnection extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "config_id", updatable = false, nullable = false)
    private UUID configId;

    @NotNull
    @Size(max = 50)
    @Column(name = "tenant_code", unique = true, nullable = false)
    private String tenantCode;

    @NotNull
    @Column(name = "data_location_type", nullable = false)
    private String dataLocationType;

    @NotNull
    @Size(max = 255)
    @Column(name = "database_host", nullable = false)
    private String databaseHost;

    @NotNull
    @Column(name = "database_port", nullable = false)
    private Integer databasePort;

    @NotNull
    @Size(max = 100)
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    @NotNull
    @Size(max = 100)
    @Column(name = "database_username", nullable = false)
    private String databaseUsername;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "database_password", nullable = false)
    private String databasePassword;

    @Size(max = 100)
    @Column(name = "schema_name")
    private String schemaName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    @JsonIgnoreProperties({"tenantConfigs", "tables"})
    private MetadataTenants tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_database_id", referencedColumnName = "shared_database_id")
    @JsonIgnoreProperties({"tenantConfigs"})
    private MetadataSharedDatabase sharedDatabase;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("configId", configId)
                .append("tenantCode", tenantCode)
                .append("dataLocationType", dataLocationType)
                .append("schemaName", schemaName)
                .append("isActive", getIsActive())
                .build();
    }
}