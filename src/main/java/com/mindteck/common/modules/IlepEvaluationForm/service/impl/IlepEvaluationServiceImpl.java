package com.mindteck.common.modules.IlepEvaluationForm.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.IlepEvaluationForm.models.*;
import com.mindteck.common.modules.IlepEvaluationForm.models.IlepFormData.*;
import com.mindteck.common.modules.IlepEvaluationForm.service.IlepEvaluationService;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.FireBaseUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.builder.IlepEvaluationResponseBuilder;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.feedback.dao.FeedbackDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ClientRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class IlepEvaluationServiceImpl implements IlepEvaluationService {
    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    @Autowired
    IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    UserDao userDao;
    @Autowired
    FormDao formDao;

    @Autowired
    IlepEvaluationResponseBuilder ilepEvaluationResponseBuilder;

    @Autowired
    private IlepEvaluationService ilepEvaluationService;

    @Autowired
    EvaluationDao evaluationDao;

    @Autowired
    private AwsService awsService;

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

    @Autowired
    IlepAssignDao ilepAssignDao;
    @Autowired
    FeedbackDao feedbackDao;

    @Override
    @Transactional
    public IlepEvaluateResponse ilepEvaluate(IlepEvaluateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }

        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if(ilepEvaluationForm == null) {
           ilepEvaluationForm = new ILEPEvaluationForm();
        }
        setStandard1Status(ilepEvaluationForm, request);
        setStandard2Status(ilepEvaluationForm, request);
        setStandard3Status(ilepEvaluationForm, request);
        setStandard4Status(ilepEvaluationForm, request);
        setStandard5Status(ilepEvaluationForm, request);
        ilepEvaluationForm.setFormUniqueId(request.getFormUniqueId());

        ilepEvaluationDao.save(ilepEvaluationForm);
        return ilepEvaluationResponseBuilder.buldIlepEvaluateResponse("Ilep updated Status");
    }

    public ILEPEvaluationForm setStandard1Status(ILEPEvaluationForm ilepEvaluationForm, IlepEvaluateRequest request) {
        LOGGER.info("Status for Standard1Status ");
        //TODO
        ilepEvaluationForm.setStd_1_1(request.getIlepStandard1Data().getActualAndTangibleNeedComments());
        ilepEvaluationForm.setStd_1_2(request.getIlepStandard1Data().getStakeholdersFeedbackComments());
        ilepEvaluationForm.setStd_1_3(request.getIlepStandard1Data().getCareerProgressionAndLearningPathwaysComments());
        ilepEvaluationForm.setStandard1Condition(request.getIlepStandard1Data().getConditions());
        ilepEvaluationForm.setStandard1Suggestion(request.getIlepStandard1Data().getSuggestions());
        ilepEvaluationForm.setStandard1Judgement(request.getIlepStandard1Data().getJudgement());
        ilepEvaluationForm.setStandard1JudgementHistory(request.getIlepStandard1Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm setStandard2Status(ILEPEvaluationForm ilepEvaluationForm, IlepEvaluateRequest request) {
        LOGGER.debug("Status for Standard2Status");
        //TODO
        ilepEvaluationForm.setStd_2_1(request.getIlepStandard2Data().getQualificationlicenseAndApproval());
        ilepEvaluationForm.setStd_2_2(request.getIlepStandard2Data().getQualificationAccessAndTransferComments());
        ilepEvaluationForm.setStd_2_3(request.getIlepStandard2Data().getQualificationGraduationRequirementsComments());
        ilepEvaluationForm.setStd_2_4(request.getIlepStandard2Data().getQualificationAlignmentAndBenchmarkingComments());
        ilepEvaluationForm.setStd_2_5(request.getIlepStandard2Data().getQualificationInternalAndExternalEvaluationAndReviewComments());
        ilepEvaluationForm.setStd_2_6(request.getIlepStandard2Data().getMappingAndConfirmationProcessesComments());
        ilepEvaluationForm.setStd_2_7(request.getIlepStandard2Data().getProgrammeAccreditationComments());

        ilepEvaluationForm.setStandard2Condition(request.getIlepStandard2Data().getConditions());
        ilepEvaluationForm.setStandard2Suggestion(request.getIlepStandard2Data().getSuggestions());
        ilepEvaluationForm.setStandard2Judgement(request.getIlepStandard2Data().getJudgement());
        ilepEvaluationForm.setStandard2JudgementHistory(request.getIlepStandard1Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm setStandard3Status(ILEPEvaluationForm ilepEvaluationForm, IlepEvaluateRequest request) {
        LOGGER.debug("Status for Standard3Status");
        //TODO
        ilepEvaluationForm.setStd_3_1(request.getIlepStandard3Data().getQualificationTitleComments());
        ilepEvaluationForm.setStd_3_2(request.getIlepStandard3Data().getLearningOutcomesComments());
        ilepEvaluationForm.setStd_3_3(request.getIlepStandard3Data().getQualificationAttendanceAndDeliveryModesComments());
        ilepEvaluationForm.setStd_3_4(request.getIlepStandard3Data().getQualificationStructureAndDurationComments());
        ilepEvaluationForm.setStd_3_5(request.getIlepStandard3Data().getQualificationContentComments());
        ilepEvaluationForm.setStd_3_6(request.getIlepStandard3Data().getProgressionAndFlowComments());
        ilepEvaluationForm.setStd_3_7(request.getIlepStandard3Data().getUnitInformationComments());
        ilepEvaluationForm.setStd_3_8(request.getIlepStandard3Data().getLearningResourcesAndLearnerSupportComments());
        ilepEvaluationForm.setStd_3_9(request.getIlepStandard3Data().getLearnersWithSpecialNeedsComments());
        ilepEvaluationForm.setStandard3Condition(request.getIlepStandard3Data().getConditions());
        ilepEvaluationForm.setStandard3Suggestion(request.getIlepStandard3Data().getSuggestions());
        ilepEvaluationForm.setStandard3Judgement(request.getIlepStandard3Data().getJudgement());
        ilepEvaluationForm.setStandard3JudgementHistory(request.getIlepStandard3Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm setStandard4Status(ILEPEvaluationForm ilepEvaluationForm, IlepEvaluateRequest request) {
        LOGGER.debug("Status for Standard4Status");
        //TODO
        ilepEvaluationForm.setStd_4_1(request.getIlepStandard4Data().getAssessmentDesignComments());
        ilepEvaluationForm.setStd_4_2(request.getIlepStandard4Data().getInternalAndExternalVerificationAndModerationofAssessmentComments());
        ilepEvaluationForm.setStd_4_3(request.getIlepStandard4Data().getMarkingCriteriaComments());
        ilepEvaluationForm.setStd_4_4(request.getIlepStandard4Data().getMeasuringTheAchievementofLearningOutcomesComments());
        ilepEvaluationForm.setStd_4_5(request.getIlepStandard4Data().getFeedbackToLearnersComments());
        ilepEvaluationForm.setStd_4_6(request.getIlepStandard4Data().getAppealAgainstAssessmentResultComments());
        ilepEvaluationForm.setStd_4_7(request.getIlepStandard4Data().getTheIntegrityofAssessmentComments());
        ilepEvaluationForm.setStandard4Condition(request.getIlepStandard4Data().getConditions());
        ilepEvaluationForm.setStandard4Suggestion(request.getIlepStandard4Data().getSuggestions());
        ilepEvaluationForm.setStandard4Judgement(request.getIlepStandard4Data().getJudgement());
        ilepEvaluationForm.setStandard4JudgementHistory(request.getIlepStandard4Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm setStandard5Status(ILEPEvaluationForm ilepEvaluationForm, IlepEvaluateRequest request) {
        LOGGER.debug("Status for Standard4Status");
        //TODO
        ilepEvaluationForm.setStd_5_1(request.getIlepStandard5Data().getNqfLevelComments());
        ilepEvaluationForm.setStd_5_2(request.getIlepStandard5Data().getNqfCreditComments());
        ilepEvaluationForm.setStd_5_3(request.getIlepStandard5Data().getCreditFrameworkAndRequirementsComments());
        ilepEvaluationForm.setStandard5Condition(request.getIlepStandard5Data().getConditions());
        ilepEvaluationForm.setStandard5Suggestion(request.getIlepStandard5Data().getSuggestions());

        ilepEvaluationForm.setStandard5Judgement(request.getIlepStandard5Data().getJudgement());
        ilepEvaluationForm.setStandard5JudgementHistory(request.getIlepStandard5Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    @Override
    public GetILEPEvaluationDetailsResponse getILEPEvaluationDetails(Long formUniqueId) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (Objects.isNull(instituteForm)) {
            LOGGER.debug("Institute not found with id:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_ILEP_EVALUATION_APPLICATION_FORM_NOT_FOUND);
        }

        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(formUniqueId);
        if (Objects.isNull(ilepEvaluationForm)) {
            LOGGER.debug("ILEp not evaluated for formId:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_ILEP_EVALUATION_APPLICATION_ILEP_NOT_EVALUATED);
        }

        GetILEPEvaluationDetailsResponseModel responseModel = new GetILEPEvaluationDetailsResponseModel();

        IlepStandard1Data ilepStandard1Data = setIlepStandard1Data(ilepEvaluationForm);
        IlepStandard2Data ilepStandard2Data = setIlepStandard2Data(ilepEvaluationForm);
        IlepStandard3Data ilepStandard3Data = setIlepStandard3Data(ilepEvaluationForm);
        IlepStandard4Data ilepStandard4Data = setIlepStandard4Data(ilepEvaluationForm);
        IlepStandard5Data ilepStandard5Data = setIlepStandard5Data(ilepEvaluationForm);

        responseModel.setIlepStandard1Data(ilepStandard1Data);
        responseModel.setIlepStandard2Data(ilepStandard2Data);
        responseModel.setIlepStandard3Data(ilepStandard3Data);
        responseModel.setIlepStandard4Data(ilepStandard4Data);
        responseModel.setIlepStandard5Data(ilepStandard5Data);

        responseModel.setOverAllJudgement(ilepEvaluationForm.getOverAllJudgement());
        responseModel.setOverAllJudgementHistory(ilepEvaluationForm.getOverAllJudgementHistory());
        responseModel.setConditionFulfilmentDate(ilepEvaluationForm.getConditionFulfilmentDate());
        responseModel.setPartialMetCount(ilepEvaluationForm.getPartialMetCount());
        responseModel.setPartialMetDate(ilepEvaluationForm.getPartialMetDate());
        responseModel.setPartialMetComment(ilepEvaluationForm.getPartialMetComment());
        responseModel.setPartialMetStatus(ilepEvaluationForm.getPartialMetStatus());
        responseModel.setAmFeedbackComment(ilepEvaluationForm.getAmFeedbackComment());
        responseModel.setAmFeedbackActionHistory(ilepEvaluationForm.getAmFeedbackActionHistory());
        responseModel.setDec1(ilepEvaluationForm.getDec1());
        responseModel.setDec2(ilepEvaluationForm.getDec2());
        responseModel.setDec3(ilepEvaluationForm.getDec3());
        responseModel.setDraft(ilepEvaluationForm.getDraft());
        responseModel.setCondfil(ilepEvaluationForm.getCondfil());
        return ilepEvaluationResponseBuilder.buildGetILEPEvaluationDetailsResponse(responseModel);
    }

    @Override
    public AmUpdateReportResponse amUpdateReportStatus(Long formUniqueId, AmApproveReportRequest request) {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getByFormUniqueIdAndAssignedAppManager(formUniqueId, userId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.AM_APPROVES_REPORT_INVALID_UNIQUE_ID);
        }
        instituteForm.setStatus(ApplicationStatus.AM_APPROVES_EVALUATION_FORM.getCode());
        instituteForm.setSubStatus(null);
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(formUniqueId);
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(formUniqueId);
        documentFeedback.setAmApproveReportComment(request.getComment());
        documentFeedback.setAmApproveReportFile(request.getFileUrl());
        feedbackDao.save(documentFeedback);
        instituteForm = formDao.save(instituteForm);
        return ilepEvaluationResponseBuilder.amUpdateReportResponseBuild(StatusCode.SUCCESS.getCode(), instituteForm.getFormUniqueId());
    }

    @Override
    @Transactional
    public ILEPSubmitSummaryResponse submitILEPSummary(ILEPSubmitSummaryRequest request) throws JsonProcessingException {
        Long ilepUserID;
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.ILEP_SUBMIT_SUMMARY_APPLICATION_NOT_FOUND);
        }
        instituteForm.setSubStatus(instituteForm.getStatus());
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());

        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(ilepEvaluationForm)) {
            LOGGER.error("ILEP evaluation not completed for application :{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.ILEP_SUBMIT_SUMMARY_APPLICATION_NOT_EVALUATED);
        }

        ilepEvaluationForm.setDec1(request.getDec1());
        ilepEvaluationForm.setDec2(request.getDec2());
        ilepEvaluationForm.setDec3(request.getDec3());
        ilepEvaluationForm.setDraft(request.getDraft());
        if (!instituteForm.isRevalidationForm() && Objects.isNull(request.getCondfil())) {
            throw new ServiceException(ErrorCode.ILEP_SUBMIT_SUMMARY_APPLICATION_COND_FILL_REQUIRED);
        }

        if(!Objects.isNull(request.getCondfil()) && request.getCondfil() == 1) {
            ilepEvaluationForm.setCondfil(request.getCondfil());
        }

        if (request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode()) && !instituteForm.isRevalidationForm()) {
            ilepUserID = WebUtils.getUserId();
            if (Objects.isNull(request.getPanelId())) {
                throw new ServiceException(ErrorCode.ILEP_SUBMIT_SUMMARY_PANEL_ID_REQUIRED);
            }
            IlepPanel ilepPanel = evaluationDao.getIlepMemberId(ilepUserID, request.getPanelId());
            if (ilepPanel.getIsHead().equals(0)) {
                LOGGER.error("Only ilep chairperson is allowed to submit");
                throw new ServiceException(ErrorCode.ILEP_MEMBER_CANNOT_SUBMIT);
            }
        }

        User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        ApplicationStatus currentCompletedStatus = ApplicationStatus.INITIAL_STATUS;
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });
            currentCompletedStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        }
        ilepEvaluationForm.setOverAllJudgement(request.getOverAllJudgement());
        ilepEvaluationForm.setOverAllJudgementHistory(request.getOverAllJudgementHistory());
        ilepEvaluationForm.setConditionFulfilmentDate(request.getConditionFulfilmentDate());
        if (request.getOverAllJudgement().equals(JudgementEnum.MET.getCode())) {
            if (!completedStatus.contains(ApplicationStatus.ILEP_EVALUATE_LISTED.getCode())) {
                completedStatus.add(ApplicationStatus.ILEP_EVALUATE_LISTED.getCode());
            }
            instituteForm.setSubStatus(ApplicationStatus.ILEP_EVALUATE_LISTED.getCode());
        } else if (request.getOverAllJudgement().equals(JudgementEnum.PARTIALLY_MET.getCode())) {
            if (!completedStatus.contains(ApplicationStatus.ILEP_EVALUATE_DEFERRED.getCode())) {
                completedStatus.add(ApplicationStatus.ILEP_EVALUATE_DEFERRED.getCode());
            }
            instituteForm.setSubStatus(ApplicationStatus.ILEP_EVALUATE_DEFERRED.getCode());
            //ilepEvaluationForm.setCondfil(BooleanEnum.TRUE.getCode());
            ilepEvaluationForm.setPartialMetCount(ilepEvaluationForm.getPartialMetCount() + 1);
        } else {
            if (!completedStatus.contains(ApplicationStatus.ILEP_EVALUATE_NOT_LISTED.getCode())) {
                completedStatus.add(ApplicationStatus.ILEP_EVALUATE_NOT_LISTED.getCode());
            }
            instituteForm.setSubStatus(ApplicationStatus.ILEP_EVALUATE_NOT_LISTED.getCode());

        }
        ApplicationStatus newCompletedStatus = ApplicationStatus.getByCode(completedStatus.get(completedStatus.size() - 1));
        String completedStatusString = objectMapper.writeValueAsString(completedStatus);
        instituteForm.setCompletedStatus(completedStatusString);
        if (request.getSubmissionStatus().equals(BooleanEnum.TRUE.getCode())) {
            if (completedStatus.contains(ApplicationStatus.SITE_VISIT_DONE.getCode()) || instituteForm.isRevalidationForm() ) {
                instituteForm.setStatus(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode());
            } else if (!instituteForm.isRevalidationForm()) {
                throw new ServiceException(ErrorCode.ILEP_SUBMIT_SUMMARY_SITE_VISIT_NOT_DONE);
            }
        }
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        } else if (currentStatus.getCode().equals(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode())) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentCompletedStatus, newCompletedStatus, instituteForm.getFormUniqueId());

        }
        ilepEvaluationDao.save(ilepEvaluationForm);

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ILEP_SUBMIT_FINAL_SUMMARY");
        if (Objects.nonNull(mailTemplate) && amDetails.getActive()==1) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                mailBody = mailBody.replace("{userName}", amDetails.getUsername());
            }
            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
