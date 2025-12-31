package com.mindteck.common.modules.standards.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkFlowFileDetails {
    @ApiModelProperty(value = "subVersion", dataType = "Integer" ,example = "1")
    private Integer subVersion;
    @ApiModelProperty(value = "file", dataType = "String" ,example = "1")
    private String file;
    @ApiModelProperty(value = "type", dataType = "String" ,example = "1")
    private String type;
    @ApiModelProperty(value = "description", dataType = "String" ,example = "1")
    private String description;
    @ApiModelProperty(value = "amFileStatus", dataType = "Integer" ,example = "1")
    private Integer amFileStatus;
    @ApiModelProperty(value = "amFileComment", dataType = "String" ,example = "1")
    private String amFileComment;
    @ApiModelProperty(name = "latestAmFileCheck", dataType = "Integer" ,example = "null for pending, 0 for not completed 1 for completed, ")
    public Integer latestAmFileCheck;
}
