package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetBranchesResponse extends AbstractView {

    private List<GetBranchesResponseModel> data;
}
