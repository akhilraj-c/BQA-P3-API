package com.mindteck.common.constants.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ILEPPanelStatus {

    NOT_APPROVED(0 , "ILEP panel created"),

    APPROVED(1, "ILEP panel approved");

    private  final  Integer code;
    private final String description;

    public static ILEPPanelStatus getByCode(Integer code) {
        for(ILEPPanelStatus panelStatus : values()) {
            if(panelStatus.getCode() == code) {
                return panelStatus;
            }
        }
        return  null;
    }
}
