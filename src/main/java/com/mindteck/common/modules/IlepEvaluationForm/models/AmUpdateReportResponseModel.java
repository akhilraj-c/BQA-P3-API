package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AmUpdateReportResponseModel {

    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
    @ApiModelProperty(name = "formUniqueId" , example = "123123123" , value = "formUniqueId" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long formUniqueId;
}
