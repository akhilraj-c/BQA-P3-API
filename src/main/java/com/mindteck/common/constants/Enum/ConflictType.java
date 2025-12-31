package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ConflictType {

    INSTITUTION_CONFLICT(1,"Institution conflict"),
    ILEP_CONFLICT(2, "Ilep conflict")
    ;

    private  final Integer code;
    private  final String description;

    public static ConflictType getByCode(Integer code) {
        for(ConflictType type : values()) {
            if(type.getCode().equals(code))
                return type;
        }
        return null;
    }
}
