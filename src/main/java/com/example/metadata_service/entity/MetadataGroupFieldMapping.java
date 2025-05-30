package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "group_field_mapping")
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
    private MetadataGroupingType group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @Override
    public String toString() {
        return "GroupFieldMapping{" +
                "groupMappingId=" + groupMappingId +
                '}';
    }
}