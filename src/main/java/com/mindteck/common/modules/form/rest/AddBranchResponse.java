package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AddBranchResponse extends AbstractView {

    private AddBranchResponseModel data;
}
