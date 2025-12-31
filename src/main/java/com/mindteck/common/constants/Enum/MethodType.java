package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MethodType {

    MEETING_LINK(1,"Meeting link"),
    PERSON_LOCATION(2, "Person location")
    ;

    private final Integer code;
    private final String message;

    public static MethodType getByCode(Integer code) {
        for (MethodType methodType: values()) {
            if(methodType.getCode().equals(code))
                return methodType;
        }
        return null;
    }
}
