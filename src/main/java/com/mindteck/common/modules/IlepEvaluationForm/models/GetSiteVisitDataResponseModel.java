package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetSiteVisitDataResponseModel {
    @ApiModelProperty(value = "formUniqueId", dataType = "Long" ,example = "1")
    private Long formUniqueId;

    @ApiModelProperty(value = "instituteName", dataType = "String" ,example = "1")
    private String instituteName;

    @ApiModelProperty(value = "personInvolved", dataType = "String" ,example = "1")
    private String personInvolved;

    @ApiModelProperty(value = "siteVisitRequired", dataType = "Integer" ,example = "1")
    private Integer siteVisitRequired;

    @ApiModelProperty(value = "visitDate", dataType = "Long" ,example = "1")
    private Long visitDate;

    @ApiModelProperty(value = "First Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 5 , required = true)
    private Long date1;

    @ApiModelProperty(value = "Second Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 6 , required = true)
    private Long date2;

    @ApiModelProperty(value = "Third Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 7 , required = true)
    private Long date3;

    @ApiModelProperty(value = "instituteRepresentativeName", dataType = "String" ,example = "1")
    private String instituteRepresentativeName;

    @ApiModelProperty(value = "instituteRepresentativePosition", dataType = "String" ,example = "1")
    private String instituteRepresentativePosition;

    @ApiModelProperty(value = "instituteRepresentativeContact", dataType = "String" ,example = "1")
    private String instituteRepresentativeContact;

    @ApiModelProperty(value = "instituteRepresentativeEmail", dataType = "String" ,example = "1")
    private String instituteRepresentativeEmail;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 10 , required = true)
    private String ilepDocuments;
    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 11 , required = true)
    private String instDocuments;

    @ApiModelProperty(value = "confidentialityStatus", dataType = "Integer" ,example = "1")
    private Integer confidentialityStatus;

    @ApiModelProperty(value = "isDateExtensionRequested", dataType = "Integer" ,example = "1")
    private Integer isDateExtensionRequested;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 11 , required = true)
    private String reportFile;

    @ApiModelProperty(value = "agendaForm", dataType = "String" ,example = "s3 url")
    private String agendaForm;

    @ApiModelProperty(value = "agendaFormComment", dataType = "String" ,example = "comment")
    private String agendaFormComment;

    @ApiModelProperty(value = "filledAgendaForm", dataType = "String" ,example = "s3 url")
    private String filledAgendaForm;

    @ApiModelProperty(value = "filledAgendaFormComment", dataType = "String" ,example = "comment")
    private String filledAgendaFormComment;

    @ApiModelProperty(value = "amMarkSiteVisit", dataType = "Integer" ,example = "1")
    private Integer amMarkSiteVisit;

    @ApiModelProperty(value = "amMarkSiteVisitComment", dataType = "String" ,example = "comment")
    private String amMarkSiteVisitComment;

    @ApiModelProperty(value = "amRejectDocumentComment", dataType = "String" ,example = "comment")
    private String amRejectDocumentComment;

    @ApiModelProperty(value = "amEvaluateUploadedSiteVisitDoc", dataType = "String" ,example = "comment")
    private String amEvaluateUploadedSiteVisitDoc;

    @ApiModelProperty(value = "ilepEvaluateUploadedSiteVisitDoc", dataType = "String" ,example = "comment")
    private String ilepEvaluateUploadedSiteVisitDoc;

    @ApiModelProperty(name = "instituteUploadExtraEvidenceDeadLine" , example = "123123123" , value = "Dead line date and time in epoc" , dataType = "Long"
            , position = 4 ,allowEmptyValue = false )
    private Long instituteUploadExtraEvidenceDeadLine;
    
}
