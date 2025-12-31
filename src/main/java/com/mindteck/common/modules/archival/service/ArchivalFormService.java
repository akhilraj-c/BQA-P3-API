package com.mindteck.common.modules.archival.service;

import com.mindteck.common.modules.archival.model.ArchivalFormApplyRequest;
import com.mindteck.common.modules.archival.model.ArchivalFormSaveResponse;
import com.mindteck.common.modules.archival.model.ArchivalFormUpdateStatusRequest;
import com.mindteck.common.modules.archival.model.ArchivalGetResponse;

public interface ArchivalFormService {
    ArchivalFormSaveResponse applyForArchival(ArchivalFormApplyRequest request);
    ArchivalFormSaveResponse updateArchivalStatus(ArchivalFormUpdateStatusRequest request);
    ArchivalGetResponse getArchival(Long formUniqueId);
}
