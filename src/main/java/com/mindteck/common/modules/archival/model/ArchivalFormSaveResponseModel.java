package com.mindteck.common.modules.archival.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArchivalFormSaveResponseModel {
    @ApiModelProperty(value = "message", example = "Submitted for archival successfully", dataType = "String", position = 1)
    private String message;
}