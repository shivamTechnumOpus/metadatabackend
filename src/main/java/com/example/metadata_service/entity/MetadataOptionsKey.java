package com.example.metadata_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_options_key")
@Getter
@Setter
public class MetadataOptionsKey extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "option_key_id", updatable = false, nullable = false)
    private UUID optionKeyId;

    @Column(name = "options_code")
    private String optionsCode;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "optionKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataOptions> options = new HashSet<>();

    @OneToMany(mappedBy = "optionKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataOptionDisplay> optionDisplays = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataOptionsKey{" +
                "optionKeyId=" + optionKeyId +
                ", optionsCode='" + optionsCode + '\'' +
                ", keyName='" + keyName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}