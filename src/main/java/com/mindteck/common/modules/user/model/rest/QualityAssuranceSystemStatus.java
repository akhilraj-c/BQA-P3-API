package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QualityAssuranceSystemStatus {
    /**
     * "qualityAssuranceSystemOverview": 1,
     * "lastInstitutionQualityAssuranceReview": 1
     */

    @NotNull(message = "qualityAssuranceSystemOverview is required")
    @ApiModelProperty(example = "1", required = true, value = "isOfferingNanLocCourseQa", dataType = "Integer", position = 8)
    private Integer qualityAssuranceSystemOverview;

    @NotBlank(message = "qualityAssuranceSystemOverviewComment is required")
    @ApiModelProperty(example = "true", required = true, value = "isOfferingNanLocCourseQa", dataType = "String", position = 8)
    private String qualityAssuranceSystemOverviewComment;

    @NotNull(message = "lastInstitutionQualityAssuranceReview is required")
    @ApiModelProperty(example = "1", required = true, value = "isOfferingNanLocCourseQa", dataType = "Integer", position = 8)
    private Integer lastInstitutionQualityAssuranceReview;

    @NotBlank(message = "qualityAssuranceSystemOverviewComment is required")
    @ApiModelProperty(example = "true", required = true, value = "isOfferingNanLocCourseQa", dataType = "String", position = 8)
    private String lastInstitutionQualityAssuranceReviewComment;
}
