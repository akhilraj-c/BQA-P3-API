package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class GetLogResponseModel {
    @ApiModelProperty(name = "id" , example = "15" ,dataType = "Long" , value ="id of the form", position = 0)
    private Long id;

     @ApiModelProperty(name = "formUniqueId" , example = "124567" ,dataType = "String" , value ="uniqueID of the form", position = 1)
    private Long formUniqueId;

    @ApiModelProperty(name="userId", value = "userId of the user" , example = "8" ,dataType = "Long" , position = 2)
    private Long userId;

     @ApiModelProperty(name="userType" ,value = " type of the user" , example = "7" ,dataType = "Integer" , position = 3)
    private Integer userType;

     @ApiModelProperty(name="userSubType" ,value = "subType of the user" , example = "5" ,dataType = "String" , position = 4)
    private Integer userSubType;

     @ApiModelProperty(name="userName" ,value = "name of the user" , example = "Ilep member 1" ,dataType = "String" , position = 5)
    private String userName;

     @ApiModelProperty(name="previousMessage" ,value = "Status message" , example = "Institution updates the site visit document" ,dataType = "String" , position = 6)
    private String previousMessage;

     @ApiModelProperty(name="previousStatus" ,value = "previous status of the form" , example = "43" ,dataType = "Integer" , position = 7)
    private Integer previousStatus;

     @ApiModelProperty(name="changedMessage" ,value = " Changed status message " , example = "Meeting completed and Mom uploaded - AM changes the site visit date" ,dataType = "String" , position = 8)
    private String changedMessage;

     @ApiModelProperty(name="changedStatus" ,value = "Changed status of the form" , example = "42" ,dataType = "String" , position = 9)
    private Integer changedStatus;

     @ApiModelProperty(name="createdTime" ,value = "created Time" , example = "10201456780112" ,dataType = "Long" , position = 10)
    public Long createdTime;

     @ApiModelProperty(name="lastUpdatedTime" ,value = "Last updated time" , example = "10201456780112" ,dataType = "String" , position = 11)
    public Long lastUpdatedTime;


    public GetLogResponseModel(Long id, Long formUniqueId, Long userId, Integer userType, Integer userSubType, String userName, String previousMessage, Integer previousStatus, String changedMessage, Integer changedStatus, Long createdTime, Long lastUpdatedTime) {
        this.id = id;
        this.formUniqueId = formUniqueId;
        this.userId = userId;
        this.userType = userType;
        this.userSubType = userSubType;
        this.userName = userName;
        this.previousMessage = previousMessage;
        this.previousStatus = previousStatus;
        this.changedMessage = changedMessage;
        this.changedStatus = changedStatus;
        this.createdTime = createdTime;
        this.lastUpdatedTime = lastUpdatedTime;
    }
}


