package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DateExtensionApprovalRequest {
    @ApiModelProperty(example = "1", name = "userId" , required = true , value = "userId", dataType = "Integer")
    @NotNull(message = "userId is required")
    private Long userId;

    @ApiModelProperty(example = "1",name = "userType" , value = "type of the user",dataType = "Integer",required = true)
    @NotNull(message = "userType is required")
    private Integer userType;

    @ApiModelProperty(example = "12345689", name = "formUniqueId" , value = "9 digit form unique Id",dataType = "Integer",required = true)
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    @ApiModelProperty(example = "1", name = "dateExtensionStatus", value = "date extension status",dataType = "Integer",required = true)
    @NotNull(message = "dateExtensionStatus is required")
    private Integer dateExtensionStatus;

    @ApiModelProperty(example = "2", name = "dateExtensionType", value = "date extension type", dataType = "Integer", required = true)
    @NotNull(message = "dateExtensionType is required")
    private Integer dateExtensionType;

    @ApiModelProperty(example = "1234567890123", name = "newExtensionDate", value = "new extension date in milli seconds", dataType = "Integer", required = true)
    private Long newExtensionDate;

    @ApiModelProperty(name = "evaluationExtensionDate")
    private Long evaluationExtensionDate;

    @ApiModelProperty(name = "verificationExtensionDate")
    private Long verificationExtensionDate;

}

