package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateExtensionStatus {
    REQUESTED(1 , "Date extension requested by institution"),
    APPROVED(2, "Date extension request approved "),

    REJECTED(3,"Date extension rejected "),

            ;
    private  final  Integer code;
    private final String description;

    public static DateExtensionStatus getByCode(Integer code) {
        for(DateExtensionStatus date : values()) {
            if(date.getCode() == code) {
                return date;
            }
        }
        return  null;
    }
}
