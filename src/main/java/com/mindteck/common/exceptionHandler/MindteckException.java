package com.mindteck.common.exceptionHandler;

public class MindteckException extends Exception{
    private static final long serialVersionUID = 1L;
    private final String message;

    public MindteckException(String message) {
        super(message, (Throwable)null, false, false);
        this.message = message;
    }

    public MindteckException(String message, Throwable cause) {
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
