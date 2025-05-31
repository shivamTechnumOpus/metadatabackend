package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "group_field_mapping", indexes = {
    @Index(name = "idx_group_id", columnList = "group_id"),
    @Index(name = "idx_field_id", columnList = "field_id")
})
@Getter
@Setter
public class MetadataGroupFieldMapping extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "group_mapping_id", updatable = false, nullable = false)
    private UUID groupMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    @JsonIgnoreProperties({"groupFieldMappings"})
    private MetadataGroupingType group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    @JsonIgnoreProperties({"tableStructures", "groupFieldMappings"})
    private MetadataStandardFields field;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("groupMappingId", groupMappingId)
                .append("isActive", getIsActive())
                .build();
    }
}