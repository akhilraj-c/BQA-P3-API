package com.mindteck.common.modules.user.model.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "GetInstitutesHavingQualificationResponseModel")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetInstitutesHavingQualificationResponseModel {
    @ApiModelProperty(value = "insituteName", dataType = "String", example = "insituteName")
    private String insituteName;

    @ApiModelProperty(value = "pocEmail", dataType = "String", example = "email")
    private String email;

    @ApiModelProperty(value = "institution_id", dataType = "Long", example = "15")
    private Long institutionId;
}
