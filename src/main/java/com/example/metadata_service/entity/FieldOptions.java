package com.example.metadata_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "field_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FieldOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "option_group_id", nullable = false)
    private Integer optionGroupId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private MetadataTenants tenant;

    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    private MetadataApp app;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private MetadataTables table;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private MetadataStandardFields field;

    @Column(name = "option_value", nullable = false, length = 100)
    private String optionValue;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}