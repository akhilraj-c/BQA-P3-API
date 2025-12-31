package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active"),

    ;

    private final Integer code;
    private final String message;


}
