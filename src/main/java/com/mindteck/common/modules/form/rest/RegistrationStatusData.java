package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class RegistrationStatusData {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "9 digit unique id" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
   private Long formUniqueId;

    @NotNull(message = "formId is required")
    @ApiModelProperty(name = "formId" , example = "2" , value = "id of the form (Primary key)" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formId;

    @NotNull(message = "submissionDate is required")
    @ApiModelProperty(name = "plannedSubmissionDate" , example = "09-2023" , value = "yearly planned submission date" , dataType = "Long"
             , position = 1,allowEmptyValue = false , required = true)
    private String plannedSubmissionDate;




}
