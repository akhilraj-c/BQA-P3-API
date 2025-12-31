package com.mindteck.common.modules.program_structure_work_flow.service.impl;

import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.AppManagerMapping;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.models_cas.User;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.program_structure_work_flow.service.ProgramStructureFlowService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.repository.QualificationProfileApplicationManagerRepository;
import com.mindteck.repository_cas.UserRepository;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.program_structure_work_flow.ProgramStructureWorkFlowRepository;
import com.mindteck.common.modules.program_structure_work_flow.model.GetProgramStructureFlowResponse;
import com.mindteck.common.modules.program_structure_work_flow.model.ProgramStructureWorkFlow;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowRequest;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowResponse;
import com.mindteck.common.modules.standards.model.WorkFlowFileDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProgramStructureFlowServiceImpl implements ProgramStructureFlowService {

    @Autowired
    ProgramStructureWorkFlowRepository programStructureRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QualificationProfileApplicationManagerRepository qualificationProfileApplicationManagerRepository;

    @Autowired
    private FormDao formDao;

    @Override
    public GetProgramStructureFlowResponse getProgramStructureFlows(Long uniqueId) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(uniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", uniqueId);
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        List<ProgramStructureWorkFlow> finalOut = new ArrayList<>();
        try {
            List<ProgramStructureWorkFlow> dataFromDB = programStructureRepository.getProgramStructureFlows(uniqueId);
            finalOut = dataFromDB.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (!dataFromDB.isEmpty() && !finalOut.isEmpty()) {
                for (ProgramStructureWorkFlow flow : finalOut) {
                    ArrayList<WorkFlowFileDetails> files = new ArrayList<>();
                    for (ProgramStructureWorkFlow flowFromDb : dataFromDB) {
                        if (
                                flowFromDb.formUniqueId.longValue() == flow.formUniqueId.longValue() &&
                                        flowFromDb.programTag.equals(flow.programTag) &&
                                        flowFromDb.tag.equals(flow.tag) &&
                                        flowFromDb.version.intValue() == flow.version.intValue()
                        ) {
                            WorkFlowFileDetails fileDetails = new WorkFlowFileDetails();
                            fileDetails.setDescription(flowFromDb.getDescription());
                            fileDetails.setFile(flowFromDb.getFile());
                            fileDetails.setSubVersion(flowFromDb.getSubVersion());
                            files.add(fileDetails);
                        }
                    }
                    flow.setFileDetails(files);
                }
            }

        } catch (Exception e) {

        }
        return getResponse(uniqueId, finalOut);
    }

    @Override
    @Transactional
    public SaveProgramStructureFlowResponse saveProgramStructureFlow(SaveProgramStructureFlowRequest request) {
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        int saveCount = 0;
        int rowCount = 0;
        if(request.getDeleteAll() == 1){
            programStructureRepository.deleteProgramStructureFlow( request.getFormUniqueId());
        }
        for (ProgramStructureWorkFlow flow : request.getProgramStructureFlowList()) {
            flow.setFormUniqueId(request.getFormUniqueId());
            if (!Objects.isNull(flow.getFileDetails()) && !flow.getFileDetails().isEmpty()) {
                programStructureRepository.deleteProgramStructureFlow(
                        request.getFormUniqueId(),
                        flow.getProgramTag(),
                        flow.getTag(),
                        flow.getVersion()
                );
                for (WorkFlowFileDetails fileDetails : flow.getFileDetails()) {
                    try {
                        ProgramStructureWorkFlow clonedFlow = (ProgramStructureWorkFlow) flow.clone();
                        clonedFlow.setFile(fileDetails.getFile());
                        clonedFlow.setSubVersion(fileDetails.getSubVersion());
                        clonedFlow.setDescription(fileDetails.getDescription());
                        programStructureRepository.save(clonedFlow);
                        rowCount++;
                    } catch (CloneNotSupportedException exception) {
                        exception.printStackTrace();
                    }
                }
                saveCount++;
            }
        }

        return getSaveResponse(request.getFormUniqueId(), saveCount, rowCount);
    }

    @Override
    @Transactional
    public SaveProgramStructureFlowResponse verifyProgramStructureFlow(SaveProgramStructureFlowRequest request) {
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

        List<ProgramStructureWorkFlow> workFlowListInDb = programStructureRepository.getProgramStructureFlows(request.getFormUniqueId());

        int saveCount = 0;
        int rowCount = 0;
        if (request.getProgramStructureFlowList() != null && !request.getProgramStructureFlowList().isEmpty()) {

            for (ProgramStructureWorkFlow flow : request.getProgramStructureFlowList()) {
                flow.setFormUniqueId(request.getFormUniqueId());
                boolean flowExist = workFlowListInDb.stream().anyMatch(
                        standardWorkFlow -> standardWorkFlow.formUniqueId.longValue() == flow.getFormUniqueId().longValue() &&
                                standardWorkFlow.programTag.equals(flow.programTag) &&
                                standardWorkFlow.tag.equals(flow.tag) &&
                                standardWorkFlow.version.intValue() == flow.version.intValue()
                );// &&
                // standardWorkFlow.subVersion == flow.subVersion);//TODO: In case sub version not required remove this line

                if (Objects.isNull(flow.getAmVerificationStatus()) || flow.getAmVerificationStatus() < 0) {
                    throw new ServiceException(ErrorCode.ADMINISTRATIVE_CHECK_FAILED);
                }

                if (flowExist) {
                    if (deleteExistingContent) {
                        programStructureRepository.deleteProgramStructureFlow(
                                request.getFormUniqueId(),
                                flow.getProgramTag(),
                                flow.getTag(),
                                flow.getVersion()//,
                                // data.getSubVersion() //TODO: In case sub version not required remove this line
                        );
                    }

                    // flow.setAmVerificationStatus(flow.getAmVerificationStatus());
                    flow.setAmUserId(request.getAmUserId());
                    //flow.setAmFile(flow.getAmFile());
                    //flow.setAmComment(flow.getAmComment());
                    programStructureRepository.save(flow);
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

    public GetProgramStructureFlowResponse getResponse(Long formUniqueId, List<ProgramStructureWorkFlow> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetProgramStructureFlowResponse.builder().status(status)
                .programStructureFlowList(new ArrayList(data))
                .formUniqueId(formUniqueId)
                .build();
    }

    public SaveProgramStructureFlowResponse getSaveResponse(Long formUniqueId, int savedCount, int totalRowsSaved) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SaveProgramStructureFlowResponse.builder().status(status)
                .saved(savedCount)
                .totalRowsSaved(totalRowsSaved)
                .formUniqueId(formUniqueId)
                .build();
    }


}
