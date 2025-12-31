package com.mindteck.common.modules.evaluation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.modules.evaluation.rest.*;
import com.mindteck.common.modules.evaluation.service.EvaluationService;
import com.mindteck.common.repository.IlepPanelRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.evaluation.builder.EvaluationResponseBuilder;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.util.UserUtils;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    LogService logService;
    @Autowired
    private EvaluationDao evaluationDao;
    @Autowired
    private FormDao formDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private EvaluationResponseBuilder responseBuilder;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private AwsService awsService;

    @Autowired
    private IlepAssignDao ilepAssignDao;

    @Autowired
    private IlepPanelRepository ilepPanelRepository;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private FireBaseUtils fireBaseUtils;

    @Override
    public CreateInstConflictResponse createInstConflict(CreateInstConflictRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INSTITUTION_CONFLICT_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (!instituteForm.getStatus().equals(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode())) {
            throw new ServiceException(ErrorCode.DFO_ILEP_NOT_APPROVED);
        }
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (conflictForm == null) {
            conflictForm = new ConflictForm();
            conflictForm.setIsConflictApproved(BooleanEnum.FALSE.getCode());
            conflictForm.setIsHistory(BooleanEnum.FALSE.getCode());
        }

        if (request.getInstitutionConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
            instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT.getCode());
        } else {
            instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode());
        }
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        conflictForm.setFormUniqueId(request.getFormUniqueId());
        conflictForm.setInstitutionConflictStatus(request.getInstitutionConflictStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String institutionConflictData = objectMapper.writeValueAsString(request.getInstitutionConflictDataList());
            conflictForm.setInstitutionConflictData(institutionConflictData);
        } catch (JsonProcessingException exception) {
            LOGGER.error("Error while parsing convertion object to json {}", exception.getMessage());
            throw exception;
        }
        conflictForm = evaluationDao.save(conflictForm);

        if (request.getInstitutionConflictStatus().equals(BooleanEnum.FALSE.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_SIGN_NO_CONFLICT");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                String mailBody = mailTemplate.getTemplateBody();
                User amUserDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
//                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                for (IlepPanel ilep : ilepList) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                            mailBody = mailBody.replace("{instituteName} ", instituteForm.getQualificationTitle());
                            mailBody = mailBody.replace("{amName}", amUserDetails.getUsername());
                            mailBody = mailBody.replace("{amMail}", amUserDetails.getEmailId());
                            mailBody = mailBody.replace("{amPhone}", amUserDetails.getContactNumber());
                            Map<String, Object> templateModel = new HashMap<>();

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
                            awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                        }
                    }
                }
            }
        }
        return responseBuilder.createInstConflictResponseBuilder(StatusCode.SUCCESS.getCode()
                , conflictForm.getFormUniqueId());
    }

    @Override
    public DfoApprovePanelResponse approvePanel(DfoApprovePanelRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (!instituteForm.getStatus().equals(ApplicationStatus.ILEP_PANEL_CREATED.getCode())) {
            throw new ServiceException(ErrorCode.ILEP_NOT_CREATED);
        }
        List<IlepPanel> ilepPanel = evaluationDao.getByFormUniqueIdAndPanelId(request.getFormUniqueId(), request.getPanelId());
        if (ilepPanel.isEmpty()) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_PANEL_NOT_FOUND);
        }
        for (IlepPanel member : ilepPanel) {
            member.setIsDfoApproved(BooleanEnum.TRUE.getCode());
            evaluationDao.save(member);
        }
        instituteForm.setStatus(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        InstituteForm instituteDetails = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (!Objects.isNull(instituteDetails)) {
            LOGGER.info("Before initializing variables for institution notification mail for signing no conflict form");
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DFOA_APPROVE_ILEP_PANEL");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User instituteUser = userDao.getByUserEmail(instituteDetails.getContactPersonEmail());
//                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{userName}", instituteUser.getUsername());
                    mailBody = mailBody.replace("{instituteName}", instituteDetails.getInstitutionName());
                    mailBody = mailBody.replace("{QualificationTitle}", instituteForm.getQualificationTitle());
                }
                Map<String, Object> templateModel = new HashMap<>();

                templateModel.put("mailBody", mailBody);
                String mailHtmlPath = "mail-template.html";
                /*List<String> ccAdresses = new ArrayList<>();
                ccAdresses.add(amDetails.getEmailId());*/
                awsService.sendMail(instituteUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
            }
           /* User user = commonUtils.getUserDetails(UserType.INSTITUTION.getCode(), null, instituteDetails.getFormUniqueId());
            List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
            String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_TITLE, NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_MESSAGE, null, tokens);
  */
        }
        return responseBuilder.dfoApprovePanelResponseBuilder("Dfo updated the panel status");
    }

    @Override
    public UploadMomResponse uploadMom(UploadMomRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.UPLOAD_MOM_INPUT_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        if (!instituteForm.getStatus().equals(ApplicationStatus.FIRST_MEETING_CREATED.getCode())) {
            throw new ServiceException(ErrorCode.UPLOAD_MOM_INPUT_MEETING_NOT_SCHEDULED);
        }

        Meeting meeting = evaluationDao.getMeetingByFormUniqueId(request.getFormUniqueId());
        if (meeting == null) {
            throw new ServiceException(ErrorCode.UPLOAD_MOM_INPUT_MEETING_NOT_FOUND);
        }
        meeting.setFirstMeetingDocumentsAndComments(request.getFirstMeetingDocumentsAndComments());
        meeting.setDeadLine(request.getDeadLine());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode())) {
            completedStatus.add(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));
        instituteForm.setStatus(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode());
        instituteForm.setSubStatus(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode());
        evaluationDao.save(meeting);
        formDao.save(instituteForm);

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("FIRST_MEETING_OUTCOMES");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            Map<String, Object> templateModel = new HashMap<>();

            Date date = new Date(request.getDeadLine());
            DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
            Instant instant = Instant.ofEpochMilli(request.getDeadLine());
            LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = deadlineDate.format(formatter);


            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                mailBody = mailBody.replace("{deadLine}",formattedDate);
                mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
            }
            List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
            for (IlepPanel ilep : ilepList) {
                User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                if(ilepMember.getActive()==1){
                    if (mailBody != null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                    }
                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
                    awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                }
            }
        }


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return responseBuilder.uploadMomResponseBuilder("First meeting files uploaded successfully");
    }

    @Override
    public RemovePanelResponse removePanel(RemovePanelRequest request) throws JsonProcessingException {
        if (null == formDao.getInstitutionFormById(request.getFormUniqueId())) {
            throw new ServiceException(ErrorCode.REMOVE_PANEL_INVALID_FORM_UNIQUE_ID);
        }
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (conflictForm == null) {
            throw new ServiceException(ErrorCode.REMOVE_PANEL_CONFLICT_FORM_NOT_FOUND);
        }
        List<IlepPanel> ilepPanel = evaluationDao.getByFormUniqueIdAndPanelId(request.getFormUniqueId(), request.getPanelId());
        for (Long userId : request.getUserId()) {
            if (ilepPanel == null || ilepPanel.stream().noneMatch(el -> el.getIlepMemberId().equals(userId))) {
                throw new ServiceException(ErrorCode.REMOVE_PANEL_NOT_FOUND);
            }
        }


        LOGGER.info("panelStatus 2 :: rejected");
        LOGGER.info("change the panel as History");
        conflictForm.setIsHistory(1);


        ConflictForm newConflictForm = new ConflictForm();
        ObjectMapper objectMapper = new ObjectMapper();
        newConflictForm.setIsHistory(0);
        if (conflictForm.getInstitutionConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
            List<InstitutionConflictData> conflictForms = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
            });
            for (Long userId : request.getUserId()) {
                conflictForms.removeIf(el -> el.getIlepUserId().equals(userId));
            }

            newConflictForm.setInstitutionConflictData(objectMapper.writeValueAsString(conflictForms));
            newConflictForm.setInstitutionConflictStatus(BooleanEnum.FALSE.getCode());
        } else if (conflictForm.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
            newConflictForm.setInstitutionConflictData(conflictForm.getInstitutionConflictData());
            newConflictForm.setInstitutionConflictStatus(conflictForm.getInstitutionConflictStatus());
            newConflictForm.setIsConflictApproved(conflictForm.getIsConflictApproved());
            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
            });
            for (Long userId : request.getUserId()) {
                conflictForms.removeIf(el -> el.getIlepUserId().equals(userId));
            }
            String conflictFormString = objectMapper.writeValueAsString(conflictForms);
            newConflictForm.setIlepConflictData(conflictFormString);
            newConflictForm.setIlepConflictStatus(BooleanEnum.FALSE.getCode());
        } else {
            List<InstitutionConflictData> conflictForms = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
            });
            for (Long userId : request.getUserId()) {
                conflictForms.removeIf(el -> el.getIlepUserId().equals(userId));
            }
            newConflictForm.setInstitutionConflictData(objectMapper.writeValueAsString(conflictForms));
            List<IlepConflictForm> ilepConflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
            });
            for (Long userId : request.getUserId()) {
                ilepConflictForms.removeIf(el -> el.getIlepUserId().equals(userId));
            }
            String conflictFormString = objectMapper.writeValueAsString(ilepConflictForms);
            newConflictForm.setIlepConflictData(conflictFormString);
            newConflictForm.setInstitutionConflictStatus(BooleanEnum.FALSE.getCode());
            newConflictForm.setIsConflictApproved(BooleanEnum.FALSE.getCode());
            newConflictForm.setIlepConflictStatus(BooleanEnum.FALSE.getCode());
        }


        newConflictForm.setPanelStatus(conflictForm.getPanelStatus());
        newConflictForm.setFormUniqueId(conflictForm.getFormUniqueId());

        evaluationDao.save(newConflictForm);
        evaluationDao.save(conflictForm);

        for (IlepPanel member : ilepPanel) {
            for (Long userId : request.getUserId()) {
                if (member.getIlepMemberId().equals(userId)) {
                    evaluationDao.delete(member);
                }
            }


        }

        return responseBuilder.removePanelResponseBuilder("Panel removed successfully");
    }

    @Override
    @Transactional
    public CreateMeetingResponse createMeeting(CreateMeetingRequest request) throws JsonProcessingException {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.CREATE_MEETING_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        if (instituteForm.getStatus() < ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode()) {
            throw new ServiceException(ErrorCode.CREATE_MEETING_ACCESS_NOT_GRANTED_ILEP);
        }


        MethodType methodType = MethodType.getByCode(request.getMethod());
        if (null == methodType) {
            throw new ServiceException(ErrorCode.CREATE_MEETING_INVALID_METHOD_TYPE);
        }
        MeetingType meetingType = MeetingType.getByCode(request.getMeetingType());
        if (null == meetingType) {
            throw new ServiceException(ErrorCode.CREATE_MEETING_INVALID_MEETING_TYPE);
        }
        Meeting meeting = evaluationDao.getMeetingByFormUniqueId(request.getFormUniqueId());
        if (null == meeting) {
            meeting = new Meeting();
            meeting.setFormUniqueId(request.getFormUniqueId());
        }
        ApplicationStatus applicationStatus;
        if (meetingType.equals(MeetingType.FIRST_MEETING)) {
            applicationStatus = ApplicationStatus.FIRST_MEETING_CREATED;
            meeting.setFirstDateAndTime(request.getDateTime());
            meeting.setFirstMeetingType(meetingType.getCode());
            meeting.setFirstMethod(methodType.getCode());
            meeting.setFirstLinkOrLocation(request.getLinkOrLocation());
        } else {
            applicationStatus = ApplicationStatus.SECOND_MEETING_CREATED;
            meeting.setSecondDateAndTime(request.getDateTime());
            meeting.setSecondMeetingType(meetingType.getCode());
            meeting.setSecondMethod(methodType.getCode());
            meeting.setSecondLinkOrLocation(request.getLinkOrLocation());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(applicationStatus.getCode())) {
            completedStatus.add(applicationStatus.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));
        instituteForm.setSubStatus(applicationStatus.getCode());
        instituteForm.setStatus(applicationStatus.getCode());
        evaluationDao.save(meeting);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        if (meetingType.equals(MeetingType.FIRST_MEETING)) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("FIRST_EVALUATON_MEETING");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                Map<String, Object> templateModel = new HashMap<>();

                Instant instant = Instant.ofEpochMilli( request.getDateTime());
                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                Date date = new Date(request.getDateTime());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = localDateFormat.format(date);

                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{meetingTime}", time);
                    mailBody = mailBody.replace("{meetingDate}", formattedDate);
                    mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                    mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                    mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                }
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                for (IlepPanel ilep : ilepList) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                        }
                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        /*List<String> ccAdresses = new ArrayList<>();
                        ccAdresses.add(amDetails.getEmailId());*/
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
            }
        } else {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("SECOND_EVALUATON_MEETING");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                Map<String, Object> templateModel = new HashMap<>();

                Instant instant = Instant.ofEpochMilli( request.getDateTime());
                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                Date date = new Date(request.getDateTime());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = localDateFormat.format(date);

                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{meetingTime}", time);
                    mailBody = mailBody.replace("{meetingDate}", formattedDate);
                    mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                    mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                    mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
//                    mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                }
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                for (IlepPanel ilep : ilepList) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                        }
                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        /*List<String> ccAdresses = new ArrayList<>();
                        ccAdresses.add(amDetails.getEmailId());*/
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
//                templateModel.put("mailBody", mailBody);
//                String mailHtmlPath = "mail-template.html";
//                /*List<String> ccAdresses = new ArrayList<>();
//                ccAdresses.add(amDetails.getEmailId());*/
//                awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
            }
        }

        return responseBuilder.buildCreateMeetingResponse(meeting.getId(), "meeting created successfully");
    }

    @Override
    public GetAMConflictFormResponse getAMConflictFormDetails(Long formUniqueId) throws JsonProcessingException {

        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_AM_CONFLICT_DETAILS_INSTITUTE_NOT_FOUND);
        }

        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(formUniqueId, BooleanEnum.FALSE.getCode());
        if (Objects.isNull(conflictForm)) {
            LOGGER.debug("Conflict not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_AM_CONFLICT_DETAILS_CONFLICT_FORM_NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();

        GetAMConflictFormResponseModel responseModel = new GetAMConflictFormResponseModel();
        responseModel.setInstitutionConflictStatus(conflictForm.getInstitutionConflictStatus());
        responseModel.setIlepConflictStatus(conflictForm.getIlepConflictStatus());
        responseModel.setIlepConflictForm(mapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
        }));
        responseModel.setInstitutionConflictDataList(mapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
        }));


        return responseBuilder.buildGetAMConflictFormResponse(responseModel);
    }

    public CreateILepConflictResponse createIlepConflict(CreateILepConflictRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.ILEP_CONFLICT_INVALID_FORM_UNIQUE_ID);
        }

        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
       /* if (!instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode())) {
            throw new ServiceException(ErrorCode.ILEP_CONFLICT_INSTITUTION_CONFLICT_NOT_SUBMITTED);
        }*/
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (conflictForm == null) {
            throw new ServiceException(ErrorCode.ILEP_CONFLICT_INSTITUTION_CONFLICT_NOT_SUBMITTED);
        }
        Long ilepMemberCount = evaluationDao.countByFormUniqueIdAndIsDfoApproved(request.getFormUniqueId(), BooleanEnum.TRUE.getCode());
        if (conflictForm.getIlepConflictStatus() == null || conflictForm.getIlepConflictStatus().equals(BooleanEnum.FALSE.getCode())) {
            conflictForm.setIlepConflictStatus(request.getIlepConflictStatus());
        }
        if (request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
            conflictForm.setPanelStatus(2);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            request.getIlepConflictForm().setIlepUserId(userId);
            if (null == conflictForm.getIlepConflictData()) {
                List<IlepConflictForm> conflictForms = List.of(request.getIlepConflictForm());
                String ilepConflictData = objectMapper.writeValueAsString(conflictForms);
                conflictForm.setIlepConflictData(ilepConflictData);
                if (request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                    instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode());
                } else {
                    List<IlepPanel> totalIlep = ilepPanelRepository.getByFormUniqueId(request.getFormUniqueId());
                    if (totalIlep.size() == 1) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_ASSIGNED.getCode());
                    }
                    else {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode());
                    }
                }
                if(instituteForm.isRevalidationForm()) {
                    //if revalidation
                    instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_ASSIGNED.getCode());
                }
            } else {
                List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                });
                conflictForms.add(request.getIlepConflictForm());
                if (conflictForms.size() == 2) {
                    if (request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT.getCode());
                    } else {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode());
                    }
                }
                if (conflictForms.size() == 3) {
                    if (request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_CONFLICT.getCode());
                    } else {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT.getCode());
                    }
                }
                String ilepConflictData = objectMapper.writeValueAsString(conflictForms);
                conflictForm.setIlepConflictData(ilepConflictData);
                if (null != conflictForm.getInstitutionConflictStatus()) {
                    if (conflictForms.size() == ilepMemberCount
                            && (conflictForm.getIlepConflictStatus().equals(BooleanEnum.FALSE.getCode()))
                            && (conflictForm.getInstitutionConflictStatus().equals(BooleanEnum.FALSE.getCode()))) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_ASSIGNED.getCode());
                    }
                }

            }
            formDao.save(instituteForm);

            ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
            /*Writing Application status log to database */
            if (!currentStatus.equals(newStatus)) {
                logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
            }
            evaluationDao.save(conflictForm);
            if (request.getIlepConflictStatus().equals(BooleanEnum.FALSE.getCode())) {
                User applicationManager = userDao.getByUserId(instituteForm.getAssignedAppManager());
                if (!Objects.isNull(applicationManager)) {
                    LOGGER.info("Before initializing variables for application manager notification mail for scheduling meeting");
                    /* commented as not using now
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ILEP_SIGN_NON_CONFLICT");
                    if (Objects.nonNull(mailTemplate)) {
                        String mailBody = mailTemplate.getTemplateBody();
                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                        mailBody = mailBody.replace("{userName}", applicationManager.getUsername());

                        Map<String, Object> templateModel = new HashMap<>();
                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        awsService.sendMail(applicationManager.getEmailId(), templateModel, mailHtmlPath);
                    }*/
                   /* User user = commonUtils.getUserDetails(UserType.APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode(), instituteForm.getFormUniqueId());
                    List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                    String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ILEP_SIGN_NON_CONFLICT_TITLE, NotifcationConstatnts.ILEP_SIGN_NON_CONFLICT_MESSAGE, null, tokens);
 */
                }
            }
        } catch (JsonProcessingException exception) {
            LOGGER.error("Error while parsing convertion object to json {}", exception.getMessage());
            throw exception;
        }
        conflictForm = evaluationDao.save(conflictForm);
        return responseBuilder.createCreateILepConflictResponse(StatusCode.SUCCESS.getCode()
                , conflictForm.getFormUniqueId());

    }

    @Override
    public GetInstituteOwnConflictDetailsResponse getInstitutionOwnConflictDetails(Long formUniqueId) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_INSTITUTE_OWN_CONFLICT_DETAILS_INSTITUTE_NOT_FOUND);
        }

        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(formUniqueId, BooleanEnum.FALSE.getCode());
        if (Objects.isNull(conflictForm)) {
            LOGGER.debug("Conflict not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_AM_CONFLICT_DETAILS_CONFLICT_FORM_NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();
        GetInstituteOwnConflictDetailsResponseModel responseModel = new GetInstituteOwnConflictDetailsResponseModel();
        responseModel.setInstitutionConflictStatus(conflictForm.getInstitutionConflictStatus());
        if (conflictForm.getInstitutionConflictData() != null) {
            responseModel.setInstitutionConflictDataList(mapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
            }));
        }
        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(formUniqueId, BooleanEnum.FALSE.getCode());
        if (null != ilepList && !ilepList.isEmpty()) {
            for (Ilep ilep : ilepList) {
                if (null != ilep.getInstitutionConflictData() && !ilep.getInstitutionConflictData().isEmpty()) {
                    List<InstitutionConflictData> institutionConflictData = mapper.readValue(ilep.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                    });
                    responseModel.getInstitutionConflictDataList().addAll(institutionConflictData);
                }
            }
        }
        return responseBuilder.buildGetInstituteOwnConflictDetailsResponse(responseModel);
    }

    @Override
    public GetILEPOwnConflictDetailsResponse getGetILEPOwnConflictDetails(Long formUniqueId) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_INSTITUTE_OWN_CONFLICT_DETAILS_INSTITUTE_NOT_FOUND);
        }

        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(formUniqueId, BooleanEnum.FALSE.getCode());
        if (Objects.isNull(conflictForm)) {
            LOGGER.debug("Conflict not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_AM_CONFLICT_DETAILS_CONFLICT_FORM_NOT_FOUND);
        }
        ObjectMapper mapper = new ObjectMapper();
        GetILEPOwnConflictDetailsResponseModel responseModel = new GetILEPOwnConflictDetailsResponseModel();
        responseModel.setIlepConflictStatus(conflictForm.getIlepConflictStatus());
        responseModel.setIlepConflictForm(mapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
        }));
        return responseBuilder.buildGetILEPOwnConflictDetailsResponse(responseModel);
    }

    @Override
    public GetFormIlepMemberResponse getFormIlepMembers(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.GET_FORM_ILEP_MEMBERS_FORM_UNIQUE_ID_NOT_FOUND);
        }
        List<GetFormIlepMemberResponseModel> getFormIlepMemberResponseModels = evaluationDao.getFormIlepMember(formUniqueId);
        if (getFormIlepMemberResponseModels.isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }

        return responseBuilder.getFormIlepMemberResponseBuilder(getFormIlepMemberResponseModels);
    }

    @Override
    @Transactional
    public ConflictApproveResponse approveConflict(ConflictApproveRequest request) throws JsonProcessingException {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_APPROVE_CONFLICT_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (Objects.isNull(conflictForm)) {
            LOGGER.debug("Conflict not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_APPROVE_CONFLICT_CONFLICT_FORM_NOT_FOUND);
        }

        if (!conflictForm.getInstitutionConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
            LOGGER.debug("Institution not submitted conflict form");
            throw new ServiceException(ErrorCode.AM_APPROVE_CONFLICT_INSTITUTION_CONFLICT_NOT_SUBMITTED);
        }

        conflictForm.setIsConflictApproved(request.getConflictApproved().equals(true) ? BooleanEnum.TRUE.getCode() :
                BooleanEnum.FALSE.getCode());
        if (request.getConflictApproved().equals(false)) {
            /**
             * Changed by jobin jacob paily
             * setting the application status if conflict not approved
             */
            if (null != conflictForm.getIlepConflictData()) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                });
                String ilepConflictData = objectMapper.writeValueAsString(conflictForms);
                conflictForm.setIlepConflictData(ilepConflictData);
                Long countIlepMember = evaluationDao.countByFormUniqueIdAndIsDfoApproved(request.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                if (conflictForms.size() == countIlepMember
                        && (conflictForm.getIlepConflictStatus().equals(BooleanEnum.FALSE.getCode()))) {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode());
                }
            }
            /***********************/
            //send notification
            LOGGER.info("Before initializing variables for dfo notification mail for approving ilep panel");
            /* commented as not using now
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_NOT_APPROVE_CONFLICT");
            List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
            if (Objects.nonNull(mailTemplate)) {
                String mailBody = mailTemplate.getTemplateBody();

                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                Map<String, Object> templateModel = new HashMap<>();

                for (Ilep ilepPanel : ilepList) {
                    User ilepDetails = userDao.getByUserId(ilepPanel.getIlepMemberId());
                    mailBody = mailBody.replace("{userName}", ilepDetails.getUsername());
                    String mailHtmlPath = "mail-template.html";
                    templateModel.put("mailBody", mailBody);

                    awsService.sendMail(ilepDetails.getEmailId(), templateModel, mailHtmlPath);
                }
            }*/
          /*  User user = commonUtils.getUserDetails(UserType.ILEP_MEMBER.getCode(), UserSubType.ADMIN.getCode(), conflictForm.getFormUniqueId());
            List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
            String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.AM_NOT_APPROVE_CONFLICT_TITLE, NotifcationConstatnts.AM_NOT_APPROVE_CONFLICT_MESSAGE, null, tokens);
*/
        } else {
            /**
             * Changed by jobin jacob paily
             * setting the application status if conflict  approved
             */
            instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT_AM_APPROVE.getCode());
            /************************/
        }
        evaluationDao.save(conflictForm);
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return responseBuilder.buildConflictApproveResponse("conflict status updated");
    }

    @Override
    @Transactional
    public CreateILEPMemberResponse createILEPMember(CreateILEPMemberRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_INSTITUTE_NOT_FOUND);
        }

        if (instituteForm.isRevalidationForm()
                && !Objects.isNull(request.getIsVerificationPanelRequired())
                && request.getIsVerificationPanelRequired() == 0) {
            //for revalidation no panel verification required
            instituteForm.setStatus(ApplicationStatus.REVALIDATION_AM_VALIDATION_TO_BE_SUBMITTED.getCode());
            instituteForm.setIsVerificationPanelRequired(request.getIsVerificationPanelRequired());
            userDao.saveInstitutionDetails(instituteForm);
            return responseBuilder.buildCreateILEPMemberResponse(0L, "panel created successfully");
        }

        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*if (!(instituteForm.getStatus().equals(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.PROCEED_WITH_INCOMPLETE_STATUS.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_CONFLICT.getCode()))) {
            throw new ServiceException(ErrorCode.ILEP_MEMBER_CANNOT_BE_CREATED);
        }*/
        List<IlepPanel> ilepPaneExist = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
        if ((ilepPaneExist.size() + request.getMemberId().size()) > 3) {
            LOGGER.debug("Panel already exist for institution:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_PANEL_EXIST);
        }

        List<User> memberList = userDao.getUserList(request.getMemberId());
        if (memberList.size() != request.getMemberId().size()) {
            LOGGER.debug("User not found");
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_USER_NOT_FOUND);
        }

        if (memberList.stream().noneMatch(user ->
                user.getRoles().stream().anyMatch(
                        role -> role.getUserType()== UserType.ILEP_MEMBER.getCode()
                ) ))  {
            LOGGER.debug("Invalid userId given");
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_INVALID_USER);
        }

        Long panelUniqueId = userUtils.generateUniqueId();
        List<IlepPanel> ilepPanelList = new ArrayList<>();

        for (Long userId : request.getMemberId()) {
            IlepPanel ilepPanel = new IlepPanel();
            ilepPanel.setFormUniqueId(request.getFormUniqueId());
            if (ilepPaneExist.isEmpty()) {
                ilepPanel.setPanelId(panelUniqueId);
            } else {
                ilepPanel.setPanelId(ilepPaneExist.get(0).getPanelId());

            }
            ilepPanel.setIlepMemberId(userId);
            if (request.getMemberHead().equals(userId)) {
                ilepPanel.setIsHead(BooleanEnum.TRUE.getCode());
            } else {
                ilepPanel.setIsHead(BooleanEnum.FALSE.getCode());
            }
            ilepPanel.setIsDfoApproved(BooleanEnum.FALSE.getCode());
            ilepPanel.setPanelStatus(BooleanEnum.FALSE.getCode());
            ilepPaneExist.add(ilepPanel);
        }

        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_CREATED.getCode());
        if (instituteForm.isRevalidationForm()) {
            instituteForm.setIsVerificationPanelRequired(request.getIsVerificationPanelRequired());
        }

        userDao.saveInstitutionDetails(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        evaluationDao.saveILEPPanel(ilepPaneExist);

        LOGGER.info("Before initializing variables for dfo notification mail for approving ilep panel");
        List<User> dfoAdminList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ILEP_PANEL_CREATE");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            Map<String, Object> templateModel = new HashMap<>();
//            if (!CollectionUtils.isEmpty(dfoAdminList)) {
//                for (User user : dfoAdminList) {
            User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
            }
            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
            /*List<String> ccAdresses = new ArrayList<>();*/
            awsService.sendMail(instituteUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
              /*  }
            }*/
        }

        return responseBuilder.buildCreateILEPMemberResponse(ilepPaneExist.get(0).getPanelId(), "panel created successfully");
    }


    @Override
    public DfoApproveIlepPanelConflictResponse approvePanel(DfoApproveIlepPanelConflictRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT_DFO_APPROVE.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        InstituteForm instituteDetails = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (!Objects.isNull(instituteDetails)) {
            LOGGER.info("Before initializing variables for institution notification mail for signing no conflict form");

            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DFOA_APPROVE_ILEP_PANEL");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User instituteUser = userDao.getUserByEmailId(instituteDetails.getContactPersonEmail());
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
//                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{userName}", instituteUser.getUsername());
                    mailBody = mailBody.replace("{instituteName}", instituteDetails.getInstitutionName());
                    mailBody = mailBody.replace("{QualificationTitle}", instituteForm.getQualificationTitle());
                }
                Map<String, Object> templateModel = new HashMap<>();

                templateModel.put("mailBody", mailBody);
                String mailHtmlPath = "mail-template.html";
               /* List<String> ccAdresses = new ArrayList<>();
                ccAdresses.add(amDetails.getEmailId());*/
                awsService.sendMail(instituteUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
            }
         /*   User user = commonUtils.getUserDetails(UserType.INSTITUTION.getCode(), null, instituteDetails.getFormUniqueId());
            List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
            String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_TITLE, NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_MESSAGE, null, tokens);
*/
        }
        return responseBuilder.dfoApproveIlepPanelConflictResponseBuilder("Dfo approved the conflict");
    }

    @Transactional
    @Override
    public GrandAccessResponse amGrandAccessToIlepUser(GrandAccessRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        List<IlepPanel> ilepPanel = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
        IlepPanel ilepPanelMember = ilepPanel.stream().filter(el -> el.getIlepMemberId().equals(request.getUserId())).findFirst().orElse(null);
        if (ilepPanelMember == null) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_NOT_A_PANEL_MEMBER);
        }
       if (request.getGrandAccess().equals(ilepPanelMember.getGrandAccess())) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_ACCESS_GRANTED);
        }
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (conflictForm == null) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_CONFLICT_FORM_NOT_SIGNED);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<IlepConflictForm> ilepConflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
        });
        if (ilepConflictForms.stream().noneMatch(el -> el.getIlepUserId().equals(request.getUserId()) && el.getNoConflictBeingMember().equals(BooleanEnum.TRUE.getCode()))) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_CONFLICT_FORM_NOT_SIGNED);
        }
        int ilepGrandCount = (int) ilepPanel.stream().filter(el -> BooleanEnum.TRUE.getCode().equals(el.getGrandAccess())).count();
        ilepPanelMember.setGrandAccess(request.getGrandAccess());
        switch (ilepGrandCount) {
            case 0 -> {
                if(instituteForm.isRevalidationForm()) {
                    instituteForm.setStatus(ApplicationStatus.SITE_VISIT_DONE.getCode());
                } else {
                    instituteForm.setStatus(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode());
                }
            }
            case 1 -> {
                if(instituteForm.isRevalidationForm()) {
                    instituteForm.setStatus(ApplicationStatus.SITE_VISIT_DONE.getCode());
                } else {
                    instituteForm.setStatus(ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode());
                }
            }
            case 2 -> {
                if(instituteForm.isRevalidationForm()) {
                    instituteForm.setStatus(ApplicationStatus.SITE_VISIT_DONE.getCode());
                } else {
                    instituteForm.setStatus(ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode());
                }
            }
            default -> throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_INVALID_FORM_UNIQUE_ID);
        }
        evaluationDao.save(ilepPanelMember);
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("GRANT_ACCESS");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            List<IlepPanel> ilepListDetails = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
            String mailBody = mailTemplate.getTemplateBody();
            User amUserDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            for (IlepPanel ilep : ilepListDetails) {
                if(ilep.getIlepMemberId().equals(request.getUserId())){

                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                            mailBody = mailBody.replace("{amName}", amUserDetails.getUsername());
                            mailBody = mailBody.replace("{amEmail}", amUserDetails.getEmailId());
                            mailBody = mailBody.replace("{amPhone}", amUserDetails.getContactNumber());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                        }

                        Map<String, Object> templateModel = new HashMap<>();

                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                    /*List<String> ccAdresses = new ArrayList<>();
                    ccAdresses.add(amUser.getEmailId());*/
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }

                }
            }
        }

        return responseBuilder.grandAccessResponseBuilder(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public RevertConflictResponse revertConflict(RevertConflictRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.REVERT_CONFLICT_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ApplicationStatus newStatus;
        if (null != evaluationDao.getByIlepMemberAndFormUniqueIdAndGrandAccess(request.getIlepMemberId(), request.getFormUniqueId(), BooleanEnum.TRUE.getCode())) {
            throw new ServiceException(ErrorCode.REVERT_CONFLICT_ACCESS_GRANTED);
        }
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (null == conflictForm) {
            throw new ServiceException(ErrorCode.REVERT_CONFLICT_CONFLICT_NOT_FOUND);
        }
        ConflictType conflictType = ConflictType.getByCode(request.getType());
        if (null == conflictType) {
            throw new ServiceException(ErrorCode.REVERT_CONFLICT_INVALID_TYPE);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        switch (conflictType) {
            case ILEP_CONFLICT -> {
                if (null == conflictForm.getIlepConflictData()) {
                    throw new ServiceException(ErrorCode.REVERT_CONFLICT_CONFLICT_NOT_FOUND);
                }
                List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                });
                if (!conflictForms.removeIf(el -> el.getIlepUserId().equals(request.getIlepMemberId()))) {
                    throw new ServiceException(ErrorCode.REVERT_CONFLICT_CONFLICT_USER_NOT_FOUND);
                }
                String conflictFormString = objectMapper.writeValueAsString(conflictForms);
                if (conflictForms.isEmpty() || conflictForms.stream().noneMatch(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode()))) {
                    conflictForm.setIlepConflictStatus(BooleanEnum.FALSE.getCode());
                }
                conflictForm.setIlepConflictData(conflictFormString);
                int size = conflictForms.size();
                if (size == 2) {
                    Integer nonConflictBeingMember = conflictForms.get(1).getNoConflictBeingMember();
                    if (nonConflictBeingMember.equals(BooleanEnum.TRUE.getCode())) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode());
                    } else {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT.getCode());
                    }
                } else if (size == 1) {
                    Integer nonConflictBeingMember = conflictForms.get(0).getNoConflictBeingMember();
                    if (nonConflictBeingMember.equals(BooleanEnum.TRUE.getCode())) {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode());
                    } else {
                        instituteForm.setStatus(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode());
                    }
                } else {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode());
                }
                newStatus = ApplicationStatus.ILEP_RESET;
            }
            case INSTITUTION_CONFLICT -> {
                if (null == conflictForm.getInstitutionConflictData()) {
                    throw new ServiceException(ErrorCode.REVERT_CONFLICT_CONFLICT_NOT_FOUND);
                }

                conflictForm.setInstitutionConflictStatus(null);
                conflictForm.setInstitutionConflictData(null);
                conflictForm.setIsConflictApproved(null);

                if (instituteForm.getStatus() >= ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode()) {
                    conflictForm.setIlepConflictData(null);
                    conflictForm.setIlepConflictStatus(null);
                }
                instituteForm.setStatus(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode());
                newStatus = ApplicationStatus.INSTITUTION_RESET;
            }
            default -> throw new ServiceException(ErrorCode.REVERT_CONFLICT_INVALID_TYPE);
        }
        evaluationDao.save(conflictForm);
        formDao.save(instituteForm);
        logService.writeLogToDatabase(userId, currentStatus, newStatus, instituteForm.getFormUniqueId());
        return responseBuilder.revertConflictResponseBuiler(BooleanEnum.TRUE.getCode(), request.getFormUniqueId());
    }

    @Override
    public UploadMeetingReportResponse meetingReportUpload(UploadMeetingReportRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.UPLOAD_MEETING_REPORT_INVALID_FORM_UNIQUE_ID);
        }
        Meeting meeting = evaluationDao.getMeetingByFormUniqueId(request.getFormUniqueId());
        if (null == meeting) {
            throw new ServiceException(ErrorCode.UPLOAD_MEETING_REPORT_MEETING_NOT_CREATED);
        }
        MeetingType meetingType = MeetingType.getByCode(request.getMeetingType());
        if (null == meetingType) {
            throw new ServiceException(ErrorCode.UPLOAD_MEETING_REPORT_INVALID_MEETING_TYPE);
        }
        ApplicationStatus applicationStatus;
        if (meetingType.equals(MeetingType.FIRST_MEETING)) {
          /*  if(meeting.getDeadLine() < System.currentTimeMillis()) { commented on sep 17 23:06
                throw new ServiceException(ErrorCode.UPLOAD_MEETING_REPORT_DEADLINE);
            }*/
            applicationStatus = ApplicationStatus.FIRST_MEETING_REPORT_UPLOADED;
            meeting.setFirstMeetingReportDocumentAndComments(request.getMeetingReportDocumentAndComments());
            meeting.setFirstMeetingQuestionAndComments(request.getMeetingQuestionAndComments());
            meeting.setFirstMeetingEvidenceAndComments(request.getMeetingEvidenceAndComments());
        } else {
            applicationStatus = ApplicationStatus.SECOND_MEETING_REPORT_UPLOADED;
            meeting.setSecondMeetingReportDocumentAndComments(request.getMeetingReportDocumentAndComments());
            meeting.setSecondMeetingQuestionAndComments(request.getMeetingQuestionAndComments());
            meeting.setSecondMeetingEvidenceAndComments(request.getMeetingEvidenceAndComments());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(applicationStatus.getCode())) {
            completedStatus.add(applicationStatus.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));
        if(meetingType.equals(MeetingType.SECOND_MEETING)){
            instituteForm.setStatus(applicationStatus.getCode());
            instituteForm.setSubStatus(applicationStatus.getCode());
        }
        formDao.save(instituteForm);
        meeting = evaluationDao.save(meeting);
        return responseBuilder.uploadMeetingReportResponseBuilder(StatusCode.SUCCESS.getCode(), meeting.getFormUniqueId());
    }
}
