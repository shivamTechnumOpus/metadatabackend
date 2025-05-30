package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "metadata_unit_conversions")
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
    private MetadataUnit sourceUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_unit", referencedColumnName = "unit_id")
    private MetadataUnit targetUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @Override
    public String toString() {
        return "MetadataUnitConversion{" +
                "conversionId=" + conversionId +
                ", conversionFactor=" + conversionFactor +
                '}';
    }
}