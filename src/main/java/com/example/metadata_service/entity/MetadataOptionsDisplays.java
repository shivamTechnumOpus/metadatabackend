package com.example.metadata_service.entity;

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
@Table(name = "metadata_displays", indexes = {
    @Index(name = "idx_tenant_id", columnList = "tenant_id"),
    @Index(name = "idx_app_id", columnList = "app_id"),
    @Index(name = "idx_table_id", columnList = "table_id")
})
@Getter
@Setter
public class MetadataOptionsDisplays extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "display_id", updatable = false, nullable = false)
    private UUID displayId;

    @NotNull
    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @NotNull
    @Column(name = "app_id", nullable = false)
    private UUID appId;

    @NotNull
    @Column(name = "table_id", nullable = false)
    private UUID tableId;

    @NotNull
    @Column(name = "field_id", nullable = false)
    private UUID fieldId;

    @NotNull
    @Column(name = "options_source_id", nullable = false)
    private UUID optionsSourceId;

    @Column(name = "option_key_id")
    private UUID optionKeyId;

    @Size(max = 255)
    @Column(name = "default_value")
    private String defaultValue;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("displayId", displayId)
                .append("tenantId", tenantId)
                .append("appId", appId)
                .append("tableId", tableId)
                .append("fieldId", fieldId)
                .append("optionsSourceId", optionsSourceId)
                .append("defaultValue", defaultValue)
                .append("isActive", getIsActive())
                .build();
    }
}