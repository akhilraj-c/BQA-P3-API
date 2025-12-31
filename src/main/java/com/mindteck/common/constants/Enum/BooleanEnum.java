package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanEnum {

    TRUE(1, "true"),
    FALSE(0, "false"),
    ;
    private final Integer code;
    private final String message;

}
