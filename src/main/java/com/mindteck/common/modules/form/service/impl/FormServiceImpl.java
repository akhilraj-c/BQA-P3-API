package com.mindteck.common.modules.form.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.dao.LogDao;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.*;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponse;
import com.mindteck.common.modules.evaluation.rest.GetFQDetailsResponseModel;
import com.mindteck.common.modules.form.builder.FormResponseBuilder;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.form.rest.*;
import com.mindteck.common.modules.form.service.FormService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.repository.QualificationProfileApplicationManagerRepository;
import com.mindteck.common.repository.StaticQualificationRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.feedback.dao.FeedbackDao;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import com.mindteck.models_cas.Role;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import com.mindteck.repository_cas.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class FormServiceImpl implements FormService {
    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    @Autowired
    private QualificationProfileApplicationManagerRepository qpApplicationManagerRepository;

    @Value("${mindteck.submissionDate}")
    private Long defaultSubmissonDate;
    @Value("${mindteck.institution.defaultPassword}")
    private String defaultInstitutionPassword;

    @Autowired
    private FormResponseBuilder formResponseBuilder;
    @Autowired
    private FormDao formDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogService logService;

    @Autowired
    private IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    private AwsService awsService;
    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private LogDao logDao;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private FireBaseUtils fireBaseUtils;

    @Autowired
    private IlepAssignDao ilepAssignDao;

    @Autowired
    private EvaluationDao evaluationDao;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StaticQualificationRepository staticQualificationRepository;

    public FormServiceImpl(SpringTemplateEngine thymeleafTemplateEngine) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }


    public BQAUpdateResponse updateRegistrationStatus(BQAUpdateRequest bqaUpdateRequest) {
        Long userId = WebUtils.getUserId();
        Integer code = WebUtils.getUserType();
        User adminUser = userDao.getByUserId(WebUtils.getUserId());
        UserType userType = UserType.getByCode(code, WebUtils.getSubType());
        ApplicationStatus applicationStatus = ApplicationStatus.getByCode(bqaUpdateRequest.getRegistrationStatus());
        if (null == adminUser.getRoles() || !userType.equals(UserType.DFO_ADMIN)) {
            throw new ServiceException(ErrorCode.BQA_UPDATE_ACTION_NOT_PERMITTED);
        }
        if (bqaUpdateRequest.getRegistrationStatus() > ApplicationStatus.DFO_ADMIN_APPROVED.getCode()) {
            LOGGER.info("Operation not permitted");
            LOGGER.info("userId =====> {}", userId);
            LOGGER.info("Action tried to perform ====> {}", null == applicationStatus ? "Undefined actions" : applicationStatus.getName());
            throw new ServiceException(ErrorCode.BQA_UPDATE_ACTION_NOT_PERMITTED);
        }
        int registrationStatus = bqaUpdateRequest.getRegistrationStatus();
        for (RegistrationStatusData registrationStatusData : bqaUpdateRequest.getRegistrationStatusDataList()) {
            InstituteForm instituteForm = formDao.getInstitutionFormById(registrationStatusData.getFormUniqueId());
            if (null == instituteForm) {
                LOGGER.debug("Institution not found , UniqueId : {}", registrationStatusData.getFormUniqueId());
                throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
            }
            ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
            if (instituteForm.getStatus() < ApplicationStatus.INSTITUTION_REGISTERED.getCode()) {
                throw new ServiceException(ErrorCode.BQA_UPDATE_NOT_REGISTERED);
            }
            if (instituteForm.getStatus() == ApplicationStatus.DFO_ADMIN_APPROVED.getCode()) {
                LOGGER.debug("Institution is already approved , UniqueId : {}", registrationStatusData.getFormUniqueId());
                throw new ServiceException(ErrorCode.BQA_UPDATE_ALREADY_APPROVED);
            }
            if (instituteForm.getStatus() == ApplicationStatus.DFO_ADMIN_REJECTED.getCode()) {
                LOGGER.debug("Institution is already rejected , UniqueId : {}", registrationStatusData.getFormUniqueId());
                throw new ServiceException(ErrorCode.BQA_UPDATE_ALREADY_REJECTED);
            }
           /* if(instituteForm.getStatus() == ApplicationStatus.BQA_ADMIN_SHORTLISTED.getCode()) {
                LOGGER.debug("Institution is already shortlisted , UniqueId : {}", registrationStatusData.getFormUniqueId());
                throw new ServiceException(ErrorCode.BQA_UPDATE_ALREADY_SHORTLISTED);
            }*/
            LOGGER.debug("commented ");
            /*
            if (instituteForm.getStatus() == ApplicationStatus.BQA_MOVED_MOVED.getCode()) {
                LOGGER.debug("Institution is already moved , UniqueId : {}", registrationStatusData.getFormUniqueId());
                throw new ServiceException(ErrorCode.BQA_UPDATE_ALREADY_MOVED);
            }
             */


            instituteForm.setStatus(registrationStatus);
            if (applicationStatus.equals(ApplicationStatus.DFO_ADMIN_MOVED)) {
                instituteForm.setRandomDate(Long.parseLong(registrationStatusData.getPlannedSubmissionDate()));
            } else {
                instituteForm.setPlannedSubDate(registrationStatusData.getPlannedSubmissionDate());
            }
            Map<String, Object> templateModel = new HashMap<>();
           /* if (registrationStatus == ApplicationStatus.BQA_MOVED_MOVED.getCode()) {
                instituteForm.setPlannedSubDate(registrationStatusData.getPlannedSubmissionDate());
            }*/

            if (registrationStatus == ApplicationStatus.DFO_ADMIN_REJECTED.getCode()) {
                instituteForm.setRejectedByUserType(UserType.DFO_ADMIN.getCode());
                instituteForm.setRejectionReason(bqaUpdateRequest.getRejectionReason());
                instituteForm.setRejectionDate(System.currentTimeMillis());
                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTION_REJECTED");
                if (Objects.nonNull(mailTemplate)) {
                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                    String mailBody = mailTemplate.getTemplateBody();
                    //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                    }
                    String mailHtmlPath = "mail-template.html";
                    templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
                /*User user = commonUtils.getUserDetails(UserType.INSTITUTION.getCode(), null, instituteForm.getFormUniqueId());
                List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                String NotificationResponse = fireBaseUtils.sendNotification("Ilep panel approved", "", null, tokens);*/
            }
            if (registrationStatus == ApplicationStatus.DFO_ADMIN_APPROVED.getCode()) {
                Long currentSumbitDate = System.currentTimeMillis() + (defaultSubmissonDate * 24 * 60 * 60 * 1000);
                instituteForm.setCurrentSubmitDate(currentSumbitDate);

                List<Role> instRoles = roleRepository.findByUserTypeAndSubType(UserType.INSTITUTION.getCode(),  UserSubType.ADMIN.getCode());
                Role role = null;
                if(instRoles.size()==0){
                    Role createInstitutionRole = new Role();
                    createInstitutionRole.setUserType(UserType.INSTITUTION.getCode());
                    createInstitutionRole.setSubType(UserSubType.ADMIN.getCode());
                    createInstitutionRole.setName("Institution user");
                    roleRepository.save(createInstitutionRole);
                    role = createInstitutionRole;
                }else{
                    role = instRoles.get(0);
                }
                Set<Role> institutionRoles = new HashSet<>();
                institutionRoles.add(role);
                User user = User.builder()
                        .roles(institutionRoles)
                        .emailId(instituteForm.getContactPersonEmail())
                        .username(instituteForm.getContactPersonName())
                        .createdAppId(bqaUpdateRequest.getClientId())
                        .updatedAppId(bqaUpdateRequest.getClientId())
                        .password(defaultInstitutionPassword)
                        .active(UserStatus.ACTIVE.getCode())
                        .accessRight(0)
                        .reportAccess(0)
                        .position(instituteForm.getContactPersonTitle())
//                        .subType(UserSubType.ADMIN.getCode())
                        .build();
                try {
                    List<User> usersIndDb = userDao.getUsersByEmailId(user.getEmailId());
                    if (Objects.isNull(usersIndDb)|| usersIndDb.isEmpty()) {
                        user = userDao.saveUser(user);
                    } else {
                        user = usersIndDb.get(0);
                    }
                }catch (Exception ignore){

                }
                if(instituteForm.getInstitutionId()==null){
                    instituteForm.setInstitutionId(user.getUserId());
                }
                String submissionDate = registrationStatusData.getPlannedSubmissionDate();
//                    if (instituteForm.getPlannedSubDate() == 1) {
//                        LOGGER.info("1");
//                        submissionDate = "january 2023";
//                    } else {
//                        submissionDate = "september 2023";
//                    }
                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_APPLICATION_SCHEDULED");
                if (Objects.nonNull(mailTemplate)) {

                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                    Date date = new Date(Long.parseLong(instituteForm.getPlannedSubDate()));
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
//                    Instant instant = Instant.ofEpochMilli(instituteForm.currentSubmitDate);
//                    LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                    String formattedDate = deadlineDate.format(formatter);

                    String mailBody = mailTemplate.getTemplateBody();
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                        //TODO 03-09-23
                        mailBody = mailBody.replace("{applicationUrl}", instituteForm.getInstitutionName());
                        mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                        mailBody = mailBody.replace("{userEmail}", instituteForm.getContactPersonEmail());
                        mailBody = mailBody.replace("{password}", user.getPassword());
                        mailBody = mailBody.replace("{submissionDate}", format.format(date));
                        mailBody = mailBody.replace("{QualificationTitle}",instituteForm.getQualificationTitle());

                    }

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
//                    List<String> ccAdresses = new ArrayList<>();
                    try {
                        awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
                MailTemplate mailTemplate1 = mailTemplateDao.getByTemplateCode("INSTITUTE_APPLICATION_SCHEDULED_DFO");
                if (Objects.nonNull(mailTemplate1)) {

                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate1, instituteForm);
                    Date date = new Date(Long.parseLong(instituteForm.getPlannedSubDate()));
                    DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
//                    format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                    Instant instant = Instant.ofEpochMilli(Long.parseLong(instituteForm.getPlannedSubDate()));
                    LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDate = deadlineDate.format(formatter);

                    String mailBody = mailTemplate1.getTemplateBody();
                    User instituteUser = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                        //TODO 03-09-23
                        mailBody = mailBody.replace("{applicationUrl}", instituteForm.getInstitutionName());
                        mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                        mailBody = mailBody.replace("{userEmail}", instituteForm.getContactPersonEmail());
                        mailBody = mailBody.replace("{password}", user.getPassword());
                        mailBody = mailBody.replace("{submissionDate}", formattedDate);
                    }

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
//                    List<String> ccAdresses = new ArrayList<>();
                    List<User> allRecipients = new ArrayList<>();
                    allRecipients.addAll(userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode()));
                    allRecipients.addAll(userDao.getUsersByTypeAndSubType(UserType.CHIEF.getCode(), UserSubType.ADMIN.getCode()));
                    allRecipients.addAll(userDao.getUsersByTypeAndSubType(UserType.DIRECTOR.getCode(), UserSubType.ADMIN.getCode()));

                    // Send mail individually to each active recipient
                    for (User recipient : allRecipients) {
                        if (recipient.getActive() == 1 && recipient.getEmailId() != null && !recipient.getEmailId().isBlank()) {
                            awsService.sendMail(recipient.getEmailId(), templateModel, mailHtmlPath, new ArrayList<>(), mailTemplate1.getTemplateSubject());
                        }
                    }
                }

            }
            formDao.save(instituteForm);
            ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
            /*Writing Application status log to database */
            if (!currentStatus.equals(newStatus)) {
                logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
            }

        }
        return formResponseBuilder.BQAUpdateResponseBuilder(registrationStatus);
    }




    @Override
    public GetFormListResponse getFormList(GetFormListRequest request) throws JsonProcessingException {
        if(request.getSearchValue() != null){
            String searchValue = URLDecoder.decode(request.getSearchValue(), StandardCharsets.UTF_8);
            request.setSearchValue(searchValue);
        }
        UserType userType = null;
        if(!Objects.isNull(request.getUserSubType())) {
            userType = UserType.getByCode(request.getUserType(), request.getUserSubType());
            if (userType == null) {
                throw new ServiceException(ErrorCode.FORM_LIST_INVALID_USER);
            }
        }
        PagedData<GetFormListResponseModel> pageData = formDao.getFormList(WebUtils.getUserId(),request.getFormUniqueId(), userType, request.getPlannedSubmissionDate(), request.getPage(), request.getLimit(),request.getSearchValue());
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return formResponseBuilder.getFormListResponseBuilder(pageData);
    }

    @Transactional
    @Override
    public UpdateMyFormResponse updateMyForm(UpdateMyFormRequest request) throws InvocationTargetException, IllegalAccessException {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        if (!(UserType.INSTITUTION.getCode().equals(userType) || UserType.CHIEF.getCode().equals(userType)) && !(UserType.APPLICATION_MANAGER.getCode().equals(userType))) {
            throw new ServiceException(ErrorCode.UPDATE_MY_FORM_INVALID_USER);
        }
        FormApplicationUpdateType type = FormApplicationUpdateType.getByCode(request.getType());
        if (null == type) {
            throw new ServiceException(ErrorCode.UPDATE_MY_FORM_INVALID_TYPE);
        }
        try {
            InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
            if (null == instituteForm) {
                throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
            }
            if (instituteForm.getStatus() < ApplicationStatus.DFO_ADMIN_APPROVED.getCode()) {
                throw new ServiceException(ErrorCode.UPDATE_MY_FORM_NOT_APPROVED);
            }
            if ((!ApplicationStatus.REQUIRED_ADDITION_DATA.getCode().equals(instituteForm.getStatus())) && (instituteForm.getStatus() >= ApplicationStatus.INSTITUTION_SUBMITTED.getCode()) &&
                    (instituteForm.getStatus() < ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode())) {
                throw new ServiceException(ErrorCode.UPDATE_MY_FORM_EDIT_NOT_PERMITTED);
            }
            ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
            if(type.ordinal() < FormApplicationUpdateType.QUALIFICATION_PROFILE_DATA.ordinal() ) {
                return setListingApplicationData(request, type, instituteForm, currentStatus);
            } else {
                return saveQPApplicationData(request, type, instituteForm, currentStatus);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        }

    }

    private UpdateMyFormResponse setListingApplicationData(UpdateMyFormRequest request, FormApplicationUpdateType type, InstituteForm instituteForm, ApplicationStatus currentStatus) throws IllegalAccessException, InvocationTargetException {
        FormApplicationManager form = userDao.getByFormUniqueId(request.getFormUniqueId());
        if (null == form) {
            form = new FormApplicationManager();
            form.setFormUniqueId(request.getFormUniqueId());
        }
        /**
         * Changes for resubmit data requested by AM  after GDQ review
         */
        switch (currentStatus) {
            case AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED -> {
                ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
//                if (evaluationForm.getPartialMetDate() < System.currentTimeMillis()) {
//                    throw new ServiceException(ErrorCode.UPDATE_MY_FORM_RESUBMIT_DATE_EXPIRED);
//                } else {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode());
//                }
            }
            case INSTITUTION_COMMENTS_ADDED -> instituteForm.setStatus(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode());
            case DFO_ADMIN_APPROVED, INSTITUTION_UPLOAD_DOCUMENT -> instituteForm.setStatus(ApplicationStatus.INSTITUTION_UPLOAD_DOCUMENT.getCode());


        }
        /** **********************************************/
        switch (type) {
            case INSTITUTION_PROFILE_DATA -> {
                BeanUtils.copyProperties(form, request.getInstitutionProfileDataRequest());
            }
            case QUALITY_ASSURANCE_SYSTEM -> BeanUtils.copyProperties(form, request.getQualityAssuranceSystemRequest());
            case STANDARD -> {
                BeanUtils.copyProperties(form, request.getStandardsDataRequest().getStandard1DataRequest());
                BeanUtils.copyProperties(form, request.getStandardsDataRequest().getStandard2DataRequest());
                BeanUtils.copyProperties(form, request.getStandardsDataRequest().getStandard3DataRequest());
                BeanUtils.copyProperties(form, request.getStandardsDataRequest().getStandard4DataRequest());
                BeanUtils.copyProperties(form, request.getStandardsDataRequest().getStandard5DataRequest());
            }
            case APPLICATION_CONTACT -> {
                BeanUtils.copyProperties(form, request.getApplicationContactDataRequest());
                if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode())) {
                    instituteForm.setResubmitCount(1L);
                    instituteForm.setResubmitStatus(BooleanEnum.TRUE.getCode());
                    instituteForm.setLastResubmitDate(System.currentTimeMillis());
                }
                if (instituteForm.getStatus().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode());
                    form.setFilePathParameter(request.getFilePathParam());

                } else {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_SUBMITTED.getCode());
                    form.setFilePathParameter(request.getFilePathParam());
                }
            }
        }
        form.setOverallStatus(0);
        form = formDao.save(form);
        userDao.saveInstitutionDetails(instituteForm);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return formResponseBuilder.updateMyFormResponseBuilder(StatusCode.SUCCESS.getCode(),
                form.getFormUniqueId(), form.getFormId());
    }

    private UpdateMyFormResponse saveQPApplicationData(UpdateMyFormRequest request, FormApplicationUpdateType type, InstituteForm instituteForm, ApplicationStatus currentStatus) throws IllegalAccessException, InvocationTargetException {
        QualificationProfileData form = userDao.getQualificationProfileDataByFormUniqueId(request.getFormUniqueId());
        if (null == form) {
            form = new QualificationProfileData();
            form.setFormUniqueId(request.getFormUniqueId());
        }
        /**
         * Changes for resubmit data requested by AM  after GDQ review
         */
        switch (currentStatus) {
            case AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED -> {
                ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
//                if (evaluationForm.getPartialMetDate() < System.currentTimeMillis()) {
//                    throw new ServiceException(ErrorCode.UPDATE_MY_FORM_RESUBMIT_DATE_EXPIRED);
//                } else {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode());
//                }
            }
            case INSTITUTION_COMMENTS_ADDED -> instituteForm.setStatus(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode());
            case DFO_ADMIN_APPROVED, INSTITUTION_UPLOAD_DOCUMENT -> instituteForm.setStatus(ApplicationStatus.DFO_ADMIN_APPROVED.getCode());


        }
        /** **********************************************/
        switch (type) {
            case QUALIFICATION_PROFILE_DATA, QUALIFICATION_PROFILE_STANDARD -> {
              //  BeanUtils.copyProperties(form, request.getInstitutionProfileDataRequest());
                QualificationProfileData tempForm = form;
                form = request.getQualificationProfileData();
                if(!Objects.isNull(tempForm)){
                    form.setId(tempForm.getId());
                    form.setFormUniqueId(tempForm.getFormUniqueId());
                } else {
                    form.setFormUniqueId(request.getFormUniqueId());
                }
                String checkListDoc=request.getFilePathParam();
                if(!Objects.isNull(checkListDoc)){
                    form.setFilePathParameter(checkListDoc);
                }
            }
            case QUALIFICATION_PROFILE_APPLICATION_CONTACT -> {
                BeanUtils.copyProperties(form, request.getApplicationContactDataRequest());
                if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode())) {
                    instituteForm.setResubmitCount(1L);
                    instituteForm.setResubmitStatus(BooleanEnum.TRUE.getCode());
                    instituteForm.setLastResubmitDate(System.currentTimeMillis());
                }
                if (instituteForm.getStatus().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode());
                    form.setFilePathParameter(request.getFilePathParam()); //TODO: RIYAS NEED TO VERIFY

                } else {
                    instituteForm.setStatus(ApplicationStatus.INSTITUTION_SUBMITTED.getCode());
                    form.setFilePathParameter(request.getFilePathParam()); //TODO: RIYAS NEED TO VERIFY

                }
            }
        }
        form.setOverallStatus(0);
        try {
            formDao.deleteQualificationProfileData(form);
            //formDao.deleteQualificationProfileData(form.getFormUniqueId());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        form = formDao.save(form);
        if (!Objects.isNull(request.getRegistrationStatus()) && request.getRegistrationStatus() > 0) {
            instituteForm.setStatus(request.getRegistrationStatus());
        }

        userDao.saveInstitutionDetails(instituteForm);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (newStatus != null && newStatus.getCode().equals(ApplicationStatus.INSTITUTION_SUBMITTED.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTION_SUBMITTED");
            if (Objects.nonNull(mailTemplate)) {
                List<User> dfoAdminList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{userName}", instituteForm.getQualificationTitle());
                }
                Map<String, Object> templateModel = new HashMap<>();
                String mailHtmlPath = "mail-template.html";
                templateModel.put("mailBody", mailBody);
//                    List<String> ccAdresses = new ArrayList<>();
                for (User dfoAdmin : dfoAdminList) {
                    if(dfoAdmin.getActive()==1){
                        awsService.sendMail(dfoAdmin.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }
                }

            }

        }
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return formResponseBuilder.updateMyFormResponseBuilder(StatusCode.SUCCESS.getCode(),
                form.getFormUniqueId(), form.getFormUniqueId());
    }

    @Override
    public AddBranchResponse addBranch(AddBranchRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INSITUTION_ADD_BRANCH_INVALID_UNIQUE_ID);
        }
        InstitutionBranch institutionBranch = formDao.getInstitutionByFormUniqueIdBuildingRoadBlock(
                request.getFormUniqueId(),
                request.getBuilding(),
                request.getRoad(),
                request.getBlock());
        if (null == institutionBranch) {
            institutionBranch = new InstitutionBranch();
        }
        institutionBranch.setFormUniqueId(request.getFormUniqueId());
        institutionBranch.setBuilding(request.getBuilding());
        institutionBranch.setRoad(request.getRoad());
        institutionBranch.setBlock(request.getBlock());
        institutionBranch = formDao.save(institutionBranch);
        return formResponseBuilder.addBranchResponseBuilder(StatusCode.SUCCESS.getCode(), institutionBranch.getFormUniqueId());
    }

    @Override
    public GetBranchesResponse getInstitutionBranches(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INSITUTION_ADD_BRANCH_INVALID_UNIQUE_ID);
        }
        List<GetBranchesResponseModel> getBranchesResponseModelList = formDao.getInstitutionBranches(formUniqueId);
        if (getBranchesResponseModelList.isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return formResponseBuilder.getBranchesResponseBuilder(getBranchesResponseModelList);
    }

    @Override
    public ChangeApplicationStatusResponse changeApplicationStatus(ChangeApplicationStatusRequest request) {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.APPLICATION_MANAGER.getCode())) {
            throw new ServiceException(ErrorCode.CHANGE_APPLICATION_STATUS_USER_NOT_PERMITTED);
        }
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.CHANGE_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID);
        }
        if (null == ApplicationStatus.getByCode(request.getApplicationStatus())) {
            throw new ServiceException(ErrorCode.CHANGE_APPLICATION_STATUS_INVALID_STATUS);
        }
        instituteForm.setStatus(request.getApplicationStatus());
        formDao.save(instituteForm);
        return formResponseBuilder.changeApplicationStatusResponseBuilder(StatusCode.SUCCESS.getCode(), instituteForm.getFormUniqueId());
    }

    @Override
    public DFOApproveAppealResponse dfoApproveAppeal(DFOApproveAppealRequest request) {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_APPEAL_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (!ApplicationStatus.INSTITUTION_INITIATE_AN_APPEAL_5_DAYS.getCode().equals(instituteForm.getStatus())) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_APPEAL_INSTITUTION_NOT_CREATED_APPEAL);
        }
        if (instituteForm.getInstitutionAppeal() == null || instituteForm.getInstitutionAppeal() == 0) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_APPEAL_INSTITUTION_NOT_CREATED_APPEAL);
        }
        if (request.getApprove().equals(BooleanEnum.TRUE.getCode())) {
            instituteForm.setStatus(ApplicationStatus.DFO_DIRECTOR_APPROVE_THE_APPEAL.getCode());
        } else {
            instituteForm.setStatus(ApplicationStatus.DFO_DIRECTOR_REJECTED_THE_APPEAL.getCode());
        }
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        documentFeedback.setDfoApproveAppealComment(request.getComment());
        documentFeedback.setDfoApproveAppealFile(request.getFileUrl());
        instituteForm.setInstitutionAppealApprove(request.getApprove());
        feedbackDao.save(documentFeedback);
        instituteForm = formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        if (request.getApprove().equals(BooleanEnum.TRUE.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ON_APPEAL_APPROVED");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                String mailBody = mailTemplate.getTemplateBody();
                if (mailBody !=null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                    mailBody = mailBody.replace("{QPName}", instituteForm.getQualificationTitle());
                }
                for (IlepPanel ilepPanel : ilepList) {
                    User userDetail = userDao.getByUserId(ilepPanel.getIlepMemberId());
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", userDetail.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
//                    List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(userDetail.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    mailBody = mailBody.replace( userDetail.getUsername(),"{userName}");
                }
            }
        }

        return formResponseBuilder.dfoApproveAppealResponseBuilder(BooleanEnum.TRUE.getCode(), instituteForm.getFormUniqueId());
    }

    @Override
    public DFOChiefSignResponse dfoChiefSign(DFOChiefSignRequest request) {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_CHIEF_SIGN_REPORT_INVALID_FORM_UNIQUE);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (!(ApplicationStatus.MCO_UPLOADED_EDITED_DOC.getCode().equals(instituteForm.getStatus()) ||
                ApplicationStatus.MCO_RE_UPLOAD_THE_EDITED_REPORT.getCode().equals(instituteForm.getStatus()))) {
            throw new ServiceException(ErrorCode.DFO_CHIEF_SIGN_REPORT_STATUS_NOT_MET);
        }
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (documentFeedback == null) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setDfoSignedFile(request.getDocUrl());
        documentFeedback.setDfoSignedStatus(request.getSign());
        documentFeedback.setDfoSignedComment(request.getComment());
        instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_SIGNED_OFF_AND_UPLOAD_THE_REPORT.getCode());
        institutionFormRepository.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        feedbackDao.save(documentFeedback);

        return formResponseBuilder.dfoChiefSignResponseBuild(BooleanEnum.TRUE.getCode(), instituteForm.getFormUniqueId());
    }

    @Override
    public SetAllApproveResponse setAllApproveHistory(SetAllApproveRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.SET_APPROVE_HISTORY_INVALID_FORM_UNIQUE_ID);
        }
        instituteForm.setOverAllApproveHistory(request.getApproveHistory());
        formDao.save(instituteForm);
        return formResponseBuilder.setAllApproveResponseBuild(BooleanEnum.TRUE.getCode(), request.getFormUniqueId());

    }

    @Override
    public GetAllApproveResponse getAllApproveHistory(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.GET_APPROVE_HISTORY_INVALID_FORM_UNIQUE_ID);
        }
        return formResponseBuilder.getAllApproveResponseBuild(instituteForm.getOverAllApproveHistory(), formUniqueId);
    }

    @Override
    public GetLogResponse getLogs(GetLogRequest request) {
        Long userId = WebUtils.getUserId();
        PagedData<GetLogResponseModel> logResponseModelPagedData = logDao.getLog(request.getFormUniqueId(), request.getPage(), request.getLimit());
        if (logResponseModelPagedData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return formResponseBuilder.getLogResponseBuild(logResponseModelPagedData);
    }

    @Override
    @Transactional
    public ResetApplicationStatusResponse resetApplicationStatus(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.RESET_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID);
        }

        instituteForm.setStatus(instituteForm.getStatus() + 1);
        formDao.save(instituteForm);

        return formResponseBuilder.buildResetApplicationStatusResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    public ResetApplicationStatusResponse amRejectApplicationStatus(Long formUniqueId, String rejectionReason) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.REJECT_APPLICATION_STATUS_INVALID_FORM_UNIQUE_ID);
        }
        Map<String, Object> templateModel = new HashMap<>();
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        instituteForm.setStatus(ApplicationStatus.AM_REJECTED_THE_DOCUMENT.getCode());
        instituteForm.setRejectionReason(rejectionReason);
        instituteForm.setRejectedByUserType(WebUtils.getUserType());
        formDao.save(instituteForm);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_INSTITUTION_REJECTED");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
            }
            String mailHtmlPath = "mail-template.html";
            templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }

        return formResponseBuilder.buildResetApplicationStatusResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    public ResetApplicationStatusResponse amRejectSiteVisitDocuments(RejectSiteVisitRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.REJECT_SITE_VISIT_DOC_INVALID_FORM_UNIQUE_ID);
        }
        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            throw new ServiceException(ErrorCode.REJECT_SITE_VISIT_DOC_SITE_VISIT_NOT_FOUND);
        }

        instituteForm.setSubStatus(ApplicationStatus.AM_REJECT_SITE_VISIT_DOCUMENT.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });
            completedStatus.add(ApplicationStatus.AM_REJECT_SITE_VISIT_DOCUMENT.getCode());
        }

        formDao.save(instituteForm);

        return formResponseBuilder.buildResetApplicationStatusResponse(BooleanEnum.TRUE.getCode());

    }

    @Override
    public GetRejectionDetailsResponse getRejectionDetails(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.REJECT_SITE_VISIT_DOC_INVALID_FORM_UNIQUE_ID);
        }
        if(instituteForm.getStatus()<0){
            throw new ServiceException(ErrorCode.INSTITUTE_IS_NOT_REJECTED);
        }
        String reason = instituteForm.getRejectionReason();
        return formResponseBuilder.buildGetRejectionDetailsResponse(BooleanEnum.TRUE.getCode(),reason);
    }

    public PaymentApprovedResponse approvePayment(Long formUniqueId){
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        System.out.println(instituteForm + "formUniqueId");
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }
        if (instituteForm.getIsPaid()==1) {
            throw new IllegalArgumentException("Payment already approved");
        }
        instituteForm.setIsPaid(1);
        formDao.save(instituteForm);
        PaymentApprovedResponse response = new PaymentApprovedResponse();
        return formResponseBuilder.approvePaymentResponseBuilder();

    }

    @Override
    public SaveQpIdResponse saveQpId(SaveQpIdRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }
        instituteForm.setQpId(request.getQpId());
        formDao.save(instituteForm);

        SaveQpIdResponseModel model = SaveQpIdResponseModel.builder()
                .formUniqueId(instituteForm.getFormUniqueId())
                .qpId(instituteForm.getQpId())
                .build();
        return formResponseBuilder.SaveQpIdResponseBuilder(model);
    }

    @Override
    public GetQualificationsWithStatusResponse getQualificationWithStatusForBQA() {
        List<StaticQualificationDetails> sortedQualifications = staticQualificationRepository.findAll()
                .stream()
                .sorted(java.util.Comparator.comparing(StaticQualificationDetails::getQualificationTitle))
                .collect(java.util.stream.Collectors.toList());
        return formResponseBuilder.getQualificationsWithStatusForBQABuilder(
                institutionFormRepository.getQualificationsWithStatusForBQA(),
                sortedQualifications
        );
    }

    @Override
    public GetFQDetailsResponse getFqDetails(Long formUniqueId) {
        InstituteForm instituteForm =  formDao.getInstitutionFormById(formUniqueId);

        GetFQDetailsResponseModel.Qualification qualification = GetFQDetailsResponseModel.Qualification.builder()
                .qualificationTitle(instituteForm.getQualificationTitle())
                .awardingBody(instituteForm.getAwardingBody())
                .providers(instituteForm.getProviders())
                .levelAndCredit(instituteForm.getLevelAndCredit())
                .numberOfUnitsCoursesModules(instituteForm.getNumberOfUnitsCoursesModules())
                .expectedSubmissionDate(instituteForm.getExpectedSubmissionDate())
                .build();

        GetFQDetailsResponseModel getFQDetailsResponseModel = GetFQDetailsResponseModel.builder()
                .applicantOrganizationName(instituteForm.getApplicantOrganizationName())
                .qualificationIncludedOtherFramework(instituteForm.getIncludedInOther())
                .contactName(instituteForm.getContactPersonName())
                .contactPosition(instituteForm.getContactPersonTitle())
                .contactNumber(instituteForm.getContactPersonNumber())
                .contactEmail(instituteForm.getContactPersonEmail())
                .qualifications(List.of(qualification))
                .qualificationType(instituteForm.getQualificationType())
                .build();

        return formResponseBuilder.getFqDetailsBuilder(getFQDetailsResponseModel);
    }
}
