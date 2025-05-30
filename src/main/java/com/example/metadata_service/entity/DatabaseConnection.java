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

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_tenant_configs")
@Getter
@Setter
public class DatabaseConnection extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "config_id", updatable = false, nullable = false)
    private UUID configId;

    @Column(name = "tenant_code", unique = true, nullable = false)
    private String tenantCode;

    @Column(name = "data_location_type")
    private String dataLocationType;

    @Column(name = "database_host")
    private String databaseHost;

    @Column(name = "database_port")
    private Integer databasePort;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "database_username")
    private String databaseUsername;

    @Column(name = "database_password")
    private String databasePassword;

    @Column(name = "schema_name")
    private String schemaName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    private MetadataTenants tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_database_id", referencedColumnName = "shared_database_id")
    private MetadataSharedDatabase sharedDatabase;

    @Override
    public String toString() {
        return "MetadataTenantConfig{" +
                "configId=" + configId +
                ", tenantCode='" + tenantCode + '\'' +
                ", dataLocationType='" + dataLocationType + '\'' +
                ", databaseHost='" + databaseHost + '\'' +
                ", databasePort=" + databasePort +
                ", databaseName='" + databaseName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                '}';
    }
}