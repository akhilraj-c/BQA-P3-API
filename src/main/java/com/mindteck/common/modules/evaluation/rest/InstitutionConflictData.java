package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstitutionConflictData {

    @ApiModelProperty(name = "ilepUserId" , example = "6" , value = "User Id of the ilep member" , dataType = "Long" ,
            position = 1)
    private Long ilepUserId;

    @ApiModelProperty(name = "ilepMemberName" , example = "ILEP member 1" , value = "User name of the ilep member" , dataType = "String" ,
            position = 2)
    private String ilepMemberName;

    @ApiModelProperty(name = "ilepUserBio" , example = "s3 url" , value = "S3 link points to ilep member bio file" , dataType = "String" ,
            position = 2, required = true)
    private String ilepUserBio;

    private InstitutionConflictForm institutionConflictForm;


}
