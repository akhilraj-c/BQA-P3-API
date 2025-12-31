package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SetMailTemplateRequest {

    @ApiModelProperty(example = "<div>....</div>", required = true)
    private String mailBody;

    @ApiModelProperty(example = "DFO_ASSIGN_AM", required = true)
    private String templateCode;

    @ApiModelProperty(example = "Institution - Listing to ILEP" , required = true)
    private String templateSubject;

    @ApiModelProperty(example = "1" , required = true)
    private Integer instituteCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer dfoAdminCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer amCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer dfoMemberCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer chiefCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer directorCc;

    @ApiModelProperty(example = "1" , required = true)
    private Integer ilepCc;
}
