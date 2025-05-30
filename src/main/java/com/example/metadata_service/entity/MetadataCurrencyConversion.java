package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "metadata_currency_conversions")
@Getter
@Setter
public class MetadataCurrencyConversion extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "conversion_id", updatable = false, nullable = false)
    private UUID conversionId;

    @Column(name = "conversion_rate", precision = 15, scale = 6)
    private BigDecimal conversionRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_currency", referencedColumnName = "currency_id")
    private MetadataCurrency fromCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_currency", referencedColumnName = "currency_id")
    private MetadataCurrency toCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @Override
    public String toString() {
        return "MetadataCurrencyConversion{" +
                "conversionId=" + conversionId +
                ", conversionRate=" + conversionRate +
                '}';
    }
}