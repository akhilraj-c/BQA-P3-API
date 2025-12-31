package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateUserResponseRequest {

    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "appId is required")
    private Long appId;

    @ApiModelProperty(example = "Hello user", required = true)
    @NotBlank(message = "username is required")
    @NotNull(message = "username is required")
    private String username;

    @ApiModelProperty(example = "test@gmail.com", required = true)
    @NotBlank(message = "mailId is required")
    @NotNull(message = "mailId is required")
    @Email(message = "invalid email")
    private String mailId;

    @ApiModelProperty(example = "+918849451665", required = true)
    @NotBlank(message = "contactNo is required")
    @NotNull(message = "contactNo is required")
    private String contactNo;

    @ApiModelProperty(example = "test123", required = true)
    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    private String password;

//    @ApiModelProperty(example = "1", required = true)
//    @NotNull(message = "userType is required")
//    private Integer userType;
//
//    @ApiModelProperty(example = "0", required = true)
//    @NotNull(message = "subType is required")
//    private Integer subType;

    @ApiModelProperty(example = "101010", required = true)
    @NotNull(message = "access_right is required")
    private Integer access_right;

    @ApiModelProperty(example = "101010", required = true)
    @NotNull(message = "report_access is required")
    private Integer report_access;

    @ApiModelProperty(example = "Ad", value = "position", required = true)
    @NotBlank(message = "position is required")
    @NotNull(message = "position is required")
    private String position;

    @ApiModelProperty(example = "Ad", value = "bio", required = true)
    private String bio;

    @ApiModelProperty(example = "roleIds", value = "[1,5]", required = true)
    private Set<Long> roleIds;
}
