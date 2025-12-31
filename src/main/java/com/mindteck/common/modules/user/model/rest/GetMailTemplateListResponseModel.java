package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetMailTemplateListResponseModel {
    @ApiModelProperty(value = "id" , example = "1" , dataType = "Long" , position = 1)
    private Long id;

    @ApiModelProperty(value = "templateName" , example = "Dfo admin mail" , dataType = "String" , position = 2)
    private String templateName;

    @ApiModelProperty(value = "templateDescription" , example = "Ti send email to dfo" , dataType = "String" , position = 3)
    private String templateDescription;

    @ApiModelProperty(value = "templateBody" , example = "<div>....</div>>" , dataType = "String" , position = 4)
    private String templateBody;

    @ApiModelProperty(value = "templateCode" , example = "DFO_ASSIGN_AM" , dataType = "String" , position = 5)
    private String templateCode;

    @ApiModelProperty(value = "templateSubject" , example = "Institution - Listing to ILEP" , dataType = "String" , position = 6)
    private String templateSubject;
}
