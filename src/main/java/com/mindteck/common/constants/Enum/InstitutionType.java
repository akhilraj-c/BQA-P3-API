package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstitutionType  {

    MINISTRY( 1 , "Ministry"),
    UNIVERSITY(2 , "University"),
    COLLEGE(3 , "College"),
    POLYTECHNIC(4 , "Polytechnic"),
    TRAINING_INSTITUTION(5 , "Training institution"),
    SCHOOL(6 , "School"),
    OTHERS(7 , "Others")

    ;

    private final Integer code;
    private final String name;
}
