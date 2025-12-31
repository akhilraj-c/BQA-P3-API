package com.mindteck.common.modules.user.model.rest;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "InstituteFormResponseModel")
public class InstituteFormResponseModel {

    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "12")
    public Long formUniqueId;

    @ApiModelProperty(value = "institutionName", dataType = "String" ,example = "BQA inst")
    public String institutionName;

    @ApiModelProperty(value = "licenceNo", dataType = "String" ,example = "12Asa")
    public String licenceNo;

    @ApiModelProperty(value = "licenseDocUrl", dataType = "String" ,example = "https://....png")
    public String licenseDocUrl;

    @ApiModelProperty(value = "issueDate", dataType = "Long" ,example = "1600000000")
    public Long issueDate;

    @ApiModelProperty(value = "expiryDate", dataType = "Long" ,example = "1600000000")
    public Long expiryDate;

    @ApiModelProperty(value = "bqaReviewed", dataType = "Integer" ,example = "true")
    public Integer bqaReviewed;

    @ApiModelProperty(value = "reviewDate", dataType = "Long" ,example = "1600000000")
    public Long reviewDate;

    @ApiModelProperty(value = "reviewResult", dataType = "String" ,example = "Hello good result")
    public String reviewResult;

    @ApiModelProperty(value = "nationalCourseOffered", dataType = "Integer" ,example = "true")
    public Integer nationalCourseOffered;

    @ApiModelProperty(value = "courseOfferedDescription", dataType = "String" ,example = "Course desc")
    public String courseOfferedDescription;

    @ApiModelProperty(value = "submissionDate", dataType = "Long" ,example = "1600000000")
    public String submissionDate;

    @ApiModelProperty(value = "contactName", dataType = "String" ,example = "John Doe")
    public String contactName;

    @ApiModelProperty(value = "emailId", dataType = "String" ,example = "sample@gmail.com")
    public String emailId;

    @ApiModelProperty(value = "contactNo", dataType = "String" ,example = "+91884455454")
    public String contactNo;

    @ApiModelProperty(value = "pocTitle", dataType = "String" ,example = "Suprevisor")
    public String pocTitle;

    @ApiModelProperty(value = "status", dataType = "Integer" ,example = "1")
    public Integer status;

    @ApiModelProperty(value = "licenseType", dataType = "Integer" ,example = "1")
    public Integer licenseType;

    @ApiModelProperty(example = "any thing is string", value =  "regulatoryOthersData" , name = "regulatoryOthersData" ,dataType = "String", position = 12)
    private String regulatoryOthersData;
    @ApiModelProperty(example = "any thing is string", value =  "licencedByOthersData" , name = "licencedByOthersData" ,dataType = "String", position = 12)
    private String licencedByOthersData;

    @ApiModelProperty(example = "any thing is string", value =  "institutionTypeOtherData" , name = "institutionTypeOtherData" ,dataType = "String", position = 12)
    private String institutionTypeOtherData;

    @ApiModelProperty(example = "any thing is string", value =  "fieldOtherData" , name = "fieldOtherData" ,dataType = "String", position = 12)
    private String fieldOtherData;




}
