package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserEditResponse extends AbstractView {

    private UserEditResponseModel data;
}
