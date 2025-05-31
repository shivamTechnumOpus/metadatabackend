//package com.example.metadata_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "metadata_options_category")
//@Data
//public class MetadataOptionsCategory {
//    @Id
//    @Column(name = "options_source_id")
//    private Integer optionsSourceId;
//
//    @Column(name = "category_name", nullable = false)
//    private String categoryName;
//
//    @Column(name = "description")
//    private String description;
//}

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
@Table(name = "metadata_options_category", indexes = {
    @Index(name = "idx_option_source_code", columnList = "option_source_code")
})
@Getter
@Setter
public class MetadataOptionsCategory extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "options_source_id", updatable = false, nullable = false)
    private UUID optionsSourceId;

    @Size(max = 50)
    @Column(name = "option_source_code")
    private String optionSourceCode;

    @Size(max = 100)
    @Column(name = "category_name")
    private String categoryName;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "optionsSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 100)
    private Set<MetadataOptionDisplay> optionDisplays = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("optionsSourceId", optionsSourceId)
                .append("optionSourceCode", optionSourceCode)
                .append("categoryName", categoryName)
                .append("isActive", getIsActive())
                .build();
    }
}