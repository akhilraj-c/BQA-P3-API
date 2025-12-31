package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateExtensionType {

    IDLE(0 , "No date extension requested"),
    BQA_DATE_EXTENSION(1 , "Date extension request for completing the registration"),
    APCMGR_DATE_EXTENSION(2, "Date extension request for completing additional data submission"),
    CONDITION_FULL_DATE_EXTENSION(3,"Date extension request for condition fulfilment"),
    QP_ADMIN_CHECK_DATE_EXTENSION(21,"Date extension request for admin check"),
    QP_VERIFICATION_DATE_EXTENSION(22,"Date extension request for verification")

    ;
    private  final  Integer code;
    private final String description;

    public static DateExtensionType getByCode(Integer code) {
        for(DateExtensionType date : values()) {
            if(date.getCode() == code) {
                return date;
            }
        }
        return  null;
    }

}
