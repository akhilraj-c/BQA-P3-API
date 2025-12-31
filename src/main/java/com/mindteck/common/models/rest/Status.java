package com.mindteck.common.models.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status implements Serializable {

    @ApiModelProperty(example = "20001", position = 1)
    private Integer statusCode;

    @ApiModelProperty(example = "1025454301", position = 3)
    private Long startTime;

    @ApiModelProperty(example = "1025459301", position = 4)
    private Long endTime;

    @ApiModelProperty(example = "1", position = 5)
    private Integer apiId;

    @ApiModelProperty(example = "api url" , position = 6)
    private String apiUrl;

    public Status(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
        if (!WebUtils.isDebug())
            this.startTime = null;

    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
        if (!WebUtils.isDebug())
            this.endTime = null;

    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
        if (!WebUtils.isDebug())
            this.apiId = null;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        if(!WebUtils.isDebug()) {
            this.apiUrl = null;
        }
    }
}
