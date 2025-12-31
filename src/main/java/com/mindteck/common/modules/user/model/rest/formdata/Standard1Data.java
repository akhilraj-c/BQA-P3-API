package com.mindteck.common.modules.user.model.rest.formdata;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "Standard1Data")
public class Standard1Data {

    @ApiModelProperty(value = "accessAndAdmissionDesc", dataType = "String" ,example = "Sampl name")
    private String accessAndAdmissionDesc;

    @ApiModelProperty(value = "accessAndAdmissionFile", dataType = "String" ,example = "Sampl name")
    private String accessAndAdmissionFile;

    @ApiModelProperty(value = "accessAndAdmissionStatus", dataType = "Integer" ,example = "1")
    private Integer accessAndAdmissionStatus;

    @ApiModelProperty(value = "accessAndAdmissionStatusComment", dataType = "String" ,example = "Sampl name")
    private String accessAndAdmissionStatusComment;

    @ApiModelProperty(value = "creditAccumulationDesc", dataType = "String" ,example = "Sampl name")
    private String creditAccumulationDesc;

    @ApiModelProperty(value = "creditAccumulationFile", dataType = "String" ,example = "Sampl name")
    private String creditAccumulationFile;

    @ApiModelProperty(value = "creditAccumulationStatus", dataType = "Integer" ,example = "1")
    private Integer creditAccumulationStatus;

    @ApiModelProperty(value = "creditAccumulationStatusComment", dataType = "String" ,example = "Sampl name")
    private String creditAccumulationStatusComment;

    @ApiModelProperty(value = "internalAndExternalCreditTransferDesc", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalCreditTransferDesc;

    @ApiModelProperty(value = "internalAndExternalCreditTransferFile", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalCreditTransferFile;

    @ApiModelProperty(value = "internalAndExternalCreditTransferStatus",dataType = "Integer" ,example = "1")
    private Integer internalAndExternalCreditTransferStatus;

    @ApiModelProperty(value = "internalAndExternalCreditTransferStatusComment", dataType = "String" ,example = "Sampl name")
    private String internalAndExternalCreditTransferStatusComment;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysDesc", dataType = "String" ,example = "Sampl name")
    private String careerProgressionAndLearningPathwaysDesc;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysFile", dataType = "String" ,example = "Sampl name")
    private String careerProgressionAndLearningPathwaysFile;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysStatus", dataType = "Integer" ,example = "1")
    private Integer careerProgressionAndLearningPathwaysStatus;

    @ApiModelProperty(value = "careerProgressionAndLearningPathwaysStatusComment", dataType = "String" ,example = "Sampl name")
    private String careerProgressionAndLearningPathwaysStatusComment;

    @ApiModelProperty(value = "RecognitionOfPrioLearningDesc", dataType = "String" ,example = "Sampl name")
    private String RecognitionOfPrioLearningDesc;

    @ApiModelProperty(value = "RecognitionOfPrioLearningFile", dataType = "String" ,example = "Sampl name")
    private String RecognitionOfPrioLearningFile;

    @ApiModelProperty(value = "RecognitionOfPrioLearningStatus", dataType = "Integer" ,example = "1")
    private Integer RecognitionOfPrioLearningStatus;

    @ApiModelProperty(value = "RecognitionOfPrioLearningStatusComment", dataType = "String" ,example = "Sampl name")
    private String RecognitionOfPrioLearningStatusComment;

    @ApiModelProperty(value = "appealAgainstAccessAndTransferDesc", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAccessAndTransferDesc;

    @ApiModelProperty(value = "appealAgainstAccessAndTransferFile", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAccessAndTransferFile;

    @ApiModelProperty(value = "appealAgainstAccessAndTransferStatus", dataType = "Integer" ,example = "1")
    private Integer appealAgainstAccessAndTransferStatus;

    @ApiModelProperty(value = "appealAgainstAccessAndTransferStatusComment", dataType = "String" ,example = "Sampl name")
    private String appealAgainstAccessAndTransferStatusComment;
}
