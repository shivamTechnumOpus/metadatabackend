package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_units", indexes = {
    @Index(name = "idx_unit_code", columnList = "unit_code")
})
@Getter
@Setter
public class MetadataUnit extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unit_id", updatable = false, nullable = false)
    private UUID unitId;

    @Size(max = 50)
    @Column(name = "unit_code", unique = true)
    private String unitCode;

    @Size(max = 100)
    @Column(name = "unit_name")
    private String unitName;

    @Size(max = 10)
    @Column(name = "symbol")
    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_category_id", referencedColumnName = "unit_category_id")
    @JsonIgnoreProperties({"units"})
    private MetadataUnitCategory unitCategory;

    @OneToMany(mappedBy = "sourceUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataUnitConversion> sourceConversions = new HashSet<>();

    @OneToMany(mappedBy = "targetUnit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataUnitConversion> targetConversions = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("unitId", unitId)
                .append("unitCode", unitCode)
                .append("unitName", unitName)
                .append("symbol", symbol)
                .append("isActive", getIsActive())
                .build();
    }
}