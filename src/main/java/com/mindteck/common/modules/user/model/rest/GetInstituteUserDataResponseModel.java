package com.mindteck.common.modules.user.model.rest;

import com.mindteck.models_cas.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetInstituteUserDataResponseModel {

    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long"
            , position = 1, allowEmptyValue = false, required = true)
    private Long userId;

    @ApiModelProperty(name = "username", example = "testName", value = "formId", dataType = "String"
            , position = 2, allowEmptyValue = false, required = true)
    private String username;

    @ApiModelProperty(name = "emailId", example = "1", value = "emailId", dataType = "String"
            , position = 3, allowEmptyValue = false, required = true)
    private String emailId;

    @ApiModelProperty(name = "contactNumber", example = "954475802", value = "contactNumber", dataType = "String"
            , position = 5, allowEmptyValue = false, required = true)
    private String contactNumber;

    @ApiModelProperty(example = "1", required = true, value = "active", dataType = "Integer", position = 6)
    private Integer active;

    private String institutionName;

    public GetInstituteUserDataResponseModel(User user, String institutionName){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.emailId = user.getEmailId();
        this.active = user.getActive();
        this.contactNumber = user.getContactNumber();
        this.institutionName=institutionName;

    }

}
