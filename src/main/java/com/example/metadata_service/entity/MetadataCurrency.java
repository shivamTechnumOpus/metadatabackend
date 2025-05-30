package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_currency")
@Getter
@Setter
public class MetadataCurrency extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "currency_id", updatable = false, nullable = false)
    private UUID currencyId;

    @Column(name = "currency_code", unique = true)
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "fromCurrency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MetadataCurrencyConversion> fromConversions = new HashSet<>();

    @OneToMany(mappedBy = "toCurrency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MetadataCurrencyConversion> toConversions = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataCurrency{" +
                "currencyId=" + currencyId +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}