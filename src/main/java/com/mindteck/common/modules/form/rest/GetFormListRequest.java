package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.rest.AbstractListRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
public class GetFormListRequest extends AbstractListRequest {

   // @NotNull(message = "userId is required")
    @ApiModelProperty(name = "userId" , example = "1" , value = "Logged user Id " , dataType = "Long"
            , position = 1)
    private Long userId;

    @NotNull(message = "userType is required")
    @ApiModelProperty(name = "userType" , example = "2" , value = "Type of the user " +
            "\n 1 - Institution \n 2 - BQA-admin \n 3 - DFO member \n 4 - CHIEF  \n 5 - Director \n 6 - General director \n 7 - ILEP Member" +
            "\n 8 - QAC \n 9 - NAC  \n 10 - MOC " , dataType = "Integer"
            , position = 2)
    private Integer userType;

    @NotNull(message = "userSubType is required")
    @ApiModelProperty(name = "userSubType" , example = "1" , value = "SubType of user for knowing admin or normal user \n 0 - Admin \n 1 - Manager \n 2 - User"
            , dataType = "Integer")
    private Integer userSubType;


    @ApiModelProperty(name = "plannedSubmissionDate" , example = "09-2023" , value = "Planned submission date for current year \n eg : 09-2023 , 01-2024 \n 0 - To get all forms"
            , dataType = "String", position = 4)
    private String plannedSubmissionDate;

    @ApiModelProperty(name = "formUniqueId" , example = "1" , value = "Form unique ID " , dataType = "Long"
           )
    private Long formUniqueId;

    private Long institutionId;

    private String searchValue;

}
