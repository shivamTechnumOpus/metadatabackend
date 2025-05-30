package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_option_displays")
@Getter
@Setter
public class MetadataOptionDisplay extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "option_display_id", updatable = false, nullable = false)
    private UUID optionDisplayId;

    @Column(name = "default_value")
    private String defaultValue;

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
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "options_source_id", referencedColumnName = "options_source_id")
    private MetadataOptionsCategory optionsSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_key_id", referencedColumnName = "option_key_id")
    private MetadataOptionsKey optionKey;

    @Override
    public String toString() {
        return "MetadataOptionDisplay{" +
                "optionDisplayId=" + optionDisplayId +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}