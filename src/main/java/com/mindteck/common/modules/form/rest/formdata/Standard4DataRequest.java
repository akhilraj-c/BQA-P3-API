package com.mindteck.common.modules.form.rest.formdata;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ApiModel(value = "Standard4Data")
public class Standard4DataRequest {
    /*
        @ApiModelProperty(value = "certificateIssuanceDesc", dataType = "String" ,example = "Sampl name")
        private String certificateIssuanceDesc;

        @ApiModelProperty(value = "certificateIssuanceFile", dataType = "String" ,example = "Sampl name")
        private String certificateIssuanceFile;

        @ApiModelProperty(value = "certificateAuthenticationDesc", dataType = "String" ,example = "Sampl name")
        private String certificateAuthenticationDesc;

        @ApiModelProperty(value = "certificateAuthenticationFile", dataType = "String" ,example = "Sampl name")
        private String certificateAuthenticationFile;

        @ApiModelProperty(value = "recordsOfCertificationDesc", dataType = "String" ,example = "Sampl name")
        private String recordsOfCertificationDesc;

        @ApiModelProperty(value = "recordsOfCertificationFile", dataType = "String" ,example = "Sampl name")
        private String recordsOfCertificationFile;

     */
    @ApiModelProperty(value = "certificateIssuanceStatusComment", dataType = "String", example = "Sampl name")
    private String certificateIssuanceStatusComment;

    @ApiModelProperty(value = "certificateAuthenticationStatusComment", dataType = "String", example = "Sampl name")
    private String certificateAuthenticationStatusComment;

    @ApiModelProperty(value = "recordsOfCertificationStatusComment", dataType = "String", example = "Sampl name")
    private String recordsOfCertificationStatusComment;
}
