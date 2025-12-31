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
public class NationalQualification {
    @NotBlank(message = "title is required")
    @ApiModelProperty(example = "DAta structure", required = true , value =  "qualificationTitle" , dataType = "String")
    private String qualificationTitle;

    @NotBlank(message = "qualificationSize is required")
    @ApiModelProperty(example = "short", required = true , value =  "qualificationSize" , dataType = "String")
    private String qualificationSize;

    @NotBlank(message = "plannedDate is required")
    @ApiModelProperty(example = "Epoch time", required = true , value =  "plannedSubmissionDate" , dataType = "Long")
    private Long plannedSubmissionDate;
}