//            List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(amDetails.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }
     /*   User user = commonUtils.getUserDetails(UserType.APPLICATION_MANAGER.getCode(), UserSubType.MANAGER.getCode(), instituteForm.getFormUniqueId());
        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
        String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.ILEP_SUBMIT_FINAL_SUMMARY_TITLE, NotifcationConstatnts.ILEP_SUBMIT_FINAL_SUMMARY_MESSAGE, null, tokens);
*/
        return ilepEvaluationResponseBuilder.buildILEPSubmitSummaryResponse("Summary updated successfully");
    }

    @Override
    @Transactional
    public QACApproveReportResponse qacApprovesReport(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.QAC_APPROVES_REPORT_APPLICATION_NOT_FOUND);
        }

        if (WebUtils.getUserType().equals(UserType.DFO_MEMBER.getCode())) {
            LOGGER.error("User not authorized to perform action");
            throw new ServiceException(ErrorCode.QAC_APPROVES_REPORT_USER_NOT_AUTHORIZED);
        }

        User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
        if (instituteForm.getStatus() < ApplicationStatus.DFO_APPROVES_EVALUATION_FORM.getCode()) {
            LOGGER.error("DFO not approved the updated report");
            throw new ServiceException(ErrorCode.QAC_APPROVES_REPORT_DFO_NOT_APPROVED);
        }

        instituteForm.setStatus(ApplicationStatus.QAC_APPROVES_EVALUATION_FORM.getCode());
        formDao.save(instituteForm);

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("QAC_APPROVE_REPORT");
        if (Objects.nonNull(mailTemplate) && amDetails.getActive()==1) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                mailBody = mailBody.replace("{userName}", amDetails.getEmailId());
            }

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
//            List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(amDetails.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }

        return ilepEvaluationResponseBuilder.buildQACApproveReportResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    public DfoApprovesReportResponse dfoUpdateReportStatus(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.DFO_APPROVES_REPORT_INVALID_UNIQUE_ID);
        }
        instituteForm.setStatus(ApplicationStatus.DFO_APPROVES_EVALUATION_FORM.getCode());
        instituteForm = formDao.save(instituteForm);
        return ilepEvaluationResponseBuilder.dfoApprovesReportResponseBuild(StatusCode.SUCCESS.getCode(), instituteForm.getFormUniqueId());
    }

    @Override
    public IlepEvaluateResponse ilepUpdateEvaluation(IlepUpdateEvaluationRequest request) {
        Long ilepUserID;
        InstituteForm instituteForm1 = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm1 == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (ilepEvaluationForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.ILEP_APPLICATION_NOT_EVALUATED);
        }

        if (request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode())) {
            ilepUserID = WebUtils.getUserId();
            Integer userType = WebUtils.getUserType();
            if (UserType.ILEP_MEMBER.getCode().equals(userType)) {
                IlepPanel ilepPanel = evaluationDao.getIlepMemberId(ilepUserID, request.getPanelId());
                if (ilepPanel.getIsHead().equals(0)) {
                    LOGGER.error("Only ilep chairperson is allowed to submit");
                    throw new ServiceException(ErrorCode.ILEP_MEMBER_CANNOT_SUBMIT);
                }
            }
        }

        updateStandard1Status(ilepEvaluationForm, request);
        updateStandard2Status(ilepEvaluationForm, request);
        updateStandard3Status(ilepEvaluationForm, request);
        updateStandard4Status(ilepEvaluationForm, request);
        updateStandard5Status(ilepEvaluationForm, request);

        ilepEvaluationForm.setOverAllJudgement(request.getOverAllJudgement());
        ilepEvaluationForm.setOverAllJudgementHistory(request.getOverAllJudgementHistory());
        ilepEvaluationForm.setConditionFulfilmentDate(request.getConditionFulfilmentDate());
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (null == currentStatus) {
            throw new ServiceException(ErrorCode.INVALID_APPLICATION_STATUS);
        }
        if (request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode())) {
            switch (currentStatus) {
                case DFO_CHIEF_ADD_EVALUATION, DFO_CHIEF_ADD_COMMENTS_AGAIN ->
                        instituteForm.setStatus(ApplicationStatus.AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT.getCode());
                case GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT ->
                        instituteForm.setStatus(ApplicationStatus.AM_UPDATE_EVALUATION_AS_PER_GDQ_COMMENT.getCode());
                case QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR ->
                        instituteForm.setStatus(ApplicationStatus.DFO_DIRECTOR_UPDATE_EVALUATION_FORM_AS_PER_QAC.getCode());
                case INSTITUTION_COMMENTS_ADDED ->
                        instituteForm.setStatus(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode());
                default -> instituteForm.setStatus(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode());

            }
        }
        formDao.save(instituteForm);
        ilepEvaluationDao.save(ilepEvaluationForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        if (currentStatus.equals(ApplicationStatus.QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR)) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("QAC_UPDATED");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                }
                List<User> dfoAdmin = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
                for (User user : dfoAdmin) {
                    if(user.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", user.getUsername());
                        }
                        Map<String, Object> templateModel = new HashMap<>();

                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
    //                    List<String> ccAdresses = new ArrayList<>();
                        awsService.sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }
                }
            }
        }

        return ilepEvaluationResponseBuilder.buldIlepEvaluateResponse("Ilep updated Report");
    }

    @Override
    @Transactional
    public NoConfidentialityAgreementResponse signNoConfidentiality(Long formUniqueId, Integer status) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.INSTITUTE_SIGN_NON_CONFIDENTIAL_APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (!instituteForm.getSubStatus().equals(ApplicationStatus.INSTITUTION_UPDATES_SITE_VISIT_DOCUMENT.getCode())) {
            throw new ServiceException(ErrorCode.INSTITUTE_SIGN_NON_CONFIDENTIAL_INSTITUTE_DOCUMENT_NOT_UPLOADED);
        }*/

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.AM_UPLOAD_AGENDA_FORM.getCode())) {
            throw new ServiceException(ErrorCode.INSTITUTE_SIGN_NON_CONFIDENTIAL_AM_AGENDA_NOT_UPLOADED);
        }
        SiteVisit siteVisitDetails = ilepEvaluationDao.getSiteVisitByFormUniqueId(formUniqueId);
        if (Objects.isNull(siteVisitDetails)) {
            LOGGER.error("Site visit details not found for application :{}", formUniqueId);
            throw new ServiceException(ErrorCode.INSTITUTE_SIGN_NON_CONFIDENTIAL_SITE_VISIT_NOT_FOUND);
        }

        /*if (!(instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_REQUIRED.getCode()) ||
                instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode()))) {
            LOGGER.error("Institution not requested for site visit");
            throw new ServiceException(ErrorCode.INSTITUTE_SIGN_NON_CONFIDENTIAL_SITE_VISIT_NOT_REQUIRED);
        }*/

        siteVisitDetails.setConfidentialityStatus(status);
        ilepEvaluationDao.saveSiteVisit(siteVisitDetails);

        if (!completedStatus.contains(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode())) {
            completedStatus.add(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode());
        } else if (!completedStatus.contains(ApplicationStatus.NOT_SIGN_NON_CONFIDENTIAL.getCode())) {
            completedStatus.add(ApplicationStatus.NOT_SIGN_NON_CONFIDENTIAL.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        if (status.equals(BooleanEnum.TRUE.getCode())) {
            instituteForm.setSubStatus(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode());
        } else {
            instituteForm.setSubStatus(ApplicationStatus.NOT_SIGN_NON_CONFIDENTIAL.getCode());
        }
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        if (status.equals(BooleanEnum.TRUE.getCode())) {
            /*commented as not using now
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUE_SIGN_NON_CONFIDENTIALITY");
            if (Objects.nonNull(mailTemplate)) {
                String mailBody = mailTemplate.getTemplateBody();
                mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());

                Map<String, Object> templateModel = new HashMap<>();
                templateModel.put("userName", instituteForm.getFormUniqueId());

                List<IlepPanel> ilepPanelList = evaluationDao.getILEPByFormUniqueId(formUniqueId);
                if (!CollectionUtils.isEmpty(ilepPanelList)) {
                    for (IlepPanel ilepMember : ilepPanelList) {
                        User userDetails = userDao.getByUserId(ilepMember.getIlepMemberId());
                        mailBody = mailBody.replace("{userName}", userDetails.getUsername());
                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        awsService.sendMail(userDetails.getEmailId(), templateModel, mailHtmlPath);
                    }
                }
            }*/
           /* User user = commonUtils.getUserDetails(UserType.ILEP_MEMBER.getCode(), UserSubType.ADMIN.getCode(), instituteForm.getFormUniqueId());
            List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
            String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.INSTITUE_SIGN_NON_CONFIDENTIALITY_TITLE, NotifcationConstatnts.INSTITUE_SIGN_NON_CONFIDENTIALITY_MESSAGE, null, tokens);
*/
        }

        return ilepEvaluationResponseBuilder.buildNoConfidentialityAgreementResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    public GetSiteVisitDataResponse getSiteVisitDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.SITE_VISIT_APPLICATION_NOT_FOUND);
        }

        SiteVisit siteVisitDetails = ilepEvaluationDao.getSiteVisitByFormUniqueId(formUniqueId);
        if (Objects.isNull(siteVisitDetails)) {
            LOGGER.error("Site visit details not found for application :{}", formUniqueId);
            throw new ServiceException(ErrorCode.SITE_VISIT_NOT_FOUND);
        }
        GetSiteVisitDataResponseModel responseModel = new GetSiteVisitDataResponseModel();
        BeanUtils.copyProperties(responseModel, siteVisitDetails);
        return ilepEvaluationResponseBuilder.buildGetSiteVisitDataResponse(responseModel);
    }

    @Override
    public CreateSiteVisitResponse createSiteVisit(CreateSiteVisitRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.SITE_VISIT_CREATE_INVALID_FORM_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
      /*  if (instituteForm.getStatus() < ApplicationStatus.MEETING_COMPLETED.getCode() || null == currentStatus) {
            throw new ServiceException(ErrorCode.SITE_VISIT_CREATE_STATUS_NOT_MET);
        }*/

        List<IlepPanel> ilepPanel = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
/*  commented on 24-04-23
        if (request.getSiteVisitRequired().equals(BooleanEnum.TRUE.getCode())) {
            if (ilepPanel.size() == 2) {
                if (instituteForm.getStatus() < ApplicationStatus.ACCESS_GRANT_ILEP_2.getCode()) {
                    throw new ServiceException(ErrorCode.SITE_VISIT_CREATE_ACCESS_GRANT_ILEP_NOT_COMPLETED);
                }
            } else {
                if (instituteForm.getStatus() < ApplicationStatus.ACCESS_GRANT_ILEP_3.getCode()) {
                    throw new ServiceException(ErrorCode.SITE_VISIT_CREATE_ACCESS_GRANT_ILEP_NOT_COMPLETED);
                }
            }
        }*/

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            siteVisit = new SiteVisit();
        }
        BeanUtils.copyProperties(siteVisit, request);
        siteVisit.setVisitDate(request.getDate1());
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });
        }

        if (request.getSiteVisitRequired().equals(BooleanEnum.TRUE.getCode())) {
            if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_REQUIRED.getCode())) {
                completedStatus.add(ApplicationStatus.SITE_VISIT_REQUIRED.getCode());
            }
            instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_REQUIRED.getCode());
        } else {
            if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_NOT_REQUIRED.getCode())) {
                completedStatus.add(ApplicationStatus.SITE_VISIT_NOT_REQUIRED.getCode());
            }
            instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_NOT_REQUIRED.getCode());
        }
        String completedStatusString = objectMapper.writeValueAsString(completedStatus);
        instituteForm.setCompletedStatus(completedStatusString);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        if (request.getSiteVisitRequired().equals(BooleanEnum.TRUE.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_CREATE_SITE_VISIT");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                Date date = new Date(request.getDate1());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                Instant instant = Instant.ofEpochMilli(request.getDate1());
                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                mailBody = mailBody.replace("{amPhone}", amDetails.getContactNumber());
                mailBody = mailBody.replace("{amEmail}", amDetails.getEmailId());
                mailBody = mailBody.replace("{visitDate}",formattedDate);
                mailBody = mailBody.replace("{amName}", amDetails.getUsername());
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                for (IlepPanel ilep : ilepList) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        mailBody = mailBody.replace("{userName}", ilepMember.getUsername());

                        Map<String, Object> templateModel = new HashMap<>();

                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
            }
        }
     /*   User user = commonUtils.getUserDetails(UserType.INSTITUTION.getCode(), null, instituteForm.getFormUniqueId());
        List<String> tokens = clientRegistrationRepository.getFcmTokens(Arrays.asList(user.getUserId()), System.currentTimeMillis());
        String NotificationResponse = fireBaseUtils.sendNotification(NotifcationConstatnts.AM_CREATE_SITE_VISIT_TITLE, NotifcationConstatnts.AM_CREATE_SITE_VISIT_MESSAGE, null, tokens);
*/
        return ilepEvaluationResponseBuilder.createSiteVisitResponseBuild(StatusCode.SUCCESS.getCode(), siteVisit.getFormUniqueId());
    }

    @Override
    public GetMeetingDetailsResponse getMeetingDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.SITE_VISIT_APPLICATION_NOT_FOUND);
        }

        Meeting meeting = ilepEvaluationDao.getMeeting(formUniqueId);
        if (Objects.isNull(meeting)) {
            LOGGER.error("Meeting not found for application :{}", formUniqueId);
            throw new ServiceException(ErrorCode.MEETING_NOT_FOUND);
        }
        GetMeetingDetailsResponseModel responseModel = new GetMeetingDetailsResponseModel();
        BeanUtils.copyProperties(responseModel, meeting);
        return ilepEvaluationResponseBuilder.buildGetMeetingDetailsResponse(responseModel);
    }

    @Override
    public InstUpdateDocumentResponse institutionUpdateDocument(InstituteUpdateDocumentRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_UPDATE_DOC_INVALID_FORM_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (!(instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode()) ||
                !(instituteForm.getStatus() < ApplicationStatus.SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST.getCode() || instituteForm.getStatus() >
                        ApplicationStatus.SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE.getCode()))) {
            throw new ServiceException(ErrorCode.INSTITUTION_UPDATE_DOC_ACTION_NOT_ALLOWED);
        }*/

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_UPDATE_DOC_INVALID_SITE_VISIT);
        }
        BeanUtils.copyProperties(siteVisit, request);
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode())) {
            completedStatus.add(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return ilepEvaluationResponseBuilder.instUpdateDocumentResponseBuild(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public InstitutionAcceptDateResponse instituteAcceptSiteVisitDate(InstitutionAcceptDateRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        ObjectMapper objectMapper = new ObjectMapper();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_ACCEPT_DATE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_REQUIRED.getCode())) {
            throw new ServiceException(ErrorCode.INSTITUTION_ACCEPT_DATE_SITE_VISIT_NOT_CREATED);
        }

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_ACCEPT_DATE_SITE_VISIT_NOT_CREATED);
        }
        if (request.getAcceptedDate().equals(siteVisit.getDate1()) ||
                request.getAcceptedDate().equals(siteVisit.getDate2()) ||
                request.getAcceptedDate().equals(siteVisit.getDate3())) {
            siteVisit.setVisitDate(request.getAcceptedDate());
            ilepEvaluationDao.saveSiteVisit(siteVisit);


            if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode())) {
                completedStatus.add(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode());
            }
            instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));
            instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_INSTITUTION_ACCEPT_SITE_VISIT_DATE.getCode());
            formDao.save(instituteForm);

            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_CREATE_SITE_VISIT");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                Date date = new Date(request.getAcceptedDate());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                    mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                    mailBody = mailBody.replace("{visitDate}", format.format(date));
                    mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                    mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                }
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                for (IlepPanel ilep : ilepList) {
                    User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                        }

                        Map<String, Object> templateModel = new HashMap<>();

                        templateModel.put("mailBody", mailBody);
                        String mailHtmlPath = "mail-template.html";
    //                    List<String> ccAdresses = new ArrayList<>();
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
            }

            ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
            /*Writing Application status log to database */
            if (!currentStatus.equals(newStatus)) {
                logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
            }
        } else {
            LOGGER.error("Date was not in the given 3 date : {}", request.getAcceptedDate());
            throw new ServiceException(ErrorCode.INSTITUTION_ACCEPT_DATE_NOT_ALLOWED);
        }
        return ilepEvaluationResponseBuilder.institutionAcceptDateResponseBuild(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public SiteVisitDateChangeResponse instituteRequestSiteVisitDateChange(SiteVisitDateChangeRequest request) throws InvocationTargetException, IllegalAccessException, JsonProcessingException {

        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (!instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_REQUIRED.getCode())) {
            throw new ServiceException(ErrorCode.INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED);
        }*/

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.INSTITUTION_REQUEST_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED);
        }
        SiteVisitDateChange siteVisitDateChange = ilepEvaluationDao.getVisitDateChangeByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisitDateChange) {
            siteVisitDateChange = new SiteVisitDateChange();
        }
        siteVisit.setIsDateExtensionRequested(BooleanEnum.TRUE.getCode());
        BeanUtils.copyProperties(siteVisitDateChange, request);
        siteVisitDateChange = ilepEvaluationDao.saveSiteVisitDateChange(siteVisitDateChange);
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });
        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode())) {
            completedStatus.add(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode());
        }
        String completedStatusString = objectMapper.writeValueAsString(completedStatus);
        instituteForm.setCompletedStatus(completedStatusString);
        instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return ilepEvaluationResponseBuilder.siteVisitDateChangeResponseBuild(StatusCode.SUCCESS.getCode(), siteVisitDateChange.getFormUniqueId());
    }

    @Override
    public GetSiteVisitDateChangeResponse getSiteVisitDateChangeDetails(Long formUniqueId) throws InvocationTargetException, IllegalAccessException {

        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID);
        }
        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(formUniqueId);
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED);
        }
        SiteVisitDateChange siteVisitDateChange = ilepEvaluationDao.getVisitDateChangeByFormUniqueId(formUniqueId);
        if (null == siteVisitDateChange) {
            LOGGER.error("Site visit date  Not created for :{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_NOT_FOUND);
        }
        GetSiteVisitDateChangeResponseModel model = new GetSiteVisitDateChangeResponseModel();
        BeanUtils.copyProperties(model, siteVisitDateChange);
        return ilepEvaluationResponseBuilder.getSiteVisitDateChangeResponseBuild(model);
    }

    @Override
    public MeetingHeldResponse updateMeetingHeldStatus(MeetingHeldRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_ACTION_MEETING_HELD_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (!instituteForm.getSubStatus().equals(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode())) {
            throw new ServiceException(ErrorCode.AM_ACTION_MEETING_HELD_INSTITUTION_NON_CONFIDENTIAL_NOT_SIGNED);
        }*/

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode())) {
            completedStatus.add(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode());
        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED);
        }
        siteVisit.setReportFile(request.getReportFile());
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("POST_VISIT_OUTCOME");
        if (Objects.nonNull(mailTemplate)) {
            DueDates dueDates = userDao.findByAction(ApplicationStatus.SITE_VISIT_REPORT_UPLOAD.getCode());
            long turnAroundTime = 3;
            if (!Objects.isNull(dueDates)) {
                turnAroundTime = dueDates.getNoOfDays();
            }

            Long dueDate = instituteForm.getLastUpdatedTime()+ TimeUnit.DAYS.toMillis(turnAroundTime);
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody != null && !mailBody.isBlank()) {
                Date date = new Date(dueDate);
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                Instant lastUpdatedInstant = Instant.ofEpochMilli(instituteForm.getLastUpdatedTime());
                ZoneId bahrainZone = ZoneId.of("Asia/Bahrain");
                LocalDate lastUpdatedDate = LocalDateTime.ofInstant(lastUpdatedInstant, bahrainZone).toLocalDate();
                LocalDate deadlineDate = getDeadlineExcludingFridaysAndSaturdays(lastUpdatedDate, 5);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                mailBody = mailBody.replace("{frstDate}", formattedDate);
            }
            for (IlepPanel ilep : ilepList) {
                User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                if(ilepMember.getActive()==1){
                    if (mailBody != null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    if (mailBody != null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                    }
                }
            }
        }

        return ilepEvaluationResponseBuilder.buildMeetingHeldResponse("Meeting Held");
    }
    private LocalDate getDeadlineExcludingFridaysAndSaturdays(LocalDate startDate, int workingDays) {
        int addedDays = 0;
        LocalDate date = startDate;
        while (addedDays < workingDays) {
            date = date.plusDays(1);
            DayOfWeek day = date.getDayOfWeek();
            if (day != DayOfWeek.FRIDAY && day != DayOfWeek.SATURDAY) {
                addedDays++;
            }
        }
        return date;
    }
    @Override
    @Transactional
    public AMUpdateDateRequestResponse amUpdateDateRequest(AMUpdateDateRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_ACTION_DATE_REQUEST_APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (!instituteForm.getStatus().equals(ApplicationStatus.SITE_VISIT_DATE_EXTENSION_REQUESTED.getCode())) {
            throw new ServiceException(ErrorCode.AM_ACTION_DATE_REQUEST_SITE_VISIT_CHANGE_NOT_REQUESTED);
        }*/

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(siteVisit)) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_ACTION_DATE_REQUEST_SITE_VISIT_NOT_CREATED);
        }

        siteVisit.setVisitDate(request.getVisitDate());
        ilepEvaluationDao.saveSiteVisit(siteVisit);

        SiteVisitDateChange siteVisitDateChange = formDao.getSiteVisitDateChangeByFormUniqueId(request.getFormUniqueId());
        if (!Objects.isNull(siteVisitDateChange)) {
            siteVisitDateChange.setAmApprove(request.getAction());
            formDao.save(siteVisitDateChange);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST.getCode())) {
            completedStatus.add(ApplicationStatus.SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return ilepEvaluationResponseBuilder.buildAMUpdateDateRequestResponse();
    }

    @Override
    public GDQUploadDocumentResponse gdqUploadDocuments(GDQUploadDocumentRequest request) throws InvocationTargetException, IllegalAccessException {

        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.GDQ_UPLOAD_DOCUMENT_SITE_VISIT_DATE_CHANGE_INVALID_FORM_UNIQUE_ID);
        }
        GDQDocument document = ilepEvaluationDao.getGDQDocumentByFormUniqueId(request.getFormUniqueId());
        if (document == null) {
            document = new GDQDocument();
        }
        BeanUtils.copyProperties(document, request);
        document.setFormUpdatedBy(userId);
        document = ilepEvaluationDao.saveGDQDocument(document);
        instituteForm.setSubStatus(ApplicationStatus.GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT.getCode());
        formDao.save(instituteForm);
        return ilepEvaluationResponseBuilder.gdqUploadDocumentResponseBuild(StatusCode.SUCCESS.getCode(), document.getFormUniqueId());
    }

    @Override
    public GDQGetDocumentResponse getGdqUploadedDocuments(Long formUniqueId) throws InvocationTargetException, IllegalAccessException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GDQ_GET_DOCUMENT_INVALID_FORM_UNIQUE_ID);
        }
        GDQDocument document = ilepEvaluationDao.getGDQDocumentByFormUniqueId(formUniqueId);
        if (document == null) {
            LOGGER.error("Document not found {}", formUniqueId);
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        GDQGetDocumentResponseModel model = new GDQGetDocumentResponseModel();
        BeanUtils.copyProperties(model, document);
        return ilepEvaluationResponseBuilder.gdqGetDocumentResponseBuild(model);
    }

    @Override
    public AllowGrantAccessResponse allowGrantAccess(AllowGrantAccessRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        if (instituteForm.isRevalidationForm()) {
            instituteForm.setStatus(ApplicationStatus.REVALIDATION_AM_VALIDATION_TO_BE_SUBMITTED.getCode());
        } else {
            instituteForm.setStatus(ApplicationStatus.ACCESS_GRANT.getCode());
        }
        formDao.save(instituteForm);
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_GRANT_ACCESS_EVALUATION_PANEL");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
            String mailBody = mailTemplate.getTemplateBody();
            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            for (IlepPanel ilep : ilepList) {
                User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
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
        return ilepEvaluationResponseBuilder.buildAllowGrantAccessResponse("Access Granted");
    }

    @Override
    public AMUpdateSharedDocStatusResponse updateAmDocSharedStatus(AMUpdateSharedDocStatusRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        instituteForm.setStatus(ApplicationStatus.AM_SHARE_TO_GDQ_AC_COMMITTIE.getCode());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        documentFeedback.setGdqAcSharedComment(request.getComment());
        documentFeedback.setGdqAcSharedFile(request.getFileUrl());
        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return ilepEvaluationResponseBuilder.buildAMUpdateSharedDocStatusResponse("Doc Shared");
    }

    @Override
    public GDQReviewCompletedResponse updateGdqReviewCompleted(GDQReviewCompletedRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        instituteForm.setSubStatus(ApplicationStatus.GDQ_AC_COMMITTIE_APPROVED.getCode());
        formDao.save(instituteForm);
        return ilepEvaluationResponseBuilder.buildGDQReviewCompletedResponse("GDQ review Completed");
    }

    @Override
    public AMPartiallyMetUpdateResponse amUpdatePartiallyMetStatus(AMPartiallyMetUpdateRequest request) throws InvocationTargetException, IllegalAccessException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_UPDATE_PARTIAL_MET_STATUS_INVALID_FORM_UNIQUE_ID);
        }

        ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (null == evaluationForm) {
            LOGGER.error("Application not evaluated : {}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_UPDATE_PARTIAL_MET_STATUS_ILEP_NOT_EVALUATED);
        }
        if (instituteForm.getStatus().equals(ApplicationStatus.ILEP_EVALUATE_LISTED.getCode())) {
            LOGGER.error("Application already met : {}", request.getFormUniqueId());
        }

        BeanUtils.copyProperties(evaluationForm, request);
        evaluationForm.setPartialMetStatus(BooleanEnum.TRUE.getCode());
        instituteForm.setStatus(ApplicationStatus.AM_REQUESTED_FOR_ADDITIONAL_DATA_TO_RESUBMIT.getCode());
        formDao.save(instituteForm);
        evaluationForm = ilepEvaluationDao.save(evaluationForm);
        return ilepEvaluationResponseBuilder.amPartiallyMetUpdateResponseBuild(StatusCode.SUCCESS.getCode(), evaluationForm.getFormUniqueId());
    }

    @Override
    @Transactional
    public AMChangeSiteVisitDateResponse changeSiteVisitDate(AMChangeSiteVisitDateRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_CHANGE_SITE_VISIT_DATE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus() == null ? instituteForm.getStatus() : instituteForm.getSubStatus());
