//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.util.UUID;
//
//@Entity
//@Table(name = "grouping_type")
//@Getter
//@Setter
//public class MetadataGroupingType extends BaseMetaDataEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "group_id", nullable = false, updatable = false)
//    private UUID groupId;
//
//    @Column(name = "group_name", nullable = false)
//    private String groupName;
//
//    @Override
//    public String toString() {
//        return "GroupingType{" +
//                "groupId=" + groupId +
//                ", groupName='" + groupName + '\'' +
//                ", createdAt=" + getCreatedAt() +
//                ", createdBy='" + getCreatedBy() + '\'' +
//                ", updatedAt=" + getUpdatedAt() +
//                ", updatedBy='" + getUpdatedBy() + '\'' +
//                ", deletedAt=" + getDeletedAt() +
//                ", deletedBy='" + getDeletedBy() + '\'' +
//                ", isActive=" + getIsActive() +
//                ", isDeleted=" + getIsDeleted() +
//          '}';
//}
//}

package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "grouping_type")
@Getter
@Setter
public class MetadataGroupingType extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "group_id", updatable = false, nullable = false)
    private UUID groupId;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataGroupFieldMapping> groupFieldMappings = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataGroupingType{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}


