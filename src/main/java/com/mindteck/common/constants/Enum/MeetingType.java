package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingType {

    FIRST_MEETING(1,"First Meeting"),
    SECOND_MEETING(2, "Second Meeting")
    ;

    private final Integer code;
    private final String message;

    public static MeetingType getByCode(Integer code) {
        for (MeetingType methodType: values()) {
            if(methodType.getCode().equals(code))
                return methodType;
        }
        return null;
    }
}
