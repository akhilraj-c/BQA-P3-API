package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CreateMeetingResponseModel {

    @ApiModelProperty(value = " meetingId" , example = "1" ,dataType = "Long" , position = 1)
    private Long meetingId;

    @ApiModelProperty(value = " message" , example = "meeting created successfully" ,dataType = "String" , position = 2)
    private String message;
}
