package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Component
public class OverallAdminCheckDetails {

    @NotNull(message = "overAllEvaluationStatus is required")
    @ApiModelProperty( example = "1",required = true , value =  "overAllEvaluationStatus" , dataType = "Long", position = 1)
    private Integer overAllEvaluationStatus;

    @NotNull(message = "overAllEvaluationStatusComment is required")
    @ApiModelProperty( example = "sss" ,required = true , value =  "comment" , dataType = "String", position = 1)
    private String overAllEvaluationStatusComment;

    @ApiModelProperty(name = "evaluationDeadLine" , example = "1234567890123" , value = "resubmit date in epoc" ,dataType = "Long",position = 10)
    private Long evaluationDeadLine;

    @NotNull(message = "overAllVerificationStatus is required")
    @ApiModelProperty( example = "1",required = true , value =  "overAllVerificationStatus" , dataType = "Integer", position = 1)
    private Integer overAllVerificationStatus;

    @NotNull(message = "overAllVerificationStatusComment is required")
    @ApiModelProperty( example = "sss" ,required = true , value =  "overAllVerificationStatusComment" , dataType = "String", position = 1)
    private String overAllVerificationStatusComment;

    @ApiModelProperty(name = "verificationDeadLine" , example = "1234567890123" , value = "resubmit date in epoc" ,dataType = "Long",position = 10)
    private Long verificationDeadLine;

    private Integer registrationStatus;

    private Integer evaluationFlag;

    private Integer verificationFlag;

    private Integer evaluationRejectionCount = 0;

    private Integer verificationRejectionCount = 0;

}