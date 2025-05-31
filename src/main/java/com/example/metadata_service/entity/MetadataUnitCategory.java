package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "metadata_unit_categories", indexes = {
    @Index(name = "idx_unit_category_code", columnList = "unit_category_code")
})
@Getter
@Setter
public class MetadataUnitCategory extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unit_category_id", updatable = false, nullable = false)
    private UUID unitCategoryId;

    @Size(max = 50)
    @Column(name = "unit_category_code", unique = true)
    private String unitCategoryCode;

    @Size(max = 100)
    @Column(name = "category_name")
    private String categoryName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "unitCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataUnit> units = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("unitCategoryId", unitCategoryId)
                .append("unitCategoryCode", unitCategoryCode)
                .append("categoryName", categoryName)
                .append("isActive", getIsActive())
                .build();
    }
}