package com.mindteck.common.modules.user.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstituteUserEditRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Active status cannot be null")
    private Integer active;

    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;
}
