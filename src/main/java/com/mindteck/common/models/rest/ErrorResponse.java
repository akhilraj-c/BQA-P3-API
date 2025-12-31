package com.mindteck.common.models.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mindteck.common.constants.Enum.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeName("errorResponse")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ErrorResponse {
    @JsonProperty
    private int code;
    @JsonProperty
    private HttpStatus status;
    @JsonProperty
    private String description;
    @JsonProperty
    private List errors;

    public ErrorResponse(int code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    public ErrorResponse(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorResponse(int code, HttpStatus status, String description, List<HashMap<String, String>> errors) {
        this.code = code;
        this.status = status;
        this.description = description;
        this.errors = errors;
    }

    public ErrorResponse(ErrorCode errorCodes) {
        this.code = errorCodes.getCode();
        this.status = errorCodes.getStatus();
        this.description = errorCodes.getDescription();
    }
}
