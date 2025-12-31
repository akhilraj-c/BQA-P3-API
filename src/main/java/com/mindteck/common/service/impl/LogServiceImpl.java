package com.mindteck.common.service.impl;

import com.mindteck.common.constants.Enum.ApplicationStatus;
import com.mindteck.common.constants.Enum.UserSubType;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.dao.LogDao;
import com.mindteck.common.models.*;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FormDao formDao;

    Map<Integer, Integer> statusUserType;
    Map<Integer, Integer> statusSubUserType;

    @Autowired
    IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private FireBaseUtils fireBaseUtils;

    LogServiceImpl() {
        statusUserType = new HashMap<>();
        statusUserType.put(ApplicationStatus.INSTITUTION_REGISTERED.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_UPLOAD_DOCUMENT.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_CREATED.getCode(), UserType.DIRECTOR.getCode());
        statusUserType.put(ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT.getCode(), UserType.ILEP_MEMBER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode(), UserType.ILEP_MEMBER.getCode());
        statusUserType.put(ApplicationStatus.SITE_VISIT_REQUIRED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.SITE_VISIT_DONE.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_APPROVE_THE_EVALUATION.getCode(), UserType.CHIEF.getCode());
        statusUserType.put(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.DOC_SHARED_TO_QAC_BY_DIRECTOR.getCode(), UserType.DIRECTOR.getCode());
        statusUserType.put(ApplicationStatus.QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.DOC_SHARED_TO_NAC.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.NAC_DOC_APPROVED_BY_DFO_ADMIN.getCode(), UserType.MCO.getCode());statusUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.MCO_UPLOADED_EDITED_DOC.getCode(), UserType.CHIEF.getCode());
        statusUserType.put(ApplicationStatus.DFO_CHIEF_SHARED_TO_CABINET.getCode(), UserType.DFO_ADMIN.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_NOT_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode(), UserType.INSTITUTION.getCode());statusUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserType.APPLICATION_MANAGER.getCode());
        statusUserType.put(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.INSTITUTION_INITIATE_AN_APPEAL_5_DAYS.getCode(), UserType.DIRECTOR.getCode());
        statusUserType.put(ApplicationStatus.DFO_DIRECTOR_APPROVE_THE_APPEAL.getCode(), UserType.INSTITUTION.getCode());
        statusUserType.put(ApplicationStatus.MCO_RE_UPLOAD_THE_EDITED_REPORT.getCode(), UserType.DFO_ADMIN.getCode());


        statusSubUserType = new HashMap<>();
        statusSubUserType.put(ApplicationStatus.INSTITUTION_UPLOAD_DOCUMENT.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.DOC_SHARED_TO_NAC.getCode(), UserSubType.ADMIN.getCode());
        statusSubUserType.put(ApplicationStatus.MCO_RE_UPLOAD_THE_EDITED_REPORT.getCode(), UserSubType.ADMIN.getCode());

        statusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode(), UserSubType.MANAGER.getCode());
        statusSubUserType.put(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode(), UserSubType.MANAGER.getCode());

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

    Map<Integer, Integer> specificDueDateStatusUserType;
    Map<Integer, Integer> specificDueDateStatusSubUserType;

    Map<Integer, Integer> calenderNextActionDueDateStatusUserType;
    Map<Integer, Integer> calenderNextActionDueDateStatusSubUserType;

    Map<Integer, Integer> otherDueDateStatusUserType;
    Map<Integer, Integer> otherDueDateStatusSubUserType;

    Map<Integer, Integer> specificDateNextActionDueDateStatusUserType;
    Map<Integer, Integer> specificDateNextActionDueDateStatusSubUserType;

    @Override
    public void writeLogToDatabase(Long currentUserId, ApplicationStatus currentStatus, ApplicationStatus newStatus, Long formUniqueId) {
      try{
          Log log = new Log();
          User currentUser = userDao.getByUserId(currentUserId);
          if (null != currentUser) {
              log.setUserName(currentUser.getUsername());
              log.setUserType(WebUtils.getUserType());
              log.setUserSubType(WebUtils.getSubType());
          }
          log.setFormUniqueId(formUniqueId);
          log.setPreviousStatus(currentStatus.getCode());
          log.setPreviousMessage(currentStatus.getName());
          log.setChangedStatus(newStatus.getCode());
          log.setChangedMessage(newStatus.getName());
          log.setUserId(currentUserId);
          logDao.save(log);

          /**
           * Micahel updaetd 09-10-23
           * */

          Long dueDate = null;
          InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
          Integer applicationStatus = newStatus.getCode();
          Integer userType = null;
          Integer subUserType = null;
          User user = null;

          if (specificDueDateStatusUserType.containsKey(applicationStatus)) {
              dueDate = getSpecificDueDate(instituteForm);
              userType = specificDueDateStatusUserType.get(instituteForm.getStatus());
              if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                  subUserType = specificDateNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                  user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
              } else {
                  user = commonUtils.getUserDetails(userType, null, formUniqueId);
              }
          } else if (otherDueDateStatusUserType.containsKey(applicationStatus)) {
              dueDate = otherActionDueDate(instituteForm);
              userType = otherDueDateStatusUserType.get(instituteForm.getStatus());
              if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                  subUserType = otherDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                  user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
              } else {
                  user = commonUtils.getUserDetails(userType, null, formUniqueId);
              }
          } else if (calenderNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
              dueDate = getCalenderDateNextActionDueDate(instituteForm);
              userType = calenderNextActionDueDateStatusUserType.get(instituteForm.getStatus());
              if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                  subUserType = calenderNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                  user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
              } else {
                  user = commonUtils.getUserDetails(userType, null, formUniqueId);
              }
          } else if (specificDateNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
              dueDate = getSpecificDateNextActionDueDate(instituteForm);
              userType = specificDateNextActionDueDateStatusUserType.get(instituteForm.getStatus());
              if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                  subUserType = specificDateNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                  user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
              } else {
                  user = commonUtils.getUserDetails(userType, null, formUniqueId);
              }
          } else {
              dueDate = System.currentTimeMillis() + (TimeUnit.DAYS.toMillis(3));
          }

