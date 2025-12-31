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
@ApiModel(value = "Standard1Data")
public class Standard1DataRequest {
    /*
        @ApiModelProperty(value = "accessAndAdmissionDesc", dataType = "String" ,example = "Sampl name")
        private String accessAndAdmissionDesc;

        @ApiModelProperty(value = "accessAndAdmissionFile", dataType = "String" ,example = "Sampl name")
        private String accessAndAdmissionFile;

        @ApiModelProperty(value = "creditAccumulationDesc", dataType = "String" ,example = "Sampl name")
        private String creditAccumulationDesc;

        @ApiModelProperty(value = "creditAccumulationFile", dataType = "String" ,example = "Sampl name")
        private String creditAccumulationFile;

        @ApiModelProperty(value = "internalAndExternalCreditTransferDesc", dataType = "String" ,example = "Sampl name")
        private String internalAndExternalCreditTransferDesc;

        @ApiModelProperty(value = "internalAndExternalCreditTransferFile", dataType = "String" ,example = "Sampl name")
        private String internalAndExternalCreditTransferFile;

        @ApiModelProperty(value = "careerProgressionAndLearningPathwaysDesc", dataType = "String" ,example = "Sampl name")
        private String careerProgressionAndLearningPathwaysDesc;

        @ApiModelProperty(value = "careerProgressionAndLearningPathwaysFile", dataType = "String" ,example = "Sampl name")
        private String careerProgressionAndLearningPathwaysFile;

        @ApiModelProperty(value = "RecognitionOfPrioLearningDesc", dataType = "String" ,example = "Sampl name")
        private String recognitionOfPrioLearningDesc;

        @ApiModelProperty(value = "RecognitionOfPrioLearningFile", dataType = "String" ,example = "Sampl name")
        private String recognitionOfPrioLearningFile;

        @ApiModelProperty(value = "appealAgainstAccessAndTransferDesc", dataType = "String" ,example = "Sampl name")
        private String appealAgainstAccessAndTransferDesc;

        @ApiModelProperty(value = "appealAgainstAccessAndTransferFile", dataType = "String" ,example = "Sampl name")
        private String appealAgainstAccessAndTransferFile;

     */
    @ApiModelProperty(value = "accessAndAdmissionStatusComment", dataType = "String", example = "Sampl name")
    private String accessAndAdmissionStatusComment;

    @ApiModelProperty(value = "creditAccumulationStatusComment", dataType = "String", example = "Sampl name")
    private String creditAccumulationStatusComment;

    @ApiModelProperty(value = "internalAndExternalCreditTransferStatusComment", dataType = "String", example = "Sampl name")
    private String internalAndExternalCreditTransferStatusComment;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysStatusComment", dataType = "String", example = "Sampl name")
    private String careerProgressionAndLearningPathwaysStatusComment;

    @ApiModelProperty(value = "RecognitionOfPrioLearningStatusComment", dataType = "String", example = "Sampl name")
    private String RecognitionOfPrioLearningStatusComment;

    @ApiModelProperty(value = "appealAgainstAccessAndTransferStatusComment", dataType = "String", example = "Sampl name")
    private String appealAgainstAccessAndTransferStatusComment;
}
