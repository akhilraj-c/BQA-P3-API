package com.mindteck.common.modules.user.model.rest.qp;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "tbl_qp_institute_form")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualificationTitle{

    @Column(name = "form_unique_id")
    private Long formUniqueId;

    @NotBlank(message = "title is required")
    @ApiModelProperty(example = "DAta structure", required = true , value =  "title" , dataType = "String")
    private String title;

    @NotBlank(message = "noOfModules is required")
    @ApiModelProperty(example = "1", required = true , value =  "noOfModules" , dataType = "Integer")
    private Integer noOfModules;

    @NotBlank(message = "plannedSubmissionDate is required")
    @ApiModelProperty(example = "Epoch time", required = true , value =  "plannedSubmissionDate" , dataType = "Long")
    private Long plannedSubmissionDate;
}