package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
public class DateExtensionRequest {

    @NotNull(message = "type is required")
    @ApiModelProperty(name = "type" , example = "1" , value = "type" , dataType = "Integer", required = true)
    private Integer type;

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;

    @NotNull(message = "formId is required")
    @ApiModelProperty(name = "formId" , example = "2" , value = "Id of the form" , dataType = "Long"
            , position = 3, allowEmptyValue = false , required = true )
    private Long formId;

    @NotNull(message = "extensionDate is required")
    @ApiModelProperty(name = "extensionDate" , example = "1231231231231" , value = "extension date in epoch" , dataType = "Long"
            , position = 4,allowEmptyValue = false , required = true)
    private Long extensionDate;
    @NotNull(message = "dateExtensionReason is required")
    @NotEmpty(message = "dateExtensionReason is required")
    @ApiModelProperty(name = "dateExtensionReason" , example = "valid reason" , value = "Reason for the date extension" , dataType = "String"
            , position = 5,allowEmptyValue = false , required = true)
    private String dateExtensionReason;

    @ApiModelProperty(name = "evaluationExtensionDate")
    private Long evaluationExtensionDate;

    @ApiModelProperty(name = "verificationExtensionDate")
    private Long verificationExtensionDate;
}
