package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstitutionDomain {

    PDE(1 , "primarily dedicated to education"),
    PDT(2 , "primarily dedicated to training"),
    E_NPFOI(3 , "training and/or education are not the primary functions of the institution"),

    OTHER(4 , "Others")
    ;

    private final Integer code;
    private final String name;
}
