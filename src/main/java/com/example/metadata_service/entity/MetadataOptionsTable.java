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

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "metadata_options_table")
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
    private MetadataTables optionsTableCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
    private MetadataStandardFields sourceField;

    @Override
    public String toString() {
        return "MetadataOptionsTable{" +
                "tableOptionId=" + tableOptionId +
                '}';
    }
}
