package com.mindteck.common.modules.user.model.rest;

import com.mindteck.models_cas.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetRoleResponseModel {
    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long")
    private Long roleId;

    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long")
    private int userType;

    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long")
    private int subType;

    @ApiModelProperty(name = "userId", example = "123123123", value = "userId", dataType = "Long")
    private String name;


    public GetRoleResponseModel(Role role) {
        this.roleId = role.getId();
        this.name = role.getName();
        this.userType = role.getUserType();
        this.subType = role.getSubType();
    }

}
