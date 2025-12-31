package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class SetAllApproveResponse extends AbstractView {

    private SetAllApproveResponseModel data;
}
