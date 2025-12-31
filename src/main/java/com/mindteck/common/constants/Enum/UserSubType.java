package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSubType {

    ADMIN(0, "Admin type"),
    USER(2, "User type"),
    MANAGER(1, "Manager type"),

    ;

    private final Integer code;
    private final String message;

    public static UserSubType getByCode(Integer code) {

        for (UserSubType user : values()) {
            if (user.getCode() == code) {
                return user;
            }
        }
        return null;
    }


}
