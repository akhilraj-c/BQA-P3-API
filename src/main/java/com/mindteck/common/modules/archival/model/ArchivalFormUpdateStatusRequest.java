package com.mindteck.common.modules.archival.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivalFormUpdateStatusRequest {
    private Long archivalFormId;
    private String decision;
    private String comment;
    private Integer status;
}
