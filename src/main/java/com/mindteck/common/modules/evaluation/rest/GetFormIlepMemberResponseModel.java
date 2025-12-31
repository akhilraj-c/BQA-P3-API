package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetFormIlepMemberResponseModel {


    @ApiModelProperty(name = "formUniqueId", example = "123456789", value = "Unique form id assigned for the form", dataType = "Long",
            position = 1, required = true)
    private Long formUniqueId;

    @ApiModelProperty(name = "panelId", example = "5", value = "Panel id is used to determine the panel associated with the form", dataType = "Long",
            position = 2, required = true)
    private Long panelId;

    @ApiModelProperty(name = "lepMemberId", example = "2", value = "user id of the ilep member", dataType = "Long",
            position = 3, required = true)
    private Long ilepMemberId;

    @ApiModelProperty(name = "ilepMemberName", example = "ilep_member_1", value = "Name of the ilep member", dataType = "String",
            position = 4, required = true)
    private String ilepMemberName;

    @ApiModelProperty(name = "isHead", example = "1", value = "0 - false \n 1 - true", dataType = "Integer",
            position = 5, required = true)
    private Integer isHead;

    @ApiModelProperty(name = "panelStatus", example = "1", value = "0 - false \n 1 - true", dataType = "Integer",
            position = 6, required = true)
    private Integer panelStatus;


    public GetFormIlepMemberResponseModel(Long formUniqueId, Long panelId, Long ilepMemberId, String ilepMemberName, Integer isHead, Integer panelStatus) {
        this.formUniqueId = formUniqueId;
        this.panelId = panelId;
        this.ilepMemberId = ilepMemberId;
        this.ilepMemberName = ilepMemberName;
        this.isHead = isHead;
        this.panelStatus = panelStatus;
    }
}
