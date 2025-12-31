package com.mindteck.common.modules.archival.service.impl;

import com.mindteck.common.constants.Enum.ApplicationStatusBackup6;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.models.MailTemplate;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.archival.ArchivalFormRepository;
import com.mindteck.common.modules.archival.model.*;
import com.mindteck.common.modules.archival.service.ArchivalFormService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@Service

public class ArchivalFormServiceImpl implements ArchivalFormService {
    @Autowired
    private ArchivalFormRepository archivalFormRepository;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    FormDao formDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private CommonUtils commonUtils;
    @Autowired
    private AwsService awsService;

    @Override
    @Transactional
    public ArchivalFormSaveResponse applyForArchival(ArchivalFormApplyRequest request) {
        if(request.getIsWithdrawnOrClosed()!=1){
            if (request.getSector() == null) {
                throw new IllegalArgumentException("Qualification Sector cannot be empty");
            }

            if (request.getReason() == null) {
                throw new IllegalArgumentException("Archiving Reasons cannot be empty");
            }

            if(request.getIsDirectDfoArchival()==1){
                if (request.getDecision() == null) {
                    throw new IllegalArgumentException("Archiving decision cannot be empty");
                }
            }
        }
        if(request.getIsSingleQualification()==0 && request.getInstituteId()==null && request.getIsDirectDfoArchival()==1){
            throw new IllegalArgumentException("Institution details cannot be null");
        }
        if(request.getIsSingleQualification()==1){ //For qualification
            if (request.getFormUniqueId() == null) {
                throw new IllegalArgumentException("Form unique ID cannot be empty");
            }

            InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
            if (instituteForm == null) {
                LOGGER.error("Application Not found:{}");
                throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
            }

            if(request.getIsWithdrawnOrClosed()!=1){
                ArchivalForm archivalForm = new ArchivalForm();
                archivalForm.setFormUniqueId(request.getFormUniqueId());
                archivalForm.setSector(request.getSector());
                archivalForm.setReason(request.getReason());
                archivalFormRepository.save(archivalForm);
                if(request.getIsDirectDfoArchival()==1){
                    instituteForm.setStatus(ApplicationStatusBackup6.ARCHIVAL_APPROVED.getCode());
                    archivalForm.setDecision(request.getDecision());
                    archivalForm.setComment(request.getComment());
                }else{
                    instituteForm.setStatus(ApplicationStatusBackup6.APPLIED_FOR_ARCHIVAL.getCode());
                }
                institutionFormRepository.save(instituteForm);

                Map<String, Object> templateModel = new HashMap<>();
                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ARCHIVAL_REQUEST");
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
            }
            else{
                if(request.getIsDirectDfoArchival()==1){
                    instituteForm.setStatus(ApplicationStatusBackup6.INSTITUTION_WITHDRAWN.getCode());
                }
                else{
                    instituteForm.setStatus(ApplicationStatusBackup6.INSTITUTION_CLOSED.getCode());
                }
                institutionFormRepository.save(instituteForm);
            }

            Status status = WebUtils.getStatus();
            status.setEndTime(System.currentTimeMillis());
            status.setStatusCode(StatusEnum.SUCCESS.getCode());
            ArchivalFormSaveResponse response = new ArchivalFormSaveResponse();
            ArchivalFormSaveResponseModel data = new ArchivalFormSaveResponseModel();
            if(request.getIsWithdrawnOrClosed()!=1) {
                data.setMessage("Applied for archival Successfully");
            }
            else{
                if(request.getIsDirectDfoArchival()==1){
                    data.setMessage("Application Withdrawn Successfully");
                }
                else{
                    data.setMessage("Application Closed Successfully");
                }

            }
            response.setData(data);
            response.setStatus(status);
            return response;
        }else if(request.getIsSingleQualification()==0){ //for whole institute
            List<InstituteForm> institutes;
            if(request.getIsDirectDfoArchival()==1){
                institutes=formDao.getAllQualificationForArchival(request.getInstituteId(),ApplicationStatusBackup6.APPLIED_FOR_ARCHIVAL.getCode());
            }else{
                institutes=formDao.getAllQualificationForArchival(WebUtils.getUserId(),ApplicationStatusBackup6.APPLIED_FOR_ARCHIVAL.getCode());
            }
            if(institutes.size()==0){
                throw new ServiceException(ErrorCode.INSTITUTION_ARCHIVAL_FAILED);
            }
            for(InstituteForm institute: institutes){
                InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(institute.getFormUniqueId());
                System.out.println(instituteForm.getStatus());
                if(Objects.equals(instituteForm.getStatus(), ApplicationStatusBackup6.DFO_ADMIN_APPROVE_AS_CABINET_APPROVE.getCode())) {
                    ArchivalForm archivalForm = new ArchivalForm();
                    archivalForm.setFormUniqueId(institute.getFormUniqueId());
                    archivalForm.setSector(request.getSector());
                    archivalForm.setReason(request.getReason());
                    if(request.getIsDirectDfoArchival()==1){
                        instituteForm.setStatus(ApplicationStatusBackup6.ARCHIVAL_APPROVED.getCode());
                        archivalForm.setDecision(request.getDecision());
                        archivalForm.setComment(request.getComment());
                        archivalFormRepository.save(archivalForm);
                        institutionFormRepository.save(instituteForm);
                    }else{
                        instituteForm.setStatus(ApplicationStatusBackup6.APPLIED_FOR_ARCHIVAL.getCode());
                        archivalFormRepository.save(archivalForm);
                        institutionFormRepository.save(instituteForm);
                    }
                    InstituteForm firstEntry = institutes.get(0);
                    Map<String, Object> templateModel = new HashMap<>();
//            InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(request.getFormUniqueId());
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ARCHIVAL_INSTITUTE");
                    if (Objects.nonNull(mailTemplate)) {
                        List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, firstEntry);
                        String mailBody = mailTemplate.getTemplateBody();
                        //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                        if (mailBody !=null && !mailBody.isBlank()) {
//                    mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                            mailBody = mailBody.replace("{name}", firstEntry.getInstitutionName());
                        }
                        String mailHtmlPath = "mail-template.html";
                        templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
                        awsService.sendMail(firstEntry.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }

                }
                else{
                    if(request.getIsDirectDfoArchival()==1){
                        instituteForm.setStatus(ApplicationStatusBackup6.INSTITUTION_WITHDRAWN.getCode());
                        institutionFormRepository.save(instituteForm);
                    }
                    else{
                        instituteForm.setStatus(ApplicationStatusBackup6.INSTITUTION_CLOSED.getCode());
                        institutionFormRepository.save(instituteForm);
                    }
                }

            }
            Status status = WebUtils.getStatus();
            status.setEndTime(System.currentTimeMillis());
            status.setStatusCode(StatusEnum.SUCCESS.getCode());
            ArchivalFormSaveResponse response = new ArchivalFormSaveResponse();
            ArchivalFormSaveResponseModel data = new ArchivalFormSaveResponseModel("Applied for archival Successfully");
            response.setData(data);
            response.setStatus(status);
            return response;

        }
        throw new IllegalArgumentException("Something went wrong");
    }

    public ArchivalFormSaveResponse updateArchivalStatus(ArchivalFormUpdateStatusRequest request){
        ArchivalForm archivalForm = archivalFormRepository.getById(request.getArchivalFormId());
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(archivalForm.getFormUniqueId());

        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}");
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        if(archivalForm==null){
            throw new IllegalArgumentException("Not applied for archival");
        }
        if(instituteForm.getStatus()== ApplicationStatusBackup6.ARCHIVAL_APPROVED.getCode() || instituteForm.getStatus()== ApplicationStatusBackup6.ARCHIVAL_REJECTED.getCode()){
            throw new IllegalArgumentException("Status Already updated");
        }
        if(request.getDecision()==null){
            throw new IllegalArgumentException("Decision is empty");
        }

        if(request.getStatus()==1){ //Approed
            instituteForm.setStatus(ApplicationStatusBackup6.ARCHIVAL_APPROVED.getCode());
            Map<String, Object> templateModel = new HashMap<>();
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ARCHIVAL_APPROVE");
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
        } else if (request.getStatus()==0) { //Rejected
            instituteForm.setStatus(ApplicationStatusBackup6.ARCHIVAL_REJECTED.getCode());
            Map<String, Object> templateModel = new HashMap<>();
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ARCHIVAL_REJECTED");
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
        }else{
            throw new IllegalArgumentException("Something went wrong");
        }
        archivalForm.setDecision(request.getDecision());
        archivalForm.setComment(request.getComment());
        archivalFormRepository.save(archivalForm);

        institutionFormRepository.save(instituteForm);


        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        ArchivalFormSaveResponse response = new ArchivalFormSaveResponse();
        ArchivalFormSaveResponseModel data =new ArchivalFormSaveResponseModel("Archival approved");
        response.setData(data);
        response.setStatus(status);
        return response;
    }

    public ArchivalGetResponse getArchival(Long formUniqueId){
        InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}");
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ArchivalForm archivalForm = archivalFormRepository.getByFormUniqueId(formUniqueId);

        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        ArchivalGetResponse response = new ArchivalGetResponse();
        response.setData(archivalForm);
        response.setStatus(status);
        return response;
    }
}
