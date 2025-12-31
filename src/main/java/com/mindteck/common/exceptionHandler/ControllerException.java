package com.mindteck.common.exceptionHandler;


import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.models.rest.Status;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ControllerException extends MindteckRuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String INPUT_VALIDATION_ERROR = "Input Validation errors. Unable to proceed with processing";

    private final String message;

    private final ErrorCode errorCode;

    private final Status status;


    private final transient Errors bindingErrors;


    public ControllerException(Errors bindingErrors) {
        this(ErrorCode.INPUT_VALIDATION_FAILED, bindingErrors, INPUT_VALIDATION_ERROR);
    }

    public ControllerException(Errors bindingErrors, String message) {
        this(ErrorCode.INPUT_VALIDATION_FAILED, bindingErrors, message);
    }

    public ControllerException(ErrorCode errorCode, Errors bindingErrors, String message) {
        super(message);
        this.errorCode = errorCode;
        this.bindingErrors = bindingErrors;
        this.message = message;
        this.status = null;
    }

    public ControllerException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
        this.bindingErrors = null;
        this.status = null;
    }

    public ControllerException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
        this.bindingErrors = null;
        this.status = null;
    }

    public ControllerException(Status status, Errors bindingErrors, ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.message = errorCode.getDescription();
        this.status = status;
        this.bindingErrors = bindingErrors;
        this.errorCode = errorCode;
    }

    public ControllerException(Errors bindingErrors , ErrorCode errorCode){
        super(errorCode.getDescription());
        this.message = errorCode.getDescription();
        this.status = null;
        this.bindingErrors = bindingErrors;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
