package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "metadata_unit_conversions", indexes = {
    @Index(name = "idx_source_unit", columnList = "source_unit"),
    @Index(name = "idx_target_unit", columnList = "target_unit")
})
@Getter
@Setter
public class MetadataUnitConversion extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "conversion_id", updatable = false, nullable = false)
    private UUID conversionId;

    @Column(name = "conversion_factor", precision = 15, scale = 6)
    private BigDecimal conversionFactor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_unit", referencedColumnName = "unit_id")
    @JsonIgnoreProperties({"sourceConversions", "targetConversions"})
    private MetadataUnit sourceUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_unit", referencedColumnName = "unit_id")
    @JsonIgnoreProperties({"sourceConversions", "targetConversions"})
    private MetadataUnit targetUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = true)
    @JsonIgnoreProperties({"tenantConfigs", "tables"})
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    @JsonIgnoreProperties({"tables"})
    private MetadataApp appCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("conversionId", conversionId)
                .append("conversionFactor", conversionFactor)
                .append("isActive", getIsActive())
                .build();
    }
}