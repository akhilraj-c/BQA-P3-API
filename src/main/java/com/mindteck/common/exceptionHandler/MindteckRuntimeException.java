package com.mindteck.common.exceptionHandler;

public class MindteckRuntimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final String message;

    public MindteckRuntimeException(String message) {
        super(message, (Throwable)null, false, false);
        this.message = message;
    }

    public MindteckRuntimeException(String message, Throwable cause) {
        super(message, cause, false, false);
        this.message = message;
    }

    public String toString() {
        return this.message;
    }

    public String getMessage() {
        return this.message;
    }
}
