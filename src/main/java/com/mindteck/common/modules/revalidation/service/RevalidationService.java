package com.mindteck.common.modules.revalidation.service;

import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanelResponse;
import com.mindteck.common.modules.revalidation.model.EnforceMonitoringRequest;
import com.mindteck.common.modules.revalidation.model.EnforceRevalidationRequest;
import com.mindteck.common.modules.revalidation.model.MonitoringFormGetResponse;
import com.mindteck.common.modules.user.model.rest.RegistrationResponse;
import org.springframework.stereotype.Service;

@Service
public interface RevalidationService {

    ConfirmationPanelResponse getPanels(Long uniqueId);
    RegistrationResponse enforceMonitoring(EnforceRevalidationRequest request);
    RegistrationResponse enforceMonitoring(EnforceMonitoringRequest request);
    MonitoringFormGetResponse getMonitoringForm(Long formUniqueId);
}
