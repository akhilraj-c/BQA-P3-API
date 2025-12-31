package com.mindteck.common.modules.user.model.rest.formdata;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "InstitutionProfileData")
public class InstitutionProfileData {

    @ApiModelProperty(value = "institutionNameEnglish", dataType = "String" ,example = "Sampl name")
    public String institutionNameEnglish;

    @ApiModelProperty(value = "institutionNameEnglishStatus", dataType = "Integer" ,example = "1")
    private Integer institutionNameEnglishStatus;

    @ApiModelProperty(value = "institutionNameEnglishStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionNameEnglishStatusComment;

    @ApiModelProperty(value = "institutionNameArabic", dataType = "String" ,example = "Sampl name")
    public String institutionNameArabic;

    @ApiModelProperty(value = "institutionNameArabicStatus", dataType = "Integer" ,example = "1")
    private Integer institutionNameArabicStatus;

    @ApiModelProperty(value = "institutionNameArabicStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionNameArabicStatusComment;

    @ApiModelProperty(value = "institutionDetailRegulatedBy", dataType = "Integer" ,example = "1")
    public Integer institutionDetailRegulatedBy;

    @ApiModelProperty(value = "institutionDetailRegulatedByStatus", dataType = "Integer" ,example = "1")
    private Integer institutionDetailRegulatedByStatus;

    @ApiModelProperty(value = "institutionDetailRegulatedByStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionDetailRegulatedByStatusComment;

    @ApiModelProperty(value = "institutionDetailLicensedBy", dataType = "Integer" ,example = "1")
    public Integer institutionDetailLicensedBy;

    @ApiModelProperty(value = "institutionDetailLicensedByStatus", dataType = "Integer" ,example = "1")
    private Integer institutionDetailLicensedByStatus;

    @ApiModelProperty(value = "institutionDetailLicensedByStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionDetailLicensedByStatusComment;

    @ApiModelProperty(value = "institutionApprovalNumber", dataType = "String" ,example = "Sampl name")
    public String institutionApprovalNumber;

    @ApiModelProperty(value = "institutionIssueDate", dataType = "Long" ,example = "16161")
    public Long institutionIssueDate;

    @ApiModelProperty(value = "institutionExpiryDate", dataType = "Long" ,example = "16161")
    public Long institutionExpiryDate;

    @ApiModelProperty(value = "institutionApprovalNumberStatus", dataType = "Integer" ,example = "1")
    private Integer institutionApprovalNumberStatus;

    @ApiModelProperty(value = "institutionApprovalNumberStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionApprovalNumberStatusComment;

    @ApiModelProperty(value = "institutionDetailType", dataType = "Integer" ,example = "1")
    public Integer institutionDetailType;

    @ApiModelProperty(value = "institutionDetailTypeStatus", dataType = "Integer" ,example = "1")
    private Integer institutionDetailTypeStatus;

    @ApiModelProperty(value = "institutionDetailTypeStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionDetailTypeStatusComment;

    @ApiModelProperty(value = "institutionDetailDomain", dataType = "Integer" ,example = "11")
    public Integer institutionDetailDomain;

    @ApiModelProperty(value = "institutionDetailDomainStatus", dataType = "Integer" ,example = "1")
    private Integer institutionDetailDomainStatus;

    @ApiModelProperty(value = "institutionDetailDomainStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionDetailDomainStatusComment;

    @ApiModelProperty(value = "institutionDetailField", dataType = "Integer" ,example = "1")
    public Integer institutionDetailField;

    @ApiModelProperty(value = "institutionDetailDoc", dataType = "String" ,example = "Sampl name")
    public String institutionDetailDoc;

    @ApiModelProperty(value = "institutionDetailFieldStatus", dataType = "Integer" ,example = "1")
    private Integer institutionDetailFieldStatus;

    @ApiModelProperty(value = "institutionDetailFieldStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionDetailFieldStatusComment;

    @ApiModelProperty(value = "addressDetailBuilding", dataType = "String" ,example = "Sampl name")
    public String addressDetailBuilding;

    @ApiModelProperty(value = "addressDetailRoad", dataType = "String" ,example = "Sampl name")
    public String addressDetailRoad;

    @ApiModelProperty(value = "addressDetailBlock", dataType = "String" ,example = "Sampl name")
    public String addressDetailBlock;

    @ApiModelProperty(value = "addressDetailStatus", dataType = "Integer" ,example = "1")
    private Integer addressDetailStatus;

    @ApiModelProperty(value = "addressDetailStatusComment", dataType = "String" ,example = "Sampl name")
    private String addressDetailStatusComment;

    @ApiModelProperty(value = "contactDetailNumber", dataType = "String" ,example = "Sampl name")
    public String contactDetailNumber;

    @ApiModelProperty(value = "contactDetailEmailId", dataType = "String" ,example = "Sampl name")
    public String contactDetailEmailId;

    @ApiModelProperty(value = "contactDetailWebSite", dataType = "String" ,example = "Sampl name")
    public String contactDetailWebSite;

    @ApiModelProperty(value = "contactDetailStatus", dataType = "Integer" ,example = "1")
    private Integer contactDetailStatus;

    @ApiModelProperty(value = "contactDetailStatusComment", dataType = "String" ,example = "Sampl name")
    private String contactDetailStatusComment;

    @ApiModelProperty(value = "headOfInstitutionName", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionName;

    @ApiModelProperty(value = "headOfInstitutionPosition", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionPosition;

    @ApiModelProperty(value = "headOfInstitutionContactNumber", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionContactNumber;

    @ApiModelProperty(value = "headOfInstitutionEmailId", dataType = "String" ,example = "Sampl name")
    public String headOfInstitutionEmailId;

    @ApiModelProperty(value = "headOfInstitutionStatus", dataType = "Integer" ,example = "1")
    private Integer headOfInstitutionStatus;

    @ApiModelProperty(value = "headOfInstitutionStatusComment", dataType = "String" ,example = "Sampl name")
    private String headOfInstitutionStatusComment;

    @ApiModelProperty(value = "headOfQualityName", dataType = "String" ,example = "Sampl name")
    public String headOfQualityName;

    @ApiModelProperty(value = "headOfQualityPosition", dataType = "String" ,example = "Sampl name")
    public String headOfQualityPosition;

    @ApiModelProperty(value = "headOfQualityContactNumber", dataType = "String" ,example = "Sampl name")
    public String headOfQualityContactNumber;

    @ApiModelProperty(value = "headOfQualityEmailId", dataType = "String" ,example = "Sampl name")
    public String headOfQualityEmailId;

    @ApiModelProperty(value = "headOfQualityStatus", dataType = "Integer" ,example = "1")
    private Integer headOfQualityStatus;

    @ApiModelProperty(value = "headOfQualityStatusComment", dataType = "String" ,example = "Sampl name")
    private String headOfQualityStatusComment;

    @ApiModelProperty(example = "any thing is string", value =  "regulatoryOthersData" , name = "regulatoryOthersData" ,dataType = "String", position = 12)
    private String regulatoryOthersData;
    @ApiModelProperty(example = "any thing is string", value =  "licencedByOthersData" , name = "licencedByOthersData" ,dataType = "String", position = 12)
    private String licencedByOthersData;

    @ApiModelProperty(example = "any thing is string", value =  "institutionTypeOtherData" , name = "institutionTypeOtherData" ,dataType = "String", position = 12)
    private String institutionTypeOtherData;

    @ApiModelProperty(example = "any thing is string", value =  "fieldOtherData" , name = "fieldOtherData" ,dataType = "String", position = 12)
    private String fieldOtherData;

    @ApiModelProperty(value = "submissionDate", dataType = "Long" ,example = "1600000000")
    public String submissionDate;
}
