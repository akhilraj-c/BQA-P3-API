package com.mindteck.common.modules.feedback.service.impl;

import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.*;
import com.mindteck.common.modules.feedback.dao.FeedbackDao;
import com.mindteck.common.modules.feedback.models.*;
import com.mindteck.common.modules.feedback.service.FeedbackService;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.IlepEvaluationForm.service.IlepEvaluationService;
import com.mindteck.common.modules.feedback.builder.FeedbackResponseBuilder;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.models_cas.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private FormDao formDao;

    @Autowired
    private FeedbackResponseBuilder feedbackResponseBuilder;

    @Autowired
    private IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    private LogService logService;

    @Autowired
    private AwsService awsService;

    @Autowired
    UserDao userDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private IlepEvaluationService ilepEvaluationService;

    @Override
    @Transactional
    public QACSubmitFeedbackResponse qacSubmitFeedback(QACSubmitFeedbackRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.QAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID);
        }

        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        documentFeedback.setQacFeedbackDescription(request.getFeedback());
        documentFeedback.setQacFeedbackFile(request.getFeedbackFile());


        feedbackDao.save(documentFeedback);
        instituteForm.setStatus(ApplicationStatus.QAC_FEEDBACK_SUBMITTED_UPDATED_DIRECTOR.getCode());
        formDao.save(instituteForm);

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("QAC_FEEDBACK_SUBMITTED");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            List<User> dfoUserList = userDao.getUsersByTypeAndSubType(UserType.DIRECTOR.getCode(), UserSubType.ADMIN.getCode());
            for (User dfoUser: dfoUserList ) {
                if(dfoUser.getActive()==1){
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", dfoUser.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(dfoUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }

        return feedbackResponseBuilder.createQACSubmitFeedbackResponse(1);
    }

    @Override
    public SharedDocToQacStatusUpdateResponse updateDocSharedToQac(SharedDocToQacStatusUpdateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.AM_SHARED_DOC_TO_QAC_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (ilepEvaluationForm == null) {
            throw new ServiceException(ErrorCode.AM_SHARED_DOC_TO_QAC_ILEP_NOT_EVALUATED);
        }
        List<Integer> judgmentArray = List.of(OverallJudgementEnum.LISTED.getCode(), OverallJudgementEnum.NOT_LISTED.getCode());
        if (!judgmentArray.contains(ilepEvaluationForm.getOverAllJudgement())) {
            throw new ServiceException(ErrorCode.AM_SHARED_DOC_TO_QAC_STATUS_NOT_LISTED_OR_NOTLISTED);
        }
        List<Integer> allowedStatus = List.of(ApplicationStatus.GDQ_AC_COMMITTIE_APPROVED.getCode(), ApplicationStatus.ILEP_UPDATE_THE_EVALUATION_AS_LISTED_OR_NOTLISTED.getCode());
        if (!allowedStatus.contains(instituteForm.getStatus())) {
            throw new ServiceException(ErrorCode.AM_SHARED_DOC_TO_QAC_STATUS_NOT_MET);
        }
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setDocSharedToQacComment(request.getComment());
        documentFeedback.setDocSharedToQacFile(request.getFileUrl());
        feedbackDao.save(documentFeedback);

        instituteForm.setStatus(ApplicationStatus.DOC_SHARED_TO_QAC_BY_DIRECTOR.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return feedbackResponseBuilder.buildSharedDocToQacStatusUpdateResponse("Doc Shared to QAC");
    }

    @Override
    public SharedDocToNacStatusUpdateResponse updateDocSharedToNac(SharedDocToNacStatusUpdateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        instituteForm.setStatus(ApplicationStatus.DOC_SHARED_TO_NAC.getCode());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setDocSharedToNacComment(request.getComment());
        documentFeedback.setDocSharedToNacFile(request.getFileUrl());
        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("NAC_SHARED");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            List<User> dfoUserList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
            for (User dfoUser: dfoUserList ) {
                if(dfoUser.getActive()==1){
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", dfoUser.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(dfoUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }

        return feedbackResponseBuilder.buildSharedDocToNacStatusUpdateResponse("Doc Shared to NAC");
    }

    @Override
    public McuScannedDocumentCompletedStatusUpdateResponse updateMcuScannedDocumentUpdate(McuScannedDocumentCompletedStatusUpdateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setMcuScannedFile(request.getScannedDocument());
        documentFeedback.setMcuFileComment(request.getComment());

        feedbackDao.save(documentFeedback);
        instituteForm.setStatus(ApplicationStatus.MCO_UPLOADED_EDITED_DOC.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }


        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ON_MCO_UPLOAD");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            List<User> dfoUserList = userDao.getUsersByTypeAndSubType(UserType.CHIEF.getCode(), UserSubType.ADMIN.getCode());
            for (User dfoUser: dfoUserList ) {
                if(dfoUser.getActive()==1){
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", dfoUser.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(dfoUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }

        return feedbackResponseBuilder.buildMcuScannedDocumentCompletedStatusUpdateResponse("MCO scanned Document Updated");
    }

    @Override
    public DfoApprovedStatusUpdateResponse updateDfoApprovedStatus(DfoApprovedStatusUpdateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        instituteForm.setStatus(ApplicationStatus.DFO_DIRECTOR_DOC_APPROVED.getCode());
        formDao.save(instituteForm);
        return feedbackResponseBuilder.buildDfoApprovedStatusUpdateResponse("DFO approved");
    }

    @Override
    public InstitutionUpdateCommentResponse institutionUpdateComment(InstitutionUpdateCommentRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.INSTITUTION_COMMENT_BACK_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
        if (null == ilepEvaluationForm) {
            throw new ServiceException(ErrorCode.INSTITUTION_COMMENT_BACK_ILEP_NOT_EVALUATED);
        }
        if (!(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode().equals(instituteForm.getStatus()) ||
                ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_NOT_LISTED.getCode().equals(instituteForm.getStatus()))) {
            throw new ServiceException(ErrorCode.INSTITUTION_COMMENT_BACK_FACTUAL_ACCURACY_NOT_STARTED);
        }
        ilepEvaluationForm.setInstitutionCommentBack(request.getComment());
        ilepEvaluationForm.setInstitutionCommentBackFile(request.getDocUrl());
        ilepEvaluationForm.setFactualAccuracyStatus(request.getFactualAccuracyStatus());
        ilepEvaluationDao.save(ilepEvaluationForm);
        instituteForm.setStatus(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return feedbackResponseBuilder.buildInstitutionUpdateCommentResponse("Institution updated comments");
    }

    @Override
    @Transactional
    public NACApproveDocResponse nacApproveDoc(NACApproveDocRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.NAC_DOC_APPROVE_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        documentFeedback.setNacComment(request.getComment());
        documentFeedback.setNacFile(request.getFileUrl());
        documentFeedback.setNacAdditionalInfoStatus(request.getMoreInfoStatus());
        feedbackDao.save(documentFeedback);

        instituteForm.setStatus(ApplicationStatus.NAC_DOC_APPROVED_BY_DFO_ADMIN.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ON_NAC_APPROVAL");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            }
            List<User> mcoUserList = userDao.getUsersByTypeAndSubType(UserType.MCO.getCode(), UserSubType.ADMIN.getCode());
            for (User dfoUser: mcoUserList ) {
                if(dfoUser.getActive()==1){
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", dfoUser.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(dfoUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }

        return feedbackResponseBuilder.createNACApproveDocResponse(1);
    }

    @Override
    public GetDocumentFeedbackResponse getFeedbackDocumentDetails(Long formUniqueId) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueId);
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.GET_FEEDBACK_DOCUMENT_DETAIL_INVALID_FORM_UNIQUE_ID);
        }

        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(formUniqueId);
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
           // throw new ServiceException(ErrorCode.GET_FEEDBACK_DOCUMENT_DETAIL_INVALID_DOCUMENT);
        }

        ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(formUniqueId);

        GetDocumentFeedbackResponseModel responseModel = new GetDocumentFeedbackResponseModel();
        responseModel.setQacFeedback(documentFeedback.getQacFeedbackDescription());
        responseModel.setQacFeedbackFile(documentFeedback.getQacFeedbackFile());
        responseModel.setQacAdditionalInfoStatus(documentFeedback.getInstitutionAdditionalInfoStatus());
        responseModel.setInstitutionComment(documentFeedback.getInstitutionComment());
        responseModel.setSubmittedReportFile(documentFeedback.getSubmittedReportFile());
        responseModel.setNacComment(documentFeedback.getNacComment());
        responseModel.setNacAdditionInfoStatus(documentFeedback.getNacAdditionalInfoStatus());
        responseModel.setSerialNo(documentFeedback.getSerialNo());
        responseModel.setMcuScannedFile(documentFeedback.getMcuScannedFile());
        responseModel.setNacDescription(documentFeedback.getNacFeedbackDescription());
        responseModel.setNacFeedBackFile(documentFeedback.getNacFeedbackFile());
        responseModel.setMcoFileComment(documentFeedback.getMcuFileComment());
        responseModel.setDfoCommentMCO(documentFeedback.getDfoCommentMCO());
        responseModel.setDfoFileMco(documentFeedback.getDfoFileMco());
        responseModel.setDfoSignedFile(documentFeedback.getDfoSignedFile());
        responseModel.setDfoSignedStatus(documentFeedback.getDfoSignedStatus());
        responseModel.setDfoSharedCabinetStatus(documentFeedback.getDfoSharedCabinet());
        responseModel.setDfoCabinetApprovedStatus(documentFeedback.getDfoCabinetApproved());
        responseModel.setNacFile(documentFeedback.getNacFile());
        responseModel.setDfoSignedComment(documentFeedback.getDfoSignedComment());
        responseModel.setDfoSharedCabinetComment(documentFeedback.getDfoSharedCabinetComment());
        responseModel.setDfoSharedCabinetFile(documentFeedback.getDfoSharedCabinetFile());
        responseModel.setDfoCabinetApprovedComment(documentFeedback.getDfoCabinetApprovedComment());
        responseModel.setDfoCabinetApprovedFile(documentFeedback.getDfoCabinetApprovedFile());
        responseModel.setDfoApproveAppealComment(documentFeedback.getDfoApproveAppealComment());
        responseModel.setDfoApproveAppealFile(documentFeedback.getDfoApproveAppealFile());
        responseModel.setAmApproveReportComment(documentFeedback.getAmApproveReportComment());
        responseModel.setAmApproveReportFile(documentFeedback.getAmApproveReportFile());
        responseModel.setDocSharedToNacComment(documentFeedback.getDocSharedToNacComment());
        responseModel.setDocSharedToNacFile(documentFeedback.getDocSharedToNacFile());
        responseModel.setDocSharedToQacComment(documentFeedback.getDocSharedToQacComment());
        responseModel.setDocSharedToQacFile(documentFeedback.getDocSharedToQacFile());
        responseModel.setFactualAccuracyStartComment(documentFeedback.getFactualAccuracyStartComment());
        responseModel.setFactualAccuracyStartFile(documentFeedback.getFactualAccuracyStartFile());
        responseModel.setQacApproveComment(documentFeedback.getQacApproveComment());
        responseModel.setQacApproveFile(documentFeedback.getQacApproveFile());
        responseModel.setDfoChiefApproveComment(documentFeedback.getDfoChiefApproveComment());
        responseModel.setDfoChiefApproveFile(documentFeedback.getDfoChiefApproveFile());
        responseModel.setGdqAcApproveComment(documentFeedback.getGdqAcApproveComment());
        responseModel.setGdqAcApproveFile(documentFeedback.getGdqAcApproveFile());
        responseModel.setInstituteAppealComment(documentFeedback.getInstituteAppealComment());
        responseModel.setInstituteAppealFile(documentFeedback.getInstituteAppealFile());
        responseModel.setGdqAcSharedComment(documentFeedback.getGdqAcSharedComment());
        responseModel.setGdqAcSharedFile(documentFeedback.getGdqAcSharedFile());


        responseModel.setDfoChiefEvaluationComment(ilepEvaluationForm.getDfoChiefEvaluationComment());
        responseModel.setDfoChiefPlainComment(ilepEvaluationForm.getDfoChiefPlainComment());
        responseModel.setGdqAcEvaluationComment(ilepEvaluationForm.getGdqAcEvaluationComment());
        responseModel.setGdqAcPlainComment(ilepEvaluationForm.getGdqAcPlainComment());
        responseModel.setQacEvaluationComment(ilepEvaluationForm.getQacEvaluationComment());
        responseModel.setQacPlainComment(ilepEvaluationForm.getQacPlainComment());
        responseModel.setInstitutionCommentBackFile(ilepEvaluationForm.getInstitutionCommentBackFile());
        responseModel.setInstitutionCommentBack(ilepEvaluationForm.getInstitutionCommentBack());
        responseModel.setPartialMetComment(ilepEvaluationForm.getPartialMetComment());
        responseModel.setPartialMetFile(ilepEvaluationForm.getPartialMetFile());
        responseModel.setAmFeedbackComment(ilepEvaluationForm.getAmFeedbackComment());
        responseModel.setAmFeedbackFile(ilepEvaluationForm.getAmFeedbackFile());


        responseModel.setFactualAccuracyReportFile(instituteForm.getFactualAccuracyFile());
        responseModel.setApplicationStatus(instituteForm.getStatus());
        responseModel.setFactualAccuracyFile(instituteForm.getFactualAccuracyFile());
        responseModel.setFactualAccuracyComment(instituteForm.getFactualAccuracyComment());
        responseModel.setInstitutionAppealExpiry(instituteForm.getInstitutionAppealExpiry());
        responseModel.setInstituteFactualAccuracyDeadLine(instituteForm.getInstituteFactualAccuracyDeadLine());
        responseModel.setFactualAccuracyStatus(ilepEvaluationForm.getFactualAccuracyStatus());


        return feedbackResponseBuilder.createGetDocumentFeedbackResponse(responseModel);
    }

    @Override
    @Transactional
    public SaveSerialNumberResponse saveSerialNumber(SaveSerialNumberRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.MCU_SAVE_SERIAL_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setSerialNo(request.getSerialNo());
        feedbackDao.save(documentFeedback);


        instituteForm.setStatus(ApplicationStatus.MCU_DOC_GENERATED.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return feedbackResponseBuilder.createSaveSerialNumberResponse(1);
    }

    @Override
    @Transactional
    public QacSubmitReportResponse qacSubmitReport(QacSubmitReportRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }

        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setSubmittedReportFile(request.getReport());
        feedbackDao.save(documentFeedback);

        instituteForm.setStatus(ApplicationStatus.QAC_REPORT_SUBMITTED.getCode());
        formDao.save(instituteForm);
        return feedbackResponseBuilder.buildQacSubmitReportResponse("Qac submitted Report");
    }

    @Override
    public NACSubmitFeedbackResponse nacSubmitFeedback(NACSubmitFeedbackRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.NAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        documentFeedback.setNacFeedbackDescription(request.getFeedback());
        documentFeedback.setNacFeedbackFile(request.getFeedbackFile());

        feedbackDao.save(documentFeedback);
        instituteForm.setStatus(ApplicationStatus.NAC_DOC_APPROVED_BY_DFO_ADMIN.getCode());
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return feedbackResponseBuilder.createNACSubmitFeedbackResponse(1);
    }

    @Override
    public FeedbackStatusUpdateResponse feedbackStatusUpdate(FeedbackStatusUpdateRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.NAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        Integer userType = request.getUserType();
        String message = null;
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setFormUniqueId(request.getFormUniqueId());
        feedbackDao.save(documentFeedback);
        switch (userType) {
            case 3 -> {
                LOGGER.info("Dfo admin:: ");
                documentFeedback.setQacApproveComment(request.getComment());
                documentFeedback.setQacApproveFile(request.getFileUrl());
                instituteForm.setStatus(ApplicationStatus.DFO_ADMIN_CHANGE_STATUS_QAC_COMMITTE_APPROVED.getCode());
                message = "DFO Admin updated the status as QAC approved";
            }
            case 4 -> {
                LOGGER.info("Dfo chief");
                documentFeedback.setDfoChiefApproveComment(request.getComment());
                documentFeedback.setDfoChiefApproveFile(request.getFileUrl());
                instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_APPROVED_THE_REPORT.getCode());
                message = "DFO chief approved the report";
            }
            case 6 -> {
                LOGGER.info("GDQ ");
                documentFeedback.setGdqAcApproveComment(request.getComment());
                documentFeedback.setGdqAcApproveFile(request.getFileUrl());
                instituteForm.setStatus(ApplicationStatus.GDQ_AC_COMMITTIE_APPROVED.getCode());
                message = "GDQ AC Committie approved the report";
            }
            default -> throw new ServiceException(ErrorCode.INVALID_USER_TYPE);

        }
        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        if (request.getUserType().equals(UserType.DFO_ADMIN.getCode())) {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("QAC_APPROVE_REPORT");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                String mailBody = mailTemplate.getTemplateBody();
                User amUser = userDao.getByUserId(instituteForm.getAssignedAppManager());
                if (mailBody !=null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                    mailBody = mailBody.replace("{userName}", amUser.getUsername());
                }
                Map<String, Object> templateModel = new HashMap<>();

                templateModel.put("mailBody", mailBody);
                String mailHtmlPath = "mail-template.html";
//                List<String> ccAdresses = new ArrayList<>();
                if(amUser.getActive()==1){
                    awsService.sendMail(amUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }

        return feedbackResponseBuilder.buildFeedbackStatusUpdateResponse(message);
    }

    @Override
    @Transactional
    public FactualAccuracyReportResponse updateFactualAccuracy(FactualAccuracyReportRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.FACTUAL_ACCURACY_COMPLETED_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        if (!instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_COMMENTS_ADDED.getCode())) {
            throw new ServiceException(ErrorCode.FACTUAL_ACCURACY_COMPLETED_INSTITUTE_NOT_COMMENTED);
        }

        MailTemplate mailTemplate = new MailTemplate();
        instituteForm.setFactualAccuracyFile(request.getReportFile());
        instituteForm.setFactualAccuracyComment(request.getComment());
        if (request.getStatus().equals(BooleanEnum.TRUE.getCode())) {
            instituteForm.setStatus(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode());
            mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_LISTED");
        } else {
            instituteForm.setStatus(ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_NOT_LISTED.getCode());
            mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_LISTING_CONDITION_FULFILMENT_REPORT_FACTUAL_NOT_LISTED");
        }
        instituteForm.setInstitutionAppealExpiry(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000L);
        formDao.save(instituteForm);

        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            Map<String, Object> templateModel = new HashMap<>();

            Date date = new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000L);
            DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
            User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                mailBody = mailBody.replace("{dueDate}", format.format(date));
            }
            String mailHtmlPath = "mail-template.html";
            templateModel.put("mailBody", mailBody);

          /*  List<String> ccAdresses = new ArrayList<>();
            ccAdresses.add(amDetails.getEmailId());*/
            awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        return feedbackResponseBuilder.buildFactualAccuracyReportResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    @Transactional
    public FactualAccuracyStartResponse startFactualAccuracy(FactualAccuracyStartRequest request) {


        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (null == instituteForm) {
            throw new ServiceException(ErrorCode.NAC_SUBMIT_FEEDBACK_INVALID_FORM_UNIQUE_ID);
        }
        MailTemplate mailTemplate = new MailTemplate();

        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (request.getApplicationStatus() == 1) {
            mailTemplate = mailTemplateDao.getByTemplateCode("FACTUAL_ACCURACY_NOT_LISTED");
            instituteForm.setStatus(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_NOT_LISTED.getCode());
        } else {
            mailTemplate = mailTemplateDao.getByTemplateCode("FACTUAL_ACCURACY_LISTED");
            instituteForm.setStatus(ApplicationStatus.AM_FACTUAL_ACCURACY_WITH_LISTED.getCode());
        }
        /**
         * copying ilep evaluation to new table on factual accuracy
         */
//        try {
//            GetILEPEvaluationDetailsResponse ilepEvaluationDetailsResponse = ilepEvaluationService.getILEPEvaluationDetails(request.getFormUniqueId());
//            GetILEPEvaluationDetailsResponseModel ilepEvaluationModel = ilepEvaluationDetailsResponse.getData();
//            ObjectMapper objectMapper = new ObjectMapper();
//            String evaluationJsonString = objectMapper.writeValueAsString(ilepEvaluationModel);
//            IlepEvaluationReportCopy ilepEvaluationReportCopy = ilepEvaluationDao.getEvalCopyByFormUniqueId(request.getFormUniqueId());
//            if(null == ilepEvaluationReportCopy) {
//                ilepEvaluationReportCopy = new IlepEvaluationReportCopy();
//                ilepEvaluationReportCopy.setFormUniqueId(request.getFormUniqueId());
//            }
//            ilepEvaluationReportCopy.setReportAsJsonString(evaluationJsonString);
//            ilepEvaluationDao.save(ilepEvaluationReportCopy);
//
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage());
//        }
        /*************************************************/
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        instituteForm.setInstituteFactualAccuracyDeadLine(request.getInstituteFactualAccuracyDeadLine());
        documentFeedback.setFactualAccuracyStartFile(request.getFileUrl());
        documentFeedback.setFactualAccuracyStartComment(request.getComment());

        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }


        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
//            mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            User instituteUser = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                // TODO 0-09-23 date
                mailBody = mailBody.replace("{proposedDate}", instituteForm.getInstitutionName());
            }
            Map<String, Object> templateModel = new HashMap<>();

            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
            awsService.sendMail(instituteUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }

        return feedbackResponseBuilder.buildFactualAccuracyStartResponse("Factual Accuracy started");
    }

    @Override
    @Transactional
    public DFOSharedToCabinetResponse sharedReportToCabinet(DFOSharedToCabinetRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.DFO_CHIEF_SHARED_REPORT_CABINET_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
        }
        documentFeedback.setDfoSharedCabinet(request.getStatus());
        documentFeedback.setDfoSharedCabinetFile(request.getFileUrl());
        documentFeedback.setDfoSharedCabinetComment(request.getComment());

        instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_SHARED_TO_CABINET.getCode());

        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }


        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ON_SHARED_CABINET");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
            List<User> mcoUserList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
            for (User dfoUser: mcoUserList ) {
                if(dfoUser.getActive()==1){
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", dfoUser.getUsername());
                    }
                    Map<String, Object> templateModel = new HashMap<>();

                    templateModel.put("mailBody", mailBody);
                    String mailHtmlPath = "mail-template.html";
    //                List<String> ccAdresses = new ArrayList<>();
                    awsService.sendMail(dfoUser.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
        }
        return feedbackResponseBuilder.buildDFOSharedToCabinetResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    @Transactional
    public DFOAdminCabinetApprovedResponse dfoAdminCabinetApproved(DFOAdminCabinetApprovedRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.DFO_ADMIN_CABINET_APPROVED_INVALID_FORM_UNIQUE_ID);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
            documentFeedback.setFormUniqueId(request.getFormUniqueId());
        }
        documentFeedback.setDfoCabinetApproved(request.getStatus());
        documentFeedback.setDfoCabinetApprovedComment(request.getComment());
        documentFeedback.setDfoCabinetApprovedFile(request.getFileUrl());
        instituteForm.setStatus(ApplicationStatus.DFO_ADMIN_APPROVE_AS_CABINET_APPROVE.getCode());
        instituteForm.setPlacedDate(System.currentTimeMillis());

        feedbackDao.save(documentFeedback);
        formDao.save(instituteForm);


        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_LISTING_AFTER_CABINET_APPROVE");
        if (Objects.nonNull(mailTemplate)) {
            List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
            String mailBody = mailTemplate.getTemplateBody();
            User user = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
            if (mailBody !=null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{instituteName}", instituteForm.getContactPersonName());
                mailBody = mailBody.replace("{userName}", instituteForm.getQualificationTitle());
            }
            Map<String, Object> templateModel = new HashMap<>();
            User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());

            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
           /* List<String> ccAdresses = new ArrayList<>();
            ccAdresses.add(amDetails.getEmailId());*/
            awsService.sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());

        }

        return feedbackResponseBuilder.buildDFOAdminCabinetApprovedResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    public CreateAppealResponse createAppeal(CreateAppealRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        if (request.getInst_appeal().equals(1)) {
            LOGGER.info("Action to raise Appeal");

            if (instituteForm.getInstitutionAppealExpiry() > System.currentTimeMillis()) {
                LOGGER.info("Checking whether the appeal before the expiry");
                instituteForm.setInstitutionAppeal(request.getInst_appeal());
                instituteForm.setStatus(ApplicationStatus.INSTITUTION_INITIATE_AN_APPEAL_5_DAYS.getCode());
                DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
                if (Objects.isNull(documentFeedback)) {
                    documentFeedback = new DocumentFeedback();
                    documentFeedback.setFormUniqueId(request.getFormUniqueId());
                }
                documentFeedback.setInstituteAppealComment(request.getComment());
                documentFeedback.setInstituteAppealFile(request.getFileUrl());
                feedbackDao.save(documentFeedback);
                formDao.save(instituteForm);

                ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
                /*Writing Application status log to database */
                if(!currentStatus.equals(newStatus)) {
                    logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
                }

                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("ON_APPEAL_CREATE");
                if (Objects.nonNull(mailTemplate)) {
                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                    List<User> dfoChiefList = userDao.getUsersByTypeAndSubType(UserType.DIRECTOR.getCode(), UserSubType.ADMIN.getCode());
                    String mailBody = mailTemplate.getTemplateBody();
                    if (mailBody !=null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                        mailBody = mailBody.replace("{QPName}", instituteForm.getQualificationTitle());
                    }
                    for (User user : dfoChiefList) {
                        if(user.getActive()==1){
                            if (mailBody !=null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{userName}", user.getUsername());
                            }
                            Map<String, Object> templateModel = new HashMap<>();

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
    //                        List<String> ccAdresses = new ArrayList<>();
                            awsService.sendMail(user.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                        }
                    }
                }

                return feedbackResponseBuilder.buildCreateAppealResponse("Instituted had successfully raise the appeal");
            } else {
                throw new ServiceException(ErrorCode.INSTITUTION_NOT_ALLOWED_TO_APPEAL);
            }
        }
        return feedbackResponseBuilder.buildCreateAppealResponse("Instituted had not raised appeal");
    }

    @Override
    public McoCommentResponse updateMcoComment(McoCommentRequest request) {

        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        DocumentFeedback documentFeedback = feedbackDao.getByFormUniqueId(request.getFormUniqueId());
        if (Objects.isNull(documentFeedback)) {
            documentFeedback = new DocumentFeedback();
            documentFeedback.setFormUniqueId(request.getFormUniqueId());
        }
        documentFeedback.setDfoCommentMCO(request.getComment());
        documentFeedback.setDfoFileMco(request.getFileUrl());
        feedbackDao.save(documentFeedback);

        instituteForm.setStatus(ApplicationStatus.DFO_CHIEF_ADD_COMMENT_BACK_TO_MCO.getCode());
        formDao.save(instituteForm);

        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if(!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }

        return feedbackResponseBuilder.buildMcoCommentResponse("Mco comment updated");
    }
}
