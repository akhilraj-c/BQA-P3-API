package com.mindteck.common.modules.ilepAssigin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.evaluation.rest.CreateILEPMemberRequest;
import com.mindteck.common.modules.evaluation.rest.IlepConflictForm;
import com.mindteck.common.modules.evaluation.rest.InstitutionConflictData;
import com.mindteck.common.modules.ilepAssigin.builder.IlepAssignResponseBuilder;
import com.mindteck.common.modules.ilepAssigin.models.*;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import com.mindteck.common.modules.ilepAssigin.service.IlepAssignService;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.util.UserUtils;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@Slf4j
public class IlepAssignServiceImpl implements IlepAssignService {

    @Autowired
    FormDao formDao;

    @Autowired
    AwsService awsService;

    @Autowired
    EvaluationDao evaluationDao;
    @Autowired
    UserDao userDao;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    IlepAssignDao ilepAssignDao;
    @Autowired
    IlepAssignResponseBuilder responseBuilder;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private FireBaseUtils fireBaseUtils;
    @Autowired
    private LogService logService;

    @Override
    public CreateIlepMemberSepResponse createILEPMemberSep(CreateILEPMemberRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        List<IlepPanel> ilepPaneExist = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
        if ((ilepPaneExist.size() + request.getMemberId().size()) > 3) {
            LOGGER.debug("Panel already exist for institution:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_PANEL_EXIST);
        }

        List<User> memberList = userDao.getUserList(request.getMemberId());
        if (memberList.size() != request.getMemberId().size()) {
            LOGGER.debug("User not found");
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_USER_NOT_FOUND);
        }

        if (memberList.stream().noneMatch(user ->
                user.getRoles().stream().anyMatch(
                        role -> role.getUserType()== UserType.ILEP_MEMBER.getCode()
                ) )) {
            LOGGER.debug("Invalid userId given");
            throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_INVALID_USER);
        }


        List<Ilep> ilepList = new ArrayList<>();

        for (Long userId : request.getMemberId()) {
            Ilep ilep = ilepAssignDao.findByFormUniqueIdAndUserId(request.getFormUniqueId(), userId);
            if (null == ilep) {
                ilep = new Ilep();
            } else {
                throw new ServiceException(ErrorCode.CREATE_ILEP_MEMBER_SEP_ALREADY_ASSIGNED);
            }
            ilep.setFormUniqueId(request.getFormUniqueId());
            ilep.setIlepMemberId(userId);
            if (request.getMemberHead().equals(userId)) {
                ilep.setIsHead(BooleanEnum.TRUE.getCode());
            } else {
                ilep.setIsHead(BooleanEnum.FALSE.getCode());
            }
            ilep.setIsDfoApproved(BooleanEnum.FALSE.getCode());
            ilepList.add(ilep);
        }

        ilepAssignDao.saveAll(ilepList);
        ApplicationStatus newStatus = ApplicationStatus.ILEP_PANEL_CREATED_SEP;
        logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());


        LOGGER.info("Before initializing variables for dfo notification mail for approving ilep panel");
        List<User> dfoAdminList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ILEP_PANEL_CREATE");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();

            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            Map<String, Object> templateModel = new HashMap<>();

            /*if (!CollectionUtils.isEmpty(dfoAdminList)) {
                for (User user : dfoAdminList) {*/
                    User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                    }
                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
