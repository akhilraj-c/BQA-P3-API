package com.mindteck.common.constants.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationType {

    ON_DFO_APPROVES_ILEP_PANEL(1 ,
            "The ILEP panel has been approved by DFO" ,
            true,
            0),
    AM_NOT_APPROVE_CONFLICT(2,
            "The conflict signed by institute has been not approved by AM, please sign the no conflict form ",
            true,
            1),
    ILEP_SIGN_NON_CONFLICT(3,
            "ILEP has signed the no conflict form, please schedule the metting with GDQ, DFO",
            true,
            1),

    ILEP_SUBMIT_FINAL_SUMMARY(4 ,
            "The ILEP member has updated the findal judgment details" ,
            true,
            0),
    AM_CREATE_SITE_VISIT(5,
            "Site visit has been crated by AM, please request for a extension if opting",
            true,
            1),

    INSTITUE_SIGN_NON_CONFIDENTIALITY(6,
            "The institue has signed the no confidential form",
            true,
            1);

  private final  int type;
  private final String description;
  private final boolean isPriority;
  private final int intendedUserType;
}
