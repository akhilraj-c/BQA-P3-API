package com.mindteck.common.modules.archival.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchivalGetResponse  extends AbstractView {
    private ArchivalForm data;
}
