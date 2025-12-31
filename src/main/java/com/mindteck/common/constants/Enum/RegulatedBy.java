package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegulatedBy {

    MOE(1 , " Ministry of Education (MoE)"),
    MLSD(2, " Ministry of Labour and Social Development (MLSD)"),
    HEC(3, " Higher Education Council (HEC)"),
    OTHERS(4 , "Others")
;
    private final Integer code;

    private final String name;

}
