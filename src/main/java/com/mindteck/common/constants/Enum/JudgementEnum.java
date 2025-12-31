package com.mindteck.common.constants.Enum;

import lombok.Getter;

@Getter
public enum JudgementEnum {

    MET(1, "Met"),
    PARTIALLY_MET(2, "Partially met"),
    NOT_MET(3, "Not met");

    private final Integer code;
    private final String message;

    JudgementEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
