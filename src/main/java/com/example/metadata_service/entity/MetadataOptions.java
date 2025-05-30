//package com.example.metadata_service.entity;
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "metadata_options")
//@Data
//public class MetadataOptions {
//    @Id
//    @Column(name = "option_id")
//    private Integer optionId;
//
//    @Column(name = "tenant_id", nullable = false)
//    private Integer tenantId;
//
//    @Column(name = "app_id", nullable = false)
//    private Integer appId;
//
//    @Column(name = "table_id", nullable = false)
//    private Integer tableId;
//
//    @Column(name = "option_key_id", nullable = false)
//    private Integer optionKeyId;
//
//    @Column(name = "option_value", nullable = false)
//    private String optionValue;
//
//    @Column(name = "display_order", nullable = false)
//    private Integer displayOrder;
//}

package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_options")
@Getter
@Setter
public class MetadataOptions extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "option_id", updatable = false, nullable = false)
    private UUID optionId;

    @Column(name = "option_value")
    private String optionValue;

    @Column(name = "display_order")
    private Integer displayOrder;

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
    @JoinColumn(name = "option_key_id", referencedColumnName = "option_key_id")
    private MetadataOptionsKey optionKey;

    @Override
    public String toString() {
        return "MetadataOption{" +
                "optionId=" + optionId +
                ", optionValue='" + optionValue + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}
