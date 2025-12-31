package com.mindteck.common.constants.Enum;

import lombok.Getter;

@Getter
public enum StatusCode {

    SUCCESS(1, "Success"),
    FAILED(0, "Failed"),
;
    private final Integer code;
    private final String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
