package com.mindteck.common.modules.IlepEvaluationForm.models;

import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.IlepStandard1Data;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.IlepStandard2Data;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.IlepStandard3Data;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.IlepStandard4Data;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.IlepStandard5Data;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IlepEvaluateRequest {
    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "formUniqueId" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    private IlepStandard1Data ilepStandard1Data;

    private IlepStandard2Data ilepStandard2Data;

    private IlepStandard3Data ilepStandard3Data;

    private IlepStandard4Data ilepStandard4Data;

    private IlepStandard5Data ilepStandard5Data;
}
