package com.mindteck.common.constants.Enum;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DocType {

    RESPONSE_CODE(0 , "Mindteck  API Response code" , "Response-Code.html" , true ),
    API_LIST(1 , "Mindteck  Api List", "Api-List.html", true);

    private final Integer code;
    private final String linkInfo;
    private final String templateName;
    private final boolean visible;

    DocType(Integer code, String linkInfo, String templateName, boolean visible) {
        this.code = code;
        this.linkInfo = linkInfo;
        this.templateName = templateName;
        this.visible = visible;
    }

    public static DocType of(Integer docType) {
        if(null == docType) {
            return  null;
        }
        return Arrays.stream(values())
                .filter(en -> en.getCode().equals(docType))
                .findFirst().orElse(null);
    }
}
