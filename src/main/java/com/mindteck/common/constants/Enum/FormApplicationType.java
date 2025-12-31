package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormApplicationType {

    INSTITUTION_PROFILE_DATA(1, "Institution profile data"),
    STANDARD_1_2_DATA(2, "Standard 1,2 data"),
    STANDARD_3_4__5_DATA(3, "Standard 3,4,5 data"),
    QUALITY_ASSURANCE_APPLICATION_CONTACT_DATA(4, "Quality assurance, application contactdata");


    private final Integer code;
    private final String description;

    public static FormApplicationType getByCode(Integer code) {
        for (FormApplicationType form : values()) {
            if (form.getCode() == code) {
                return form;
            }
        }
        return null;
    }
}
