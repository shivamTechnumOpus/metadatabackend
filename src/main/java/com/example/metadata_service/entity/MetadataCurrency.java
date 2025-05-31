package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "metadata_currency", indexes = {
    @Index(name = "idx_currency_code", columnList = "currency_code")
})
@Getter
@Setter
public class MetadataCurrency extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "currency_id", updatable = false, nullable = false)
    private UUID currencyId;

    @Size(max = 10)
    @Column(name = "currency_code", unique = true)
    private String currencyCode;

    @Size(max = 100)
    @Column(name = "currency_name")
    private String currencyName;

    @Size(max = 10)
    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "fromCurrency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataCurrencyConversion> fromConversions = new HashSet<>();

    @OneToMany(mappedBy = "toCurrency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataCurrencyConversion> toConversions = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("currencyId", currencyId)
                .append("currencyCode", currencyCode)
                .append("currencyName", currencyName)
                .append("symbol", symbol)
                .append("isActive", getIsActive())
                .build();
    }
}