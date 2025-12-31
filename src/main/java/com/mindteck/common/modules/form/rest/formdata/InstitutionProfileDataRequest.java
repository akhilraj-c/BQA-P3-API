package com.mindteck.common.modules.form.rest.formdata;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "InstitutionProfileData")
public class InstitutionProfileDataRequest {

    @ApiModelProperty(value = "institutionNameEnglish", dataType = "String" ,example = "Sampl name")
    public String institutionNameEnglish;

    @ApiModelProperty(value = "institutionNameArabic", dataType = "String" ,example = "Sampl name")
    public String institutionNameArabic;

    @ApiModelProperty(value = "institutionDetailRegulatedBy", dataType = "String" ,example = "1")
    public Integer institutionDetailRegulatedBy;

    @ApiModelProperty(value = "institutionDetailLicensedBy", dataType = "String" ,example = "1")
    public Integer institutionDetailLicensedBy;

    @ApiModelProperty(example = "text data", required = true , value =  "othersData" , dataType = "String", position = 3)
    private String regulatoryOthersData;

    @NotBlank(message = "expiryDateNotApplicable is required")
    @ApiModelProperty(example = "text data", required = true , value =  "expiryDateNotApplicable" , dataType = "String", position = 3)
    private String expiryDateNotApplicable;

    @ApiModelProperty(value = "institutionApprovalNumber", dataType = "String" ,example = "Sampl name")
    public String institutionApprovalNumber;

    @ApiModelProperty(value = "institutionIssueDate", dataType = "String" ,example = "16161")
    public Long institutionIssueDate;

    @ApiModelProperty(value = "institutionExpiryDate", dataType = "String" ,example = "16161")
    public Long institutionExpiryDate;

    @ApiModelProperty(value = "institutionDetailType", dataType = "String" ,example = "1")
    public Integer institutionDetailType;

    @ApiModelProperty(value = "institutionDetailDomain", dataType = "String" ,example = "11")
    public Integer institutionDetailDomain;

    @ApiModelProperty(value = "institutionDetailField", dataType = "String" ,example = "1")
    public Integer institutionDetailField;

    @ApiModelProperty(value = "institutionDetailDoc", dataType = "String" ,example = "Sampl name")
    public String institutionDetailDoc;

    @ApiModelProperty(value = "addressDetailBuilding", dataType = "String" ,example = "Sampl name")
    public String addressDetailBuilding;

    @ApiModelProperty(value = "addressDetailRoad", dataType = "String" ,example = "Sampl name")
    public String addressDetailRoad;

    @ApiModelProperty(value = "addressDetailBlock", dataType = "String" ,example = "Sampl name")
    public String addressDetailBlock;

    @ApiModelProperty(value = "contactDetailNumber", dataType = "String" ,example = "Sampl name")
    public String contactDetailNumber;

    @ApiModelProperty(value = "contactDetailEmailId", dataType = "String" ,example = "Sampl name")
    public String contactDetailEmailId;

    @ApiModelProperty(value = "contactDetailWebSite", dataType = "String" ,example = "Sampl name")
    public String contactDetailWebSite;

    @ApiModelProperty(value = "headOfInstitutionName", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionName;

    @ApiModelProperty(value = "headOfInstitutionPosition", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionPosition;

    @ApiModelProperty(value = "headOfInstitutionContactNumber", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionContactNumber;

    @ApiModelProperty(value = "headOfInstitutionEmailId", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionEmailId;

    @ApiModelProperty(value = "headOfQualityName", dataType = "String" ,example = "Sampl name")
    public String headOfQualityName;

    @ApiModelProperty(value = "headOfQualityPosition", dataType = "String" ,example = "Sampl name")
    public String headOfQualityPosition;

    @ApiModelProperty(value = "headOfQualityContactNumber", dataType = "String" ,example = "Sampl name")
    public String headOfQualityContactNumber;

    @ApiModelProperty(value = "headOfQualityEmailId", dataType = "String" ,example = "Sampl name")
    public String headOfQualityEmailId;

    @ApiModelProperty(example = "any thing is string", value =  "licencedByOthersData" , name = "licencedByOthersData" ,dataType = "String", position = 12)
    private String licencedByOthersData;
    @ApiModelProperty(example = "any thing is string", value =  "institutionTypeOtherData" , name = "institutionTypeOtherData" ,dataType = "String", position = 12)
    private String institutionTypeOtherData;
    @ApiModelProperty(example = "any thing is string", value =  "fieldOtherData" , name = "fieldOtherData" ,dataType = "String", position = 12)
    private String fieldOtherData;
}
