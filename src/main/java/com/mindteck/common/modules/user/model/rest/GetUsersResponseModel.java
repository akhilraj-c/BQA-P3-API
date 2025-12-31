package com.mindteck.common.modules.user.model.rest;

import com.mindteck.models_cas.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUsersResponseModel {

    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long"
            , position = 1, allowEmptyValue = false, required = true)
    private Long userId;

    @ApiModelProperty(name = "username", example = "testName", value = "formId", dataType = "String"
            , position = 2, allowEmptyValue = false, required = true)
    private String username;

    @ApiModelProperty(name = "emailId", example = "1", value = "emailId", dataType = "String"
            , position = 3, allowEmptyValue = false, required = true)
    private String emailId;

    @ApiModelProperty(example = "AM", required = true, value = "position", dataType = "String", position = 4)
    private String position;

    @ApiModelProperty(name = "contactNumber", example = "954475802", value = "contactNumber", dataType = "String"
            , position = 5, allowEmptyValue = false, required = true)
    private String contactNumber;

    @ApiModelProperty(name = "bio", example = "s3 url", value = "s3 url of the user bio doc", dataType = "String"
            , position = 1, allowEmptyValue = false, required = true)
    private String bio;

    private Set<GetRoleResponseModel> roles;

    public GetUsersResponseModel(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.emailId = user.getEmailId();
        this.position = user.getPosition();
        this.roles = user.getRoles().stream()
                .map(GetRoleResponseModel::new)
                .collect(Collectors.toSet());
    }



}
