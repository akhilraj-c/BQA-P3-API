package com.mindteck.common.modules.form.rest.formdata;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "Standard2Data")
public class Standard2DataRequest {
    /*
        @ApiModelProperty(value = "justificationOfNeedDesc", dataType = "String" ,example = "Sampl name")
        private String justificationOfNeedDesc;

        @ApiModelProperty(value = "justificationOfNeedFile", dataType = "String" ,example = "Sampl name")
        private String justificationOfNeedFile;

        @ApiModelProperty(value = "qualificationDesignDesc", dataType = "String" ,example = "Sampl name")
        private String qualificationDesignDesc;

        @ApiModelProperty(value = "qualificationDesignFile", dataType = "String" ,example = "Sampl name")
        private String qualificationDesignFile;

        @ApiModelProperty(value = "qualificationComplianceDesc", dataType = "String" ,example = "Sampl name")
        private String qualificationComplianceDesc;

        @ApiModelProperty(value = "qualificationComplianceFile", dataType = "String" ,example = "Sampl name")
        private String qualificationComplianceFile;

        @ApiModelProperty(value = "learningRecoursesAndLearnersSupportDesc", dataType = "String" ,example = "Sampl name")
        private String learningRecoursesAndLearnersSupportDesc;

        @ApiModelProperty(value = "learningRecoursesAndLearnersSupportFile", dataType = "String" ,example = "Sampl name")
        private String learningRecoursesAndLearnersSupportFile;

        @ApiModelProperty(value = "qualificationInternalApprovalDesc", dataType = "String" ,example = "Sampl name")
        private String qualificationInternalApprovalDesc;

        @ApiModelProperty(value = "qualificationInternalApprovalFile", dataType = "String" ,example = "Sampl name")
        private String qualificationInternalApprovalFile;

        @ApiModelProperty(value = "qualificationInternalAndExternalEvaluationAndReviewDesc", dataType = "String" ,example = "Sampl name")
        private String qualificationInternalAndExternalEvaluationAndReviewDesc;

        @ApiModelProperty(value = "qualificationInternalAndExternalEvaluationAndReviewFile", dataType = "String" ,example = "Sampl name")
        private String qualificationInternalAndExternalEvaluationAndReviewFile;

     */
    @ApiModelProperty(value = "justificationOfNeedStatusComment", dataType = "String", example = "Sampl name")
    private String justificationOfNeedStatusComment;

    @ApiModelProperty(value = "qualificationDesignStatusComment", dataType = "String", example = "Sampl name")
    private String qualificationDesignStatusComment;

    @ApiModelProperty(value = "qualificationDesignStatusComment", dataType = "String", example = "Sampl name")
    private String qualificationComplianceStatusComment;

    @ApiModelProperty(value = "learningRecoursesAndLearnersSupportStatusComment", dataType = "String", example = "Sampl name")
    private String learningRecoursesAndLearnersSupportStatusComment;

    @ApiModelProperty(value = "qualificationInternalApprovalStatusComment", dataType = "String", example = "Sampl name")
    private String qualificationInternalApprovalStatusComment;

    @ApiModelProperty(value = "qualificationInternalAndExternalEvaluationAndReviewStatusComment", dataType = "String", example = "Sampl name")
    private String qualificationInternalAndExternalEvaluationAndReviewStatusComment;
}
