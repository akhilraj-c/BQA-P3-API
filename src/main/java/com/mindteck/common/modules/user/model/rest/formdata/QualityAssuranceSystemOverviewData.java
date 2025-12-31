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
@ApiModel(value = "QualityAssuranceSystemOverviewData")
public class QualityAssuranceSystemOverviewData {

    @ApiModelProperty(value = "qualityAssuranceDescription", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceDescription;

    @ApiModelProperty(value = "qualityAssuranceDescriptionStatus", dataType = "Integer" ,example = "16616")
    private Integer qualityAssuranceDescriptionStatus;

    @ApiModelProperty(value = "qualityAssuranceDescriptionStatusComment", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceDescriptionStatusComment;

    @ApiModelProperty(value = "qualityDepartmentDoc", dataType = "String" ,example = "Sampl name")
    private String qualityDepartmentDoc;

    @ApiModelProperty(value = "qualityAssuranceSystemOverviewStatus", dataType = "Integer" ,example = "16616")
    private Integer qualityAssuranceSystemOverviewStatus;

    @ApiModelProperty(value = "qualityAssuranceSystemOverviewStatusComment", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceSystemOverviewStatusComment;

    @ApiModelProperty(value = "qualityReviewDate", dataType = "Long" ,example = "16616")
    private Long qualityReviewDate;

    @ApiModelProperty(value = "qualityReviewDateStatus", dataType = "Integer" ,example = "16616")
    private Integer qualityReviewDateStatus;

    @ApiModelProperty(value = "qualityReviewDateStatusComment", dataType = "String" ,example = "Sampl name")
    private String qualityReviewDateStatusComment;

    @ApiModelProperty(value = "qualityAssuranceReport", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceReport;

    @ApiModelProperty(value = "lastInstitutionQualityAssuranceReviewStatus", dataType = "Integer" ,example = "16616")
    private Integer lastInstitutionQualityAssuranceReviewStatus;

    @ApiModelProperty(value = "lastInstitutionQualityAssuranceReviewStatusComment", dataType = "String" ,example = "Sampl name")
    private String lastInstitutionQualityAssuranceReviewStatusComment;
}
