package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    SUPER_ADMIN(0, "Super admin", UserSubType.ADMIN),
    INSTITUTION(1, "Institution user", UserSubType.ADMIN),
    //BQA_ADMIN(2, "BQA admin", UserSubType.ADMIN),

    DFO_ADMIN(3, "DFO adimin", UserSubType.ADMIN),
    APPLICATION_MANAGER(3, "Application manager member of DFOs", UserSubType.MANAGER),
    DFO_MEMBER(3, "member of DFOs", UserSubType.USER),
    CHIEF(4, "CHEIF", UserSubType.ADMIN),
    DIRECTOR(5, "Director", UserSubType.ADMIN),
    OBSERVER(9, "Observer", UserSubType.ADMIN),

    GDQ(6, "GDQ", UserSubType.ADMIN),
    ILEP_MEMBER(7, "ILEP Member", UserSubType.ADMIN),

    MCO(8, "MCO", UserSubType.ADMIN);
    private final Integer code;
    private final String description;

    private final UserSubType subType;

    public static UserType getByCode(Integer code, Integer subType) {
        UserSubType userSubType = UserSubType.getByCode(subType);
        for (UserType user : values()) {
            if (user.getCode() == code && user.getSubType() == userSubType) {
                return user;
            }
        }
        return null;
    }
}
