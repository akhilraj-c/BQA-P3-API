package com.mindteck.common.modules.user.model.rest;

import com.mindteck.models_cas.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetUserDataResponseModel {

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


    @ApiModelProperty(example = "1", required = true, value = "subType", dataType = "Integer", position = 6)
    private Integer reportAccess;

    @ApiModelProperty(example = "1", required = true, value = "subType", dataType = "Integer", position = 6)
    private Integer accessRight;

    @ApiModelProperty(example = "1", required = true, value = "active", dataType = "Integer", position = 6)
    private Integer active;

    @ApiModelProperty(name = "bio", example = "s3 url", value = "s3 url of the user bio doc", dataType = "String"
            , position = 1, allowEmptyValue = false, required = true)
    private String bio;

    @ApiModelProperty(name = "roles", value = "Roles assigned to the user", dataType = "Set<RoleDTO>", position = 10)
    private Set<GetRoleResponseModel> roles;

    public GetUserDataResponseModel(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.emailId = user.getEmailId();
        this.position = user.getPosition();
        this.contactNumber = user.getContactNumber();
        this.reportAccess = user.getReportAccess();
        this.accessRight = user.getAccessRight();
        this.active = user.getActive();
        this.bio = user.getBio();
        this.roles = user.getRoles().stream()
                .map(GetRoleResponseModel::new)
                .collect(Collectors.toSet());
    }
}
