package com.mindteck.common.modules.program_structure_work_flow.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaveProgramStructureFlowRequest {


    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "appId is required")
    private Long formUniqueId;

    @ApiModelProperty(name = "amUserId", example = "1")
    private Long amUserId;

    @ApiModelProperty(name = "amUserId", example = "1")
    private Integer enableUserIdCheck = 1;

    @ApiModelProperty(name = "overallStatus", example = "1")
    private Integer overallStatus;

    @ApiModelProperty(name = "deleteExistingContent", example = "1")
    private Integer deleteExistingContent = 0;

    private Integer deleteAll = 0;

    private List<ProgramStructureWorkFlow> programStructureFlowList;

}
