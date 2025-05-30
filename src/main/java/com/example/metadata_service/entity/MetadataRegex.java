package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_regex")
@Getter
@Setter
public class MetadataRegex extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "regex_id", updatable = false, nullable = false)
    private UUID regexId;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "max_length")
    private Integer maxLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    private MetadataTables tableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields tableField;

    @Override
    public String toString() {
        return "MetadataRegex{" +
                "regexId=" + regexId +
                ", pattern='" + pattern + '\'' +
                ", maxLength=" + maxLength +
                '}';
    }
}