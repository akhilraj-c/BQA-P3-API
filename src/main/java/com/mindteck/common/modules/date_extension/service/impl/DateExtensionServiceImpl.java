package com.mindteck.common.modules.date_extension.service.impl;

import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.FormApplicationManager;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.modules.date_extension.DateExtensionRepository;
import com.mindteck.common.modules.date_extension.model.DateExtension;
import com.mindteck.common.modules.date_extension.service.DateExtensionService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.form.builder.FormResponseBuilder;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.form.rest.DateExtensionRequest;
import com.mindteck.common.modules.form.rest.DateExtensionResponse;
import com.mindteck.repository_cas.ListingRepository;
import com.mindteck.common.modules.user.builder.UserResponseBuilder;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalRequest;
import com.mindteck.common.modules.user.model.rest.DateExtensionApprovalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DateExtensionServiceImpl implements DateExtensionService {

    @Autowired
    private FormResponseBuilder formResponseBuilder;

    @Autowired
    private UserResponseBuilder responseBuilder;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    FormDao formDao;

    @Autowired
    UserDao userDao;

    @Autowired
    IlepEvaluationDao ilepEvaluationDao;

    @Autowired
    private LogService logService;

    @Autowired
    private DateExtensionRepository dateExtensionRepository;

    @Override
    @Transactional
    public List<DateExtension> getPendingDateExtensions(Long formUniqueId) {
        return getDateExtensions(formUniqueId, DateExtensionStatus.REQUESTED.getCode());
    }

    @Override
    @Transactional
    public List<DateExtension> getDateExtensions(Long formUniqueId, Integer status) {
        return dateExtensionRepository.getDateExtensions(formUniqueId, status);
    }

    @Override
    @Transactional
    public DateExtensionResponse dateExtension(DateExtensionRequest dateExtensionRequest) {
        DateExtensionType dateExtensionType = DateExtensionType.getByCode(dateExtensionRequest.getType());
        if (null == dateExtensionType) {
            LOGGER.debug("Invalid type provided");
            throw new ServiceException(ErrorCode.DATE_EXTENSION_INVALID_TYPE);
        }
        InstituteForm instituteForm = formDao.getInstitutionFormById(dateExtensionRequest.getFormUniqueId());
        if (null == instituteForm) {
            LOGGER.debug("Institution not found , UniqueId : {}", dateExtensionRequest.getFormUniqueId());
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ApplicationStatus newStatus;
        switch (dateExtensionType) {
            case QP_ADMIN_CHECK_DATE_EXTENSION,
                    QP_VERIFICATION_DATE_EXTENSION -> {
                instituteForm.setDateExtensionStatus(DateExtensionStatus.REQUESTED.getCode());
                instituteForm.setDateExtensionReason(dateExtensionRequest.getDateExtensionReason());
                instituteForm.setRequestedExtensionDate(dateExtensionRequest.getExtensionDate());
                instituteForm.setVerificationExtensionDate(dateExtensionRequest.getVerificationExtensionDate());
                instituteForm.setEvaluationExtensionDate(dateExtensionRequest.getExtensionDate());
                instituteForm.setIsDateExtensionRequested(BooleanEnum.TRUE.getCode());
                formDao.save(instituteForm);

                saveDateExtension(dateExtensionRequest, instituteForm, DateExtensionStatus.REQUESTED);
                newStatus = ApplicationStatus.INST_DATE_EXT_REQ_COMPLETE_REG;
            }

            case BQA_DATE_EXTENSION -> {
                instituteForm.setDateExtensionStatus(DateExtensionStatus.REQUESTED.getCode());
                instituteForm.setDateExtensionReason(dateExtensionRequest.getDateExtensionReason());
                instituteForm.setRequestedExtensionDate(dateExtensionRequest.getExtensionDate());
                instituteForm.setVerificationExtensionDate(dateExtensionRequest.getVerificationExtensionDate());
                instituteForm.setEvaluationExtensionDate(dateExtensionRequest.getEvaluationExtensionDate());
                instituteForm.setIsDateExtensionRequested(BooleanEnum.TRUE.getCode());
                formDao.save(instituteForm);
                newStatus = ApplicationStatus.INST_DATE_EXT_REQ_COMPLETE_REG;
            }
            case APCMGR_DATE_EXTENSION -> {
                FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(dateExtensionRequest.getFormUniqueId());
                formApplicationManager.setExtensionRequestedDate(dateExtensionRequest.getExtensionDate());
                formApplicationManager.setExtensionRequestedStatus(DateExtensionStatus.REQUESTED.getCode());
                formApplicationManager.setReasonForExtension(dateExtensionRequest.getDateExtensionReason());
                formApplicationManager.setIsExtensionRequested(BooleanEnum.TRUE.getCode());
                userDao.save(formApplicationManager);
                newStatus = ApplicationStatus.INST_DATE_EXT_REQ_RESUBMIT_DOC;
            }
            case CONDITION_FULL_DATE_EXTENSION -> {
//                ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(dateExtensionRequest.getFormUniqueId());
//                evaluationForm.setExtensionRequestedDate(dateExtensionRequest.getExtensionDate());
//                evaluationForm.setExtensionRequestedStatus(DateExtensionStatus.REQUESTED.getCode());
//                evaluationForm.setReasonForExtension(dateExtensionRequest.getDateExtensionReason());
//                evaluationForm.setIsExtensionRequested(BooleanEnum.TRUE.getCode());
//                formDao.save(instituteForm);
//                newStatus = ApplicationStatus.INST_DATE_EXT_REQ_CONDI_FULL;
                    instituteForm.setDateExtensionStatus(DateExtensionStatus.REQUESTED.getCode());
                    instituteForm.setDateExtensionReason(dateExtensionRequest.getDateExtensionReason());
                    instituteForm.setRequestedExtensionDate(dateExtensionRequest.getExtensionDate());
                    instituteForm.setIsDateExtensionRequested(BooleanEnum.TRUE.getCode());
                    formDao.save(instituteForm);

                    saveDateExtension(dateExtensionRequest, instituteForm, DateExtensionStatus.REQUESTED);
                    newStatus = ApplicationStatus.INST_DATE_EXT_REQ_CONDI_FULL;;
            }
            default -> throw new ServiceException(ErrorCode.DATE_EXTENSION_INVALID_TYPE);
        }

        logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        return formResponseBuilder.dateExtensionResponseBuilder(StatusCode.SUCCESS.getCode(),
                dateExtensionRequest.getFormUniqueId());
    }


    @Override
    public DateExtensionApprovalResponse updateDateExtensionStatus(DateExtensionApprovalRequest request) {
        Integer dateExtensionType1 = request.getDateExtensionType();
        Integer dateExtensionStatus = request.getDateExtensionStatus();
        Long newExtensionDate = request.getNewExtensionDate();
        String log = "New Extension date : " + newExtensionDate;
        log += "\n dateExtensionType: " + dateExtensionType1;
        log += "\n dateExtensionStatus: " + dateExtensionStatus;
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            throw new ServiceException(ErrorCode.APPLICATION_DETAILS_INSTITUTE_NOT_FOUND);
        }
        if (!request.getDateExtensionStatus().equals(DateExtensionStatus.APPROVED.getCode()) && request.getNewExtensionDate() == null) {
            throw new ServiceException(ErrorCode.APPLICATION_DETAILS_NEW_EXTENSION);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        ApplicationStatus newStatus = currentStatus;
        DateExtensionType dateExtensionType = DateExtensionType.getByCode(request.getDateExtensionType());

        switch (dateExtensionType) {
            case QP_ADMIN_CHECK_DATE_EXTENSION:
            case QP_VERIFICATION_DATE_EXTENSION:


                updateDateExtension(request, instituteForm, dateExtensionStatus);
                newStatus = ApplicationStatus.INST_DATE_EXT_REQ_COMPLETE_REG;

                break;
            case BQA_DATE_EXTENSION:
                LOGGER.info("Update By BQA Admin");
                instituteForm.setDateExtensionStatus(dateExtensionStatus);
                if (Objects.equals(request.getDateExtensionStatus(), DateExtensionStatus.APPROVED.getCode())) {
                    newStatus = ApplicationStatus.DATE_EXT_APPROVED_FOR_REG;
                    if (!Objects.isNull(newExtensionDate)) {
                        instituteForm.setCurrentSubmitDate(newExtensionDate);
                        log += "\n set newExtensionDate>>getCurrentSubmitDate: " + instituteForm.getCurrentSubmitDate();

                    } else {
                        instituteForm.setCurrentSubmitDate(instituteForm.getRequestedExtensionDate());
                        log += "\n set getRequestedExtensionDatedd>>getCurrentSubmitDate: " + instituteForm.getCurrentSubmitDate();
                    }

                    Long verificationExtensionDate = request.getVerificationExtensionDate();
                    if (!Objects.isNull(verificationExtensionDate)) {
                        instituteForm.setVerificationDeadLine(verificationExtensionDate);
                        log += "\n set setVerificationDeadLine from the json request: " + instituteForm.getVerificationDeadLine();

                    } else {
                        instituteForm.setVerificationDeadLine(instituteForm.getVerificationExtensionDate());
                        log += "\n set setVerificationDeadLine from the verification extension date : " + instituteForm.getVerificationDeadLine();
                    }

                    Long evaluationExtensionDate = request.getEvaluationExtensionDate();
                    if (!Objects.isNull(evaluationExtensionDate)) {
                        instituteForm.setEvaluationDeadLine(evaluationExtensionDate);
                        log += "\n set setEvaluationDeadLine from the json request: " + instituteForm.getVerificationDeadLine();

                    } else {
                        instituteForm.setEvaluationDeadLine(instituteForm.getEvaluationExtensionDate());
                        log += "\n set setEvaluationDeadLine from the evaluation extension date : " + instituteForm.getEvaluationDeadLine();
                    }
                } else {
                    newStatus = ApplicationStatus.DATE_EXT_REJECTED_FOR_REG;
                    instituteForm.setCurrentSubmitDate(request.getNewExtensionDate());
                }
                formDao.save(instituteForm);
                break;

            case APCMGR_DATE_EXTENSION:
                LOGGER.info("Update By Application Manger");
                // FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(request.getFormUniqueId());
                // if (formApplicationManager == null) {
                ///  throw new ServiceException(ErrorCode.APPLICATION_DETAILS_INSTITUTE_NOT_FOUND);
                // }
                //saveDateExtension(request, instituteForm);
                instituteForm.setDateExtensionStatus(dateExtensionStatus);
                if (Objects.equals(request.getDateExtensionStatus(), DateExtensionStatus.APPROVED.getCode())) {
                    newStatus = ApplicationStatus.DATE_EXT_APPROVED_FOR_RESUBMIT_DOC;
                    //  formApplicationManager.setAdditionalDataSubDate(formApplicationManager.getExtensionRequestedDate());
                } else {
                    newStatus = ApplicationStatus.DATE_EXT_REJECTED_FOR_RESUBMIT_DOC;
                    // formApplicationManager.setAdditionalDataSubDate(request.getNewExtensionDate());
                }
                formDao.save(instituteForm);
                break;

            case CONDITION_FULL_DATE_EXTENSION:
                LOGGER.info("Update By Application Manger");
//                ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationDao.getByFormUniqueId(request.getFormUniqueId());
//                if (ilepEvaluationForm == null) {
//                    throw new ServiceException(ErrorCode.APPLICATION_DETAILS_INSTITUTE_NOT_FOUND);
//                }
//                ilepEvaluationForm.setExtensionRequestedStatus(dateExtensionStatus);
//                if (Objects.equals(request.getDateExtensionStatus(), DateExtensionStatus.APPROVED.getCode())) {
//                    newStatus = ApplicationStatus.DATE_EXT_APPROVED_CONDI_FULL;
//                    ilepEvaluationForm.setPartialMetDate(ilepEvaluationForm.getExtensionRequestedDate());
//                } else {
//                    newStatus = ApplicationStatus.DATE_EXT_REJECTED_CONDI_FULL;
//                    ilepEvaluationForm.setPartialMetDate(request.getNewExtensionDate());
//                }
//                ilepEvaluationDao.save(ilepEvaluationForm);
                updateDateExtension(request, instituteForm, dateExtensionStatus);
                newStatus = ApplicationStatus.DATE_EXT_APPROVED_CONDI_FULL;

                break;
        }
        logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        return responseBuilder.buildDateExtensionApprovalResponse(dateExtensionStatus, log);
    }


    private void saveDateExtension(DateExtensionRequest dateExtensionRequest, InstituteForm instituteForm, DateExtensionStatus status) {
        // dateExtensionRepository.deleteDateExtension(instituteForm.getFormUniqueId(), dateExtensionRequest.getType());
        DateExtension dateExtension = dateExtensionRepository.getDateExtension(instituteForm.getFormUniqueId(), dateExtensionRequest.getType(), status.getCode());
        if (dateExtension == null) {
            dateExtension = new DateExtension();
        }
        dateExtension.setFormUniqueId(instituteForm.getFormUniqueId());
        dateExtension.setRequestedDate(dateExtensionRequest.getExtensionDate());
        dateExtension.setReason(dateExtensionRequest.getDateExtensionReason());
        dateExtension.setStatus(status.getCode());
        dateExtension.setType(dateExtensionRequest.getType());
        dateExtensionRepository.save(dateExtension);
    }

    private void updateDateExtension(DateExtensionApprovalRequest dateExtensionRequest, InstituteForm instituteForm, Integer status) {
        // dateExtensionRepository.deleteDateExtension(instituteForm.getFormUniqueId(), dateExtensionRequest.getType());
        DateExtension dateExtension = dateExtensionRepository.getDateExtension(instituteForm.getFormUniqueId(), dateExtensionRequest.getDateExtensionType(), DateExtensionStatus.REQUESTED.getCode());
        if (dateExtension == null) {
            dateExtension = new DateExtension();
        }
        if(!Objects.isNull(dateExtensionRequest.getNewExtensionDate()) && dateExtensionRequest.getNewExtensionDate().longValue() > 0){
            dateExtension.setRequestedDate(dateExtensionRequest.getNewExtensionDate());
        }
        dateExtension.setFormUniqueId(instituteForm.getFormUniqueId());
        dateExtension.setStatus(status);
        dateExtensionRepository.save(dateExtension);

        instituteForm.setDateExtensionStatus(status);
        if(Objects.equals(status, DateExtensionStatus.APPROVED.getCode())) {
            if (Objects.equals(dateExtensionRequest.getDateExtensionType(), DateExtensionType.QP_ADMIN_CHECK_DATE_EXTENSION.getCode())) {
                instituteForm.setEvaluationDeadLine(dateExtensionRequest.getNewExtensionDate());
            } else if (Objects.equals(dateExtensionRequest.getDateExtensionType(), DateExtensionType.QP_VERIFICATION_DATE_EXTENSION.getCode())) {
                instituteForm.setVerificationDeadLine(dateExtensionRequest.getNewExtensionDate());
            }
        }
        //  instituteForm.setDateExtensionReason(dateExtensionRequest.getDateExtensionReason());
        //instituteForm.setRequestedExtensionDate(dateExtensionRequest.getExtensionDate());
        // instituteForm.setVerificationExtensionDate(dateExtensionRequest.getVerificationExtensionDate());
        //  instituteForm.setEvaluationExtensionDate(dateExtensionRequest.getExtensionDate());
        //instituteForm.setIsDateExtensionRequested(BooleanEnum.TRUE.getCode());
        formDao.save(instituteForm);
    }


}
