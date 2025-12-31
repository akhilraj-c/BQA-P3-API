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
public class CertificationStatus {
    /**
     * "certificateIssuance": 1,
     * 		"certificateAuthentication": 1,
     * 		"recordsOfCertification": 1
     */
    @NotNull(message = "certificateIssuance is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer certificateIssuance;

    @NotBlank(message = "certificateIssuanceComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String certificateIssuanceComment;

    @NotNull(message = "certificateAuthentication is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer certificateAuthentication;

    @NotBlank(message = "certificateIssuanceComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String certificateAuthenticationComment;

    @NotNull(message = "recordsOfCertification is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "Integer", position = 8)
    private Integer recordsOfCertification;

    @NotBlank(message = "recordsOfCertificationComment is required")
    @ApiModelProperty(example = "1", required = true , value =  "isOfferingNanLocCourseQa" , dataType = "String", position = 8)
    private String recordsOfCertificationComment;
}
