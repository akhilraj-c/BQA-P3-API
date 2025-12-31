package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class AbstractListRequest {

    private static final long serialVersionUID = 1L;

    private static final Integer PAGE_DEFAULT_VALUE = 1;

    private static final Integer PAGE_SIZE_DEFAULT_VALUE = 100;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @ApiModelProperty(value = "Page number", notes = "Start from 1. default : 1" , example = "1")
    private Integer page = PAGE_DEFAULT_VALUE;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @ApiModelProperty(value = "Rows per page", notes = "default : 20" , example = "20")
    private Integer limit = PAGE_SIZE_DEFAULT_VALUE;

    public void setPage(Integer page) {
        if(page == null) page = PAGE_DEFAULT_VALUE;
        this.page = page;
    }

    public void setPageSize(Integer limit) {
        if(limit == null) limit = PAGE_SIZE_DEFAULT_VALUE;
        this.limit = limit;
    }
}
