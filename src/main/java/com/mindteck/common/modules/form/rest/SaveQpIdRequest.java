package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SaveQpIdRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long formUniqueId;
    @NotBlank(message = "qpid is required")
    @ApiModelProperty(name = "qpId" , example = "1345" , value = "qpId" , dataType = "String"
            , position = 2, allowEmptyValue = false , required = true)
    private String qpId;
}
