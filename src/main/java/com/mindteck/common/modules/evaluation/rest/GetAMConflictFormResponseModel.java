package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GetAMConflictFormResponseModel {

    @ApiModelProperty(name = "institutionConflictStatus" , example = "1" , value = "0 - no conflict \n 1 - conflict" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer institutionConflictStatus;

    private List<InstitutionConflictData> institutionConflictDataList;

    @ApiModelProperty(name = "ilepConflictStatus" , example = "1" , value = "0 - no conflict \n 1 - conflict" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer ilepConflictStatus;

    private List<IlepConflictForm> ilepConflictForm;
}
