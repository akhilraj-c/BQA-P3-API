package com.mindteck.common.modules.confirmation_panel.service;

import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelRequest;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelResponse;
import org.springframework.stereotype.Service;

@Service
public interface ConfirmationPanelService {

    ConfirmationPanelResponse getPanels(Long uniqueId);
    SaveConfirmationPanelResponse savePanels(SaveConfirmationPanelRequest request);

}
