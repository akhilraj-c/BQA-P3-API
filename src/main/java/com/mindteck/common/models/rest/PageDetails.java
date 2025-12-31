package com.mindteck.common.models.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Current page", example = "0")
    private Integer page;

    @ApiModelProperty(value = "Records per page", example = "20")
    private Integer pageSize;

    @ApiModelProperty(value = "Total no of pages", example = "10")
    private Integer pageCount;

    @ApiModelProperty(value = "Total records matching criteria", example = "100")
    private Long totalElements;

    @ApiModelProperty(value = "Total remaining records", example = "80" )
    private Long remainingElements;
}
