package com.mindteck.common.exceptionHandler;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.models.rest.Status;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EmptyInputException extends RuntimeException{
    private int code;
    private HttpStatus status;
    private String description;
    private Status status1;
    private ErrorCode errorCode;

    public EmptyInputException(int code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }

    public EmptyInputException(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public EmptyInputException(Status status1, ErrorCode errorCode) {
        this.status1 = status1;
        this.errorCode = errorCode;
    }

    public EmptyInputException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getStatus(), errorCode.getDescription());
    }
}
