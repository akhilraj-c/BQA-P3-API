package com.mindteck.common.constants.Enum;

import lombok.Getter;

@Getter
public enum LicenseType {
    MINISTRY_OF_EDUCATION(1, "Ministry of Education"),
    MINISTRY_OF_LABOUR(2, "Ministry of Labour"),
    HIGHER_EDUCATION_COUNCIL(3, "Higher Educational Council"),
    DECREE(4, "Decree"),
    OTHERS(5, "Others");
    private final Integer code;

    private final String LicenceName;

    LicenseType(Integer code, String LicenceName) {
        this.code = code;
        this.LicenceName = LicenceName;
    }
}
