//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "metadata_options_table")
//@Data
//public class MetadataOptionsTable {
//    @Id
//    @Column(name = "table_option_id")
//    private Integer tableOptionId;
//
//    @Column(name = "options_table_id", nullable = false)
//    private Integer optionsTableId;
//
////    @Column(name = "source_field_id")
////    private Integer sourceFieldId;
////
////    @Column(name = "option_key_id")
////    private Integer optionKeyId;
//
//    @ManyToOne
//    @JoinColumn(name = "options_source_id", nullable = false)
//    private MetadataOptionsCategory optionsSource;
//
//    @Column(name = "description")
//    private String description;
//}


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
@Table(name = "metadata_options_table", indexes = {
    @Index(name = "idx_tenant_code", columnList = "tenant_code"),
    @Index(name = "idx_app_code", columnList = "app_code")
})
@Getter
@Setter
public class MetadataOptionsTable extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "table_option_id", updatable = false, nullable = false)
    private UUID tableOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "options_table_code", referencedColumnName = "table_code", nullable = false),
            @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    })
    @JsonIgnoreProperties({"tableStructures", "appCode", "tenantCode"})
    private MetadataTables optionsTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
    @JsonIgnoreProperties({"tableStructures", "groupFieldMappings"})
    private MetadataStandardFields sourceField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_code", referencedColumnName = "tenant_code", nullable = true)
    @JsonIgnoreProperties({"tenantConfigs", "tables"})
    private MetadataTenants tenantCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_code", referencedColumnName = "app_code", nullable = false)
    @JsonIgnoreProperties({"tables"})
    private MetadataApp appCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("tableOptionId", tableOptionId)
                .append("isActive", getIsActive())
                .build();
    }
}