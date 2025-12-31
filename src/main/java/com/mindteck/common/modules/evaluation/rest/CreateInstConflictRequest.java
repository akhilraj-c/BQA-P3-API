package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstConflictRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "Unique form id assigned for the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "institutionConflictStatus is required")
    @ApiModelProperty(name = "institutionConflictStatus" , example = "1" , value = "0 - no conflict \n 1 - conflict" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer institutionConflictStatus;

    private List<InstitutionConflictData> institutionConflictDataList;





}
