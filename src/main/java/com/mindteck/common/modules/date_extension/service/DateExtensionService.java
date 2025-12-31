package com.mindteck.common.modules.date_extension.service;

import com.mindteck.common.modules.date_extension.model.DateExtension;
import com.mindteck.common.modules.form.rest.DateExtensionRequest;
import com.mindteck.common.modules.form.rest.DateExtensionResponse;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalRequest;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DateExtensionService {

    public DateExtensionResponse dateExtension(DateExtensionRequest dateExtensionRequest);
    DateExtensionApprovalResponse updateDateExtensionStatus(DateExtensionApprovalRequest request);
    List<DateExtension> getPendingDateExtensions(Long formUniqueId);
    List<DateExtension> getDateExtensions(Long formUniqueId, Integer status);
}
