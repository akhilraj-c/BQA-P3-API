package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class CreateSiteVisitRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "unique id of the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "instituteName is required")
    @NotBlank(message = "instituteName is required")
    @ApiModelProperty(value = "name of the institution name", dataType = "String" ,example = "University"  , position = 2 , required = true)
    private String instituteName;

    @NotNull(message = "personInvolved is required")
    @NotBlank(message = "personInvolved is required")
    @ApiModelProperty(value = "Name of the person involved", dataType = "String" ,example = "john" , position = 3 , required = true)
    private String personInvolved;

    @NotNull(message = "siteVisitRequired is required")
    @ApiModelProperty(value = " 0 - Not required \n 1 - required", dataType = "Integer" ,example = "1" , position = 4, required = true)
    private Integer siteVisitRequired;


    @ApiModelProperty(value = "First Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 5 , required = true)
    private Long date1;

    @ApiModelProperty(value = "Second Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 6 , required = true)
    private Long date2;

    @ApiModelProperty(value = "Third Date for the visit", dataType = "Long" ,example = "1000145674123" , position = 7 , required = true)
    private Long date3;


    @ApiModelProperty(value = "Name of the representative", dataType = "String" ,example = "Ram kumar" , position = 8 , required = true)
    private String instituteRepresentativeName;

    @ApiModelProperty(value = "Position of the representative", dataType = "String" ,example = "Manager" , position = 9 , required = true)
    private String instituteRepresentativePosition;

    @ApiModelProperty(value = "Contact of the representative", dataType = "String" ,example = "919474158870" , position = 10 , required = true)
    private String instituteRepresentativeContact;

    @ApiModelProperty(value = "Email of the representative", dataType = "String" ,example = "ramkumar@gmail.com" , position = 11 , required = true)
    private String instituteRepresentativeEmail;

    @ApiModelProperty(value = "array of json contains s3 url , version , filename and description", dataType = "String" ,example = "\"[{\"url\":\"s3 url\",\"version\":1,\"fileName\":\"fileName\",\"remarks\":\"remarks\"}]\"" , position = 12 , required = true)
    private String ilepDocuments;


}
