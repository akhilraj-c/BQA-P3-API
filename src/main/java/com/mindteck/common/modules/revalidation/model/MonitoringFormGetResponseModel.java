package com.mindteck.common.modules.revalidation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitoringFormGetResponseModel {
    private Long formUniqueId;
    private int haveChanges;
    private int plannedForChange;
    private String changeDescription;
    private String plannedDate;
    private String comments;
    private int confirmation;
    private List<ChangeFormGetResponse> changeForm;
}
