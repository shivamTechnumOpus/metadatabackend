//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "metadata_field_overrides")
//@Data
//public class MetadataFieldOverrides {
//    @Id
//
//    @Column(name = "override_id")
//    private Integer overrideId;
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
//    @Column(name = "overridden_field")
//    private String overriddenField;
//
//    @Column(name = "override_value")
//    private String overrideValue;
//
//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive;
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

import java.util.UUID;

@Entity
@Table(name = "metadata_field_overrides", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code"),
    @Index(name = "idx_app_code", columnList = "app_code"),
    @Index(name = "idx_table_code", columnList = "table_code")
})
@Getter
@Setter
public class MetadataFieldOverrides extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "override_id", updatable = false, nullable = false)
    private UUID overrideId;

    @Size(max = 100)
    @Column(name = "overridden_field")
    private String overriddenField;

    @Size(max = 255)
    @Column(name = "override_value")
    private String overrideValue;

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
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    @JsonIgnoreProperties({"tableStructures", "groupFieldMappings"})
    private MetadataStandardFields field;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("overrideId", overrideId)
                .append("overriddenField", overriddenField)
                .append("overrideValue", overrideValue)
                .append("isActive", getIsActive())
                .build();
    }
}