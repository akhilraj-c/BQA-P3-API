package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetApplicationManagerListResponseModel {

    @ApiModelProperty(name = "userId", example = "123123123", value = "123456", dataType = "Long"
            , position = 1, allowEmptyValue = false, required = true)
    private Long userId;


    @ApiModelProperty(name = "username", example = "1345", value = "username", dataType = "String"
            , position = 2, allowEmptyValue = false, required = true)
    private String username;

    @ApiModelProperty(name = "registrationStatus", example = "1", value = "registrationStatus", dataType = "Integer"
            , position = 3, allowEmptyValue = false, required = true)
    private String emailId;

//    @ApiModelProperty(example = "Hello asmpale", required = true, value = "institutionName", dataType = "String", position = 1)
//    private int userType;
//
//    @ApiModelProperty(name = "submissionDate", example = "1231231231231", value = "submissionDate", dataType = "Long"
//            , position = 1, allowEmptyValue = false, required = true)
//    private int subType;

    @ApiModelProperty(name = "bio", example = "s3 url", value = "s3 url of the user bio doc", dataType = "String"
            , position = 1, allowEmptyValue = false, required = true)
    private String bio;

    @ApiModelProperty(name = "active", example = "1", value = "1 or  0", dataType = "Integer")
    private Integer active;
}
