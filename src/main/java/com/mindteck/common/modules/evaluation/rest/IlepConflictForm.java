package com.mindteck.common.modules.evaluation.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IlepConflictForm {

    @ApiModelProperty(name = "institutionName" , example = "Beharin university" , value = "Name of the institution" , dataType = "String" ,
            position = 1 )
    private String institutionName;

    private Long IlepUserId;

    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "nine digit unique id of the form" , dataType = "Long" ,
            position = 2 )
    private Long formUniqueId;

    @ApiModelProperty(name = "noConflictBeingMember" , example = "1" ,  value = "0 - false \n 1 - true"  , dataType = "Integer" ,
            position = 3 )
    private Integer noConflictBeingMember;

    @ApiModelProperty(name = "agreeNoConsultOtherAssignment" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 4 )
    private Integer agreeNoConsultOtherAssignment;

    @ApiModelProperty(name = "confirmConfidentialityAndSecureReturn" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 5 )
    private Integer confirmConfidentialityAndSecureReturn;

    @ApiModelProperty(name = "agreeToShareMyBiographyWithInstitution" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 6 )
    private Integer agreeToShareMyBiographyWithInstitution;

    @ApiModelProperty(name = "agreeToShareMyBiographyInBqaWebsite" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 7 )
    private Integer agreeToShareMyBiographyInBqaWebsite;

    @ApiModelProperty(name = "declareFiveStatementAsTrue" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 7 )
    private Integer declareFiveStatementAsTrue;

    @ApiModelProperty(name = "publicSector" , example = "1" , value = "0 - false \n 1 - true" , dataType = "Integer" ,
            position = 8 )
    private Integer publicSector;


}
