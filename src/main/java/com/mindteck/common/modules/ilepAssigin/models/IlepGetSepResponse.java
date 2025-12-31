package com.mindteck.common.modules.ilepAssigin.models;

import com.mindteck.common.models.rest.AbstractView;
import com.mindteck.common.models.rest.PagedData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class IlepGetSepResponse extends AbstractView {

    PagedData<IlepGetSepResponseModel> data;
}
