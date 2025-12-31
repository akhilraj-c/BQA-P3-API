package com.mindteck.common.modules.form.rest.formdata;


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
public class QualityAssuranceSystemRequest {

    @ApiModelProperty(value = "qualityAssuranceDescription", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceDescription;

    @ApiModelProperty(value = "qualityDepartmentDoc", dataType = "String" ,example = "Sampl name")
    private String qualityDepartmentDoc;

    @ApiModelProperty(value = "qualityReviewDate", dataType = "Long" ,example = "16616")
    private Long qualityReviewDate;

    @ApiModelProperty(value = "qualityAssuranceReport", dataType = "String" ,example = "Sampl name")
    private String qualityAssuranceReport;
}
