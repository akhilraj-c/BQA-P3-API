package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.models.rest.AbstractListRequest;
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
public class GetUsersRequest extends AbstractListRequest {

    @NotNull(message = "userType is required")
    @ApiModelProperty(name = "userType" , example = "2" , value = "Type of the user " +
            "\n 1 - Institution \n 2 - BQA-admin \n 3 - DFO member \n 4 - CHIEF  \n 5 - Director \n 6 - General director \n 7 - ILEP Member" +
            "\n 8 - QAC \n 9 - NAC  \n 10 - MOC " , dataType = "Integer"
            , position = 2, allowEmptyValue = false , allowableValues = "1,2,3,4,5,6,7,8,9,10", required = true)
    private Integer userType;

    @NotNull(message = "userSubType is required")
    @ApiModelProperty(name = "userSubType" , example = "1" , value = "SubType of user for knowing admin or normal user \n 0 - Admin \n 1 - Manager \n 2 - User"
            , dataType = "Integer", position = 3, allowEmptyValue = false , allowableValues = "0,1,2", required = true)
    private Integer userSubType;
}
