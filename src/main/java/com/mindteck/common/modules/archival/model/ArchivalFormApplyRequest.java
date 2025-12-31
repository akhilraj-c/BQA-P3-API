package com.mindteck.common.modules.archival.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivalFormApplyRequest {

    private Long formUniqueId;
    private Integer sector;
    private String reason;
    private Integer isSingleQualification;
    private Integer isDirectDfoArchival=0;
    private String decision;
    private String comment;
    private Long instituteId;
    private Integer isWithdrawnOrClosed ;
}
