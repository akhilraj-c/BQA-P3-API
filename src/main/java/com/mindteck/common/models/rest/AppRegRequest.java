package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AppRegRequest {

    @ApiModelProperty(example = "NOKIAXXXXX520XP", required = true)
    @NotBlank(message = "deviceId cannot be empty")
    public String deviceId;

    @ApiModelProperty(example = "Window 8", required = true)
    @NotBlank(message = "osInfo cannot be empty")
    public String osInfo;

    @ApiModelProperty(example = "abcdkfladxxxxafldasd", required = true)
    @NotBlank(message = "deviceInfo cannot be empty")
    public String deviceInfo;


    @ApiModelProperty(example = "8.0")
    public String appVersion;

    @NotBlank(message = "deviceToken cannot be empty")
    @ApiModelProperty(example = "thga3i943459vgfgakjflgkj__935_fhdjsahewjhrtq fhdfkej_fcm")
    public String deviceToken;
}
