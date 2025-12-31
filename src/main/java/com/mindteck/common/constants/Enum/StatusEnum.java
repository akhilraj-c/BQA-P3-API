package com.mindteck.common.constants.Enum;

import lombok.Getter;

@Getter
public enum StatusEnum {

    SUCCESS(20001, "Success"),
    FAILED(20002, "Failed"),
    NO_DATA(20004, "Not found");

    private final Integer code;
    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
