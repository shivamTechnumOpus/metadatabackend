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
@Table(name = "metadata_shared_database", indexes = {
    @Index(name = "idx_database_name", columnList = "database_name")
})
@Getter
@Setter
public class MetadataSharedDatabase extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "shared_database_id", updatable = false, nullable = false)
    private UUID sharedDatabaseId;

    @NotNull
    @Size(max = 255)
    @Column(name = "host", nullable = false)
    private String host;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @NotNull
    @Size(max = 100)
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    @NotNull
    @Size(max = 100)
    @Column(name = "username", nullable = false)
    private String username;

    @JsonIgnore
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "sharedDatabase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<DatabaseConnection> tenantConfigs = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sharedDatabaseId", sharedDatabaseId)
                .append("host", host)
                .append("port", port)
                .append("databaseName", databaseName)
                .append("isActive", getIsActive())
                .build();
    }
}