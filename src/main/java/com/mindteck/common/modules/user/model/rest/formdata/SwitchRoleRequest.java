package com.mindteck.common.modules.user.model.rest.formdata;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SwitchRoleRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "App ID cannot be null")
    private Long appId;

    @NotNull(message = "User type cannot be null")
    private Integer userType;

    @NotNull(message = "sub type cannot be null")
    private Integer subType;
}
