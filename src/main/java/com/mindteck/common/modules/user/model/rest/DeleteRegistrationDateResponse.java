package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class DeleteRegistrationDateResponse extends AbstractView {

    private DeleteRegistrationDateResponseModel data;
}
