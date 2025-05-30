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
@Table(name = "metadata_unit_categories")
@Getter
@Setter
public class MetadataUnitCategory extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "unit_category_id", updatable = false, nullable = false)
    private UUID unitCategoryId;

    @Column(name = "unit_category_code", unique = true)
    private String unitCategoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "unitCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataUnit> units = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataUnitCategory{" +
                "unitCategoryId=" + unitCategoryId +
                ", unitCategoryCode='" + unitCategoryCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}