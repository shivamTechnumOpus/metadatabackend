//package com.example.metadata_service.entity;
//import jakarta.persistence.*;
//import lombok.Data;
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name = "metadata_dependencies")
//@Data
//public class MetadataDependencies {
//    @Id
//    @Column(name = "dependency_id")
//    private Integer dependencyId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "source_table_id", referencedColumnName = "table_id")
//    private MetadataTables sourceTable;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "source_field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields sourceField;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "target_table_id", referencedColumnName = "table_id")
//    private MetadataTables targetTable;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "target_field_id", referencedColumnName = "field_id")
//    private MetadataStandardFields targetField;
//
//    @Column(name = "condition_type")
//    private String conditionType;
//
//    @Column(name = "condition_value")
//    private String conditionValue;
//
//    @Column(name = "condition_expression")
//    private String conditionExpression;
//
//    @Column(name = "description")
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
//    private MetadataTenants tenant;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
//    private MetadataApp app;
//}