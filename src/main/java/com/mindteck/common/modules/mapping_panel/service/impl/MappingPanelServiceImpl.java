package com.mindteck.common.modules.mapping_panel.service.impl;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.mapping_panel.model.MappingPanel;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelRequest;
import com.mindteck.common.modules.mapping_panel.service.MappingPanelService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.mapping_panel.MappingPanelRepository;
import com.mindteck.common.modules.mapping_panel.model.GetMappingPanelResponse;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MappingPanelServiceImpl implements MappingPanelService {

    @Autowired
    MappingPanelRepository programStructureRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Override
    public GetMappingPanelResponse getMappingPanels(Long uniqueId) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(uniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", uniqueId);
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        List<MappingPanel> data = new ArrayList<>();
        try {
            data = programStructureRepository.getProgramStructures(uniqueId);
        } catch (Exception e) {

        }
        return getResponse(uniqueId, data);
    }

    @Override
    @Transactional
    public SaveMappingPanelResponse saveMappingPanels(SaveMappingPanelRequest request) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        programStructureRepository.delete(request.getFormUniqueId());
        List<MappingPanel> saved = new ArrayList<>();
        for (MappingPanel data : request.getMappingPanelList()) {
            data.setFormUniqueId(request.getFormUniqueId());
            MappingPanel savedData = programStructureRepository.save(data);
            saved.add(savedData);
        }

        return getSaveResponse(request.getFormUniqueId(), saved);
    }

    public GetMappingPanelResponse getResponse(Long formUniqueId, List<MappingPanel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetMappingPanelResponse.builder().status(status)
                .mappingPanelList(new ArrayList(data))
                .formUniqueId(formUniqueId)
                .build();
    }

    public SaveMappingPanelResponse getSaveResponse(Long formUniqueId, List<MappingPanel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SaveMappingPanelResponse.builder().status(status)
                .saved(data.size())
                .formUniqueId(formUniqueId)
                .build();
    }


}
