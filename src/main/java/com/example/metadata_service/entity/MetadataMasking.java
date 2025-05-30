package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_masking")
@Getter
@Setter
public class MetadataMasking extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "masking_id", updatable = false, nullable = false)
    private UUID maskingId;

    @Column(name = "visible_char")
    private Integer visibleChar;

    @Column(name = "position")
    private String position;

    @Column(name = "mask_symbol")
    private String maskSymbol;

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
    @JoinColumn(name = "field_id", referencedColumnName = "field_id")
    private MetadataStandardFields field;

    @Override
    public String toString() {
        return "MetadataMasking{" +
                "maskingId=" + maskingId +
                ", visibleChar=" + visibleChar +
                ", position='" + position + '\'' +
                ", maskSymbol='" + maskSymbol + '\'' +
                '}';
    }
}