package com.example.metadata_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "metadata_field_options")
public class MetadataFieldOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_config_id")
    private Integer optionConfigId;

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

    @Column(name = "is_dropdown", nullable = false)
    private Boolean isDropdown = false;

    @Column(name = "option_group_id")
    private Integer optionGroupId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Integer getOptionConfigId() { return optionConfigId; }
    public void setOptionConfigId(Integer optionConfigId) { this.optionConfigId = optionConfigId; }
    public MetadataTenants getTenant() { return tenant; }
    public void setTenant(MetadataTenants tenant) { this.tenant = tenant; }
    public MetadataApp getApp() { return app; }
    public void setApp(MetadataApp app) { this.app = app; }
    public MetadataTables getTable() { return table; }
    public void setTable(MetadataTables table) { this.table = table; }
    public MetadataStandardFields getField() { return field; }
    public void setField(MetadataStandardFields field) { this.field = field; }
    public Boolean getIsDropdown() { return isDropdown; }
    public void setIsDropdown(Boolean isDropdown) { this.isDropdown = isDropdown; }
    public Integer getOptionGroupId() { return optionGroupId; }
    public void setOptionGroupId(Integer optionGroupId) { this.optionGroupId = optionGroupId; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}