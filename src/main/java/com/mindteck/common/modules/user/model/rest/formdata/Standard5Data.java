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
@ApiModel(value = "Standard5Data")
public class Standard5Data {

    @ApiModelProperty(value = "institutionQualityAssuranceSystemDesc", dataType = "String" ,example = "Sampl name")
    private String institutionQualityAssuranceSystemDesc;

    @ApiModelProperty(value = "institutionQualityAssuranceSystemFile", dataType = "String" ,example = "Sampl name")
    private String institutionQualityAssuranceSystemFile;

    @ApiModelProperty(value = "institutionQualityAssuranceSystemStatus", dataType = "Integer" ,example = "1")
    private Integer institutionQualityAssuranceSystemStatus;

    @ApiModelProperty(value = "institutionQualityAssuranceSystemStatusComment", dataType = "String" ,example = "Sampl name")
    private String institutionQualityAssuranceSystemStatusComment;

    @ApiModelProperty(value = "continuousImprovementOfInstitutionQualityAssuranceSystemDesc", dataType = "String" ,example = "Sampl name")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemDesc;

    @ApiModelProperty(value = "continuousImprovementOfInstitutionQualityAssuranceSystemFile", dataType = "String" ,example = "Sampl name")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemFile;

    @ApiModelProperty(value = "continuousImprovementOfInstitutionQualityAssuranceSystemStatus", dataType = "Integer" ,example = "1")
    private Integer continuousImprovementOfInstitutionQualityAssuranceSystemStatus;

    @ApiModelProperty(value = "continuousImprovementOfInstitutionQualityAssuranceSystemStatusComment", dataType = "String" ,example = "Sampl name")
    private String continuousImprovementOfInstitutionQualityAssuranceSystemStatusComment;

    @ApiModelProperty(value = "riskAndCrisisManagementDesc", dataType = "String" ,example = "Sampl name")
    private String riskAndCrisisManagementDesc;

    @ApiModelProperty(value = "riskAndCrisisManagementFile", dataType = "String" ,example = "Sampl name")
    private String riskAndCrisisManagementFile;

    @ApiModelProperty(value = "riskAndCrisisManagementStatus", dataType = "Integer" ,example = "1")
    private Integer riskAndCrisisManagementStatus;

    @ApiModelProperty(value = "riskAndCrisisManagementStatusComment", dataType = "String" ,example = "Sampl name")
    private String riskAndCrisisManagementStatusComment;
}
