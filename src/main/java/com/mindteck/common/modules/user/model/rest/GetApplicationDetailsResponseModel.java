package com.mindteck.common.modules.user.model.rest;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindteck.common.modules.date_extension.model.DateExtension;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "GetApplicationDetailsResponseModel")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class GetApplicationDetailsResponseModel {

    private InstituteFormResponseModel instituteFormData;

    private QualificationProfileData qualificationProfileData;

    @ApiModelProperty(name = "dateExtensionDetails")
    private List<DateExtension> dateExtensionList;


    private FormApplicationResponseModel formApplicationData;

    @ApiModelProperty(value = "overAllStatus", dataType = "Integer", example = "1")
    private Integer overAllStatus;

    @ApiModelProperty(value = "overallStatusComment", dataType = "String", example = "Comment")
    private String overallStatusComment;

    /*@ApiModelProperty(value = "extensionRequestedStatus", dataType = "Integer" ,example = "1")
    private Integer extensionRequestedStatus;*/

    @ApiModelProperty(value = "registrationStatus", dataType = "Integer", example = "1")
    private Integer registrationStatus;

    @ApiModelProperty(value = "isDateExtensionRequested", dataType = "Integer", example = "1")
    private Integer isDateExtensionRequested;

    @ApiModelProperty(value = "dateExtensionReason", dataType = "String", example = "Comment")
    private String dateExtensionReason;

    @ApiModelProperty(value = "requestedExtensionDate", dataType = "Long", example = "1")
    private Long requestedExtensionDate;

    @ApiModelProperty(value = "dateExtensionStatus", dataType = "Integer", example = "1")
    private Integer dateExtensionStatus;

    @ApiModelProperty(value = "submissionDate", dataType = "Long", example = "1")
    private Long submissionDate;

    @ApiModelProperty(value = "formId", dataType = "Long", example = "1")
    private Long formId;

    @ApiModelProperty(value = "formUniqueId", dataType = "Long", example = "1")
    private Long formUniqueId;

    @ApiModelProperty(value = "subStatus", dataType = "Integer", example = "24")
    private Integer subStatus;

    @ApiModelProperty(value = "completedStatus", dataType = "Array", example = "[24,25]")
    private List<Integer> completedStatus;

    @ApiModelProperty(value = "ilepCount", dataType = "Integer", example = "2")
    private Integer ilepCount;
    @ApiModelProperty(value = "ilepsGotGrandAccess", dataType = "Array", example = "[24,25]")
    private List<Long> ilepsGotGrandAccess = new ArrayList<>();

    @ApiModelProperty(name = "filePathParam", example = "https://filepath/aaa/bb/cc", value = "File path parameter", dataType = "String",
            position = 13)
    private String filePathParam;

    @ApiModelProperty(value = "qualificationTitle", dataType = "String", example = "Comment")
    private String qualificationTitle;

    @ApiModelProperty(value = "noOfModules", dataType = "Integer", example = "Comment")
    private Integer noOfModules;

    @ApiModelProperty(value = "awardingBody", dataType = "String", example = "yes")
    private String awardingBody;

    @ApiModelProperty(value = "includedInOther", dataType = "String", example = "yes")
    private String includedInOther;

    @ApiModelProperty(value = "qualificationSize", dataType = "String", example = "Short")
    private String qualificationSize;

    @ApiModelProperty(value = "qualificationType", dataType = "Integer", example = "1")
    private Integer qualificationType;

    @ApiModelProperty(value = "plannedSubmissionDate", dataType = "String", example = "Comment")
    private String plannedSubmissionDate;

    @ApiModelProperty(name = "overAllEvaluationStatus")
    private Integer overAllEvaluationStatus;

    @ApiModelProperty(name = "overAllEvaluationStatusComment")
    private String overAllEvaluationStatusComment;

    @ApiModelProperty(name = "evaluationDeadLine")
    private Long evaluationDeadLine;

    @ApiModelProperty(name = "overAllVerificationStatus")
    private Integer overAllVerificationStatus;

    @ApiModelProperty(value = "overAllVerificationStatusComment")
    private String overAllVerificationStatusComment;

    @ApiModelProperty(value = "verificationDeadLine")
    private Long verificationDeadLine;

    @ApiModelProperty(value = "evaluationFlag")
    private Integer evaluationFlag = 0;
    @ApiModelProperty(value = "verificationFlag")
    private Integer verificationFlag = 0;
    @ApiModelProperty(value = "evaluationRejectionCount")
    private Integer evaluationRejectionCount = 0;
    @ApiModelProperty(value = "verificationRejectionCount")
    private Integer verificationRejectionCount = 0;

    @ApiModelProperty(value = "isVerificationPanelRequired")
    private Integer isVerificationPanelRequired = 0;

    @ApiModelProperty(value = "isRevalidation")
    private Integer isRevalidation = 0;

    @ApiModelProperty(value = "isPaid")
    private Integer isPaid ;

    @ApiModelProperty(value = "qpId")
    private String qpId ;
}
