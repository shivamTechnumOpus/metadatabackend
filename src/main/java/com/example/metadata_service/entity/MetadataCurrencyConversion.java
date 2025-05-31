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
@Table(name = "metadata_currency_conversions", indexes = {
    @Index(name = "idx_from_currency", columnList = "from_currency"),
    @Index(name = "idx_to_currency", columnList = "to_currency")
})
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
    @JsonIgnoreProperties({"fromConversions", "toConversions"})
    private MetadataCurrency fromCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_currency", referencedColumnName = "currency_id")
    @JsonIgnoreProperties({"fromConversions", "toConversions"})
    private MetadataCurrency toCurrency;

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
                .append("conversionRate", conversionRate)
                .append("isActive", getIsActive())
                .build();
    }
}