package com.mindteck.common.constants.Enum;

import lombok.Getter;

@Getter
public enum OverallJudgementEnum {

    LISTED(1, "Listed"),
    DEFERRED_FOR_CONDITION_FULFILMENT(2, "Deferred for condition fulfilment"),
    NOT_LISTED(0, "Not listed");

    private final Integer code;
    private final String message;

    OverallJudgementEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
