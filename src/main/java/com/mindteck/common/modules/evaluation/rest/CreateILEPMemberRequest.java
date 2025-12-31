package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateILEPMemberRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "Unique form id assigned for the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "memberId")
    private List<Long> memberId;

    @ApiModelProperty(name = "memberHead")
    private Long memberHead;

    @ApiModelProperty(name = "isVerificationPanelRequired")
    private Integer isVerificationPanelRequired;
}
