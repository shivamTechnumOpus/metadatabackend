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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "metadata_constraints", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code"),
    @Index(name = "idx_app_code", columnList = "app_code"),
    @Index(name = "idx_table_code", columnList = "table_code")
})
@Getter
@Setter
public class MetadataConstraints extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "constraint_id", updatable = false, nullable = false)
    private UUID constraintId;

    @Column(name = "min_value", precision = 15, scale = 6)
    private BigDecimal minValue;

    @Column(name = "max_value", precision = 15, scale = 6)
    private BigDecimal maxValue;

    @Column(name = "precision")
    private Integer precision;

    @Size(max = 50)
    @Column(name = "code")
    private String code;

    @Size(max = 20)
    @Column(name = "code_placement")
    private String codePlacement;

    @Size(max = 1)
    @Column(name = "decimal_separator")
    private String decimalSeparator;

    @Size(max = 1)
    @Column(name = "thousand_separator")
    private String thousandSeparator;

    @Size(max = 50)
    @Column(name = "format_type")
    private String formatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = true)
    @JsonIgnoreProperties({"tenantConfigs", "tables"})
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    @JsonIgnoreProperties({"tables"})
    private MetadataApp appCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    @JsonIgnoreProperties({"tableStructures", "appCode", "tenantCode"})
    private MetadataTables tableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_field_id", referencedColumnName = "field_id")
    @JsonIgnoreProperties({"tableStructures", "groupFieldMappings"})
    private MetadataStandardFields tableField;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("constraintId", constraintId)
                .append("minValue", minValue)
                .append("maxValue", maxValue)
                .append("precision", precision)
                .append("code", code)
                .append("formatType", formatType)
                .append("isActive", getIsActive())
                .build();
    }
}