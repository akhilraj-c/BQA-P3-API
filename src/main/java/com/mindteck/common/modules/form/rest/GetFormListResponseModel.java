package com.mindteck.common.modules.form.rest;

import com.mindteck.common.constants.Enum.ApplicationStatus;
import com.mindteck.common.constants.Enum.DateExtensionType;
import com.mindteck.common.modules.date_extension.model.DateExtension;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class GetFormListResponseModel {


    @ApiModelProperty(name = "formUniqueId", example = "123123123", value = "formUniqueId", dataType = "Integer"
            , position = 1, allowEmptyValue = false, required = true)
    private Long formUniqueId;


    @ApiModelProperty(name = "formId", example = "1345", value = "formId", dataType = "Integer"
            , position = 2, allowEmptyValue = false, required = true)
    private Long formId;

    @ApiModelProperty(name = "registrationStatus", example = "1", value = "registrationStatus", dataType = "Integer"
            , position = 3, allowEmptyValue = false, required = true)
    private Integer registrationStatus;

    @ApiModelProperty(name = "registrationStatus", example = "1", value = "registrationStatus", dataType = "Integer"
            , position = 3, allowEmptyValue = false, required = true)
    private Integer registrationSubStatus;

    @ApiModelProperty(example = "Hello asmpale", required = true, value = "institutionName", dataType = "String", position = 4)
    private String institutionName;

    @ApiModelProperty(name = "submissionDate", example = "1231231231231", value = "submissionDate", dataType = "Integer"
            , position = 5, allowEmptyValue = false, required = true)
    private Long submissionDate;
    @ApiModelProperty(example = "af2341riqrqwer24", required = true, value = "Licence number of the institution", dataType = "String", position = 6)
    private String institutionLicenceNo;

    @ApiModelProperty(name = "assignedAppManger", example = "5", value = "userId of the application currently assigned to this form", dataType = "Integer"
            , position = 7, allowEmptyValue = false, required = true)
    private Long assignedAppManager;

    @ApiModelProperty(example = "09-2023", required = true, value = "current planned submission date", dataType = "String", position = 6)
    private String plannedSubDate;

    @ApiModelProperty(example = "0", required = true, value = "0 - not requested \n 1 - requested", dataType = "Integer", position = 7)
    private Integer isDateExtensionRequested;
    @ApiModelProperty(example = "2", required = true, value = "0 - not requested \n 1 - requested \n 2 - approved \n 3 - rejected", dataType = "Integer", position = 8)
    private Integer dateExtensionStatus;

    @ApiModelProperty(name = "type", example = "1", value = "1 - Date extension request for completing the registration \n 2 - Date extension request for completing additional data submission", dataType = "Integer",
            allowableValues = "1,2", position = 9, allowEmptyValue = false, required = true)

    private Integer dateExtensionType;
    @ApiModelProperty(name = "requestedDate", example = "1231231231231", value = "requested extension date in epoch", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Long requestedDate;
    @ApiModelProperty(example = "I need an extension", required = true, value = "Reason for the date extension", dataType = "String")
    private String dateExtensionReason;

    @ApiModelProperty(name = "currentStatusDueDate", example = "5", value = "due date", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Long currentStatusDueDate;

    @ApiModelProperty(name = "ilepCount", example = "5", value = "ilepCount", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Integer ilepCount = 0;

    @ApiModelProperty(name = "conflictCount", example = "5", value = "conflictCount", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Integer conflictCount = 0;

    @ApiModelProperty(name = "completedStatus", example = "[1,2,3]", value = "array of status", dataType = "Array"
            , position = 4, allowEmptyValue = false, required = true)
    private String completedStatus;

    @ApiModelProperty(name = "institutionAppealExpiry", example = "1231231231231", value = "appeal expiry date in epoch", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Long institutionAppealExpiry;

    @ApiModelProperty(name = "lastActionTime", example = "1231231231231", value = "last action date in epoch", dataType = "Integer"
            , position = 4, allowEmptyValue = false, required = true)
    private Long lastActionTime;

    @ApiModelProperty(example = "1", required = true, value = "isBqaReviewed", dataType = "Integer", position = 7)
    private Integer isBqaReviewed;

    @ApiModelProperty(example = "Good", value = "bqaReviewResult", position = 14)
    private String bqaReviewResult;

    @ApiModelProperty(example = "qualificationTitle", value = "qualificationTitle")
    private String qualificationTitle;
    @ApiModelProperty(example = "noOfModules", value = "qualificationTitle")
    private Integer noOfModules;
    @ApiModelProperty(example = "listingId", value = "qualificationTitle")
    private String listingId;
    @ApiModelProperty(example = "formParentUniqueId", value = "qualificationTitle")
    private Long formParentUniqueId;

    //Date for date extension
    @ApiModelProperty(example = "1/0", value = "dateExtensionFlagV2")
    private Integer dateExtensionFlagV2;

    @ApiModelProperty(name = "dateExtensionDetails")
    private List<DateExtension> dateExtensionList;

    @ApiModelProperty(name = "isRevalidation")
    private Integer isRevalidation;

    @ApiModelProperty(name = "isPaid")
    private Integer isPaid;

    @ApiModelProperty(example = "Some rejection reason", value = "rejectionReason", position = 15)
    private String rejectionReason;

    @ApiModelProperty( example = "1231231231231" , value = "rejection Date date in epoch" , dataType = "Integer", position = 16)
    private Long rejectionDate;

    @ApiModelProperty(example = "pocName", value = "pocName")
    private String pocName;

    @ApiModelProperty(example = "pocEmail", value = "pocEmail")
    private String pocEmail;

/*
    @ApiModelProperty(name = "lastActionUserType" , example = "2" , value = "last action done user Type" , dataType = "Integer"
            , position = 4,allowEmptyValue = false , required = true)
    private Integer lastActionUserType;

    @ApiModelProperty(name = "lastActionUserId" , example = "2" , value = "last action done userId" , dataType = "Integer"
            , position = 4,allowEmptyValue = false , required = true)
    private Long lastActionUserId;

    @ApiModelProperty(name = "lastActionUserName" , example = "Sumesh" , value = "last action done userName" , dataType = "String"
            , position = 4,allowEmptyValue = false , required = true)
    private String lastActionUserName;
*/

    public GetFormListResponseModel(Long formUniqueId,
                                    Long formId,
                                    Integer registrationStatus,
                                    Integer registrationSubStatus,
                                    String institutionName,
                                    Long submissionDate,
                                    String institutionLicenceNo,
                                    Long assignedAppManager,
                                    String plannedSubDate,
                                    String completedStatus,
                                    Long institutionAppealExpiry,
                                    Long currentStatusDueDate,
                                    Long lastActionTime,
                                    Integer isBqaReviewed,
                                    String bqaReviewResult
                                    ) {
        this.formUniqueId = formUniqueId;
        this.formId = formId;
        this.registrationStatus = registrationStatus;
        this.registrationSubStatus = registrationSubStatus;
        this.institutionName = institutionName;
        this.submissionDate = submissionDate;
        this.institutionLicenceNo = institutionLicenceNo;
        this.assignedAppManager = assignedAppManager;
        this.plannedSubDate = plannedSubDate;
        this.completedStatus = completedStatus;
        this.institutionAppealExpiry = institutionAppealExpiry;
        this.currentStatusDueDate = currentStatusDueDate;
        this.lastActionTime = lastActionTime;
        this.isBqaReviewed = isBqaReviewed;
        this.bqaReviewResult = bqaReviewResult;
/*        this.lastActionUserId = lastActionUserId;
        this.lastActionUserType = lastActionUserType;
        this.lastActionUserName = lastActionUserName;*/
        setExtensionType(registrationStatus, isDateExtensionRequested);

    }


    public GetFormListResponseModel(
            Long formUniqueId,
            Long formId,
            Integer registrationStatus,
            Integer registrationSubStatus,
            String institutionName,
            Long submissionDate,
            String institutionLicenceNo,
            Long assignedAppManager,
            String plannedSubDate,
            String completedStatus,
            Long institutionAppealExpiry,
            Long currentStatusDueDate,
            Long lastActionTime,
            Integer isBqaReviewed,
            String bqaReviewResult,
            String qualificationTitle,
            Integer noOfModules,
            String listingId,
            Long formParentUniqueId,
            Integer isRevalidation
    ) {
        this.isRevalidation = isRevalidation;
        this.formUniqueId = formUniqueId;
        this.formId = formId;
        this.registrationStatus = registrationStatus;
        this.registrationSubStatus = registrationSubStatus;
        this.institutionName = institutionName;
        this.submissionDate = submissionDate;
        this.institutionLicenceNo = institutionLicenceNo;
        this.assignedAppManager = assignedAppManager;
        this.plannedSubDate = plannedSubDate;
        this.completedStatus = completedStatus;
        this.institutionAppealExpiry = institutionAppealExpiry;
        this.currentStatusDueDate = currentStatusDueDate;
        this.lastActionTime = lastActionTime;
        this.isBqaReviewed = isBqaReviewed;
        this.bqaReviewResult = bqaReviewResult;
        this.qualificationTitle = qualificationTitle;
        this.noOfModules = noOfModules;
        this.listingId = listingId;
        this.formParentUniqueId = formParentUniqueId;
/*        this.lastActionUserId = lastActionUserId;
        this.lastActionUserType = lastActionUserType;
        this.lastActionUserName = lastActionUserName;*/
        setExtensionType(registrationStatus, isDateExtensionRequested);

    }


    public GetFormListResponseModel(
            Long formUniqueId,
            Long formId,
            Integer registrationStatus,
            Integer registrationSubStatus,
            String institutionName,
            Long submissionDate,
            String institutionLicenceNo,
            Long assignedAppManager,
            String plannedSubDate,
            Integer isDateExtensionRequested,
            Integer dateExtensionStatus,
            Long requestedDate,
            String completedStatus,
            Long institutionAppealExpiry,
            Long currentStatusDueDate,
            Long lastActionTime,
            Integer isBqaReviewed,
            String bqaReviewResult
    ) {
        this.formUniqueId = formUniqueId;
        this.formId = formId;
        this.registrationStatus = registrationStatus;
        this.registrationSubStatus = registrationSubStatus;
        this.institutionName = institutionName;
        this.submissionDate = submissionDate;
        this.institutionLicenceNo = institutionLicenceNo;
        this.assignedAppManager = assignedAppManager;
        this.plannedSubDate = plannedSubDate;
        this.isDateExtensionRequested = isDateExtensionRequested;
        this.dateExtensionStatus = dateExtensionStatus;
        this.requestedDate = requestedDate;
        this.completedStatus = completedStatus;
        this.institutionAppealExpiry = institutionAppealExpiry;
        this.currentStatusDueDate = currentStatusDueDate;
        this.lastActionTime = lastActionTime;
        this.isBqaReviewed = isBqaReviewed;
        this.bqaReviewResult = bqaReviewResult;

    /*    this.lastActionUserId = lastActionUserId;
        this.lastActionUserType = lastActionUserType;
        this.lastActionUserName = lastActionUserName;*/
        setExtensionType(registrationStatus, isDateExtensionRequested);
    }

    public GetFormListResponseModel(
            Long formUniqueId,
            Long formId,
            Integer registrationStatus,
            Integer registrationSubStatus,
            String institutionName,
            Long submissionDate,
            String institutionLicenceNo,
            Long assignedAppManager,
            String plannedSubDate,
            Integer isDateExtensionRequested,
            Integer dateExtensionStatus,
            String dateExtensionReason,
            Long requestedDate,
            String completedStatus,
            Long institutionAppealExpiry,
            Long currentStatusDueDate,
            Long lastActionTime,
            Integer isBqaReviewed,
            String bqaReviewResult,
            String qualificationTitle,
            Integer noOfModules,
            String listingId,
            Long formParentUniqueId,
            Integer isRevalidation,
            String rejectionReason,
            Long rejectionDate,
            Integer isPaid,
            String pocName,
            String pocEmail
    ) {
        this.formUniqueId = formUniqueId;
        this.formId = formId;
        this.registrationStatus = registrationStatus;
        this.registrationSubStatus = registrationSubStatus;
        this.institutionName = institutionName;
        this.submissionDate = submissionDate;
        this.institutionLicenceNo = institutionLicenceNo;
        this.assignedAppManager = assignedAppManager;
        this.plannedSubDate = plannedSubDate;
        this.isDateExtensionRequested = isDateExtensionRequested;
        this.dateExtensionStatus = dateExtensionStatus;
        this.requestedDate = requestedDate;
        this.completedStatus = completedStatus;
        this.institutionAppealExpiry = institutionAppealExpiry;
        this.currentStatusDueDate = currentStatusDueDate;
        this.lastActionTime = lastActionTime;
        this.isBqaReviewed = isBqaReviewed;
        this.bqaReviewResult = bqaReviewResult;
        this.qualificationTitle = qualificationTitle;
        this.noOfModules = noOfModules;
        this.listingId = listingId;
        this.formParentUniqueId = formParentUniqueId;
        this.dateExtensionReason = dateExtensionReason;
        this.isRevalidation = isRevalidation;
        this.rejectionReason = rejectionReason;
        this.rejectionDate = rejectionDate;
        this.isPaid = isPaid;
        this.pocName = pocName;
        this.pocEmail = pocEmail;

    /*    this.lastActionUserId = lastActionUserId;
        this.lastActionUserType = lastActionUserType;
        this.lastActionUserName = lastActionUserName;*/
        setExtensionType(registrationStatus, isDateExtensionRequested);
    }

    private void setExtensionType(Integer registrationStatus, Integer isDateExtensionRequested) {
        if (!Objects.isNull(isDateExtensionRequested) && isDateExtensionRequested >= 1) {
            if (registrationStatus < ApplicationStatus.INSTITUTION_SUBMITTED.getCode()) {
                this.dateExtensionType = DateExtensionType.BQA_DATE_EXTENSION.getCode();
            } else if (registrationStatus > ApplicationStatus.INSTITUTION_SUBMITTED.getCode()
                    && registrationStatus < ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode()) {
                this.dateExtensionType = DateExtensionType.APCMGR_DATE_EXTENSION.getCode();
            } else {
                this.dateExtensionType = DateExtensionType.CONDITION_FULL_DATE_EXTENSION.getCode();
            }
        } else {
            this.dateExtensionType = DateExtensionType.IDLE.getCode();
        }
    }
}
