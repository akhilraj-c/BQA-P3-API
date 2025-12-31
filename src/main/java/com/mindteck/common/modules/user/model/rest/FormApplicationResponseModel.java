package com.mindteck.common.modules.user.model.rest;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindteck.common.modules.user.model.rest.formdata.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "FormApplicationResponseModel")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormApplicationResponseModel {

    private InstitutionProfileData institutionProfileData;
    private QualificationProfileData qualificationProfileData;

    private QualityAssuranceSystemOverviewData qualityAssuranceSystemOverviewData;

    private Standard1Data standard1Data;

    private Standard2Data standard2Data;

    private Standard3Data standard3Data;

    private Standard4Data standard4Data;

    private Standard5Data standard5Data;

    private ApplicationContactData applicationContactData;

}
