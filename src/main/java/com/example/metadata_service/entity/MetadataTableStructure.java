//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "metadata_table_structure")
//@Getter
//@Setter
//public class MetadataTableStructure {
//    @Id
//    @Column(name = "table_structure_id")
//    private Integer tableStructureId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
//    private MetadataTables table;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields field;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
//    private MetadataApp app;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    private MetadataTenants tenant;
//    @Override
//    public String toString() {
//        return "MetadataTableStructure{" +
//                "tableStructureId=" + tableStructureId +
//                '}';
//    }
//}

package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_table_str")
@Getter
@Setter
public class MetadataTableStructure extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "structure_id", updatable = false, nullable = false)
    private UUID structureId;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    private MetadataTables tableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

    @Override
    public String toString() {
        return "MetadataTableStructure{" +
                "structureId=" + structureId +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
