package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.BatchSize;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_options_key", indexes = {
    @Index(name = "idx_options_code", columnList = "options_code")
})
@Getter
@Setter
public class MetadataOptionsKey extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "option_key_id", updatable = false, nullable = false)
    private UUID optionKeyId;

    @Size(max = 50)
    @Column(name = "options_code")
    private String optionsCode;

    @Size(max = 100)
    @Column(name = "key_name")
    private String keyName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "optionKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataOptions> options = new HashSet<>();

    @OneToMany(mappedBy = "optionKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataOptionDisplay> optionDisplays = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("optionKeyId", optionKeyId)
                .append("optionsCode", optionsCode)
                .append("keyName", keyName)
                .append("isActive", getIsActive())
                .build();
    }
    
}
