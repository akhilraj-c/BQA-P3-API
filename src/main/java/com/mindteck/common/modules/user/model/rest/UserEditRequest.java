package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    @ApiModelProperty( name = "userId" , example = "5", required = true , value = "Id of the user to be edited" ,  dataType = "Long"
            , position = 1,allowEmptyValue = false )
    @NotNull(message = "reportAccess is required")
    private Long userId;
    @ApiModelProperty( name = "name" , example = "Don Bosco", required = true , value = "Name of the user to edit" ,  dataType = "String"
            , position = 2,allowEmptyValue = false )
    @NotBlank(message = "name is required")
    private String name;

    @ApiModelProperty( name = "position" , example = "Manager", required = true , value = "Position of the user to edit" ,  dataType = "String"
            , position = 3,allowEmptyValue = false )
    @NotBlank(message = "position is required")
    private String position;

    @ApiModelProperty( name = "accessRight" , example = "5", required = true , value = "Integer value of the byte " ,  dataType = "Integer"
            , position = 4,allowEmptyValue = false )
    @NotNull(message = "accessRight is required")
    private Integer accessRight;

    @ApiModelProperty( name = "reportAccess" , example = "0", required = true , value = "0 - false \n 1 - true " ,  dataType = "Integer"
            , position = 5,allowEmptyValue = false )
    @NotNull(message = "reportAccess is required")
    private  Integer reportAccess;

    @ApiModelProperty( name = "active" , example = "1", required = true , value = "0 - inactive \n 1 - active " ,  dataType = "Integer"
            , position = 6,allowEmptyValue = false )
    @NotNull(message = "active is required")
    private Integer active;

    @ApiModelProperty( name = "password" , example = "1234", required = false , value = "new Password for the user to edit" ,  dataType = "String"
            , position = 7,allowEmptyValue = true )
    private String password;

    @ApiModelProperty( name = "contactNo" , example = "919474547798", required = true , value = "Contact no of the user to edit" ,  dataType = "String"
            , position = 8,allowEmptyValue = false )
    @NotNull(message = "contactNo is required")
    @NotBlank(message = "contactNo is required")
    private String contactNo;

    @ApiModelProperty( name = "bio" , example = "s3 url", required = true , value = "S3 url of the bio file" ,  dataType = "String"
            , position = 9,allowEmptyValue = true )
    private String bio;

    @ApiModelProperty(example = "roleIds", value = "[1,5]", required = true)
    private Set<Long> roleIds;
}
