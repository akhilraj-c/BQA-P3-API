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
@ApiModel(value = "Standard4Data")
public class Standard4Data {

    @ApiModelProperty(value = "certificateIssuanceDesc", dataType = "String" ,example = "Sampl name")
    private String certificateIssuanceDesc;

    @ApiModelProperty(value = "certificateIssuanceFile", dataType = "String" ,example = "Sampl name")
    private String certificateIssuanceFile;

    @ApiModelProperty(value = "certificateIssuanceStatus", dataType = "Integer" ,example = "1")
    private Integer certificateIssuanceStatus;

    @ApiModelProperty(value = "certificateIssuanceStatusComment", dataType = "String" ,example = "Sampl name")
    private String certificateIssuanceStatusComment;

    @ApiModelProperty(value = "certificateAuthenticationDesc", dataType = "String" ,example = "Sampl name")
    private String certificateAuthenticationDesc;

    @ApiModelProperty(value = "certificateAuthenticationFile", dataType = "String" ,example = "Sampl name")
    private String certificateAuthenticationFile;

    @ApiModelProperty(value = "certificateAuthenticationStatus", dataType = "Integer" ,example = "1")
    private Integer certificateAuthenticationStatus;

    @ApiModelProperty(value = "certificateAuthenticationStatusComment", dataType = "String" ,example = "Sampl name")
    private String certificateAuthenticationStatusComment;

    @ApiModelProperty(value = "recordsOfCertificationDesc", dataType = "String" ,example = "Sampl name")
    private String recordsOfCertificationDesc;

    @ApiModelProperty(value = "recordsOfCertificationFile", dataType = "String" ,example = "Sampl name")
    private String recordsOfCertificationFile;

    @ApiModelProperty(value = "recordsOfCertificationStatus", dataType = "Integer" ,example = "1")
    private Integer recordsOfCertificationStatus;

    @ApiModelProperty(value = "recordsOfCertificationStatusComment", dataType = "String" ,example = "Sampl name")
    private String recordsOfCertificationStatusComment;
}
