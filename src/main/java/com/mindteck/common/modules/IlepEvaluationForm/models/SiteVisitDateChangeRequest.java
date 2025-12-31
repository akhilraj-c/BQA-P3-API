package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SiteVisitDateChangeRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "nine digit unique id of the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;
    @NotNull(message = "instituteName is required")
    @NotBlank(message = "instituteName is required")
    @ApiModelProperty(value = "name of the institution name", dataType = "String" ,example = "University"  , position = 2 , required = true)
    private String instituteName;
    @NotNull(message = "ilepMemberNames is required")
    @NotBlank(message = "ilepMemberNames is required")
    @ApiModelProperty(value = "Name of the ilep members", dataType = "String" ,example = "john" , position = 3 , required = true)
    private String IlepMemberNames;

    @ApiModelProperty(value = "Name of the licence Body", dataType = "String" ,example = "University of licence body " , position = 4 , required = true)
    private String licenceBody;

    @NotNull(message = "requestedDate1 is required")
    @ApiModelProperty(value = "Date requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 5 , required = true)
    private Long requestedDate1;


    @ApiModelProperty(value = "Date two requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 6 , required = true)
    private Long requestedDate2;


    @ApiModelProperty(value = "Date three requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 7 , required = true)
    private Long requestedDate3;
    @NotNull(message = "justification is required")
    @NotBlank(message = "justification is required")
    @ApiModelProperty(value = "Justification by the institution about date change", dataType = "String"
            ,example = "I will not be availabe due to personal reasons which i cannot disclose" , position = 8 , required = true)
    private String justification;
}
