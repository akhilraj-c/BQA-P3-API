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
public class GetMeetingDetailsResponseModel {
    @ApiModelProperty(value = "formUniqueId", dataType = "Long", example = "1234789850" , position = 1)
    private Long formUniqueId;

    @ApiModelProperty(value = "first meeting date and time in epoc", dataType = "Long", example = "1692973539000" , position = 2)
    private Long firstDateAndTime;

    @ApiModelProperty(name = "firstLinkOrLocation" , example = "https://meeting.link/wreffds" , value = "Meeting link or the location of the person for first meeting" , dataType = "String" ,
            position = 3)
    private String firstLinkOrLocation;


    @ApiModelProperty(name = "firstMethod" , example = "1" , value = "first meeting method\n1 - meeting link  , 2 - person's location" , dataType = "Integer" ,
            position = 4)
    private Integer firstMethod;

    @ApiModelProperty(name = "deadLine" , example = "123123123" , value = "date and time in epoc" , dataType = "Long"
            , position = 5 ,allowEmptyValue = false )
    private Long deadLine;

    @ApiModelProperty(value = "second meeting date and time in epoc", dataType = "Long", example = "1692973539000" , position = 6)
    private Long secondDateAndTime;

    @ApiModelProperty(name = "secondLinkOrLocation" , example = "https://meeting.link/wreffds" , value = "Meeting link or the location of the person for second meeting" , dataType = "String" ,
            position = 7)
    private String secondLinkOrLocation;


    @ApiModelProperty(name = "secondMethod" , example = "1" , value = "Second meeting method\n1 - meeting link  , 2 - person's location" , dataType = "Integer" ,
            position = 8)
    private Integer secondMethod;

      @ApiModelProperty(name = "firstMeetingDocumentsAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 9 ,allowEmptyValue = false )
    private String firstMeetingDocumentsAndComments;



    @ApiModelProperty(name = "firstMeetingReportDocumentAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 10 ,allowEmptyValue = false )
    private String firstMeetingReportDocumentAndComments;

    @ApiModelProperty(name = "firstMeetingQuestionAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 11 ,allowEmptyValue = false )
    private String firstMeetingQuestionAndComments;

    @ApiModelProperty(name = "firstMeetingEvidenceAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 12 ,allowEmptyValue = false )
    private String firstMeetingEvidenceAndComments;


    @ApiModelProperty(name = "secondMeetingReportDocumentAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 13 ,allowEmptyValue = false )
    private String secondMeetingReportDocumentAndComments;

    @ApiModelProperty(name = "secondMeetingQuestionAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 14 ,allowEmptyValue = false )
    private String secondMeetingQuestionAndComments;

    @ApiModelProperty(name = "secondMeetingEvidenceAndComments" , example = "[{\"document\":\"s3 file link 1\",\"comment\":\"comment 1\"},{\"document\":\"s3 file link 2\",\"comment\":\"comment 2\"},{\"document\":\"s3 file link 3\",\"comment\":\"comment 3\"}]" , value = "Stringified JSON Array" , dataType = "String"
            , position = 15 ,allowEmptyValue = false )
    private String secondMeetingEvidenceAndComments;
}
