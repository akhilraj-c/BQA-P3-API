package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetPreSignedUrlRequest {

    @NotNull(message = "fileName is required")
    @ApiModelProperty(name = "fileName", example = "test-file.pdf", value = "name of the file", dataType = "String",
            position = 1, required = true)
    private String fileName;

    @NotNull(message = "method is required")
    @ApiModelProperty(name = "method", example = "PUT", value = "http methods", dataType = "String",
            position = 2, required = true , allowableValues = "GET,PUT,DELETE")
    private String method;
    @ApiModelProperty(name = "bucketName", example = "test-bucket", value = "name of the s3 bucket", dataType = "String",
            position = 3, required = false)
    private String bucketName;

}
