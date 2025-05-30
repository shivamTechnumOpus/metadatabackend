package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_api_options")
@Getter
@Setter
public class MetadataApiOption extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "api_options_id", updatable = false, nullable = false)
    private UUID apiOptionsId;

    @Column(name = "name")
    private String name;

    @Column(name = "endpoint_url")
    private String endpointUrl;

    @Column(name = "method")
    private String method;

    @Column(name = "headers", columnDefinition = "TEXT")
    private String headers;

    @Column(name = "params", columnDefinition = "TEXT")
    private String params;

    @Column(name = "response_path")
    private String responsePath;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "mapped_response_field")
    private String mappedResponseField;

    @Override
    public String toString() {
        return "MetadataApiOption{" +
                "apiOptionsId=" + apiOptionsId +
                ", name='" + name + '\'' +
                ", endpointUrl='" + endpointUrl + '\'' +
                ", method='" + method + '\'' +
                ", purpose='" + purpose + '\'' +
                ", mappedResponseField='" + mappedResponseField + '\'' +
                '}';
    }
}