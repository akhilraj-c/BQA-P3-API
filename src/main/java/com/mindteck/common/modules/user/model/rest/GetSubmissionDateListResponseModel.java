package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(value = "GetSubmissionDatesResponseModel")
public class GetSubmissionDateListResponseModel {

    @ApiModelProperty(name = "submissionDate" , example = "1231231231231" , value = "submission date in epoch" , dataType = "Long"
            , position = 1,allowEmptyValue = false , required = true)
    private Long submissionDate;

    @ApiModelProperty(name = "submissionEndDate" , example = "1231231231231" , value = "submission end date in epoch" , dataType = "Long"
            , position = 2,allowEmptyValue = false , required = true)
    private Long submissionEndDate;

    @ApiModelProperty(example = "1" , value = "dateId")
    private Long dateId;
}