//                    List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(instituteUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
               /* }
            }*/
        }

        return responseBuilder.createIlepMemberSepResponseBuild(0L, "Ilep added successfully");
    }

    @Override
    public DFOApproveIlepSepResponse approvePanelSep(DFOApprovePanelSepRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_SEP_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        Ilep ilep = ilepAssignDao.findByFormUniqueIdAndUserId(request.getFormUniqueId(), request.getUserId());
        if (null == ilep) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_SEP_PANEL_NOT_FOUND);
        }
        ilep.setIsDfoApproved(request.getApproveStatus());
        ilepAssignDao.save(ilep);
        ApplicationStatus newStatus = ApplicationStatus.AM_APPROVED_ILEP_PANEL_SEP;
        logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());

        InstituteForm instituteDetails = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (!Objects.isNull(instituteDetails)) {
            LOGGER.info("Before initializing variables for institution notification mail for signing no conflict form");

            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DFOA_APPROVE_ILEP_PANEL");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User instituteUser = userDao.getUserByEmailId(instituteDetails.getContactPersonEmail());
//                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                if (mailBody !=null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                    mailBody = mailBody.replace("{QualificationTitle}", instituteForm.getQualificationTitle());
                    mailBody = mailBody.replace("{instituteName}", instituteDetails.getInstitutionName());
                }
                Map<String, Object> templateModel = new HashMap<>();

                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());

                templateModel.put("mailBody", mailBody);
                String mailHtmlPath = "mail-template.html";
                /*List<String> ccAdresses = new ArrayList<>();
                ccAdresses.add(amDetails.getEmailId());*/
                awsService.sendMail(instituteDetails.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
            }
         /*   User user = commonUtils.getUserDetails(UserType.INSTITUTION.getCode(), null, instituteDetails.getFormUniqueId());
            List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
            String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_TITLE, NotifcationConstatnts.ON_DFO_APPROVES_ILEP_PANEL_MESSAGE, null, tokens);
*/        }
        return responseBuilder.dfoApproveIlepSepResponseBuild("Dfo updated the panel status");

    }

    @Override
    public IlepGetSepResponse getIlepMemberSep(Long formUniqueId) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.GET_ILEP_MEMBER_SEP_INVALID_FORM_UNIQUE_ID);
        }
        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(formUniqueId, BooleanEnum.FALSE.getCode());
        if (ilepList.isEmpty()) {
            throw new ServiceException(ErrorCode.GET_ILEP_MEMBERS_SEP_NOT_FOUND);
        }
        List<IlepGetSepResponseModel> ilepGetSepResponseModels = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Ilep ilep : ilepList) {
            IlepGetSepResponseModel ilepGetSepResponseModel = new IlepGetSepResponseModel();
            BeanUtils.copyProperties(ilepGetSepResponseModel, ilep);
            User user = userDao.getByUserId(ilep.getIlepMemberId());
            ilepGetSepResponseModel.setIlepMemberName(user.getUsername());
            if (ilep.getInstitutionConflictData() != null) {
                ilepGetSepResponseModel.setInstitutionConflictDataList(objectMapper.readValue(ilep.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                }));
            }
            if (ilep.getIlepConflictData() != null) {
                ilepGetSepResponseModel.setIlepConflictForm(objectMapper.readValue(ilep.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                }));
            }
            ilepGetSepResponseModels.add(ilepGetSepResponseModel);
        }
        PagedData<IlepGetSepResponseModel> ilepGetSepResponseModelPagedData = new PagedData<>();
        ilepGetSepResponseModelPagedData.setList(ilepGetSepResponseModels);
        return responseBuilder.ilepGetSepResponseBuild(ilepGetSepResponseModelPagedData);
    }

    @Override
    public CreateInstConflictSepResponse createInstConflictSep(CreateInstConflictSepRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INSTITUTION_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ApplicationStatus newStatus;
        ObjectMapper objectMapper = new ObjectMapper();
        List<InstitutionConflictData> institutionConflictData = request.getInstitutionConflictDataList();
        List<Ilep> conflictList = new ArrayList<>();
        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(instituteForm.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        for (InstitutionConflictData conflictData : institutionConflictData) {
            Ilep ilep = ilepList.stream().filter(ilep1 -> ilep1.getIlepMemberId().equals(conflictData.getIlepUserId())).findFirst().orElse(null);
            if (null == ilep) {
                throw new ServiceException(ErrorCode.INSTITUTION_CONFLICT_SEP_ILEP_NOT_FOUND);
            }
            int index = ilepList.indexOf(ilep);
            ilep.setInstitutionConflictStatus(request.getInstitutionConflictStatus());
            ilep.setInstitutionConflictData(objectMapper.writeValueAsString(List.of(conflictData)));
            conflictList.add(ilep);
            ilepList.remove(index);
        }
        if(request.getInstitutionConflictStatus().equals(BooleanEnum.FALSE.getCode())) {
            newStatus = ApplicationStatus.INSTITUTION_ILEP_PANEL_NON_CONFLICT_SEP;
        } else {
            newStatus = ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT_SEP;
        }
        for (Ilep ilep : ilepList) {
            ilep.setInstitutionConflictData(objectMapper.writeValueAsString(new ArrayList<>()));
        }
        ilepList.addAll(conflictList);
        ilepAssignDao.saveAll(ilepList);
        logService.writeLogToDatabase(userId,currentStatus,newStatus,instituteForm.getFormUniqueId());

        if (request.getInstitutionConflictStatus().equals(BooleanEnum.FALSE.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_SIGN_NO_CONFLICT");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                List<IlepPanel> ilepListDetails = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                String mailBody = mailTemplate.getTemplateBody();
                User amUserDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
//                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                for (IlepPanel ilep : ilepListDetails) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody !=null && !mailBody.isBlank()) {
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

        return responseBuilder.createInstConflictSepResponseBuild(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public AmConflictApproveSepResponse amConflictApproveSep(AmApproveConflictSepRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_APPROVE_CONFLICT_SEP_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());


        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (ilepList.isEmpty()) {
            throw new ServiceException(ErrorCode.AM_APPROVE_CONFLICT_SEP_CONFLICT_FORM_NOT_FOUND);
        }
        for (Ilep ilep : ilepList) {
            if (ilep.getInstitutionConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                ilep.setIsConflictApprovedAm(request.getConflictApproved() ? BooleanEnum.TRUE.getCode() : BooleanEnum.FALSE.getCode());
            }
        }
        ilepAssignDao.saveAll(ilepList);
        ApplicationStatus newStatus = ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT_AM_APPROVE_SEP;
        logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());
        //send notification
        LOGGER.info("Before initializing variables for dfo notification mail for approving ilep panel");
        /* commented as not using now
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_NOT_APPROVE_CONFLICT");
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
        /*User user = commonUtils.getUserDetails(UserType.ILEP_MEMBER.getCode(), UserSubType.ADMIN.getCode(), instituteForm.getFormUniqueId());
        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
        String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.AM_NOT_APPROVE_CONFLICT_TITLE, NotifcationConstatnts.AM_NOT_APPROVE_CONFLICT_MESSAGE, null, tokens);
*/
        return responseBuilder.amConflictApproveSepResponseBuild("conflict status updated");
    }

    @Override
    public DFOApproveConflictSepResponse dfoConflictApproveSep(DFOApproveConflictSepRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (ilepList.isEmpty()) {
            throw new ServiceException(ErrorCode.DFO_APPROVE_CONFLICT_SEP_ILEP_NOT_FOUND);
        }
        List<Ilep> notApproved = new ArrayList<>();
        for (Ilep ilep : ilepList) {
            if (ilep.getInstitutionConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                if (ilep.getIsConflictApprovedAm().equals(BooleanEnum.TRUE.getCode())) {
                    ilep.setIsConflictApprovedDfo(request.getApproveStatus());
                } else {
                    notApproved.add(ilep);
                }
            }
        }
        ilepAssignDao.saveAll(ilepList);
        ApplicationStatus newStatus = ApplicationStatus.INSTITUTION_ILEP_PANEL_CONFLICT_DFO_APPROVE_SEP;
        logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());
        //Todo update proper mail TODO 17-09-23
        LOGGER.info("Before initializing variables for institution notification mail for signing no conflict form");
        Map<String, Object> templateModel = new HashMap<>();

        User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
        templateModel.put("applicationId", request.getFormUniqueId());
        templateModel.put("userName", instituteForm.getInstitutionName());

        String mailHtmlPath = "email-on-ilep-inst.user-noconflict-reminder.html";
        List<String> ccAdresses = new ArrayList<>();
        ccAdresses.add(amDetails.getEmailId());
        awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, "");
        return responseBuilder.dfoApproveConflictSepResponseBuild(notApproved.size() == 0 ? "Dfo approved the conflict" : "Partial success - Am not approved all conflict");
    }

    @Override
    public CreateIlepConflictSepResponse createIlepConflict(CreateIlepConflictSepRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.ILEP_CONFLICT_SEP_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        try {

            Ilep ilep = ilepAssignDao.findByFormUniqueIdAndUserId(request.getFormUniqueId(), userId);
            if (null == ilep) {
                throw new ServiceException(ErrorCode.ILEP_CONFLICT_SEP_ILEP_NOT_ASSIGNED_INSTITUTION_CONFLICT_NOT_SUBMITTED);
            }
            if (ilep.getInstitutionConflictData() == null || ilep.getInstitutionConflictData().isEmpty()) {
                throw new ServiceException(ErrorCode.ILEP_CONFLICT_SEP_INSTITUTION_CONFLICT_NOT_SUBMITTED);
            }
            List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(instituteForm.getFormUniqueId(),BooleanEnum.FALSE.getCode());
            int count = ilepList.stream().filter(el -> el.getIlepConflictData() != null).toList().size();
            ApplicationStatus newStatus;
            switch (count) {
                case 0 -> {
                    if ( request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_CONFLICT_SEP;
                    } else {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_1_INSTITUTION_NON_CONFLICT_SEP;
                    }
                }
                case 1 -> {
                    if ( request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_CONFLICT_SEP;
                    } else {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_2_INSTITUTION_NON_CONFLICT_SEP;
                    }
                }
                case 2 -> {
                    if ( request.getIlepConflictStatus().equals(BooleanEnum.TRUE.getCode())) {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_CONFLICT_SEP;
                    } else {
                        newStatus = ApplicationStatus.ILEP_PANEL_USER_3_INSTITUTION_NON_CONFLICT_SEP;
                    }
                }
                default -> newStatus = ApplicationStatus.ILEP_PANEL_ASSIGNED_SEP;
            }
            request.getIlepConflictForm().setIlepUserId(userId);
            ilep.setIlepConflictStatus(request.getIlepConflictStatus());
            ObjectMapper objectMapper = new ObjectMapper();
            ilep.setIlepConflictData(objectMapper.writeValueAsString(List.of(request.getIlepConflictForm())));
            ilepAssignDao.save(ilep);

            logService.writeLogToDatabase(WebUtils.getUserId(),currentStatus,newStatus,instituteForm.getFormUniqueId());
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
                  /*  User user = commonUtils.getUserDetails(UserType.APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode(), instituteForm.getFormUniqueId());
                    List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
                    String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ILEP_SIGN_NON_CONFLICT_TITLE, NotifcationConstatnts.ILEP_SIGN_NON_CONFLICT_MESSAGE, null, tokens);
 */               }
            }
        } catch (JsonProcessingException exception) {
            LOGGER.error("Error while parsing convertion object to json {}", exception.getMessage());
            throw exception;
        }

        return responseBuilder.createIlepConflictSepResponseBuild(request.getFormUniqueId(), StatusCode.SUCCESS.getCode());
    }

    @Override
    public RemoveIlepSepResponse removeIlep(RemoveIlepSepRequest request) {
        String message = "Ilep removed successfully";
        if (null == formDao.getInstitutionFormById(request.getFormUniqueId())) {
            throw new ServiceException(ErrorCode.REMOVE_PANEL_SEP_INVALID_FORM_UNIQUE_ID);
        }
        List<Ilep> ilepList = new ArrayList<>();
        for (Long userId : request.getUserId()) {
            Ilep ilep = ilepAssignDao.findByFormUniqueIdAndUserId(request.getFormUniqueId(), userId);
            if (null != ilep) {
                ilep.setIsHistory(BooleanEnum.TRUE.getCode());
                ilepList.add(ilep);
            } else {
                message = "Partial success , all members not found";
            }
        }
        if (!ilepList.isEmpty()) {
            ilepAssignDao.saveAll(ilepList);
        }
        return responseBuilder.removeIlepSep(message);
    }

    @Override
    public GrandAccessSepResponse grandAccessSep(GrandAccessSepRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_SEP_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ApplicationStatus newStatus = currentStatus;
        Ilep ilep = ilepAssignDao.findByFormUniqueIdAndUserId(request.getFormUniqueId(), request.getUserId());
        if (null == ilep || ilep.getIsHistory().equals(BooleanEnum.TRUE.getCode())) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_SEP_NOT_A_PANEL_MEMBER);
        }
        if(ilep.getIlepConflictData() == null || ilep.getInstitutionConflictData() == null) {
            throw new ServiceException(ErrorCode.AM_GRAND_ACCESS_CONFLICT_FORM_NOT_SIGNED);
        }
        ilep.setGrandAccess(request.getGrandAccess());
        IlepPanel ilepPnl = new IlepPanel();
        ConflictForm conflictForm = evaluationDao.getByFormUniqueId(request.getFormUniqueId(), BooleanEnum.FALSE.getCode());
        if (request.getGrandAccess().equals(BooleanEnum.TRUE.getCode())) {
            ilepPnl.setFormUniqueId(request.getFormUniqueId());
            ilepPnl.setGrandAccess(request.getGrandAccess());
            ilepPnl.setIlepMemberId(request.getUserId());
            ilepPnl.setIsHead(ilep.getIsHead());
            ilepPnl.setIsDfoApproved(ilep.getIsDfoApproved());
            List<IlepPanel> ilepPanel = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
            switch (ilepPanel.size()) {
                case 0 -> newStatus =ApplicationStatus.ACCESS_GRANT_ILEP_1_SEP;
                case 1 -> newStatus = ApplicationStatus.ACCESS_GRANT_ILEP_2_SEP;
                case 3 -> newStatus = ApplicationStatus.ACCESS_GRANT_ILEP_3_SEP;
                default -> newStatus = ApplicationStatus.ILEP_PANEL_ASSIGNED_SEP;
            }
            if (ilepPanel.isEmpty()) {
                ilepPnl.setPanelStatus(BooleanEnum.FALSE.getCode());
                Long panelId = userUtils.generateUniqueId();
                ilepPnl.setPanelId(panelId);
            } else {
                ilepPnl.setPanelStatus(ilepPanel.get(0).getPanelStatus());
                ilepPnl.setPanelId(ilepPanel.get(0).getPanelId());
            }


            if (conflictForm == null) {
                conflictForm = new ConflictForm();
                conflictForm.setFormUniqueId(request.getFormUniqueId());
                conflictForm.setIsHistory(BooleanEnum.FALSE.getCode());
                conflictForm.setInstitutionConflictData(ilep.getInstitutionConflictData());
                conflictForm.setInstitutionConflictStatus(ilep.getInstitutionConflictStatus());
                conflictForm.setIlepConflictData(ilep.getIlepConflictData());
                conflictForm.setIlepConflictStatus(ilep.getIlepConflictStatus());

            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                List<IlepConflictForm> ilepConflictFormNew = objectMapper.readValue(ilep.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                });
                List<InstitutionConflictData> institutionConflictDataNew = objectMapper.readValue(ilep.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                });
                List<IlepConflictForm> ilepConflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                });
                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                });
                ilepConflictForms.addAll(ilepConflictFormNew);
                institutionConflictData.addAll(institutionConflictDataNew);

                conflictForm.setIlepConflictData(objectMapper.writeValueAsString(ilepConflictForms));
                conflictForm.setInstitutionConflictData(objectMapper.writeValueAsString(institutionConflictData));


            }

            ilep.setIsHistory(BooleanEnum.TRUE.getCode());
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_GRANT_ACCESS_EVALUATION_PANEL");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                String mailBody = mailTemplate.getTemplateBody();
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                for (IlepPanel ilepUser : ilepList) {
                    User ilepMember = userDao.getByUserId(ilepUser.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                            mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                            mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                            mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                        }
                        Map<String, Object> templateModel = new HashMap<>();

                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                   /* List<String> ccAdresses = new ArrayList<>();
                    ccAdresses.add(amDetails.getEmailId());*/
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
            }
        }

        ilepAssignDao.save(ilep);
        evaluationDao.save(conflictForm);
        evaluationDao.save(ilepPnl);
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(userId, currentStatus, newStatus, request.getFormUniqueId());
        }
        return responseBuilder.grandAccessSepResponseBuild(request.getFormUniqueId(), BooleanEnum.TRUE.getCode());
    }

}
