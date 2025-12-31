package com.mindteck.common.modules.revalidation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeFormGetResponse {
    private int changeType;
    private String description;
    private String file;
}
