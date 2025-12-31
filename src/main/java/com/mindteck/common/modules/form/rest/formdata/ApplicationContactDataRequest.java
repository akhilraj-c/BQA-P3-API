package com.mindteck.common.modules.form.rest.formdata;


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
@ApiModel(value = "ApplicationContactData")
public class ApplicationContactDataRequest {

    @ApiModelProperty(value = "applicationContactName", dataType = "String" ,example = "Sampl name")
    private String applicationContactName;

    @ApiModelProperty(value = "applicationContactNumber", dataType = "String" ,example = "Sampl name")
    private String applicationContactNumber;

    @ApiModelProperty(value = "applicationContactPosition", dataType = "String" ,example = "Sampl name")
    private String applicationContactPosition;

    @ApiModelProperty(value = "applicationContactEmail", dataType = "String" ,example = "Sampl name")
    private String applicationContactEmail;
}
