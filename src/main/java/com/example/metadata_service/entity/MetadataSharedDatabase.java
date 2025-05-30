//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "shared_databases")
//@Getter
//@Setter
//public class SharedDatabase extends BaseMetaDataEntity {
//
//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "shared_database_id", columnDefinition = "UUID")
//    private UUID sharedDatabaseId;
//
//    @Column(name = "tenant_code")
//    private String tenantCode;
//
//    @Column(name = "host")
//    private String host;
//
//    @Column(name = "port")
//    private Integer port;
//
//    @Column(name = "database_name")
//    private String databaseName;
//
//    @Column(name = "username")
//    private String username;
//
//    @Column(name = "password")
//    private String password;
//
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
@Table(name = "metadata_shared_database")
@Getter
@Setter
public class MetadataSharedDatabase extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "shared_database_id", updatable = false, nullable = false)
    private UUID sharedDatabaseId;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "database_name")
    private String databaseName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "sharedDatabase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<DatabaseConnection> tenantConfigs = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataSharedDatabase{" +
                "sharedDatabaseId=" + sharedDatabaseId +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", databaseName='" + databaseName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}