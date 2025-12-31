package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionConflictForm {

    @ApiModelProperty(name = "institutionName" , example = "Beharin university" , value = "Name of the institution" , dataType = "String" ,
            position = 1 )
    private String institutionName;


    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "formUniqueId" , dataType = "Long" ,
            position = 2 )
    private Long formUniqueId;

    @ApiModelProperty(name = "licenceBody" , example = "Motor Vehicle Department Kerala" , value = "Name of the licence provider body " , dataType = "String" ,
            position = 3 )
    private String licenceBody;

    @ApiModelProperty(name = "ilepMemberName" , example = "Ilep member 1" , value = "Name of the ILEP member " , dataType = "String" ,
            position = 4 )
    private String ilepMemberName;


    @ApiModelProperty(name = "areaOfConflict" , example = "This ILEP member asked bribe for a previous evaluation" , value = "The valid reason for the conflict" , dataType = "String" ,
            position = 4 )
    private String areaOfConflict;

    @ApiModelProperty(name = "date" , example = "1234567890123" , value = "Today's date" , dataType = "Long" ,
            position = 5 )
    private Long date;



}
