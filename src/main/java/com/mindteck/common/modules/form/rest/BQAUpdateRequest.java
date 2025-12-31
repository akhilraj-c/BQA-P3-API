package com.mindteck.common.modules.form.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BQAUpdateRequest {

    @NotNull(message = "clientId is required")
    @ApiModelProperty(name = "clientId" , example = "1" , value = "client registration Id or App Id" , dataType = "Long" ,
            position = 1,allowEmptyValue = false , required = true)
    private Long clientId;

    @NotNull(message = "registrationStatus is required")
    @ApiModelProperty(name = "registrationStatus" , example = "1" , value = "registrationStatus" , dataType = "Integer"
            , position = 2,allowEmptyValue = false , required = true)
    private Integer registrationStatus;
    @NotNull(message = "registrationStatusDataList  is required")
    private List<RegistrationStatusData> registrationStatusDataList;

    @ApiModelProperty(name = "rejectionReason" , example = "Reason" , value = "client registration Id or App Id" , dataType = "String" ,
            position = 3,allowEmptyValue = true )
    private String rejectionReason ;



}
