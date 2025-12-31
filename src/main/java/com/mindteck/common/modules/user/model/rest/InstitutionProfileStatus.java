package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionProfileStatus {
    /**
     * "institutionNameEnglish": 1,
     * 		"institutionNameArabic": 1,
     * 		"institutionBackground": 1,
     * 		"regulatedBy": 1,
     * 		"licencedBy": 1,
     * 		"institutionApprovalNumber": 0,
     * 		"institutionType": 1,
     * 		"institutionDomain": 1,
     * 		"Field": 1,
     * 		"institutionDetails": 1
     */
    @NotNull(message = "institutionNameEnglish is required")
    @ApiModelProperty(example = "1", required = true , value =  "1" , dataType = "Integer", position = 8)
    private Integer institutionNameEnglish;

    @NotBlank(message = "institutionNameEnglishComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "1" , dataType = "String", position = 8)
    private String institutionNameEnglishComment;

    @NotNull(message = "institutionNameArabic is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionNameArabic;

    @NotBlank(message = "institutionNameEnglishComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionNameArabicComment;

    @NotNull(message = "institutionBackground is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionBackground;

    @NotBlank(message = "institutionBackgroundComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionBackgroundComment;

    @NotNull(message = "regulatedBy is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer regulatedBy;

    @NotBlank(message = "regulatedByComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String regulatedByComment;

    @NotNull(message = "licencedBy is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer licencedBy;

    @NotBlank(message = "licencedByComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String licencedByComment;

    @NotNull(message = "institutionApprovalNumber is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionApprovalNumber;

    @NotBlank(message = "institutionApprovalNumberComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionApprovalNumberComment;

    @NotNull(message = "institutionType is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionType;

    @NotNull(message = "institutionTypeComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionTypeComment;

    @NotNull(message = "institutionDomain is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionDomain;

    @NotBlank(message = "institutionDomainComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionDomainComment;

    @NotNull(message = "Field is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer field;

    @NotBlank(message = "fieldComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String fieldComment;

    @NotNull(message = "institutionDetails is required")
    @ApiModelProperty(example = "true", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer institutionDetails;

    @NotBlank(message = "institutionDetailsComment is required")
    @ApiModelProperty(example = "comment", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String institutionDetailsComment;
}
