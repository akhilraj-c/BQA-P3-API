package com.mindteck.common.modules.user.model.rest;


import com.mindteck.common.models.rest.AbstractListRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDashboardUserListRequest extends AbstractListRequest {
    @ApiModelProperty(value = "0 ,1 or null" , example = "1")
    private Integer activeStatus;

    @ApiModelProperty(value = "searchValue" , example = "test")
    private String searchValue;
}
