package com.mindteck.common.modules.mapping_panel.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaveMappingPanelResponse extends AbstractView {

    private Long formUniqueId;
    private Integer saved;
}