/*       commented on 24-04-23
        if (instituteForm.getStatus() < ApplicationStatus.SITE_VISIT_REQUIRED.getCode() ||
                instituteForm.getStatus() > ApplicationStatus.SITE_VISIT_AM_APPROVE_DATE_EXTENSION_REQUEST.getCode()) {
            throw new ServiceException(ErrorCode.AM_CHANGE_SITE_VISIT_DATE_ACTION_NOT_ALLOWED);
        }*/

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(siteVisit)) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_CHANGE_SITE_VISIT_DATE_SITE_VISIT_NOT_CREATED);
        }

        siteVisit.setVisitDate(request.getVisitDate());

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE.getCode())) {
            completedStatus.add(ApplicationStatus.SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));
        instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_AM_CHANGE_THE_SITE_VISIT_DATE.getCode());
        formDao.save(instituteForm);
        ilepEvaluationDao.saveSiteVisit(siteVisit);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getSubStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return ilepEvaluationResponseBuilder.buildAMChangeSiteVisitDateResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    @Transactional
    public AMEvaluateWithCommentResponse evaluateWithRevertComment(AMEvaluateWithCommentRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_EVALUATE_WITH_REVERT_COMMENT_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

     /*   if (!instituteForm.getStatus().equals(ApplicationStatus.ILEP_EVALUATION_SUBMITTED.getCode())) {
            throw new ServiceException(ErrorCode.AM_EVALUATE_WITH_REVERT_COMMENT_ILEP_EVALUATION_NOT_SUBMITTED);
        }*/

        /*saving am feedback comment to ILEP Evaulation details table*/
        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        ilepEvaluationForm.setAmFeedbackComment(request.getComment());
        ilepEvaluationForm.setAmFeedbackFile(request.getFileUrl());
        ilepEvaluationForm.setAmFeedbackActionHistory(request.getActionHistory());
        ilepEvaluationDao.save(ilepEvaluationForm);

        if (request.getAction()) {
            instituteForm.setStatus(ApplicationStatus.AM_EVALUATE_SUBMISSION_WITH_COMMENT.getCode());
        } else {
            instituteForm.setStatus(ApplicationStatus.AM_APPROVE_THE_EVALUATION.getCode());
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("CHIEF_APPROVAL_REMINDER");
            if (Objects.nonNull(mailTemplate)) {
                // Get CC addresses
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);

                // Get all chiefs
                List<User> chiefList = userDao.getUsersByTypeAndSubType(UserType.CHIEF.getCode(), UserSubType.ADMIN.getCode());

                String mailHtmlPath = "mail-template.html";

                for (User chief : chiefList) {
                    if (chief.getActive() == 1) {
                        // Create a fresh mail body for each chief
                        String mailBody = mailTemplate.getTemplateBody();

                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{user}", chief.getUsername());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                        }

                        // Create template model
                        Map<String, Object> templateModel = new HashMap<>();
                        templateModel.put("mailBody", mailBody);

                        // Send mail to each chief
                        awsService.sendMail(
                                chief.getEmailId(),
                                templateModel,
                                mailHtmlPath,
                                ccAdresses,
                                mailTemplate.getTemplateSubject()
                        );
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

        return ilepEvaluationResponseBuilder.buildAMEvaluateWithCommentResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    public EvaluationFeedbackUploadResponse evaluationFeedbackUloadFile(EvaluationFeedbackUploadRequest request) {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (null == ilepEvaluationForm) {
            LOGGER.error("Ilep evaluation Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_ILEP_NOT_EVALUATED);
        }
        switch (request.getType()) {
            case 1 -> {
                if (!UserType.CHIEF.getCode().equals(userType)) {
                    LOGGER.info("Unauthorized access by userId :{}, userType: {}", userId, userType);
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_DFO_CHIEF);
                }
                if (ApplicationStatus.AM_APPROVE_THE_EVALUATION.getCode() > instituteForm.getStatus()) {
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_EVALUATION_NOT_APPROVED);
                }
                ilepEvaluationForm.setDfoChiefEvaluationComment(request.getDocUrl());
                ilepEvaluationForm.setDfoChiefPlainComment(request.getFeedbackComment());
                if (ApplicationStatus.AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT.getCode().equals(instituteForm.getStatus())) {
                    instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_ADD_COMMENTS_AGAIN.getCode());
                } else {
                    instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_ADD_EVALUATION.getCode());
                }

            }
            case 2 -> {
               /* if (!UserType.APPLICATION_MANAGER.getCode().equals(userType)) { //commented on sep 18 14:24
                    LOGGER.info("Unauthorized access by userId :{}, userType: {}", userId, userType);
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_GDQ_AC);
                }*/
                if (ApplicationStatus.AM_SHARE_TO_GDQ_AC_COMMITTIE.getCode() > instituteForm.getStatus()) {
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_AM_NOT_SHARED_TO_GDQ);
                }
                ilepEvaluationForm.setGdqAcEvaluationComment(request.getDocUrl());
                ilepEvaluationForm.setGdqAcPlainComment(request.getFeedbackComment());
                instituteForm.setStatus(ApplicationStatus.GDQ_AC_COMMITTIE_MEMBER_UPDATE_COMMENT.getCode());
            }
            case 3 -> {
                if (!UserType.APPLICATION_MANAGER.getCode().equals(userType)) {
                    LOGGER.info("Unauthorized access by userId :{}, userType: {}", userId, userType);
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_PERMISSION_FOR_AM);
                }
                if (ApplicationStatus.DOC_SHARED_TO_QAC_BY_DIRECTOR.getCode() > instituteForm.getStatus()) {
                    throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_AM_NOT_SHARED_TO_QAC);
                }
                ilepEvaluationForm.setQacEvaluationComment(request.getDocUrl());
                ilepEvaluationForm.setQacPlainComment(request.getFeedbackComment());
                instituteForm.setStatus(ApplicationStatus.QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR.getCode());
            }
            default -> throw new ServiceException(ErrorCode.EVALUATION_FEEDBACK_FILE_UPLOAD_INVALID_TYPE);
        }
        ilepEvaluationDao.save(ilepEvaluationForm);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        if (request.getType().equals(1)) {
            if (!ApplicationStatus.AM_EDIT_EVALUATION_BASED_ON_DFO_CHIEF_COMMENT.getCode().equals(instituteForm.getStatus())) {
                if (ilepEvaluationForm.getCondfil().equals(BooleanEnum.TRUE.getCode()) && ilepEvaluationForm.getOverAllJudgement().equals(JudgementEnum.MET.getCode())) {
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("CONDITION_FULFILMENT_DEADLINE");
                    if (Objects.nonNull(mailTemplate)) {
                        List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);

                        Map<String, Object> templateModel = new HashMap<>();
                        DueDates dueDates = userDao.findByAction(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode());
                        long turnAroundTime = 3;
                        if (!Objects.isNull(dueDates)) {
                            turnAroundTime = dueDates.getNoOfDays();
                        }
                        ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(instituteForm.getFormUniqueId());
                        long dueDate;
                        if (Objects.isNull(evaluationForm)) {
                            dueDate = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(turnAroundTime);
                        } else {
                            dueDate = evaluationForm.getPartialMetDate() + TimeUnit.DAYS.toMillis(turnAroundTime);
                        }


                        List<Ilep> ilepList = ilepAssignDao.findByFormUniqueIdAndIsHistory(instituteForm.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                        User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                        ilepList.forEach(ilep -> {
                            User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                            if(ilepMember.getActive()==1){
                                String mailBody = mailTemplate.getTemplateBody();
                                if (mailBody != null && !mailBody.isBlank()) {
                                    Date date = new Date(dueDate);
                                    DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                                    format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                                    mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                                    mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                                    mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                                    mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                                    mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                                    mailBody = mailBody.replace("{proposedDate}", format.format(date));
                                }
                                String mailHtmlPath = "mail-template.html";
                                templateModel.put("mailBody", mailBody);
                                awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            }
                        });
                    }
                }
            }
        }
        return ilepEvaluationResponseBuilder.evaluationFeedbackUploadResponseBuild(instituteForm.getFormUniqueId(), BooleanEnum.TRUE.getCode());
    }

    @Override
    public FactualDeferedResponse factualDeferedStarted(FactualDeferedRequest request) {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.AM_FACTUAL_DEFERED_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (!instituteForm.getStatus().equals(ApplicationStatus.GDQ_AC_COMMITTIE_APPROVED.getCode())) {
            throw new ServiceException(ErrorCode.AM_FACTUAL_DEFERED_STATUS_NOT_MET);
        }
        ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (null == evaluationForm) {
            LOGGER.error("Application not evaluated : {}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_UPDATE_PARTIAL_MET_STATUS_ILEP_NOT_EVALUATED);
        }
        /**
         * copying ilep evaluation to new table on factual accuracy
         */
        try {
            GetILEPEvaluationDetailsResponse ilepEvaluationDetailsResponse = ilepEvaluationService.getILEPEvaluationDetails(request.getFormUniqueId());
            GetILEPEvaluationDetailsResponseModel ilepEvaluationModel = ilepEvaluationDetailsResponse.getData();
            ObjectMapper objectMapper = new ObjectMapper();
            String evaluationJsonString = objectMapper.writeValueAsString(ilepEvaluationModel);
            IlepEvaluationReportCopy ilepEvaluationReportCopy = ilepEvaluationDao.getEvalCopyByFormUniqueId(request.getFormUniqueId());
            if(null == ilepEvaluationReportCopy) {
                ilepEvaluationReportCopy = new IlepEvaluationReportCopy();
                ilepEvaluationReportCopy.setFormUniqueId(request.getFormUniqueId());
            }
            ilepEvaluationReportCopy.setReportAsJsonString(evaluationJsonString);
            ilepEvaluationDao.save(ilepEvaluationReportCopy);

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        evaluationForm.setPartialMetComment(request.getPartialMetComment());
        evaluationForm.setPartialMetDate(request.getPartialMetDate());
        evaluationForm.setPartialMetFile(request.getPartialMetFileUrl());
        instituteForm.setStatus(ApplicationStatus.AM_CONDITION_FULFILLMENT_STARTED_WITH_DIFFERED.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        ilepEvaluationDao.save(evaluationForm);

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("FACTUAL_ACCURACY_DEFFERED");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
//            mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            User user = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
            if (mailBody != null && !mailBody.isBlank()) {
                Date date = new Date(request.getPartialMetDate());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                Instant instant = Instant.ofEpochMilli(request.getPartialMetDate());
                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                mailBody = mailBody.replace("{userName}", user.getUsername());
                mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                mailBody = mailBody.replace("{dueDate}",formattedDate);
            }
            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
//            List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }

        return ilepEvaluationResponseBuilder.factualDeferedResponseBuild(instituteForm.getFormUniqueId(), BooleanEnum.TRUE.getCode());
    }

    @Override
    public SubmitAgendaFormResponse uploadAgendaForm(SubmitAgendaFormRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            throw new ServiceException(ErrorCode.SITE_VISIT_NOT_FOUND);
        }
        siteVisit.setAgendaForm(request.getAgendaDocUrl());
        siteVisit.setAgendaFormComment(request.getComment());
        siteVisit.setInstituteUploadExtraEvidenceDeadLine(request.getInstituteUploadExtraEvidenceDeadLine());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode())) {
            completedStatus.add(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode());
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        formDao.save(instituteForm);

//        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("VISIT_REQUIRED_DOCUMENTS");
//        if (Objects.nonNull(mailTemplate)) {
//            DueDates dueDateData =  userDao.findByAction(ApplicationStatus.AM_UPLOAD_AGENDA_FORM.getCode());
//            Integer dueDate = 3;
//            if( !Objects.isNull(dueDateData)) {
//                dueDate = dueDateData.getNoOfDays();
//            }
//            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
//            String mailBody = mailTemplate.getTemplateBody();
//
////                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
//            Map<String, Object> templateModel = new HashMap<>();
//
//            User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
//            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
//            if (mailBody !=null && !mailBody.isBlank()) {
//                Date date = new Date(siteVisit.getInstituteUploadExtraEvidenceDeadLine());
//                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
//                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
//                Instant instant = Instant.ofEpochMilli(siteVisit.getInstituteUploadExtraEvidenceDeadLine());
//                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                String formattedDate = deadlineDate.format(formatter);
//                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
//                mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
//                mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
//                mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
//                mailBody = mailBody.replace("{dueDate}",formattedDate);
//                mailBody = mailBody.replace("{instituteName}",instituteForm.getQualificationTitle());
//            }
//            String mailHtmlPath = "mail-template.html";
//            templateModel.put("mailBody", mailBody);
//            awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
//        }
        MailTemplate mailTemplate1 = mailTemplateDao.getByTemplateCode("INSTITUE_SIGN_NON_CONFIDENTIALITY");
        if (Objects.nonNull(mailTemplate1)) {
            DueDates dueDateData =  userDao.findByAction(ApplicationStatus.SIGN_NON_CONFIDENTIAL.getCode());
            Integer dueDate = 3;
            if( !Objects.isNull(dueDateData)) {
                dueDate = dueDateData.getNoOfDays();
            }
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate1, instituteForm);
            String mailBody = mailTemplate1.getTemplateBody();

//                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            Map<String, Object> templateModel = new HashMap<>();

            User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            if (mailBody !=null && !mailBody.isBlank()) {
                Date date = new Date(siteVisit.getInstituteUploadExtraEvidenceDeadLine());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                Instant instant = Instant.ofEpochMilli(siteVisit.getInstituteUploadExtraEvidenceDeadLine());
                LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = deadlineDate.format(formatter);
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                mailBody = mailBody.replace("{dueDate}",formattedDate);
                mailBody = mailBody.replace("{instituteName}",instituteForm.getQualificationTitle());

            }
            String mailHtmlPath = "mail-template.html";
            templateModel.put("mailBody", mailBody);
            awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate1.getTemplateSubject());
        }
        return ilepEvaluationResponseBuilder.submitAgendaFormResponseBuild(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public InstituteUploadAgendaResponse instUploadAgendaForm(InstituteUploadAgendaRequest request) throws JsonProcessingException {
        Long userId = WebUtils.getUserId();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            throw new ServiceException(ErrorCode.SITE_VISIT_NOT_FOUND);
        }
        siteVisit.setFilledAgendaForm(request.getAgendaDocUrl());
        siteVisit.setFilledAgendaFormComment(request.getComment());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode())) {
            completedStatus.add(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.INSTITUTION_UPDATES_AGENDA_FORM.getCode());
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        formDao.save(instituteForm);
        return ilepEvaluationResponseBuilder.InstituteUploadAgendaResponseBuild(StatusCode.SUCCESS.getCode(), request.getFormUniqueId());
    }

    @Override
    public AmEvaluatedUploadedDocResponse uploadAmEvaluatedDoc(AmEvaluatedUploadedDocRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            throw new ServiceException(ErrorCode.SITE_VISIT_NOT_FOUND);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode())) {
            completedStatus.add(ApplicationStatus.AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        siteVisit.setAmEvaluateUploadedSiteVisitDoc(request.getDocUrl());
        instituteForm.setSubStatus(ApplicationStatus.AM_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode());

        ilepEvaluationDao.saveSiteVisit(siteVisit);
        formDao.save(instituteForm);
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("SITE_VISIT_EXTRA_EVIDENCE");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            Date date = new Date(siteVisit.getDate1());
            DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
            Instant instant = Instant.ofEpochMilli(siteVisit.getDate1());
            LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = deadlineDate.format(formatter);
            mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
            mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
            mailBody = mailBody.replace("{frstDate}", formattedDate);
            mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
            mailBody = mailBody.replace("{amName}", amDetails.getUsername());
            mailBody = mailBody.replace("{amEmail}", amDetails.getEmailId());
            mailBody = mailBody.replace("{amPhone}", amDetails.getContactNumber());
            mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
            List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
            for (IlepPanel ilep : ilepList) {
                User ilepMember = userDao.getByUserId(ilep.getIlepMemberId());
                if(ilepMember.getActive()==1){
                    mailBody = mailBody.replace("{userName}", ilepMember.getUsername());

                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
                    awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    mailBody = mailBody.replace( ilepMember.getUsername(),"{userName}");
                }
            }
        }
        return ilepEvaluationResponseBuilder.amEvaluatedUploadedDocResponseBuild(StatusCode.SUCCESS.getCode());
    }

    @Override
    public IlepEvaluatedUploadedDocResponse uploadIlepEvaluatedDoc(IlepEvaluatedUploadedDocRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INVALID_FORM_UNIQUE_ID);
        }

        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            throw new ServiceException(ErrorCode.SITE_VISIT_NOT_FOUND);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.ILEP_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode())) {
            completedStatus.add(ApplicationStatus.ILEP_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.ILEP_UPLOAD_EVALUATED_INSTITUTION_UPLOADED_FILE.getCode());
        formDao.save(instituteForm);
        siteVisit.setIlepEvaluateUploadedSiteVisitDoc(request.getDocUrl());
        ilepEvaluationDao.saveSiteVisit(siteVisit);

        return ilepEvaluationResponseBuilder.ilepEvaluatedUploadedDocResponseBuild(StatusCode.SUCCESS.getCode());
    }

    @Override
    public SiteVisitDoneResponse updateSiteVisitStatus(SiteVisitDoneRequest request) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_ACTION_MEETING_HELD_INVALID_FORM_UNIQUE_ID);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> completedStatus = new ArrayList<>();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });

        }
        if (!completedStatus.contains(ApplicationStatus.SITE_VISIT_DONE.getCode())) {
            completedStatus.add(ApplicationStatus.SITE_VISIT_DONE.getCode());
        }
        instituteForm.setCompletedStatus(objectMapper.writeValueAsString(completedStatus));

        instituteForm.setSubStatus(ApplicationStatus.SITE_VISIT_DONE.getCode());
        SiteVisit siteVisit = ilepEvaluationDao.getSiteVisitByFormUniqueId(request.getFormUniqueId());
        if (null == siteVisit) {
            LOGGER.error("Site visit Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.GET_SITE_VISIT_DATE_CHANGE_SITE_VISIT_NOT_CREATED);
        }
        ilepEvaluationDao.saveSiteVisit(siteVisit);
        formDao.save(instituteForm);

        return ilepEvaluationResponseBuilder.buildSiteVisitDoneResponse("Site Visit Held");
    }

    private IlepStandard1Data setIlepStandard1Data(ILEPEvaluationForm ilepEvaluationForm) {
        IlepStandard1Data ilepStandard1Data = new IlepStandard1Data();
        ilepStandard1Data.setActualAndTangibleNeedComments(ilepEvaluationForm.getStd_1_1());
        ilepStandard1Data.setStakeholdersFeedbackComments(ilepEvaluationForm.getStd_1_2());
        ilepStandard1Data.setCareerProgressionAndLearningPathwaysComments(ilepEvaluationForm.getStd_1_3());
        ilepStandard1Data.setConditions(ilepEvaluationForm.getStandard1Condition());
        ilepStandard1Data.setSuggestions(ilepEvaluationForm.getStandard1Suggestion());
        ilepStandard1Data.setJudgement(ilepEvaluationForm.getStandard1Judgement());
        ilepStandard1Data.setJudgementHistory(ilepEvaluationForm.getStandard1JudgementHistory());
        return ilepStandard1Data;
    }

    private IlepStandard2Data setIlepStandard2Data(ILEPEvaluationForm ilepEvaluationForm) {
        IlepStandard2Data ilepStandard2Data = new IlepStandard2Data();

        ilepStandard2Data.setQualificationlicenseAndApproval(ilepEvaluationForm.getStd_2_1());
        ilepStandard2Data.setQualificationAccessAndTransferComments(ilepEvaluationForm.getStd_2_2());
        ilepStandard2Data.setQualificationGraduationRequirementsComments(ilepEvaluationForm.getStd_2_3());
        ilepStandard2Data.setQualificationAlignmentAndBenchmarkingComments(ilepEvaluationForm.getStd_2_4());
        ilepStandard2Data.setQualificationInternalAndExternalEvaluationAndReviewComments(ilepEvaluationForm.getStd_2_5());
        ilepStandard2Data.setMappingAndConfirmationProcessesComments(ilepEvaluationForm.getStd_2_6());
        ilepStandard2Data.setProgrammeAccreditationComments(ilepEvaluationForm.getStd_2_7());
        ilepStandard2Data.setConditions(ilepEvaluationForm.getStandard2Condition());
        ilepStandard2Data.setSuggestions(ilepEvaluationForm.getStandard2Suggestion());
        ilepStandard2Data.setJudgement(ilepEvaluationForm.getStandard2Judgement());
        ilepStandard2Data.setJudgementHistory(ilepEvaluationForm.getStandard2JudgementHistory());


        return ilepStandard2Data;
    }

    private IlepStandard3Data setIlepStandard3Data(ILEPEvaluationForm ilepEvaluationForm) {
        IlepStandard3Data ilepStandard3Data = new IlepStandard3Data();

        ilepStandard3Data.setQualificationTitleComments(ilepEvaluationForm.getStd_3_1());
        ilepStandard3Data.setLearningOutcomesComments(ilepEvaluationForm.getStd_3_2());
        ilepStandard3Data.setQualificationAttendanceAndDeliveryModesComments(ilepEvaluationForm.getStd_3_3());
        ilepStandard3Data.setQualificationStructureAndDurationComments(ilepEvaluationForm.getStd_3_4());
        ilepStandard3Data.setQualificationContentComments(ilepEvaluationForm.getStd_3_5());
        ilepStandard3Data.setProgressionAndFlowComments(ilepEvaluationForm.getStd_3_6());
        ilepStandard3Data.setUnitInformationComments(ilepEvaluationForm.getStd_3_7());
        ilepStandard3Data.setLearningResourcesAndLearnerSupportComments(ilepEvaluationForm.getStd_3_8());
        ilepStandard3Data.setLearnersWithSpecialNeedsComments(ilepEvaluationForm.getStd_3_9());
        ilepStandard3Data.setConditions(ilepEvaluationForm.getStandard3Condition());
        ilepStandard3Data.setSuggestions(ilepEvaluationForm.getStandard3Suggestion());
        ilepStandard3Data.setJudgement(ilepEvaluationForm.getStandard3Judgement());
        ilepStandard3Data.setJudgementHistory(ilepEvaluationForm.getStandard3JudgementHistory());

        return ilepStandard3Data;
    }

    private IlepStandard4Data setIlepStandard4Data(ILEPEvaluationForm ilepEvaluationForm) {
        IlepStandard4Data ilepStandard4Data = new IlepStandard4Data();

        ilepStandard4Data.setAssessmentDesignComments(ilepEvaluationForm.getStd_4_1());
        ilepStandard4Data.setInternalAndExternalVerificationAndModerationofAssessmentComments(ilepEvaluationForm.getStd_4_2());
        ilepStandard4Data.setMarkingCriteriaComments(ilepEvaluationForm.getStd_4_3());
        ilepStandard4Data.setMeasuringTheAchievementofLearningOutcomesComments(ilepEvaluationForm.getStd_4_4());
        ilepStandard4Data.setFeedbackToLearnersComments(ilepEvaluationForm.getStd_4_5());
        ilepStandard4Data.setAppealAgainstAssessmentResultComments(ilepEvaluationForm.getStd_4_6());
        ilepStandard4Data.setTheIntegrityofAssessmentComments(ilepEvaluationForm.getStd_4_7());
        ilepStandard4Data.setConditions(ilepEvaluationForm.getStandard4Condition());
        ilepStandard4Data.setSuggestions(ilepEvaluationForm.getStandard4Suggestion());
        ilepStandard4Data.setJudgement(ilepEvaluationForm.getStandard4Judgement());
        ilepStandard4Data.setJudgementHistory(ilepEvaluationForm.getStandard4JudgementHistory());

        return ilepStandard4Data;
    }

    private IlepStandard5Data setIlepStandard5Data(ILEPEvaluationForm ilepEvaluationForm) {
        IlepStandard5Data ilepStandard5Data = new IlepStandard5Data();

        ilepStandard5Data.setNqfLevelComments(ilepEvaluationForm.getStd_5_1());
        ilepStandard5Data.setNqfCreditComments(ilepEvaluationForm.getStd_5_2());
        ilepStandard5Data.setCreditFrameworkAndRequirementsComments(ilepEvaluationForm.getStd_5_3());
        ilepStandard5Data.setConditions(ilepEvaluationForm.getStandard5Condition());
        ilepStandard5Data.setSuggestions(ilepEvaluationForm.getStandard5Suggestion());
        ilepStandard5Data.setJudgement(ilepEvaluationForm.getStandard5Judgement());
        ilepStandard5Data.setJudgementHistory(ilepEvaluationForm.getStandard5JudgementHistory());

        return ilepStandard5Data;
    }

    public ILEPEvaluationForm updateStandard1Status(ILEPEvaluationForm ilepEvaluationForm, IlepUpdateEvaluationRequest request) {
        LOGGER.info("Status for Standard1Status ");
        //TODO
        ilepEvaluationForm.setStd_1_1(request.getIlepStandard1Data().getActualAndTangibleNeedComments());
        ilepEvaluationForm.setStd_1_2(request.getIlepStandard1Data().getStakeholdersFeedbackComments());
        ilepEvaluationForm.setStd_1_3(request.getIlepStandard1Data().getCareerProgressionAndLearningPathwaysComments());
        ilepEvaluationForm.setStandard1Condition(request.getIlepStandard1Data().getConditions());
        ilepEvaluationForm.setStandard1Suggestion(request.getIlepStandard1Data().getSuggestions());
        ilepEvaluationForm.setStandard1Judgement(request.getIlepStandard1Data().getJudgement());
        ilepEvaluationForm.setStandard1JudgementHistory(request.getIlepStandard1Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm updateStandard2Status(ILEPEvaluationForm ilepEvaluationForm, IlepUpdateEvaluationRequest request) {
        LOGGER.debug("Status for Standard2Status");
        //TODO
        ilepEvaluationForm.setStd_2_1(request.getIlepStandard2Data().getQualificationlicenseAndApproval());

        ilepEvaluationForm.setStd_2_2(request.getIlepStandard2Data().getQualificationAccessAndTransferComments());

        ilepEvaluationForm.setStd_2_3(request.getIlepStandard2Data().getQualificationGraduationRequirementsComments());

        ilepEvaluationForm.setStd_2_4(request.getIlepStandard2Data().getQualificationAlignmentAndBenchmarkingComments());

        ilepEvaluationForm.setStd_2_5(request.getIlepStandard2Data().getQualificationInternalAndExternalEvaluationAndReviewComments());

        ilepEvaluationForm.setStd_2_6(request.getIlepStandard2Data().getMappingAndConfirmationProcessesComments());

        ilepEvaluationForm.setStd_2_7(request.getIlepStandard2Data().getProgrammeAccreditationComments());
        ilepEvaluationForm.setStandard2Condition(request.getIlepStandard2Data().getConditions());
        ilepEvaluationForm.setStandard2Suggestion(request.getIlepStandard2Data().getSuggestions());
        ilepEvaluationForm.setStandard2Judgement(request.getIlepStandard2Data().getJudgement());
        ilepEvaluationForm.setStandard2JudgementHistory(request.getIlepStandard2Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm updateStandard3Status(ILEPEvaluationForm ilepEvaluationForm, IlepUpdateEvaluationRequest request) {
        LOGGER.debug("Status for Standard3Status");
        //TODO
        ilepEvaluationForm.setStd_3_1(request.getIlepStandard3Data().getQualificationTitleComments());
        ilepEvaluationForm.setStd_3_2(request.getIlepStandard3Data().getLearningOutcomesComments());
        ilepEvaluationForm.setStd_3_3(request.getIlepStandard3Data().getQualificationAttendanceAndDeliveryModesComments());
        ilepEvaluationForm.setStd_3_4(request.getIlepStandard3Data().getQualificationStructureAndDurationComments());
        ilepEvaluationForm.setStd_3_5(request.getIlepStandard3Data().getQualificationContentComments());
        ilepEvaluationForm.setStd_3_6(request.getIlepStandard3Data().getProgressionAndFlowComments());
        ilepEvaluationForm.setStd_3_7(request.getIlepStandard3Data().getUnitInformationComments());
        ilepEvaluationForm.setStd_3_8(request.getIlepStandard3Data().getLearningResourcesAndLearnerSupportComments());
        ilepEvaluationForm.setStd_3_9(request.getIlepStandard3Data().getLearnersWithSpecialNeedsComments());
        ilepEvaluationForm.setStandard3Condition(request.getIlepStandard3Data().getConditions());
        ilepEvaluationForm.setStandard3Suggestion(request.getIlepStandard3Data().getSuggestions());
        ilepEvaluationForm.setStandard3Judgement(request.getIlepStandard3Data().getJudgement());
        ilepEvaluationForm.setStandard3JudgementHistory(request.getIlepStandard3Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm updateStandard4Status(ILEPEvaluationForm ilepEvaluationForm, IlepUpdateEvaluationRequest request) {
        LOGGER.debug("Status for Standard4Status");
        //TODO
        ilepEvaluationForm.setStd_4_1(request.getIlepStandard4Data().getAssessmentDesignComments());
        ilepEvaluationForm.setStd_4_2(request.getIlepStandard4Data().getInternalAndExternalVerificationAndModerationofAssessmentComments());
        ilepEvaluationForm.setStd_4_3(request.getIlepStandard4Data().getMarkingCriteriaComments());
        ilepEvaluationForm.setStd_4_4(request.getIlepStandard4Data().getMeasuringTheAchievementofLearningOutcomesComments());
        ilepEvaluationForm.setStd_4_5(request.getIlepStandard4Data().getFeedbackToLearnersComments());
        ilepEvaluationForm.setStd_4_6(request.getIlepStandard4Data().getAppealAgainstAssessmentResultComments());
        ilepEvaluationForm.setStd_4_7(request.getIlepStandard4Data().getTheIntegrityofAssessmentComments());
        ilepEvaluationForm.setStandard4Condition(request.getIlepStandard4Data().getConditions());
        ilepEvaluationForm.setStandard4Suggestion(request.getIlepStandard4Data().getSuggestions());
        ilepEvaluationForm.setStandard4Judgement(request.getIlepStandard4Data().getJudgement());
        ilepEvaluationForm.setStandard4JudgementHistory(request.getIlepStandard4Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    public ILEPEvaluationForm updateStandard5Status(ILEPEvaluationForm ilepEvaluationForm, IlepUpdateEvaluationRequest request) {
        LOGGER.debug("Status for Standard4Status");
        //TODO
        ilepEvaluationForm.setStd_5_1(request.getIlepStandard5Data().getNqfLevelComments());
        ilepEvaluationForm.setStd_5_2(request.getIlepStandard5Data().getNqfCreditComments());
        ilepEvaluationForm.setStd_5_3(request.getIlepStandard5Data().getCreditFrameworkAndRequirementsComments());
        ilepEvaluationForm.setStandard5Condition(request.getIlepStandard5Data().getConditions());
        ilepEvaluationForm.setStandard5Suggestion(request.getIlepStandard5Data().getSuggestions());
        ilepEvaluationForm.setStandard5Judgement(request.getIlepStandard5Data().getJudgement());
        ilepEvaluationForm.setStandard5JudgementHistory(request.getIlepStandard5Data().getJudgementHistory());
        return ilepEvaluationForm;
    }

    @Override
    public GetILEPEvaluationDetailsResponse getIlepEvaluationCopy(Long formUniqueId) throws JsonProcessingException {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", formUniqueId);
            throw new ServiceException(ErrorCode.GET_ILEP_EVALUATION_COPY_INVALID_FORM_UNIQUE_ID);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        IlepEvaluationReportCopy ilepEvaluationReportCopy = ilepEvaluationDao.getEvalCopyByFormUniqueId(formUniqueId);
        if(null == ilepEvaluationReportCopy) {
            throw new ServiceException(ErrorCode.GET_ILEP_EVALUATION_COPY_NOT_FOUND);
        }
        GetILEPEvaluationDetailsResponseModel model  = objectMapper.readValue(ilepEvaluationReportCopy.getReportAsJsonString(), GetILEPEvaluationDetailsResponseModel.class);
        return ilepEvaluationResponseBuilder.buildGetILEPEvaluationDetailsResponse(model);
    }

}
