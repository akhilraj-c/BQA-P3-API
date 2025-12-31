package com.mindteck.common.modules.mapping_panel.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetMappingPanelResponse extends AbstractView {

    private List<MappingPanel> mappingPanelList;
    private Long formUniqueId;
}