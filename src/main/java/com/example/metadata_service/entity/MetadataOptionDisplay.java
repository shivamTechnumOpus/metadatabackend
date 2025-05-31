package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_option_displays", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code"),
    @Index(name = "idx_app_code", columnList = "app_code"),
    @Index(name = "idx_table_code", columnList = "table_code")
})
@Getter
@Setter
public class MetadataOptionDisplay extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "option_display_id", updatable = false, nullable = false)
    private UUID optionDisplayId;

    @Size(max = 255)
    @Column(name = "default_value")
    private String defaultValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = true)
    @JsonIgnoreProperties({"tenantConfigs", "tables"})
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    @JsonIgnoreProperties({"tables"})
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    @JsonIgnoreProperties({"tableStructures", "appCode", "tenantCode"})
    private MetadataTables tableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    @JsonIgnoreProperties({"tableStructures", "groupFieldMappings"})
    private MetadataStandardFields field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "options_source_id", referencedColumnName = "options_source_id")
    @JsonIgnoreProperties({"optionDisplays"})
    private MetadataOptionsCategory optionsSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_key_id", referencedColumnName = "option_key_id")
    @JsonIgnoreProperties({"options", "optionDisplays"})
    private MetadataOptionsKey optionKey;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("optionDisplayId", optionDisplayId)
                .append("defaultValue", defaultValue)
                .append("isActive", getIsActive())
                .build();
    }
}