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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "metadata_options_category")
@Getter
@Setter
public class MetadataOptionsCategory extends BaseMetaDataEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "options_source_id", updatable = false, nullable = false)
    private UUID optionsSourceId;

    @Column(name = "option_source_code")
    private String optionSourceCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "optionsSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MetadataOptionDisplay> optionDisplays = new HashSet<>();

    @Override
    public String toString() {
        return "MetadataOptionsCategory{" +
                "optionsSourceId=" + optionsSourceId +
                ", optionSourceCode='" + optionSourceCode + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}