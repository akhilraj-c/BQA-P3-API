package com.mindteck.common.modules.user.model.rest.qp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForeignQualification {

    @NotBlank(message = "title is required")
    @ApiModelProperty(example = "DAta structure", required = true , value =  "qualificationTitle" , dataType = "String")
    private String qualificationTitle;

    @NotBlank(message = "qualificationSize is required")
    @ApiModelProperty(example = "short", required = true , value =  "qualificationSize" , dataType = "String")
    private String qualificationSize;

    @NotBlank(message = "plannedDate is required")
    @ApiModelProperty(example = "Epoch time", required = true , value =  "plannedSubmissionDate" , dataType = "Long")
    private Long plannedSubmissionDate;

    @NotBlank(message = "awardingBody is required")
    @ApiModelProperty(example = "awardingBody", required = true , value =  "awardingBody" , dataType = "String")
    private String awardingBody;

    @NotBlank(message = "includedInOther is required")
    @ApiModelProperty(example = "includedInOther", required = true , value =  "includedInOther" , dataType = "String")
    private String includedInOther;
}
