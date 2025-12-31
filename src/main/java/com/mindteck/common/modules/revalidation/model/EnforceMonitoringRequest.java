package com.mindteck.common.modules.revalidation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EnforceMonitoringRequest {
    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "formUniqueId is required")
    private Long formUniqueId;

    private int isMonitoring = 1;
    private List<MonitoringChangeFormRequest> changeForm;
    private int haveChanges;
    private int plannedForChange;
    private String changeDescription;
    private String plannedDate;
    private String comments;
    private int confirmation;
    private String institutionName;
    private String qualificationTitle;
    private String contactName;
    private String positionTitle;
    private String contactNumber;
    private String email;
    private String listingId;
}
