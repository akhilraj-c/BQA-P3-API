package com.mindteck.common.modules.confirmation_panel.service.impl;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.confirmation_panel.ConfirmationPanelRepository;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanel;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelRequest;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.service.ConfirmationPanelService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConfirmationPanelServiceImpl implements ConfirmationPanelService {

    @Autowired
    ConfirmationPanelRepository confirmationPanelRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Override
    public ConfirmationPanelResponse getPanels(Long uniqueId) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(uniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", uniqueId);
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        List<ConfirmationPanel> data =new ArrayList<>();
        try {
             data = confirmationPanelRepository.getConfirmationPanels(uniqueId);
        } catch (Exception e){

        }
        return getResponse(uniqueId,data);
    }

    @Override
    @Transactional
    public SaveConfirmationPanelResponse savePanels(SaveConfirmationPanelRequest request) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        confirmationPanelRepository.delete(request.getFormUniqueId());
        List<ConfirmationPanel> saved = new ArrayList<>();
        for(ConfirmationPanel data: request.getConfirmationPanelList()){
            data.setFormUniqueId(request.getFormUniqueId());
            ConfirmationPanel savedData = confirmationPanelRepository.save(data);
            saved.add(savedData);
        }

        return getSaveResponse(request.getFormUniqueId(), saved);
    }

    public ConfirmationPanelResponse getResponse(Long formUniqueId, List<ConfirmationPanel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return ConfirmationPanelResponse.builder().status(status)
                .confirmationPanelList(new ArrayList(data))
                .formUniqueId(formUniqueId)
                .build();
    }
    public SaveConfirmationPanelResponse getSaveResponse(Long formUniqueId, List<ConfirmationPanel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SaveConfirmationPanelResponse.builder().status(status)
                .saved(data.size())
                .formUniqueId(formUniqueId)
                .build();
    }


}
