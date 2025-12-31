package com.mindteck.common.modules.user.model.rest.qp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QPRegistrationRequest {

    @NotBlank(message = "institutionName is required")
    @ApiModelProperty(example = "Hello asmpale", required = true , value =  "institutionName" , dataType = "String", position = 1)
    private String institutionName;

    @NotBlank(message = "approvalLicense is required")
    @ApiModelProperty(example = "16asas56", required = true , value =  "instAppLicNo" , dataType = "String", position = 2)
    private String instAppLicNo;

    @NotNull(message = "licenseType is required")
    @ApiModelProperty(example = "1", required = true , value =  "licenseType" , dataType = "int", position = 3)
    private int licenseType;


    @NotBlank(message = "licenseFile is required")
    @ApiModelProperty(example = "1", required = true , value =  "approvalDocFile" , dataType = "String", position = 4)
    private String approvalDocFile;

    @NotNull(message = "issueDate is required")
    @ApiModelProperty(example = "16100000", required = true , value =  "issueDate" , dataType = "Long", position = 5)
    private Long issueDate;

    @NotNull(message = "expiryDate is required")
    @ApiModelProperty(example = "16100000", required = true , value =  "expiryDate" , dataType = "Long", position = 6)
    private Long expiryDate;

    //@NotBlank(message = "listingId is required")
    @ApiModelProperty(example = "1234", required = true , value =  "listingId" , dataType = "Long")
    private Long listingId;

    @NotBlank(message = "contactName is required")
    @ApiModelProperty(example = "sample name", required = true , value =  "contactName" , dataType = "String")
    private String contactName;

    @NotBlank(message = "positionTitle is required")
    @ApiModelProperty(example = "Joinee", required = true , value =  "positionTitle" , dataType = "String")
    private String positionTitle;

    @NotBlank(message = "contactNo is required")
    @ApiModelProperty(example = "9199999994", required = true , value =  "contactNo" , dataType = "String")
    private String contactNo;

    @NotBlank(message = "emailId is required")
    @ApiModelProperty(example = "sample@gmail.com", required = true , value =  "emailId" , dataType = "String")
    private String emailId;


    @ApiModelProperty(example = "List of QualificationTitle", required = true , value =  "qualificationToBeRevalidated" , dataType = "Array")
    public ArrayList<QualificationTitle> qualificationToBeRevalidated = new ArrayList<>(0);

    @ApiModelProperty(example = "List of QualificationTitle", required = true , value =  "qualificationToBePlacedTpoNQF" , dataType = "Array")
    public ArrayList<QualificationTitle> qualificationToBePlacedTpoNQF = new ArrayList<>(0);

    // Additional fields added for backward compatibilty
    @NotNull(message = "bqaReviewed is required")
    @ApiModelProperty(example = "1", required = true , value =  "isBqaReviewed" , dataType = "Integer", position = 7)
    private Integer isBqaReviewed;
    @NotNull(message = "courseOffered is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Boolean", position = 8)
    private Integer isOfferingNanLocCourseQa;

    @ApiModelProperty(example = "MBA, BSC",  value =  "offeringDescription" ,   position = 15)
    private String offeringDescription;
}

