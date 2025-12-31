package com.mindteck.common.modules.standards.model;


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
public class SaveStandardsRequest {


    @ApiModelProperty(name = "formUniqueId", example = "1", required = true)
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    @ApiModelProperty(name = "amUserId", example = "1")
    private Long amUserId;

    @ApiModelProperty(name = "amUserId", example = "1")
    private Integer enableUserIdCheck = 1;

    @ApiModelProperty(name = "overallStatus", example = "1")
    private Integer overallStatus;

    @ApiModelProperty(name = "registrationStatus", example = "1")
    private Integer registrationStatus;

    @ApiModelProperty(name = "deleteExistingContent", example = "1")
    private Integer deleteExistingContent = 0;

    @ApiModelProperty(name = "isDraft", example = "1")
    private Integer isDraft = 0;

    private List<StandardWorkFlow> standardWorkFlowsList;

    private Integer deleteAll = 0;

}
