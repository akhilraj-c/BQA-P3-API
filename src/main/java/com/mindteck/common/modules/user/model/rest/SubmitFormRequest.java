package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanel;
import com.mindteck.common.modules.mapping_panel.model.MappingPanel;
import com.mindteck.common.modules.program_structure.model.ProgramStructure;
import com.mindteck.common.modules.program_structure_work_flow.model.ProgramStructureWorkFlow;
import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitFormRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name =  "formUniqueId")
    private Long formUniqueId;

    private Integer draft = 0;
    private Integer deleteAll = 0;

    private Integer isVerification = 0;
    private Integer isEvaluation = 0 ;

    @ApiModelProperty( name = "overallAdminCheckDetails")
    private OverallAdminCheckDetails overallAdminCheckDetails; //process only if draft==0

    @ApiModelProperty( name = "programStructureList")
    private List<ProgramStructure> programStructureList;

    @ApiModelProperty( name = "standardWorkFlowsList")
    private List<StandardWorkFlow> standardWorkFlowsList;

    @ApiModelProperty( name = "programStructureFlowList")
    private List<ProgramStructureWorkFlow> programStructureFlowList;

    @ApiModelProperty( name = "mappingPanelList")
    private List<MappingPanel> mappingPanelList;

    @ApiModelProperty( name = "confirmationPanelList")
    private List<ConfirmationPanel> confirmationPanelList;

    @ApiModelProperty(name = "requestType" , example = "21" ,dataType = "Integer")
    private Integer requestType;

    private String qpId ;

}
