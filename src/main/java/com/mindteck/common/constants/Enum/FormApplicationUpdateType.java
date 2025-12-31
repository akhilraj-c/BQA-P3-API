package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum FormApplicationUpdateType {

    INSTITUTION_PROFILE_DATA(1, "Institution profile data"),
    QUALITY_ASSURANCE_SYSTEM(2, "Quality Assurance system"),
    STANDARD(3, "Standards"),
    APPLICATION_CONTACT(4, "Application Contact"),
    QUALIFICATION_PROFILE_DATA(21, "Qualification profile data"),
    QUALIFICATION_PROFILE_STANDARD(22, "Standards"),
    QUALIFICATION_PROFILE_APPLICATION_CONTACT(23, "Application Contact");


    private final Integer code;
    private final String description;

    public static FormApplicationUpdateType getByCode(Integer code) {
        for (FormApplicationUpdateType form : values()) {
            if (form.getCode() == code) {
                return form;
            }
        }
        return null;
    }
}
