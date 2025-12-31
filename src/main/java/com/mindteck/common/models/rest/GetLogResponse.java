package com.mindteck.common.models.rest;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GetLogResponse extends AbstractView {
    private PagedData<GetLogResponseModel> data;
}
