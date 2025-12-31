package com.mindteck.common.modules.mapping_panel.service;

import com.mindteck.common.modules.mapping_panel.model.GetMappingPanelResponse;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelRequest;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelResponse;
import org.springframework.stereotype.Service;

@Service
public interface MappingPanelService {

    GetMappingPanelResponse getMappingPanels(Long uniqueId);
    SaveMappingPanelResponse saveMappingPanels(SaveMappingPanelRequest request);

}
