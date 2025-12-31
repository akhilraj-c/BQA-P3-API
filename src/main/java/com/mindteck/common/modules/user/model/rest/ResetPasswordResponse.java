package com.mindteck.common.modules.user.model.rest;


import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResetPasswordResponse extends AbstractView {

    private ResetPasswordResponseModel data;
}
