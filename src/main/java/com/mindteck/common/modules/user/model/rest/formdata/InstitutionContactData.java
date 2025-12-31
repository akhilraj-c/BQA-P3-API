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
@ApiModel(value = "InstitutionContactData")
public class InstitutionContactData {

    @ApiModelProperty(value = "name", dataType = "String" ,example = "Sampl name")
    private String name;

    @ApiModelProperty(value = "emailId", dataType = "String" ,example = "sample@gmail.com")
    private String emailId;

    @ApiModelProperty(value = "contactNo", dataType = "String" ,example = "9xxxx")
    private String contactNo;
}
