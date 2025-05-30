package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_units")
@Getter
@Setter
public class MetadataUnit extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unit_id", updatable = false, nullable = false)
    private UUID unitId;

    @Column(name = "unit_code", unique = true)
    private String unitCode;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "symbol")
    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_category_id", referencedColumnName = "unit_category_id")
    private MetadataUnitCategory unitCategory;

    @OneToMany(mappedBy = "sourceUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataUnitConversion> sourceConversions = new HashSet<>();

    @OneToMany(mappedBy = "targetUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataUnitConversion> targetConversions = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataUnit{" +
                "unitId=" + unitId +
                ", unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}