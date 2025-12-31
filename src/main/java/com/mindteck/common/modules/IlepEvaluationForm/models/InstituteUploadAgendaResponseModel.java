package com.mindteck.common.modules.IlepEvaluationForm.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class InstituteUploadAgendaResponseModel {
    @ApiModelProperty(value = " 1 : success , 0 : failed" , example = "1" , allowableValues = "0,1" ,dataType = "Integer" , position = 1)
    private Integer success;
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "unique id of the form" , dataType = "Long" ,
            position = 2)
    private Long formUniqueId;
}
