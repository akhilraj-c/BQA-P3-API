package com.mindteck.common.modules.evaluation.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMeetingRequest {

    @NotNull(message = "formUniqueId is required")
    @ApiModelProperty(name = "formUniqueId" , example = "123456789" , value = "Unique form id assigned for the form" , dataType = "Long" ,
            position = 1, required = true)
    private Long formUniqueId;

    @NotNull(message = "dateTime is required")
    @ApiModelProperty(name = "dateTime" , example = "123456789" , value = "date and time epoch for meeting" , dataType = "Long" ,
            position = 2, required = true)
    private Long dateTime;

    @NotNull(message = "method is required")
    @ApiModelProperty(name = "method" , example = "1" , value = "1 - meeting link  , 2 - person's location" , dataType = "Integer" ,
            position = 3, required = true)
    private Integer method;

    @NotNull(message = "meetingType is required")
    @NotBlank(message = "meetingType is required")
    @ApiModelProperty(name = "linkOrLocation" , example = "https://meeting.link/wreffds" , value = "Meeting link or the location of the person" , dataType = "String" ,
            position = 4, required = true)
    private String linkOrLocation;

    @NotNull(message = "meetingType is required")
    @ApiModelProperty(name = "meetingType" , example = "1" , value = "1 - first meeting  , 2 - second meeting" , dataType = "Integer" ,
            position = 5, required = true)
    private Integer meetingType;
}
