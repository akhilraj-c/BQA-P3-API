package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubmissionStatus {

    SUBMIT(1, "submit"),
    DRAFT(0, "Draft"),
    ;
    private final Integer code;
    private final String message;
}
