//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "metadata_constraints")
//@Data
//public class MetadataConstraints {
//    @Id
//    @Column(name = "constraint_id")
//    private Integer constraintId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    private MetadataTenants tenant;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
//    private MetadataApp app;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "table_id", referencedColumnName = "table_id")
//    private MetadataTables table;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields field;
//
//    @Column(name = "min_value")
//    private Double minValue;
//
//    @Column(name = "max_value")
//    private Double maxValue;
//
//
//}

package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "metadata_constraints")
@Getter
@Setter
public class MetadataConstraints extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "constraint_id", updatable = false, nullable = false)
    private UUID constraintId;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "max_value")
    private BigDecimal maxValue;

    @Column(name = "precision")
    private Integer precision;

    @Column(name = "code")
    private String code;

    @Column(name = "code_placement")
    private String codePlacement;

    @Column(name = "decimal_separator")
    private String decimalSeparator;

    @Column(name = "thousand_separator")
    private String thousandSeparator;

    @Column(name = "format_type")
    private String formatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = false)
    private MetadataTenants tenantCode;

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
    @JoinColumn(name = "table_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields tableField;

    @Override
    public String toString() {
        return "MetadataConstraint{" +
                "constraintId=" + constraintId +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", precision=" + precision +
                ", code='" + code + '\'' +
                ", formatType='" + formatType + '\'' +
                '}';
    }
}