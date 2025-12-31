package com.mindteck.common.modules.archival.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArchivalFormSaveResponse extends AbstractView {
    private ArchivalFormSaveResponseModel data;
}