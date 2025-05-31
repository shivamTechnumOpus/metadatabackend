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
@Table(name = "metadata_api_options", indexes = {
    @Index(name = "idx_name", columnList = "name")
})
@Getter
@Setter
public class MetadataApiOption extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "api_options_id", updatable = false, nullable = false)
    private UUID apiOptionsId;

    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "endpoint_url")
    private String endpointUrl;

    @Size(max = 10)
    @Column(name = "method")
    private String method;

    @Column(name = "headers", columnDefinition = "TEXT")
    private String headers;

    @Column(name = "params", columnDefinition = "TEXT")
    private String params;

    @Size(max = 255)
    @Column(name = "response_path")
    private String responsePath;

    @Size(max = 255)
    @Column(name = "purpose")
    private String purpose;

    @Size(max = 100)
    @Column(name = "mapped_response_field")
    private String mappedResponseField;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("apiOptionsId", apiOptionsId)
                .append("name", name)
                .append("endpointUrl", endpointUrl)
                .append("method", method)
                .append("purpose", purpose)
                .append("isActive", getIsActive())
                .build();
    }
}