package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetPreSignedUrlModel {

    @ApiModelProperty(name = "preSignedUrl" ,value = "S3 pre signed URL" , example = "https://example.com/pre-signed-url"
            , dataType = "String" , position = 1)
    private String preSignedUrl;
}