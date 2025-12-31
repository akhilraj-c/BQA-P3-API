package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateMyFormResponse extends AbstractView {

    private UpdateMyFormResponseModel data;
}
