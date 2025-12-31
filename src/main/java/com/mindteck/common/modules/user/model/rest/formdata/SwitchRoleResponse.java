package com.mindteck.common.modules.user.model.rest.formdata;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SwitchRoleResponse  extends AbstractView {
    private SwitchRoleResponseModel data;
}
