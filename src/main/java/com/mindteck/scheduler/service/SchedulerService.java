package com.mindteck.scheduler.service;

import com.mindteck.common.constants.Enum.ApplicationStatus;
import com.mindteck.common.constants.Enum.BooleanEnum;
import com.mindteck.common.constants.Enum.UserSubType;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.models.*;
import com.mindteck.common.repository.DueDatesRepository;
import com.mindteck.common.repository.IlepPanelRepository;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import com.mindteck.repository_cas.UserRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j(topic = "scheduler")
public class SchedulerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    @Autowired
    IlepPanelRepository ilepPanelRepository;

    @Autowired
    AwsService awsService;

    @Autowired
    DueDatesRepository dueDatesRepository;

    Map<Integer, Integer> statusUserType;
    Map<Integer, Integer> statusSubUserType;

    Map<Integer, Integer> specificDueDateStatusUserType;
    Map<Integer, Integer> specificDueDateStatusSubUserType;

    Map<Integer, Integer> calenderNextActionDueDateStatusUserType;
    Map<Integer, Integer> calenderNextActionDueDateStatusSubUserType;

    Map<Integer, Integer> otherDueDateStatusUserType;
    Map<Integer, Integer> otherDueDateStatusSubUserType;

    Map<Integer, Integer> specificDateNextActionDueDateStatusUserType;
    Map<Integer, Integer> specificDateNextActionDueDateStatusSubUserType;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private EvaluationDao evaluationDao;

    @Autowired
    private IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private FireBaseUtils fireBaseUtils;

    SchedulerService() {
        statusUserType = new HashMap<>();
        statusUserType.put(ApplicationStatus.INSTITUTION_REGISTERED.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_SUBMITTED.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_CREATED.getCode(), UserType.DIRECTOR.getCode());
        statusUserType.put(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode(), UserType.ILEP_MEMBER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_ASSIGNED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.FIRST_MEETING_CREATED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_APPROVE_THE_EVALUATION.getCode(), UserType.CHIEF.getCode());
        statusUserType.put(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_NOT_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_INITIATE_AN_APPEAL_5_DAYS.getCode(), UserType.DIRECTOR.getCode());
        statusUserType.put(ApplicationStatus.DOC_SHARED_TO_NAC.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.NAC_DOC_APPROVED_BY_DFO_ADMIN.getCode(), UserType.MCO.getCode());
        statusUserType.put(ApplicationStatus.MCU_DOC_GENERATED.getCode(), UserType.MCO.getCode());
        statusUserType.put(ApplicationStatus.MCO_UPLOADED_EDITED_DOC.getCode(), UserType.CHIEF.getCode());
        statusUserType.put(ApplicationStatus.DFO_CHIEF_SIGNED_OFF_AND_UPLOAD_THE_REPORT.getCode(), UserType.CHIEF.getCode());
        statusUserType.put(ApplicationStatus.DFO_CHIEF_SHARED_TO_CABINET.getCode(), UserType.DFO_ADMIN.getCode());


        statusSubUserType = new HashMap<>();
        statusSubUserType.put(ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.FIRST_MEETING_DOC_UPLOADED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.FIRST_MEETING_CREATED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_PANEL_ASSIGNED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.DFO_CHIEF_SHARED_TO_CABINET.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.DOC_SHARED_TO_NAC.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.INSTITUTION_SUBMITTED.getCode(), UserSubType.ADMIN.getCode());

        /**
         * Michael 6-10-23  due date new logics
         * */
        specificDueDateStatusUserType = new HashMap<>();
        specificDueDateStatusUserType.put(ApplicationStatus.DFO_ADMIN_APPROVED.getCode(),UserType.INSTITUTION.getCode());
        specificDueDateStatusUserType.put(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode(), UserType.INSTITUTION.getCode());
        specificDueDateStatusUserType.put(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode(), UserType.INSTITUTION.getCode());
        specificDueDateStatusUserType.put(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode(), UserType.INSTITUTION.getCode());

        specificDueDateStatusSubUserType = new HashMap<>();
        specificDueDateStatusSubUserType.put(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode(), UserSubType.MANAGER.getCode());


        otherDueDateStatusUserType = new HashMap<>();
        otherDueDateStatusUserType.put(ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode(),UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode(),UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode(),UserType.INSTITUTION.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode(),UserType.ILEP_MEMBER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode(), UserType.ILEP_MEMBER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ACCESS_GRANT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.FIRST_MEETING_CREATED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.SECOND_MEETING_CREATED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.SECOND_MEETING_REPORT_UPLOADED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_EVALUATE_SUBMISSION_WITH_COMMENT.getCode(), UserType.ILEP_MEMBER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_APPROVE_THE_EVALUATION.getCode(), UserType.CHIEF.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.DFO_CHIEF_ADD_EVALUATION.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode(), UserType.GDQ.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.DOC_SHARED_TO_NAC.getCode(), UserType.CHIEF.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.NAC_DOC_APPROVED_BY_DFO_ADMIN.getCode(), UserType.CHIEF.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode(), UserType.DFO_ADMIN.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED.getCode(), UserType.DFO_ADMIN.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        otherDueDateStatusUserType.put(ApplicationStatus.FIRST_MEETING_REPORT_UPLOADED.getCode(), UserType.APPLICATION_MANAGER.getCode());

        otherDueDateStatusSubUserType = new HashMap<>();
        otherDueDateStatusSubUserType.put(ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ACCESS_GRANT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.FIRST_MEETING_CREATED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.SECOND_MEETING_CREATED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.SECOND_MEETING_REPORT_UPLOADED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.DFO_CHIEF_ADD_EVALUATION.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        otherDueDateStatusSubUserType.put(ApplicationStatus.FIRST_MEETING_REPORT_UPLOADED.getCode(), UserSubType.MANAGER.getCode());

        calenderNextActionDueDateStatusUserType = new HashMap<>();
        calenderNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_SUBMITTED.getCode(), UserType.DFO_ADMIN.getCode());

        calenderNextActionDueDateStatusSubUserType = new HashMap<>();

        specificDateNextActionDueDateStatusUserType = new HashMap<>();
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode(), UserType.APPLICATION_MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.SITE_VISIT_REQUIRED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode(), UserType.INSTITUTION.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.AM_UPLOAD_AGENDA_FORM.getCode(), UserType.APPLICATION_MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.SITE_VISIT_DONE.getCode(), UserType.ILEP_MEMBER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode(), UserType.ILEP_MEMBER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode(), UserType.APPLICATION_MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode(), UserType.ILEP_MEMBER.getCode());

        specificDateNextActionDueDateStatusUserType = new HashMap<>();
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode(), UserSubType.MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.SITE_VISIT_REQUIRED.getCode(), UserSubType.MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.AM_UPLOAD_AGENDA_FORM.getCode(), UserSubType.MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode(), UserSubType.MANAGER.getCode());
        specificDateNextActionDueDateStatusUserType.put(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode(), UserSubType.MANAGER.getCode());
    }

    public User getUserDetails(Integer userType, Integer userSubType, Long formUniqueId) {
        User user = null;
        switch (userType) {
            case 1 -> {
                LOGGER.info("INSTITUTION");
                LOGGER.info("send remainder to the institution");
                InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
                String email = instituteForm.getContactPersonEmail();
                user = userRepository.getByEmailId(email);
                //TODO get institution user details
            }
            case 3 -> {
                LOGGER.info("DFO_ADMIN, APPLICATION_MANAGER, DFO_MEMBER");
                if (UserSubType.MANAGER.getCode().equals(userSubType)) {
                    InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
                    Long applicationManagerUserId = instituteForm.getAssignedAppManager();
                    user = userRepository.getByUserId(applicationManagerUserId);
                } else if (UserSubType.ADMIN.getCode().equals(userSubType)) {
                    List<User> users = userRepository.getByUserTypeAndSubType(userType, userSubType);
                    if (!users.isEmpty()) {
                        user = users.get(0);
                    }
                }
            }
            case 7 -> {
                LOGGER.info("ILEP_MEMBER");
                try {
                    IlepPanel ilepPanel = ilepPanelRepository.getByFormUniqueIdAndIsHead(formUniqueId, 1);
                    Long ilepPanelHeadUserId = ilepPanel.getIlepMemberId();
                    user = userRepository.getByUserId(ilepPanelHeadUserId);
                } catch (Exception e) {
                    break;
                }
            }
            default -> {
                LOGGER.info("other users");
                List<User> users = userRepository.getByUserType(userType);
                if (!users.isEmpty()) {
                    user = users.get(0);
                }
            }
        }
        return user;

    }

    public void sendMail(String mailId, Map<String, Object> templateModel, String mailHtmlPath, List<String> ccAdresses, String subject) {
        awsService.sendMail(mailId, templateModel, mailHtmlPath, ccAdresses, subject);
    }

    public Integer getDueDatesForAction(Integer applicationStatus) {
        DueDates dueDates = dueDatesRepository.findByAction(applicationStatus);
        if (Objects.isNull(dueDates)) {
            LOGGER.info("If the action has no specific due date -> Then minimum due date is 3");
            return 3;
        }
        return dueDates.getNoOfDays();
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendDueDateRemainder() {
        LOGGER.info("Running on every seconds");
        List<InstituteForm> instituteForms = institutionFormRepository.findAll();
        List<InstituteForm> instituteForms1 = new ArrayList<>();
        for (InstituteForm instituteForm : instituteForms) {
            Integer applicationStatus = instituteForm.getStatus();
            Integer userType = null;
            Integer subUserType = null;
            Long dueDate = null;
            User user = null;

            if (specificDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getSpecificDueDate(instituteForm);
                userType = specificDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.INSTITUTION.getCode())) {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                } else if (userType.equals(3)) {
                    subUserType = specificDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = getUserDetails(userType, subUserType, instituteForm.getFormUniqueId());
                } else {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                }
            } else if (otherDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = otherActionDueDate(instituteForm);
                userType = otherDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.INSTITUTION.getCode())) {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                } else if (userType.equals(3)) {
                    subUserType = otherDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = getUserDetails(userType, subUserType, instituteForm.getFormUniqueId());
                } else {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                }
            } else if (calenderNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getCalenderDateNextActionDueDate(instituteForm);
                userType = calenderNextActionDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.INSTITUTION.getCode())) {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                } else if (userType.equals(3)) {
                    subUserType = calenderNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = getUserDetails(userType, subUserType, instituteForm.getFormUniqueId());
                } else {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                }
            } else if (specificDateNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getSpecificDateNextActionDueDate(instituteForm);
                userType = specificDateNextActionDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.INSTITUTION.getCode())) {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                } else if (userType.equals(3)) {
                    subUserType = specificDateNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = getUserDetails(userType, subUserType, instituteForm.getFormUniqueId());
                } else {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                }
            } else {
                continue;
            }

            if (user == null) {
                LOGGER.info("userId is null");
                continue;
            }

            if (System.currentTimeMillis() >= (dueDate - + TimeUnit.DAYS.toMillis(1))) {
                LOGGER.info("Send warning to the institution since the due date is near");
                if (instituteForm.getRemainderMailSent().equals(BooleanEnum.FALSE.getCode())) {
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DUE_DATE_WARNING");
                    if (Objects.nonNull(mailTemplate)) {
                        List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                        String mailBody = mailTemplate.getTemplateBody();

                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                            mailBody = mailBody.replace("{userName}", user.getUsername());
                        }
                        Map<String, Object> templateModel = new HashMap<>();
                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        instituteForm.setRemainderMailSent(BooleanEnum.TRUE.getCode());
                        institutionFormRepository.save(instituteForm);
                    }
                    List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                    String notificationResponse = fireBaseUtils.sendNotification("BQA ", "Remainder to take action against formUniqueId : " + instituteForm.getFormUniqueId(), null, tokens);
                    LOGGER.info("Response form Firebase :: " + notificationResponse);
                }
            } else if(System.currentTimeMillis() >= dueDate){
                if (instituteForm.getTerminatedMailSent().equals(BooleanEnum.FALSE.getCode())) {
                    if (userType.equals(UserType.INSTITUTION.getCode())) {
                        LOGGER.info("terminate");
                        instituteForm.setStatus(-applicationStatus);
                        instituteForm.setRejectionReason("Over due");
                        instituteForm.setTerminatedMailSent(BooleanEnum.TRUE.getCode());
                        instituteForms1.add(instituteForm);
                        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("APPLICATION_TERMINATED");
                        if (Objects.nonNull(mailTemplate)) {
                            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                            String mailBody = mailTemplate.getTemplateBody();
                            if (mailBody != null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                                mailBody = mailBody.replace("{userName}", user.getUsername());
                            }
                            Map<String, Object> templateModel = new HashMap<>();
                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
                            sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        }
                        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                        String notificationResponse = fireBaseUtils.sendNotification("BQA ", "FormId  : " + instituteForm.getFormUniqueId() + " is terminated", null, tokens);
                        LOGGER.info("Response form Firebase :: " + notificationResponse);
                    }
                }
            }

        }
        terminateInstitution(instituteForms1);
    }

    // @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDueRemainder() {
        // TODO set the correct email template

        LOGGER.info("Send due date remainder if Action not taken properly");
        List<InstituteForm> instituteForms = institutionFormRepository.findAll();
        List<InstituteForm> instituteForms1 = new ArrayList<>();
        for (InstituteForm instituteForm : instituteForms) {

            if (statusUserType.containsKey(instituteForm.getStatus())) {
                Integer applicationStatus = instituteForm.getStatus();
                Long lastUpdated = instituteForm.getLastUpdatedTime();
                Integer userType = statusUserType.get(instituteForm.getStatus());
                Integer subUserType = null;
                if (userType.equals(3)) {
                    subUserType = statusSubUserType.get(instituteForm.getSubStatus());
                }
                User user = null;

                Instant instant1 = Instant.ofEpochSecond(lastUpdated);
                Instant instant2 = Instant.ofEpochSecond(System.currentTimeMillis());
                //  long daysSinceLastAction = ChronoUnit.DAYS.between(instant1, instant2);
                long dueDate = getDueDatesForAction(instituteForm.getStatus());

                long daysSinceLastAction = (System.currentTimeMillis() - lastUpdated) / (1000 * 60 * 60 * 24);
                LOGGER.info("Application Id :: " + instituteForm.getFormUniqueId()
                        + "\n" + "Status :: " + instituteForm.getStatus()
                        + "\n" + "daysSinceLastAction :: " + daysSinceLastAction
                        + "\n" + "dueDate :: " + dueDate
                        + "\n" + "userType :: " + userType
                        + "\n" + "subUserType :: " + subUserType);


                if (userType.equals(UserType.INSTITUTION.getCode())) {
                    user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                    if (daysSinceLastAction == dueDate - 1) {
                        LOGGER.info("Send warning to the institution since the due date is near");

                        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DUE_DATE_WARNING");
                        if (Objects.nonNull(mailTemplate)) {
                            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                            String mailBody = mailTemplate.getTemplateBody();

                            if (mailBody !=null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                                mailBody = mailBody.replace("{userName}", user.getUsername());
                            }
                            Map<String, Object> templateModel = new HashMap<>();
                            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
                           /* List<String> ccAdresses = new ArrayList<>();
                            ccAdresses.add(amDetails.getEmailId());*/
                            sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        }
                        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                        String notificationResponse = fireBaseUtils.sendNotification("BQA ", "Remainder to take action against formUniqueId : " + instituteForm.getFormUniqueId(), null, tokens);
                        LOGGER.info("Response form Firebase :: " + notificationResponse);
                    } else if (daysSinceLastAction > dueDate) {
                        LOGGER.info("terminate");
                        // WebUtils.setClientIpAddress(instituteForm.get);
                        instituteForm.setStatus(-applicationStatus);
                        instituteForm.setRejectionReason("Over due");
                        instituteForms1.add(instituteForm);
                        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("APPLICATION_TERMINATED");
                        if (Objects.nonNull(mailTemplate)) {
                            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                            String mailBody = mailTemplate.getTemplateBody();
                            if (mailBody !=null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                                mailBody = mailBody.replace("{userName}", user.getUsername());
                            }
                            Map<String, Object> templateModel = new HashMap<>();
                            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
                          /*  List<String> ccAdresses = new ArrayList<>();
                            ccAdresses.add(amDetails.getEmailId());*/
                            sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        }
                        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                        String notificationResponse = fireBaseUtils.sendNotification("BQA ", "FormId  : " + instituteForm.getFormUniqueId() + " is terminated", null, tokens);
                        LOGGER.info("Response form Firebase :: " + notificationResponse);
                    }
                } else {
                    LOGGER.info("Users other than Institution");
                    if (dueDate < daysSinceLastAction) {
                        LOGGER.info("Send due date expiry warning to the In-charge");
                        if (userType.equals(3)) {
                            user = getUserDetails(userType, subUserType, instituteForm.getFormUniqueId());
                        } else {
                            user = getUserDetails(userType, null, instituteForm.getFormUniqueId());
                        }
                        if (user == null) {
                            LOGGER.info("userId is null");
                            continue;
                        }

                        LOGGER.info("Send email as warning since the due date is over for taking the action");
                        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DUE_DATE_WARNING");
                        if (Objects.nonNull(mailTemplate)) {
                            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                            String mailBody = mailTemplate.getTemplateBody();
                            if (mailBody !=null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                                mailBody = mailBody.replace("{userName}", user.getUsername());
                            }
                            Map<String, Object> templateModel = new HashMap<>();
                            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
                            /*List<String> ccAdresses = new ArrayList<>();
                            ccAdresses.add(amDetails.getEmailId());*/
                            sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        }

                        //User user = commonUtils.getUserDetails(UserType.APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode(), instituteForm.getFormUniqueId());
                        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                        String notificationResponse = fireBaseUtils.sendNotification("BQA ", "Need to take action against the formId : " + instituteForm.getFormUniqueId(), null, tokens);
                        LOGGER.info("Response form Firebase :: " + notificationResponse);
                    }
                }
            }

            /*

            if (System.currentTimeMillis() <= dueDate1) {
                //warning logic
            } else if (System.currentTimeMillis() >= dueDate1) {
                //terminate logic
            }*/

        }
        terminateInstitution(instituteForms1);

    }

    @Transactional
    public void updateTerminateInstitution(Long formUniqueId, Integer applicationStatus) {
        try {
            InstituteForm instituteForm = institutionFormRepository.getByFormUniqueId(formUniqueId);
            applicationStatus = -applicationStatus;
            instituteForm.setRejectionReason("Over due");
            instituteForm.setStatus(applicationStatus);
            institutionFormRepository.save(instituteForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminateInstitution(List<InstituteForm> instituteForms) {
        institutionFormRepository.saveAll(instituteForms);
    }

    private Long getSpecificDueDate(InstituteForm instituteForm) {
        Long dueDate = null;
        if (instituteForm.getStatus().equals(ApplicationStatus.DFO_ADMIN_APPROVED.getCode())){
            dueDate = Long.valueOf(instituteForm.getPlannedSubDate());
        } else if (instituteForm.getStatus().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
            FormApplicationManager applicationForm = userDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = applicationForm.getAdditionalDataSubDate();
        }
        /*else if (instituteForm.getStatus().equals(ApplicationStatus.FIRST_MEETING_CREATED.getCode())) {
            Meeting meeting = evaluationDao.getMeetingByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = meeting.getFirstDateAndTime();
        }
        else if (instituteForm.getStatus().equals(ApplicationStatus.SECOND_MEETING_CREATED.getCode())) {
            Meeting meeting = evaluationDao.getMeetingByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = meeting.getSecondDateAndTime();
        } */
        else if (instituteForm.getStatus().equals(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode())) {
            ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = evaluationForm.getPartialMetDate();
        } else if (instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode())) {
            dueDate = instituteForm.getLastUpdatedTime();
        }
        return dueDate;
    }


    private Long getSpecificDateNextActionDueDate(InstituteForm instituteForm) {
        long turnAroundTime = getDueDatesForAction(instituteForm.getStatus());
        Long specificDate = null;
        Long dueDate = null;
        if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode())) {
            FormApplicationManager applicationForm = userDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = applicationForm.getAdditionalDataSubDate();
            dueDate = specificDate+ TimeUnit.DAYS.toMillis(turnAroundTime);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_REQUIRED.getCode())){
            SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = siteVisit.getVisitDate();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(7);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode())){
            SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = siteVisit.getVisitDate();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(3);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.AM_UPLOAD_AGENDA_FORM.getCode())){
            SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = siteVisit.getVisitDate();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(3);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_DONE.getCode())){
            SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = siteVisit.getVisitDate();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(turnAroundTime);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode())){
            ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            specificDate = evaluationForm.getPartialMetDate();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(turnAroundTime);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode())){
            specificDate = instituteForm.getLastUpdatedTime();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(1);
        } else if (instituteForm.getStatus().equals(ApplicationStatus.AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode())){
            specificDate = instituteForm.getLastUpdatedTime();
            dueDate = specificDate - TimeUnit.DAYS.toMillis(1);
        }
        LOGGER.debug("duedTe is :{}", dueDate);
        return dueDate;
    }


    private Long getCalenderDateNextActionDueDate(InstituteForm instituteForm) {
        long turnAroundTime = getDueDatesForAction(instituteForm.getStatus());
        Long dueDate = Long.parseLong(instituteForm.getPlannedSubDate())+ TimeUnit.DAYS.toMillis(turnAroundTime);
        LOGGER.debug("duedTe is :{}", dueDate);
        return dueDate;
    }

    private Long otherActionDueDate(InstituteForm instituteForm) {
        /**
         * stuas-> ASSIGNED_APPLICATION_MANAGER,ADMINISTRATIVE_CHECk_COMPLETED, ILEP_PANEL_ASSIGNED
         * */
        long turnAroundTime = getDueDatesForAction(instituteForm.getStatus());
        Long dueDate = instituteForm.getLastUpdatedTime()+ TimeUnit.DAYS.toMillis(turnAroundTime);
        if (instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode()) || instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode())) {
            dueDate =instituteForm.getLastUpdatedTime()+ TimeUnit.DAYS.toMillis(2);
        }
        LOGGER.debug("duedTe is :{}", dueDate);
        return dueDate;
    }
}
