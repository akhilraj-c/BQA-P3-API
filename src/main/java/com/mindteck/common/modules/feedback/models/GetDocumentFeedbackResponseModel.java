package com.mindteck.common.modules.feedback.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetDocumentFeedbackResponseModel {
    @ApiModelProperty(value = "qacFeedback", example = "Doc shared to Nac", dataType = "String", position = 1)
    private String qacFeedback;

    @ApiModelProperty(value = "qacFeedbackFile", example = "httpsl://", dataType = "String", position = 2)
    private String qacFeedbackFile;

    @ApiModelProperty(value = "qacAdditionalInfoStatus", example = "0", dataType = "Integer", position = 3)
    private Integer qacAdditionalInfoStatus;

    @ApiModelProperty(value = "institutionComment", example = "Comments", dataType = "String", position = 4)
    private String institutionComment;

    @ApiModelProperty(value = "submittedReportFile", example = "httpsl://", dataType = "String", position = 5)
    private String submittedReportFile;

    @ApiModelProperty(value = "nacComment", example = "My comments", dataType = "String", position = 6)
    private String nacComment;

    @ApiModelProperty(value = "nacAdditionInfoStatus", example = "1", dataType = "Integer", position = 7)
    private Integer nacAdditionInfoStatus;

    @ApiModelProperty(value = "applicationStatus", example = "36", dataType = "Integer", position = 8)
    private Integer applicationStatus;

    @ApiModelProperty(value = "serialNo", example = "13a", dataType = "String", position = 9)
    private String serialNo;

    @ApiModelProperty(value = "mcuScannedFile", example = "htpss", dataType = "String", position = 10)
    private String mcuScannedFile;

    @ApiModelProperty(value = "mcoFileComment", example = "htpss", dataType = "String", position = 11)
    private String mcoFileComment;

    @ApiModelProperty(value = "nacDescription", example = "httpsl://", dataType = "String", position = 12)
    private String nacDescription;

    @ApiModelProperty(value = "nacFeedBackFile", example = "My comments", dataType = "String", position = 13)
    private String nacFeedBackFile;

    @ApiModelProperty(value = "dfoCommentMCO", name = "comments to mco by dfo", example = "My comments", dataType = "String", position = 14)
    private String dfoCommentMCO;

    @ApiModelProperty(value = "dfoFileMco", name = "File to mco by dfo", example = "fileurl", dataType = "String", position = 15)
    private String dfoFileMco;

    @ApiModelProperty(value = "dfoSignedFile", name = "Signed file by dfo", example = "fileurl", dataType = "String", position = 16)
    private String dfoSignedFile;

    @ApiModelProperty(value = "dfoSignedStatus", name = "dfo signed sttaus", example = "1", dataType = "Integer", position = 17)
    private Integer dfoSignedStatus;

    @ApiModelProperty(value = "dfoSharedCabinet", name = "DFO chief shared report to Cabinet status", example = "1", dataType = "Integer", position = 18)
    private Integer dfoSharedCabinetStatus;

    @ApiModelProperty(value = "dfoCabinetApproved", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "1", dataType = "Integer", position = 19)
    private Integer dfoCabinetApprovedStatus;

    @ApiModelProperty(value = "dfoChiefEvaluationComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 20)
    private String dfoChiefEvaluationComment;

    @ApiModelProperty(value = "dfoChiefPlainComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 21)
    private String dfoChiefPlainComment;

    @ApiModelProperty(value = "gdqAcEvaluationComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 22)
    private String gdqAcEvaluationComment;

    @ApiModelProperty(value = "gdqAcPlainComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 23)
    private String gdqAcPlainComment;

    @ApiModelProperty(value = "qacEvaluationComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 24)
    private String qacEvaluationComment;

    @ApiModelProperty(value = "qacPlainComment", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 25)
    private String qacPlainComment;

    @ApiModelProperty(value = "institutionCommentBackFile", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "file url", dataType = "String", position = 26)
    private String institutionCommentBackFile;

    @ApiModelProperty(value = "institutionCommentBack", name = "DFO Admin updated as Cabinet Approved- Listed status", example = "Comment", dataType = "String", position = 27)
    private String institutionCommentBack;

    @ApiModelProperty(value = "factualAccuracyReportFile", name = "Report file of factual accuracy", example = "htpss", dataType = "String", position = 28)
    private String factualAccuracyReportFile;

    @ApiModelProperty(name = "nacFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 29, required = true)
    private String nacFile;

    @ApiModelProperty(name = "dfoSignedComment" ,value = "comment by dfo", dataType = "String" ,example = "comment" ,
            position = 30, required = true)
    private String dfoSignedComment;

    @ApiModelProperty(name = "dfoSharedCabinetComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 31, required = true)
    private String dfoSharedCabinetComment;

    @ApiModelProperty(name = "dfoSharedCabinetFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 32, required = true)
    private String dfoSharedCabinetFile;

        @ApiModelProperty(name = "dfoCabinetApprovedComment" ,value = "dfo  cabinet approved comment", dataType = "String" ,example = "comment" ,
            position = 33, required = true)
    private String dfoCabinetApprovedComment;

     @ApiModelProperty(name = "dfoCabinetApprovedFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 34, required = true)
    private String dfoCabinetApprovedFile;

        @ApiModelProperty(name = "dfoApproveAppealComment" ,value = "dfo approve the appeal comment", dataType = "String" ,example = "comment" ,
            position = 35, required = true)
    private String dfoApproveAppealComment;

     @ApiModelProperty(name = "dfoApproveAppealFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 36, required = true)
    private String dfoApproveAppealFile;

        @ApiModelProperty(name = "amApproveReportComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 37, required = true)
    private String amApproveReportComment;

     @ApiModelProperty(name = "amApproveReportFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 38, required = true)
    private String amApproveReportFile;

        @ApiModelProperty(name = "docSharedToNacComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 39, required = true)
    private String docSharedToNacComment;

     @ApiModelProperty(name = "docSharedToNacFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 40, required = true)
    private String docSharedToNacFile;

        @ApiModelProperty(name = "docSharedToQacComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 41, required = true)
    private String docSharedToQacComment;

     @ApiModelProperty(name = "docSharedToQacFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 42, required = true)
    private String docSharedToQacFile;
        @ApiModelProperty(name = "factualAccuracyStartComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 43, required = true)
    private String factualAccuracyStartComment;

     @ApiModelProperty(name = "factualAccuracyStartFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 44, required = true)
    private String factualAccuracyStartFile;

        @ApiModelProperty(name = "qacApproveComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 45, required = true)
    private String qacApproveComment;

     @ApiModelProperty(name = "qacApproveFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 46, required = true)
    private String qacApproveFile;

        @ApiModelProperty(name = "dfoChiefApproveComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 47, required = true)
    private String dfoChiefApproveComment;

     @ApiModelProperty(name = "dfoChiefApproveFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 48, required = true)
    private String dfoChiefApproveFile;

        @ApiModelProperty(name = "gdqAcApproveComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 49, required = true)
    private String gdqAcApproveComment;

     @ApiModelProperty(name = "gdqAcApproveFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 50, required = true)
    private String gdqAcApproveFile;

        @ApiModelProperty(name = "instituteAppealComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 51, required = true)
    private String instituteAppealComment;
     @ApiModelProperty(name = "instituteAppealFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 52, required = true)
    private String instituteAppealFile;

    @ApiModelProperty(name = "partialMetComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 53, required = true)
    private String partialMetComment;
    @ApiModelProperty(name = "partialMetFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 54, required = true)
    private String partialMetFile;

    @ApiModelProperty(name = "amFeedbackComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 55, required = true)
    private String amFeedbackComment;
    @ApiModelProperty(name = "amFeedbackFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 56, required = true)
    private String amFeedbackFile;

    @ApiModelProperty(name = "factualAccuracyComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 57, required = true)
    private String factualAccuracyComment;

    @ApiModelProperty(name = "factualAccuracyFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 58, required = true)
    private String factualAccuracyFile;

    @ApiModelProperty(name = "gdqAcSharedComment" ,value = "comment", dataType = "String" ,example = "comment" ,
            position = 59, required = true)
    private String gdqAcSharedComment;

    @ApiModelProperty(name = "gdqAcSharedFile" ,value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" ,
            position = 60, required = true)
    private String gdqAcSharedFile;

    @ApiModelProperty(name = "institutionAppealExpiry" , example = "1231231231231" , value = "appeal expiry date in epoch" , dataType = "Long"
            , position = 61,allowEmptyValue = false , required = true)
    private Long institutionAppealExpiry;

    @ApiModelProperty(name = "instituteFactualAccuracyDeadLine" , example = "123123123" , value = "Dead line date and time in epoc" , dataType = "Long"
            , position = 62 ,allowEmptyValue = false )
    private Long instituteFactualAccuracyDeadLine;

    @ApiModelProperty(name = "factualAccuracyStatus" , example = "0" , value = "0 or 1" , dataType = "Integer"
            , position = 63 )
    private Integer factualAccuracyStatus;
}
