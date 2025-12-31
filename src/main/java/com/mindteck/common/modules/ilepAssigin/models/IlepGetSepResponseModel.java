package com.mindteck.common.modules.ilepAssigin.models;

import com.mindteck.common.modules.evaluation.rest.IlepConflictForm;
import com.mindteck.common.modules.evaluation.rest.InstitutionConflictData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IlepGetSepResponseModel {

       @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "unique id of the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

       @ApiModelProperty(name = "ilepMemberId", example = "25", value = "userId of the ilep member", dataType = "Long",
            position = 1, required = true)
    private Long ilepMemberId;

    @ApiModelProperty(name = "ilepMemberName", example = "soman", value = "userName of the ilep member", dataType = "Long",
            position = 1, required = true)
    private String ilepMemberName;

       @ApiModelProperty(name = "isHead", example = "1", value = "0 - not head \n 1 - head", dataType = "integer",
            position = 1, required = true)
    private Integer isHead;

       @ApiModelProperty(name = "isDfoApproved", example = "0", value = "0 - not approved \n 1 - approved", dataType = "Long",
            position = 1, required = true)
    private Integer isDfoApproved;

    @ApiModelProperty(name = "grandAccess", example = "0", value = "0 - not granted \n 1 - granted", dataType = "Long",
            position = 1, required = true)
    private Integer grandAccess;



       private List<InstitutionConflictData> institutionConflictDataList;


       private List<IlepConflictForm> ilepConflictForm;

    @ApiModelProperty(name = "institutionConflictStatus" , example = "1" , value = "0 - no conflict \n 1 - conflict" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer institutionConflictStatus;

    @ApiModelProperty(name = "ilepConflictStatus" , example = "1" , value = "0 - no conflict \n 1 - conflict" , dataType = "Integer" ,
            position = 1, required = true)
    private Integer ilepConflictStatus;

    @ApiModelProperty(name = "isConflictApprovedAm", example = "0", value = "0 - not approved \n 1 - approved", dataType = "Integer",
            position = 1, required = true)
    private Integer isConflictApprovedAm;

    @ApiModelProperty(name = "isConflictApprovedDfo", example = "0", value = "0 - not approved \n 1 - approved", dataType = "Integer",
            position = 1, required = true)
    private Integer isConflictApprovedDfo;
    
    
}
