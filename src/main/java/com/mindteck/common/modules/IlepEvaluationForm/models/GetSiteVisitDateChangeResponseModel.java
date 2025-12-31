package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSiteVisitDateChangeResponseModel {


    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "nine digit unique id of the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(value = "name of the institution name", dataType = "String" ,example = "University"  , position = 2 , required = true)
    private String instituteName;

    @ApiModelProperty(value = "Name of the ilep members", dataType = "String" ,example = "john,ravi,mishra" , position = 3 , required = true)
    private String IlepMemberNames;

    @ApiModelProperty(value = "Name of the licence Body", dataType = "String" ,example = "University of licence body " , position = 4 , required = true)
    private String licenceBody;


    @ApiModelProperty(value = "Date 1 requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 5 , required = true)
    private Long requestedDate1;

    @ApiModelProperty(value = "Date 2 requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 6 , required = true)
    private Long requestedDate2;

    @ApiModelProperty(value = "Date 3 requested by the institution", dataType = "Long" ,example = "1000145674123" , position = 7 , required = true)
    private Long requestedDate3;

    @ApiModelProperty(value = "Justification by the institution about date change", dataType = "String"
            ,example = "I will not be availabe due to personal reasons which i cannot disclose" , position = 8 , required = true)
    private String justification;

    @ApiModelProperty(value = " 0 - approve \n 1 - reject", dataType = "Integer" ,example = "1" , position = 9, required = true)
    private Integer amApprove;
    @ApiModelProperty(value = " 0 - approve \n 1 - reject", dataType = "Integer" ,example = "1" , position = 10, required = true)
    private String chiefApprove;
}
