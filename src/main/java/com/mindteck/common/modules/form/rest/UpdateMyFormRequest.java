package com.mindteck.common.modules.form.rest;

import com.mindteck.common.modules.form.rest.formdata.StandardsDataRequest;
import com.mindteck.common.modules.form.rest.formdata.ApplicationContactDataRequest;
import com.mindteck.common.modules.form.rest.formdata.InstitutionProfileDataRequest;
import com.mindteck.common.modules.form.rest.formdata.QualityAssuranceSystemRequest;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMyFormRequest {

    @NotNull(message = "type is required")
    @ApiModelProperty(name = "type" , example = "1" , value = "Type \n 1 : Institution Profile Data \n 2 : Quality Assurance System" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer type;

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "formUniqueId" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

//    @ApiModelProperty(name = "InstitutionProfileDataRequest"  , value = "Json of InstitutionProfileDataRequest" , dataType = "InstitutionProfileDataRequest" ,
//            position = 2 ,
//    example = "{\"institutionNameEnglish\":\"Sumskar\",\"institutionNameArabic\":\"الصيف\",\"institutionDetailRegulatedBy\":1,\"institutionDetailLicensedBy\":1,\"institutionApprovalNumber\":123456789,\"institutionIssueDate\":1673550467000,\"institutionExpiryDate\":1673550467000,\"regulatoryOthersData\":\"paryan sawkaryam illa\",\"expiryDateNotApplicable\":\"true\",\"institutionDetailType\":1,\"institutionDetailDomain\":1,\"institutionDetailField\":1,\"institutionDetailDoc\":\"s3 url\",\"addressDetailBuilding\":\"Kollam\",\"addressDetailRoad\":\"Kottarakara\",\"addressDetailBlock\":\"Adoor\",\"contactDetailNumber\":\"91949714855\",\"contactDetailEmailId\":\"soman@somalia.com\",\"contactDetailWebSite\":\"www.somalia.com\",\"headOfInstitutionName\":\"Susheelan\",\"headOfInstitutionPosition\":\"Clerk\",\"headOfInstitutionContactNumber\":\"91947485623\",\"headOfInstitutionEmailId\":\"susheelan@headweight.com\",\"headOfQualityName\":\"Soumithrayan\",\"headOfQualityPosition\":\"CEO\",\"headOfQualityContactNumber\":\"914788554722\",\"headOfQualityEmailId\":\"Soumithrayan@quality.com\"}")
    private InstitutionProfileDataRequest institutionProfileDataRequest;


//    @ApiModelProperty(name = "QualityAssuranceSystemRequest"  , value = "Json of QualityAssuranceSystemRequest" , dataType = "InstitutionProfileDataRequest" ,
//            position = 2,
//    example = "{\"qualityAssuranceDescription\":\"Quality athra pora\",\"qualityDepartmentDoc\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"version\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\",\"qualityReviewDate\":1673550467000,\"qualityAssuranceReport\":\"[\\n  {\\n  \\\"url\\\":\\\"s3 url\\\",\\n  \\\"verson\\\":1,\\n  \\\"fileName\\\":\\\"fileName\\\"\\n}\\n]\"}")
    private QualityAssuranceSystemRequest qualityAssuranceSystemRequest;

//    @ApiModelProperty(name = "ApplicationContactDataRequest"  , value = "Json of ApplicationContactDataRequest" , dataType = "ApplicationContactDataRequest" ,
//            position = 2,
//    example = "{\"applicationContactName\":\"somashekran\",\"applicationContactNumber\":\"91949714785\",\"applicationContactPosition\":\"Data Analyst\",\"applicationContactEmail\":\"analystSomasherakan@somalia.com\"}")
    private ApplicationContactDataRequest applicationContactDataRequest;



    private StandardsDataRequest standardsDataRequest;
    @ApiModelProperty(name = "filePathParam" , example = "https://filepath/aaa/bb/cc" , value = "File path parameter" , dataType = "String" ,
            position = 5)
    private String filePathParam;


    private QualificationProfileData qualificationProfileData;


    @ApiModelProperty(name = "registrationStatus" , example = "1" , value = "Type \n 1 : Institution Profile Data \n 2 : Quality Assurance System" , dataType = "Integer" ,
            position = 1)
    private Integer registrationStatus;


}
