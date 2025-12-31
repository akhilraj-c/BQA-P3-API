package com.mindteck.common.modules.standards.service.impl;

import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.standards.model.*;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.repository.QualificationProfileApplicationManagerRepository;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.UserRepository;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.standards.controller.StandardsApiType;
import com.mindteck.common.modules.standards.StandardWorkFlowManagerRepository;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.standards.service.StandardsWorkFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StandardsWorkFlowServiceImpl implements StandardsWorkFlowService {

    @Autowired
    StandardWorkFlowManagerRepository standardWorkFlowManagerRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QualificationProfileApplicationManagerRepository qualificationProfileApplicationManagerRepository;

    @Autowired
    private FormDao formDao;

    @Override
    public GetStandardWorkFlowResponse getStandards(Long uniqueId, StandardsApiType apiType) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(uniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", uniqueId);
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        List<StandardWorkFlow> finalOut = getStandardWorkFlows(uniqueId, apiType);
        return getResponse(uniqueId, finalOut);
    }

    @Override
    public List<StandardWorkFlow> getStandardWorkFlows(Long formUniqueId, StandardsApiType apiType) {
        List<StandardWorkFlow> finalOut = new ArrayList<>();
        try {
            Integer isCf = 0;
            if (apiType == StandardsApiType.CONDITIONAL_FULFILMENT) {
                isCf = 1;
            }
            List<StandardWorkFlow> dataFromDB = standardWorkFlowManagerRepository.getStandards(formUniqueId, isCf);
            finalOut = dataFromDB.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (!dataFromDB.isEmpty() && !finalOut.isEmpty()) {
                for (StandardWorkFlow flow : finalOut) {
                    ArrayList<WorkFlowFileDetails> files = new ArrayList<>();
                    StringBuilder combinedDescription = new StringBuilder();
                    int fileDescIndex = 1;
                    for (StandardWorkFlow flowFromDb : dataFromDB) {
                        if (flowFromDb.equals(flow)) {
                            WorkFlowFileDetails fileDetails = new WorkFlowFileDetails();
                            fileDetails.setDescription(flowFromDb.getDescription());
                            fileDetails.setFile(flowFromDb.getFile());
                            fileDetails.setSubVersion(flowFromDb.getSubVersion());
                            files.add(fileDetails);
                            if(flowFromDb.getInstDescription() == null){
                                if(flowFromDb.getDescription()!=null && !flowFromDb.getDescription().isEmpty())
                                combinedDescription
                                        .append(fileDescIndex)
                                        .append("-")
                                        .append(flowFromDb.getDescription())
                                        .append(" \n \n");
                                fileDescIndex++;
                            }
                        }
                    }
                    if(flow.getInstDescription() == null){
                        flow.setInstDescription(combinedDescription.toString());
                    }
                    flow.setFileDetails(files);
                }
            }

        } catch (Exception e) {

        }
        return finalOut;
    }

    @Override
    @Transactional
    public SaveStandardWorkFlowResponse saveStandards(SaveStandardsRequest request, StandardsApiType apiType) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }

        if(!Objects.isNull(request.getRegistrationStatus()) && request.getRegistrationStatus() >-1){
            instituteForm.setStatus(request.getRegistrationStatus());
            formDao.save(instituteForm);
        }

        int saveCount = 0;
        int rowCount = 0;

        if(request.getDeleteAll()  == 1) {
            standardWorkFlowManagerRepository.deleteAllStandards(request.getFormUniqueId());
        }

        StandardWorkFlowSaveResult result = saveStandardWorkflowList(request.getStandardWorkFlowsList(), request.getFormUniqueId(), apiType, saveCount, rowCount);

        return getSaveResponse(request.getFormUniqueId(), result.saveCount(), result.rowCount());
    }

    @Override
    public StandardWorkFlowSaveResult saveStandardWorkflowList(List<StandardWorkFlow> stds, Long formUniqueId, StandardsApiType apiType, int saveCount, int rowCount) {
        for (StandardWorkFlow flow : stds) {
            flow.setFormUniqueId(formUniqueId);
            if (!Objects.isNull(flow.getFileDetails()) && !flow.getFileDetails().isEmpty()) {
                Integer isCf =0;
                if(apiType == StandardsApiType.CONDITIONAL_FULFILMENT){
                    isCf = 1;
                }
                standardWorkFlowManagerRepository.deleteStandardIfExistWithCfFlag(
                        formUniqueId,
                        flow.getParentTag(),
                        flow.getTag(),
                        flow.getVersion(),
                        isCf
                        //clonedStandardWorkFlow.getSubVersion()
                );
                for (WorkFlowFileDetails fileDetails : flow.getFileDetails()) {
                    //   StandardWorkFlow workFlow = new StandardWorkFlow();
                    try {
                        StandardWorkFlow clonedStandardWorkFlow = (StandardWorkFlow) flow.clone();
                        clonedStandardWorkFlow.setIsCf(isCf);

                        clonedStandardWorkFlow.setFile(fileDetails.getFile());
                        clonedStandardWorkFlow.setSubVersion(fileDetails.getSubVersion());
                        clonedStandardWorkFlow.setDescription(fileDetails.getDescription());

                        standardWorkFlowManagerRepository.save(clonedStandardWorkFlow);
                        rowCount++;
                    } catch (CloneNotSupportedException exception) {
                        exception.printStackTrace();
                    }
                }
                saveCount++;
            }
        }
        StandardWorkFlowSaveResult result = new StandardWorkFlowSaveResult(saveCount, rowCount);
        return result;
    }

    public record StandardWorkFlowSaveResult(int saveCount, int rowCount) {
    }


    @Override
    @Transactional
    public SaveStandardWorkFlowResponse evaluate(SaveStandardsRequest request) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        boolean doUserCheck = false;
        if (!Objects.isNull(request.getEnableUserIdCheck()) && request.getEnableUserIdCheck() == 1) {
            doUserCheck = true;
        }

        User amUser = userRepository.getByUserId(request.getAmUserId());
        if (amUser == null && doUserCheck) {
            LOGGER.error("User not found not found, provide a valid administrative user id", request.getAmUserId());
            throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
        }

        boolean isApplicationManager = amUser.getRoles()
                .stream()
                .anyMatch(role ->
                        UserType.getByCode(role.getUserType(), role.getSubType()) == UserType.APPLICATION_MANAGER
                );

        if (!isApplicationManager && doUserCheck) {
            LOGGER.error("User type is not application manager", request.getAmUserId());
            throw new ServiceException(ErrorCode.INVALID_USER_TYPE);
        }

        if (Objects.isNull(request.getOverallStatus())) {
            LOGGER.error("Stds evaluation failed invalid over all status", request.getOverallStatus());
            throw new ServiceException(ErrorCode.ADMINISTRATIVE_CHECK_FAILED_INVALID_OVER_ALL_STATUS);
        }

        int saveCount = 0;
        int rowCount = 0;

        boolean deleteExistingContent = false;
        if (!Objects.isNull(request.getDeleteExistingContent()) && request.getDeleteExistingContent() == 1) {
            deleteExistingContent = true;
        }

        AppManagerMapping appManagerMappedData = formDao.getAppManagerByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(appManagerMappedData)) {
            /**
             * Application should be assigned for administrative check
             * */
            throw new ServiceException(ErrorCode.ASSIGN_APPLICATION_MANAGER_INSTITUTE_FORM_NOT_SUBMITTED);
        }

        List<StandardWorkFlow> workFlowListInDb = standardWorkFlowManagerRepository.getStandards(request.getFormUniqueId(), 0);


        if (request.getStandardWorkFlowsList() != null && !request.getStandardWorkFlowsList().isEmpty()) {

            for (StandardWorkFlow flow : request.getStandardWorkFlowsList()) {
                flow.setFormUniqueId(request.getFormUniqueId());
                boolean flowExist = workFlowListInDb.stream().anyMatch(
                        standardWorkFlow -> Objects.equals(standardWorkFlow, flow)
                );
                if (Objects.isNull(flow.getAmStatus()) || flow.getAmStatus() < 0) {
                    throw new ServiceException(ErrorCode.ADMINISTRATIVE_CHECK_FAILED);
                }

                if (flowExist) {
                    //  flow.setAmStatus(flow.getAmStatus());

                    if (!Objects.isNull(flow.getFileDetails()) && !flow.getFileDetails().isEmpty()) {
                        if (deleteExistingContent) {
                            standardWorkFlowManagerRepository.deleteStandardIfExistWithCfFlag(
                                    request.getFormUniqueId(),
                                    flow.getParentTag(),
                                    flow.getTag(),
                                    flow.getVersion(),
                                    0
                                    //clonedStandardWorkFlow.getSubVersion()
                            );
                        }
                        for (WorkFlowFileDetails fileDetails : flow.getFileDetails()) {
                            //   StandardWorkFlow workFlow = new StandardWorkFlow();
                            try {
                                StandardWorkFlow clonedStandardWorkFlow = (StandardWorkFlow) flow.clone();

                                clonedStandardWorkFlow.setFile(fileDetails.getFile());
                                clonedStandardWorkFlow.setSubVersion(fileDetails.getSubVersion());
                                clonedStandardWorkFlow.setDescription(fileDetails.getDescription());
                                clonedStandardWorkFlow.setAmUserId(request.getAmUserId());
                                standardWorkFlowManagerRepository.save(clonedStandardWorkFlow);
                                rowCount++;
                            } catch (CloneNotSupportedException exception) {
                                exception.printStackTrace();
                            }
                        }
                        saveCount++;
                    }


                    //flow.setAmFile(flow.getAmFile());
                    //flow.setAmComment(flow.getAmComment());
                    //    standardWorkFlowManagerRepository.save(flow);
                    instituteForm.setOverallStatus(request.getOverallStatus());
                    formDao.save(instituteForm);
                    qualificationProfileApplicationManagerRepository.updateOverallStatus(request.getFormUniqueId(), request.getOverallStatus());
                    saveCount++;
                    rowCount++;

                } else {
                    //TODO : Throw error and break
                }
            }
            return getSaveResponse(request.getFormUniqueId(), saveCount, rowCount);
        } else {
            throw new ServiceException(ErrorCode.ADMINISTRATIVE_CHECK_FAILED_INVALID_STDS);
        }

    }


    public GetStandardWorkFlowResponse getResponse(Long formUniqueId, List<StandardWorkFlow> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetStandardWorkFlowResponse.builder().status(status)
                .standardWorkFlowsList(new ArrayList(data))
                .formUniqueId(formUniqueId)
                .build();
    }

    public SaveStandardWorkFlowResponse getSaveResponse(Long formUniqueId, int savedCount, int totalRowsSaved) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SaveStandardWorkFlowResponse.builder().status(status)
                .saved(savedCount)
                .totalRowsSaved(totalRowsSaved)
                .formUniqueId(formUniqueId)
                .build();
    }
}
