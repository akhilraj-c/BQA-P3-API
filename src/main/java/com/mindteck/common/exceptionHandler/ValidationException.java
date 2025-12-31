package com.mindteck.common.exceptionHandler;

import com.mindteck.common.constants.Enum.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ValidationException extends RuntimeException {

    private int code;
    private HttpStatus status;
    private String description;

    public ValidationException(int code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    public ValidationException(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public ValidationException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getStatus(), errorCode.getDescription());
    }

}
