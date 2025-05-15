package com.example.metadata_service.dto;

//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;

//@Getter
//@Setter
//public class TableSchemaDTO {
//    private Integer tableId;
//    private String tableName;
//    private Integer tenantId;
//    private Integer appId;
//    private List<FieldSchemaDTO> fields;
//
//    @Getter
//    @Setter
//    public static class FieldSchemaDTO {
//        private Integer fieldId;
//        private String fieldName;
//        private String datatype;
//        private String displayName;
//        private String helpText;
//        private String placeholder;
//        private String defaultValue;
//        private Boolean isRequired;
//        private Boolean isUnique;
//        private String pattern;
//        private Boolean isActive;
//        private Double minValue;
//        private Double maxValue;
//        private Integer apiId;
//        private String overriddenField;
//        private String overrideValue;
//        private List<DependencyDTO> dependencies;
//
//        // Overridden validation fields
//        private String overriddenDisplayName;
//        private String overriddenHelpText;
//        private String overriddenPlaceholder;
//        private String overriddenDefaultValue;
//        private Boolean overriddenIsRequired;
//        private Boolean overriddenIsUnique;
//        private String overriddenPattern;
//        private Boolean overriddenIsActive;
//        private Double overriddenMinValue;
//        private Double overriddenMaxValue;
//    }
//
//    @Getter
//    @Setter
//    public static class DependencyDTO {
//        private Integer dependencyId;
//        private Integer targetFieldId;
//        private String conditionType;
//        private String conditionValue;
//        private String conditionExpression;
//    }
//}




import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TableSchemaDTO {
    private Integer tableId;
    private String tableName;
    private Integer tenantId;
    private Integer appId;
    private List<FieldSchemaDTO> fields;

    @Getter
    @Setter
    @ToString
    public static class FieldSchemaDTO {
        private Integer fieldId;
        private String fieldName;
        private String datatype;
        private String displayName;
        private String helpText;
        private String placeholder;
        private String defaultValue;
        private Boolean isRequired;
        private Boolean isUnique;
        private String pattern;
        private Boolean isActive;
        private String overriddenDisplayName;
        private String overriddenHelpText;
        private String overriddenPlaceholder;
        private String overriddenDefaultValue;
        private Boolean overriddenIsRequired;
        private Boolean overriddenIsUnique;
        private String overriddenPattern;
        private Boolean overriddenIsActive;
        private Double overriddenMinValue;
        private Double overriddenMaxValue;
        private Double minValue;
        private Double maxValue;
        private Integer apiId;
        private List<DependencyDTO> dependencies;
        private Boolean isDropdown;
        private Integer optionsSourceId; // New: From metadata_displays
        private Integer optionKeyId; // New: From metadata_displays

    }

    @Getter
    @Setter
    @ToString
    public static class DependencyDTO {
        private Integer dependencyId;
        private Integer targetFieldId;
        private String conditionType;
        private String conditionValue;
        private String conditionExpression;
    }
}

