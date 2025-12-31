package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.models.rest.AbstractListRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetInstituteUserDataListRequest extends AbstractListRequest {
    @ApiModelProperty(value = "searchValue" , example = "test")
    private String searchValue;


}
