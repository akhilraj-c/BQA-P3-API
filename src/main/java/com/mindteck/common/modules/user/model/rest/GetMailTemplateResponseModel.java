package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class GetMailTemplateResponseModel {

    @ApiModelProperty(value = "id" , example = "1" , dataType = "Long" , position = 1)
    private Long id;

    @ApiModelProperty(value = "templateName" , example = "Dfo admin mail" , dataType = "String" , position = 2)
    private String templateName;

    @ApiModelProperty(value = "templateDescription" , example = "Ti send email to dfo" , dataType = "String" , position = 3)
    private String templateDescription;

    @ApiModelProperty(value = "templateBody" , example = "<div>....</div>>" , dataType = "String" , position = 4)
    private String templateBody;

    @ApiModelProperty(value = "templateCode" , example = "DFO_TO_ADMIN" , dataType = "String" , position = 5)
    private String templateCode;

    @ApiModelProperty(value = "templateSubject" , example = "Institution - Listing to ILEP" , dataType = "String" , position = 6)
    private String templateSubject;

    @ApiModelProperty(value = "instituteCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer instituteCc;

    @ApiModelProperty(value = "dfoAdminCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer dfoAdminCc;

    @ApiModelProperty(value = "amCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer amCc;

    @ApiModelProperty(value = "dfoMemberCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer dfoMemberCc;

    @ApiModelProperty(value = "chiefCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer chiefCc;

    @ApiModelProperty(value = "directorCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer directorCc;

    @ApiModelProperty(value = "ilepCc" , example = "1" , dataType = "Integer" , position = 6)
    private Integer ilepCc;
}
