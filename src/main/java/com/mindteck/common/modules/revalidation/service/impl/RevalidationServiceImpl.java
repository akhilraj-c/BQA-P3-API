package com.mindteck.common.modules.revalidation.service.impl;

import com.mindteck.common.constants.Enum.ApplicationStatus;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.models.MailTemplate;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.revalidation.model.*;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanel;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelRequest;
import com.mindteck.common.modules.confirmation_panel.service.ConfirmationPanelService;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.mapping_panel.model.MappingPanel;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelRequest;
import com.mindteck.common.modules.mapping_panel.service.MappingPanelService;
import com.mindteck.common.modules.program_structure.model.ProgramStructure;
import com.mindteck.common.modules.program_structure.service.ProgramStructureService;
import com.mindteck.common.modules.program_structure_work_flow.model.ProgramStructureWorkFlow;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowRequest;
import com.mindteck.common.modules.program_structure_work_flow.service.ProgramStructureFlowService;
import com.mindteck.common.modules.revalidation.MonitoringChangeFormRepository;
import com.mindteck.common.modules.revalidation.MonitoringFormRepository;
import com.mindteck.common.modules.revalidation.service.RevalidationService;
import com.mindteck.common.modules.standards.controller.StandardsApiType;
import com.mindteck.common.modules.standards.model.SaveStandardsRequest;
import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import com.mindteck.common.modules.standards.model.WorkFlowFileDetails;
import com.mindteck.common.modules.standards.service.StandardsWorkFlowService;
import com.mindteck.common.modules.user.builder.UserResponseBuilder;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.RegistrationResponse;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import com.mindteck.common.modules.user.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class RevalidationServiceImpl implements RevalidationService {

    @Autowired
    private UserUtils userUtils;
    @Autowired
    private UserDao userDao;

    @Autowired
    private FormDao formDao;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    MonitoringFormRepository monitoringFormRepository;

    @Autowired
    private UserResponseBuilder responseBuilder;

    @Autowired
    ProgramStructureService programStructureService;

    @Autowired
    MappingPanelService mappingPanelService;
    @Autowired
    ProgramStructureFlowService programStructureFlowService;

    @Autowired
    StandardsWorkFlowService standardsWorkFlowService;

    @Autowired
    MonitoringChangeFormRepository monitoringChangeFormRepository;

    @Autowired
    ConfirmationPanelService confirmationPanelService;

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private AwsService awsService;

    @Override
    public ConfirmationPanelResponse getPanels(Long uniqueId) {

        return null;
    }
    @Override
    @Transactional
    public RegistrationResponse enforceMonitoring(EnforceMonitoringRequest request) {
        Long formUniqueId = request.getFormUniqueId();
        if (formUniqueId != 0) {
            // Retrieve the existing form
            InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
            if (instituteForm == null) {
                LOGGER.error("Application Not found: {}", formUniqueId);
                throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
            }
        }

        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
        EnforceRevalidationRequest enforceRevalidationRequest =  new EnforceRevalidationRequest();
        enforceRevalidationRequest.setIsRevalidation(request.getIsMonitoring());
        enforceRevalidationRequest.setFormUniqueId(request.getFormUniqueId());
        enforceRevalidationRequest.setPlannedDate(request.getPlannedDate());
        RegistrationResponse response ;
        if(formUniqueId==0){
            // Generate a new formUniqueId
            Long newFormUniqueId = userUtils.generateUniqueId();

            // Create a new InstituteForm and save it
            InstituteForm instituteFormForDirectRevalidation = new InstituteForm();
            instituteFormForDirectRevalidation.setFormUniqueId(newFormUniqueId);
            instituteFormForDirectRevalidation.setFormParentUniqueId(null);
            instituteFormForDirectRevalidation.setInstitutionName(request.getInstitutionName());
            instituteFormForDirectRevalidation.setQualificationTitle(request.getQualificationTitle());
            instituteFormForDirectRevalidation.setContactPersonName(request.getContactName());
            instituteFormForDirectRevalidation.setContactPersonTitle(request.getPositionTitle());
            instituteFormForDirectRevalidation.setContactPersonNumber(request.getContactNumber());
            instituteFormForDirectRevalidation.setContactPersonEmail(request.getEmail());
            instituteFormForDirectRevalidation.setIsRevalidation(1);
            instituteFormForDirectRevalidation.setListingId(request.getListingId());
            instituteFormForDirectRevalidation.setStatus(ApplicationStatus.INSTITUTION_REGISTERED.getCode());
            if(request.getPlannedDate()== null){
                instituteFormForDirectRevalidation.setPlannedSubDate(String.valueOf(System.currentTimeMillis()));
            }
            else {
                instituteFormForDirectRevalidation.setPlannedSubDate(request.getPlannedDate());
            }
            instituteForm = instituteFormForDirectRevalidation;
            institutionFormRepository.save(instituteFormForDirectRevalidation);
            response = responseBuilder.buildRegistrationResponse(2001, "Successfully registered", newFormUniqueId, newFormUniqueId);
        }
        else{
             response = enforceMonitoring(enforceRevalidationRequest);
        }

        for(MonitoringChangeFormRequest changeFormInstance:request.getChangeForm()){
            MonitoringChangeForm changeForm =  new MonitoringChangeForm();
            changeForm.setFormUniqueId(response.getData().getFormUniqueId());
            changeForm.setChangeType(changeFormInstance.getChangeType());
            changeForm.setDescription(changeFormInstance.getDescription());
            changeForm.setFile(changeFormInstance.getFile());
            monitoringChangeFormRepository.save(changeForm);
        }
        MonitoringForm monitoringForm = new MonitoringForm();
        monitoringForm.setConfirmation(request.getConfirmation());
        monitoringForm.setFormUniqueId(response.getData().getFormUniqueId());
        monitoringForm.setHaveChanges(request.getHaveChanges());
        monitoringForm.setPlannedForChange(request.getPlannedForChange());
        monitoringForm.setChangeDescription(request.getChangeDescription());
        monitoringForm.setComments(request.getComments());
        monitoringForm.setPlannedDate(request.getPlannedDate());
        monitoringFormRepository.save(monitoringForm);
        Map<String, Object> templateModel = new HashMap<>();
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("MONITORING_REQUEST");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{name}", instituteForm.getQualificationTitle());
            }
            String mailHtmlPath = "mail-template.html";
            templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }
        return response;
    }

    @Override
    @Transactional
    public RegistrationResponse enforceMonitoring(EnforceRevalidationRequest request) {
        Long formUniqueId = request.getFormUniqueId();
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        Long returnId = 0L;

        try {
            //Create a copy of InstituteFrom
            //Generate a random form uniqueId
            //Replace revalidation_parent_id with request.formUniqueId
            //Set is_revalidation = 1
            //Save InstituteFrom copy
            InstituteForm instituteFormForRevalidation = instituteForm.clone();
            instituteFormForRevalidation.setFormId(null);
            instituteFormForRevalidation.prePersist();
            long newFormUniqueId = userUtils.generateUniqueId();
            instituteFormForRevalidation.setFormUniqueId(newFormUniqueId);
            instituteFormForRevalidation.setRevalidationFormUniqueId(request.getFormUniqueId());
            instituteFormForRevalidation.setIsRevalidation(1);
            instituteFormForRevalidation.setOverallStatus(null);
            instituteFormForRevalidation.setOverAllEvaluationStatus(null);
            instituteFormForRevalidation.setOverAllEvaluationStatusComment("");
            instituteFormForRevalidation.setOverAllVerificationStatus(null);
            instituteFormForRevalidation.setOverAllVerificationStatusComment("");
            instituteFormForRevalidation.setVerificationFlag(null);
            instituteFormForRevalidation.setEvaluationFlag(null);
            instituteFormForRevalidation.setEvaluationRejectionCount(0);
            instituteFormForRevalidation.setVerificationRejectionCount(0);
            instituteFormForRevalidation.setCompletedStatus(null);
            instituteFormForRevalidation.setSubStatus(null);
            instituteFormForRevalidation.setOriginalPlacementDate(instituteForm.getPlacedDate());
            instituteFormForRevalidation.setPlacedDate(null);
            instituteFormForRevalidation.setAssignedAppManager(null);
            instituteFormForRevalidation.setIsVerificationPanelRequired(0);
            if(request.getPlannedDate()== null){
                instituteFormForRevalidation.setPlannedSubDate(String.valueOf(System.currentTimeMillis()));
            }
            else {
                instituteFormForRevalidation.setPlannedSubDate(request.getPlannedDate());
            }
            instituteFormForRevalidation.setStatus(ApplicationStatus.INSTITUTION_REGISTERED.getCode());
            instituteFormForRevalidation = userDao.saveInstitutionDetails(instituteFormForRevalidation);

            //get standards and create clone and save

            //check point 1: if you want stds to be retained then use the commented code in next line
            List<StandardWorkFlow> stads = new ArrayList<>() ; //standardsWorkFlowService.getStandards(formUniqueId, StandardsApiType.DEFAULT).getStandardWorkFlowsList();
            List<StandardWorkFlow> newStds = new ArrayList<>();
            if(stads!=null && !stads.isEmpty()) {
                for (StandardWorkFlow standardWorkFlow : stads) {
                    try {
                        StandardWorkFlow clonedStandard = (StandardWorkFlow) standardWorkFlow.clone();
                        clonedStandard.prePersist();
                        clonedStandard.setId(null);
                        clonedStandard.setAmStatus(null);
                        clonedStandard.setLatestAmCheck(1);
                        clonedStandard.setFormUniqueId(newFormUniqueId);
                        clonedStandard.setIsRevalidation(1);
                        newStds.add(clonedStandard);
                    } catch (Exception exception){

                    }
                }
            }

            SaveStandardsRequest saveStandardsRequest = new SaveStandardsRequest();
            saveStandardsRequest.setFormUniqueId(newFormUniqueId);
            saveStandardsRequest.setDeleteAll(1);
            saveStandardsRequest.setStandardWorkFlowsList(newStds);
            val result = standardsWorkFlowService.saveStandards(saveStandardsRequest, StandardsApiType.DEFAULT);
            System.out.println("saveStandardWorkflowList , save count = "+result.getSaved() + " row count = "+result.getTotalRowsSaved());


            //check point 2: if you want program structures to be retained then use the commented code in next line
            List<ProgramStructure> programStructureList = new ArrayList<>() ; // programStructureService.getProgramStructures(formUniqueId).getProgramStructureList();
            List<ProgramStructure> newProgramStructureList = new ArrayList<>();
            if(stads!=null && !stads.isEmpty()) {
                for (ProgramStructure data : programStructureList) {
                    try {
                        ProgramStructure cloned = (ProgramStructure) data.clone();
//                        val files = cloned.getFileDetails();
//                        for(WorkFlowFileDetails fileDetails : files){
//                            fileDetails.se
//                        }
                        cloned.prePersist();
                        cloned.setId(null);
                        cloned.setAmStatus(null);
                        if(cloned.getFileDetails() != null){
                            for(WorkFlowFileDetails fileDetails : cloned.getFileDetails()){
                                fileDetails.setLatestAmFileCheck(1);
                                fileDetails.setAmFileStatus(null);
                                fileDetails.setAmFileComment(null);
                            }
                        }
                        if(cloned.getFileDetails2() != null){
                            for(WorkFlowFileDetails fileDetails : cloned.getFileDetails2()){
                                fileDetails.setLatestAmFileCheck(1);
                                fileDetails.setAmFileStatus(null);
                                fileDetails.setAmFileComment(null);
                            }
                        }
                        cloned.setLatestAmCheck(1);
                        cloned.setFormUniqueId(newFormUniqueId);
                        cloned.setIsRevalidation(1);
                        newProgramStructureList.add(cloned);
                    } catch (Exception exception){

                    }
                }
            }
            val response = programStructureService.saveProgramStructures(newFormUniqueId, newProgramStructureList, 1);
            System.out.println("saveProgramStructures , save count = "+response.saveCount() + " row count = "+response.rowCount());


            //check point 3: if you want program structures flows to be retained then use the commented code in next line
            List<ProgramStructureWorkFlow> programStructureFlowList = new ArrayList<>() ; //programStructureFlowService.getProgramStructureFlows(formUniqueId).getProgramStructureFlowList();
            List<ProgramStructureWorkFlow> newProgramStructureFlowList = new ArrayList<>();
            if(stads!=null && !stads.isEmpty()) {
                for (ProgramStructureWorkFlow data : programStructureFlowList) {
                    try {
                        ProgramStructureWorkFlow cloned = (ProgramStructureWorkFlow) data.clone();
                        cloned.prePersist();
                        cloned.setId(null);
                        cloned.setFormUniqueId(newFormUniqueId);
                        cloned.setIsRevalidation(1);
                        newProgramStructureFlowList.add(cloned);
                    } catch (Exception exception){

                    }
                }
            }

            SaveProgramStructureFlowRequest saveProgramStructureFlowRequest = new SaveProgramStructureFlowRequest();
            saveProgramStructureFlowRequest.setFormUniqueId(newFormUniqueId);
            saveProgramStructureFlowRequest.setDeleteAll(1);
            saveProgramStructureFlowRequest.setProgramStructureFlowList(newProgramStructureFlowList);
            val response1 = programStructureFlowService.saveProgramStructureFlow(saveProgramStructureFlowRequest);
            System.out.println("saveProgramStructureFlow , save count = "+response1.getSaved() + " row count = "+response1.getTotalRowsSaved());


            //check point 4: if you want mapping to be retained then use the commented code in next line
            List<MappingPanel> mappingPanelList = new ArrayList<>() ; //mappingPanelService.getMappingPanels(formUniqueId).getMappingPanelList();
            List<MappingPanel> newMappingPanelList = new ArrayList<>();
            if(stads!=null && !stads.isEmpty()) {
                for (MappingPanel data : mappingPanelList) {
                    try {
                        MappingPanel cloned = (MappingPanel) data.clone();
                        cloned.prePersist();
                        cloned.setId(null);
                        cloned.setFormUniqueId(newFormUniqueId);
                        cloned.setIsRevalidation(1);
                        newMappingPanelList.add(cloned);
                    } catch (Exception exception){

                    }
                }
            }
            SaveMappingPanelRequest saveMappingPanelRequest = new SaveMappingPanelRequest();
            saveMappingPanelRequest.setFormUniqueId(newFormUniqueId);
            saveMappingPanelRequest.setMappingPanelList(newMappingPanelList);
            val response2 = mappingPanelService.saveMappingPanels(saveMappingPanelRequest);
            System.out.println("saveMappingPanels , save count = "+response2.getSaved());


            //check point 5: if you want confirmation to be retained then use the commented code in next line
            List<ConfirmationPanel> confirmationPanelList = new ArrayList<>() ; //confirmationPanelService.getPanels(formUniqueId).getConfirmationPanelList();
            List<ConfirmationPanel> newConfirmationPanelList = new ArrayList<>();
            if(stads!=null && !stads.isEmpty()) {
                for (ConfirmationPanel data : confirmationPanelList) {
                    try {
                        ConfirmationPanel cloned = (ConfirmationPanel) data.clone();
                        cloned.prePersist();
                        cloned.setId(null);
                        cloned.setFormUniqueId(newFormUniqueId);
                        cloned.setIsRevalidation(1);
                        newConfirmationPanelList.add(cloned);
                    } catch (Exception exception){

                    }
                }
            }
            SaveConfirmationPanelRequest saveConfirmationPanelRequest = new SaveConfirmationPanelRequest();
            saveConfirmationPanelRequest.setFormUniqueId(newFormUniqueId);
            saveConfirmationPanelRequest.setConfirmationPanelList(newConfirmationPanelList);
            val response3 = confirmationPanelService.savePanels(saveConfirmationPanelRequest);
            System.out.println("saveConfirmationPanels , save count = "+response3.getSaved());


            QualificationProfileData qualificationProfileData = userDao.getQualificationProfileDataByFormUniqueId(request.getFormUniqueId());
            if (null == qualificationProfileData) {
                qualificationProfileData = new QualificationProfileData();
                qualificationProfileData.setFormUniqueId(newFormUniqueId);
            }
            QualificationProfileData clonedQualificationProfileData = (QualificationProfileData) qualificationProfileData.clone();
            clonedQualificationProfileData.setId(null);
            clonedQualificationProfileData.setFormUniqueId(newFormUniqueId);
            clonedQualificationProfileData.setIsRevalidation(1);
            formDao.save(clonedQualificationProfileData);
            returnId = instituteFormForRevalidation.getFormUniqueId();

        } catch (CloneNotSupportedException exception){

        }


        return responseBuilder.buildRegistrationResponse(2001, "Successfully registered", returnId, returnId);
    }

    @Override
    @Transactional
    public MonitoringFormGetResponse getMonitoringForm(Long formUniqueId){
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}");
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        MonitoringForm monitoringForm = monitoringFormRepository.getByFormUniqueId(formUniqueId);
        if (monitoringForm == null) {
            throw new IllegalArgumentException("Not applied for monitoring");
        }
        List<MonitoringChangeForm> monitoringChangeForm = monitoringChangeFormRepository.findByFormUniqueId(formUniqueId);
        List<ChangeFormGetResponse> changeFormGetResponses = new ArrayList<>();
        for(MonitoringChangeForm changeFormInstance:monitoringChangeForm){
            ChangeFormGetResponse changeFormGetResponseInstance = new ChangeFormGetResponse();
            changeFormGetResponseInstance.setChangeType(changeFormInstance.getChangeType());
            changeFormGetResponseInstance.setDescription(changeFormInstance.getDescription());
            changeFormGetResponseInstance.setFile(changeFormInstance.getFile());
            changeFormGetResponses.add(changeFormGetResponseInstance);
        }

        MonitoringFormGetResponseModel data = new MonitoringFormGetResponseModel();

        data.setChangeForm(changeFormGetResponses);
        data.setFormUniqueId(monitoringForm.getFormUniqueId());
        data.setHaveChanges(monitoringForm.getHaveChanges());
        data.setPlannedForChange(monitoringForm.getPlannedForChange());
        data.setChangeDescription(monitoringForm.getChangeDescription());
        data.setPlannedDate(monitoringForm.getPlannedDate());
        data.setComments(monitoringForm.getComments());
        data.setConfirmation(monitoringForm.getConfirmation());

        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        MonitoringFormGetResponse response = new MonitoringFormGetResponse();
        response.setData(data);
        response.setStatus(status);

        return response;
    }


}