//        DueDates dueDates = userDao.findByAction(newStatus.getCode());
          if(null != dueDate) {
            /*Long dueDate = Instant.now().plus(dueDates.getNoOfDays(), ChronoUnit.DAYS).toEpochMilli();
            InstituteForm instituteForm =  formDao.getInstitutionFormById(formUniqueId);*/
              instituteForm.setCurrentStatusDueDate(dueDate);
              formDao.save(instituteForm);

          }


          if(null != user) {
              List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
              String notificationResponse = fireBaseUtils.sendNotification("BQA ", newStatus.getName(), null, tokens);
              LOGGER.debug("Response form Firebase :: " + notificationResponse);
          }

      } catch (Exception ex) {
       LOGGER.error(ex.getMessage());
      }
    }

 /*
    @Override
    public void writeLogToQPDatabase(Long currentUserId, ApplicationStatus currentStatus, ApplicationStatus newStatus, Long formUniqueId) {
        try{
            Log log = new Log();
            User currentUser = userDao.getByUserId(currentUserId);
            if (null != currentUser) {
                log.setUserName(currentUser.getUsername());
                log.setUserType(currentUser.getUserType());
                log.setUserSubType(currentUser.getSubType());
            }
            log.setFormUniqueId(formUniqueId);
            log.setPreviousStatus(currentStatus.getCode());
            log.setPreviousMessage(currentStatus.getName());
            log.setChangedStatus(newStatus.getCode());
            log.setChangedMessage(newStatus.getName());
            log.setUserId(currentUserId);
            logDao.save(log);

            *//**
             * Micahel updaetd 09-10-23
             * *//*

            Long dueDate = null;
            QPInstituteForm instituteForm = formDao.getQPInstitutionFormById(formUniqueId);
            Integer applicationStatus = newStatus.getCode();
            Integer userType = null;
            Integer subUserType = null;
            User user = null;

            if (specificDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getSpecificDueDate(instituteForm);
                userType = specificDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                    subUserType = specificDateNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
                } else {
                    user = commonUtils.getUserDetails(userType, null, formUniqueId);
                }
            } else if (otherDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = otherActionDueDate(instituteForm);
                userType = otherDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                    subUserType = otherDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
                } else {
                    user = commonUtils.getUserDetails(userType, null, formUniqueId);
                }
            } else if (calenderNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getCalenderDateNextActionDueDate(instituteForm);
                userType = calenderNextActionDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                    subUserType = calenderNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
                } else {
                    user = commonUtils.getUserDetails(userType, null, formUniqueId);
                }
            } else if (specificDateNextActionDueDateStatusUserType.containsKey(applicationStatus)) {
                dueDate = getSpecificDateNextActionDueDate(instituteForm);
                userType = specificDateNextActionDueDateStatusUserType.get(instituteForm.getStatus());
                if (userType.equals(UserType.DFO_ADMIN.getCode())) {
                    subUserType = specificDateNextActionDueDateStatusSubUserType.get(instituteForm.getSubStatus());
                    user = commonUtils.getUserDetails(userType, subUserType, formUniqueId);
                } else {
                    user = commonUtils.getUserDetails(userType, null, formUniqueId);
                }
            } else {
                dueDate = System.currentTimeMillis() + (TimeUnit.DAYS.toMillis(3));
            }

//        DueDates dueDates = userDao.findByAction(newStatus.getCode());
            if(null != dueDate) {
            *//*Long dueDate = Instant.now().plus(dueDates.getNoOfDays(), ChronoUnit.DAYS).toEpochMilli();
            InstituteForm instituteForm =  formDao.getInstitutionFormById(formUniqueId);*//*
                instituteForm.setCurrentStatusDueDate(dueDate);
                formDao.save(instituteForm);

            }


            if(null != user) {
                List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                String notificationResponse = fireBaseUtils.sendNotification("BQA ", newStatus.getName(), null, tokens);
                LOGGER.debug("Response form Firebase :: " + notificationResponse);
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
    */
    private Long getSpecificDueDate(InstituteForm instituteForm) {
        Long dueDate = null;
        if (instituteForm.getStatus().equals(ApplicationStatus.DFO_ADMIN_APPROVED.getCode())){
            dueDate = Long.valueOf(instituteForm.getPlannedSubDate());
        } else if (instituteForm.getStatus().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
            FormApplicationManager applicationForm = userDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = applicationForm.getAdditionalDataSubDate();
        }
        else if (instituteForm.getStatus().equals(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode())) {
            ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(instituteForm.getFormUniqueId());
            dueDate = evaluationForm.getPartialMetDate();
        } else if (instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode())) {
            dueDate = instituteForm.getLastUpdatedTime();
        }
        return dueDate;
    }



    private Long getSpecificDateNextActionDueDate(InstituteForm instituteForm) {
        DueDates dueDates = userDao.findByAction(instituteForm.getStatus());
        long turnAroundTime = 3;
        if (!Objects.isNull(dueDates)) {
            turnAroundTime = dueDates.getNoOfDays();
        }
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
        DueDates dueDates = userDao.findByAction(instituteForm.getStatus());
        long turnAroundTime = 3;
        if (!Objects.isNull(dueDates)) {
            turnAroundTime = dueDates.getNoOfDays();
        }
        Long dueDate = Long.parseLong(instituteForm.getPlannedSubDate())+ TimeUnit.DAYS.toMillis(turnAroundTime);
        LOGGER.debug("duedTe is :{}", dueDate);
        return dueDate;
    }

    private Long otherActionDueDate(InstituteForm instituteForm) {
        /**
         * stuas-> ASSIGNED_APPLICATION_MANAGER,ADMINISTRATIVE_CHECk_COMPLETED, ILEP_PANEL_ASSIGNED
         * */
        DueDates dueDates = userDao.findByAction(instituteForm.getStatus());
        long turnAroundTime = 3;
        if (!Objects.isNull(dueDates)) {
            turnAroundTime = dueDates.getNoOfDays();
        }
        Long dueDate = instituteForm.getLastUpdatedTime()+ TimeUnit.DAYS.toMillis(turnAroundTime);
        if (instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode()) || instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode())
                || instituteForm.getStatus().equals(ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode())) {
            dueDate =instituteForm.getLastUpdatedTime()+ TimeUnit.DAYS.toMillis(2);
        }
        LOGGER.debug("duedTe is :{}", dueDate);
        return dueDate;
    }
}
