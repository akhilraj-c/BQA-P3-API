package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RejectSiteVisitRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "comment", example = "Test comment", value = "comment",
            dataType = "String",
            position = 2)
    private String comment;
}
