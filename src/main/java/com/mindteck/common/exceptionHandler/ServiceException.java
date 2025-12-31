package com.mindteck.common.exceptionHandler;


import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.models.rest.Status;
import lombok.Getter;

@Getter
public class ServiceException extends MindteckRuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    private final ErrorCode errorCode;

    private final Status status;


    public ServiceException(ErrorCode errorCode, String message) {
        super(errorCode.getDescription() + " ===>" + message);
        this.message = errorCode.getDescription() + " ===>" + message;
        this.errorCode = errorCode;
        this.status = null;
    }

    public ServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
        this.status = null;
    }

    public ServiceException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
        this.status = null;
    }

    public ServiceException(Status status, ErrorCode errorCode){
        super(errorCode.getDescription());
        this.message = errorCode.getDescription();
        this.status = status;
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
        this.status = null;
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
