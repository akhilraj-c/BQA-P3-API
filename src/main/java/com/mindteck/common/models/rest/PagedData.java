package com.mindteck.common.models.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PagedData<T> {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("Pagination details")
    public PageDetails pageDetails;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(position = 1, value = "Array containing items")
    public List<T> list = new ArrayList<>();

}
