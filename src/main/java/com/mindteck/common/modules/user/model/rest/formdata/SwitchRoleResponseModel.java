package com.mindteck.common.modules.user.model.rest.formdata;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SwitchRoleResponseModel {
    @ApiModelProperty(name = "userType", example = "12", value = "userType number", dataType = "int"
            , position = 1 )
    private int userType;

    @ApiModelProperty(name = "subType", example = "12", value = "subType number", dataType = "int"
            , position = 2 )
    private int subType;
}
