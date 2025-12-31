package com.mindteck.common.modules.form.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.repository.*;
import com.mindteck.common.utils.PageUtils;
import com.mindteck.common.modules.date_extension.DateExtensionRepository;
import com.mindteck.common.modules.date_extension.model.DateExtension;
import com.mindteck.common.modules.evaluation.rest.IlepConflictForm;
import com.mindteck.common.modules.evaluation.rest.InstitutionConflictData;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.form.rest.GetBranchesResponseModel;
import com.mindteck.common.modules.form.rest.GetFormListResponseModel;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.GetInstitutesHavingQualificationResponseModel;
import com.mindteck.common.modules.user.model.rest.InstituteFormInterface;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import com.mindteck.repository_cas.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class FormDaoImpl implements FormDao {

    @Autowired
    UserDao userDao;

    @Autowired
    InstitutionFormRepository institutionFormRepository;

    @Autowired
    private QualificationProfileApplicationManagerRepository qpApplicationManagerRepository;
    @Autowired
    AppManagerMappingRepository appManagerMappingRepository;

    @Autowired
    FormApplicationManagerRepository formApplicationManagerRepository;

    @Autowired
    InstitutionBranchRepository institutionBranchRepository;

    @Autowired
    SiteVisitDateChangeRepository siteVisitDateChangeRepository;
    @Autowired
    ConflictFormRepository conflictFormRepository;
    @Autowired
    ILEPEvaluationFormRepository ilepEvaluationFormRepository;

    @Autowired
    ListingRepository listingInstituteRepository;

    @Autowired
    private DateExtensionRepository dateExtensionRepository;


    @Autowired
    private DueCountRepository dueCountRepository;
    @Autowired
    private IlepPanelRepository ilepPanelRepository;

    @Override
    public InstituteForm getInstitutionFormById(Long uniqueId) {
        return institutionFormRepository.getByFormUniqueId(uniqueId);
    }

    @Override
    public List<GetInstitutesHavingQualificationResponseModel> getDistinctInstitutionDetails() {
        return listingInstituteRepository.findDistinctInstitutionDetails();
    }

    @Override
    public List<InstituteForm> getAllQualificationForArchival(Long userId, Integer archivalStatus){
        return institutionFormRepository.getAllQualificationForArchival(userId,archivalStatus);
    }

    @Override
    public AppManagerMapping getAppMngMapByFormUniqueIdAndUserId(Long formUniqueId, Long userId) {
        return appManagerMappingRepository.getByFormUniqueIdAndUserId(formUniqueId, userId);
    }

    @Override
    public void deleteQualificationProfileData(Long formUniqueId) {
        qpApplicationManagerRepository.deleteQualificationProfileData(formUniqueId);
        return;
    }

    @Override
    public void deleteQualificationProfileData(QualificationProfileData qualificationProfileData) {
        qpApplicationManagerRepository.delete(qualificationProfileData);
        return;
    }


    @Override
    public AppManagerMapping saveAppManagerMapping(AppManagerMapping appManagerMappedData) {
        return appManagerMappingRepository.save(appManagerMappedData);
    }

    @Override
    public InstituteForm save(InstituteForm instituteForm) {

        return institutionFormRepository.save(instituteForm);
    }

    @Override
    public QualificationProfileData save(QualificationProfileData instituteForm) {
        return qpApplicationManagerRepository.save(instituteForm);
    }

    @Override
    public InstituteFormInterface getInstituteFormDetails(Long uniqueId) {
        return institutionFormRepository.getInstituteFormDetails(uniqueId);
    }

    @Override
    @Transactional
    public void deleteDraftForm(String email) {
        institutionFormRepository.deleteDraftForm(email);
    }

    @Override
    public PagedData<GetFormListResponseModel> getFormList(Long userId,Long formUniqueId, UserType userType, String plannedSubmissionDate, Integer page, Integer limit,String searchValue) throws JsonProcessingException {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("lastUpdatedTime").descending());
        Page<GetFormListResponseModel> institutionList = null;
        ObjectMapper objectMapper = new ObjectMapper();

        switch (userType) {
            case INSTITUTION -> {
                try {
                    if(Objects.isNull(formUniqueId) || formUniqueId <= 0) {
                        institutionList = institutionFormRepository.getFormList(searchValue,userId, ApplicationStatus.DFO_ADMIN_REJECTED.getCode(), plannedSubmissionDate, pageable);
                    } else {
                        institutionList = institutionFormRepository.getFormListByUniqueId(searchValue,formUniqueId, ApplicationStatus.DFO_ADMIN_REJECTED.getCode(), plannedSubmissionDate, pageable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList;
                if(institutionList != null) {
                    getFormListResponseModelList = institutionList.stream().toList();
                } else{
                    getFormListResponseModelList = new ArrayList<>();
                }
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    if (getFormListResponseModel.getDateExtensionType() == DateExtensionType.APCMGR_DATE_EXTENSION.getCode()) {
                        FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                        if (null != formApplicationManager) {
                            getFormListResponseModel.setDateExtensionStatus(formApplicationManager.getExtensionRequestedStatus());
                            getFormListResponseModel.setIsDateExtensionRequested(formApplicationManager.getIsExtensionRequested());
                            getFormListResponseModel.setRequestedDate(formApplicationManager.getExtensionRequestedDate());
                            getFormListResponseModel.setDateExtensionReason(formApplicationManager.getReasonForExtension());
                        }
                    } else if (getFormListResponseModel.getDateExtensionType().equals(DateExtensionType.CONDITION_FULL_DATE_EXTENSION.getCode())) {
                       /* ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationFormRepository.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                        if (null != ilepEvaluationForm) {
                            getFormListResponseModel.setDateExtensionStatus(ilepEvaluationForm.getExtensionRequestedStatus());
                            getFormListResponseModel.setIsDateExtensionRequested(ilepEvaluationForm.getIsExtensionRequested());
                            getFormListResponseModel.setRequestedDate(ilepEvaluationForm.getExtensionRequestedDate());
                            getFormListResponseModel.setDateExtensionReason(ilepEvaluationForm.getReasonForExtension());
                        }*/
                    }
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    if (getFormListResponseModel.getRegistrationStatus() >= ApplicationStatus.AM_APPROVED_ILEP_PANEL.getCode() &&
                            getFormListResponseModel.getRegistrationStatus() < ApplicationStatus.FIRST_MEETING_CREATED.getCode()) {
                        ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                        if (conflictForm != null) {
                            if (conflictForm.getIlepConflictData() == null) {
                                if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                    List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                    });
                                    getFormListResponseModel.setConflictCount(institutionConflictData.size());
                                }
                            } else {
                                List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                                });
                                if (!conflictForms.isEmpty()) {
                                    getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                                }

                            }
                        }
                    }
                }

                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());
            }
            case DFO_ADMIN -> {
                try {
                    institutionList = institutionFormRepository.getFormList(searchValue,pageable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList = institutionList.stream().toList();
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    if (Objects.equals(getFormListResponseModel.getDateExtensionType(), DateExtensionType.APCMGR_DATE_EXTENSION.getCode())) {
                        FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                        if (null != formApplicationManager) {
                            getFormListResponseModel.setDateExtensionStatus(formApplicationManager.getExtensionRequestedStatus());
                            getFormListResponseModel.setIsDateExtensionRequested(formApplicationManager.getIsExtensionRequested());
                            getFormListResponseModel.setRequestedDate(formApplicationManager.getExtensionRequestedDate());
                            getFormListResponseModel.setDateExtensionReason(formApplicationManager.getReasonForExtension());
                        }
                    } else if (getFormListResponseModel.getDateExtensionType().equals(DateExtensionType.CONDITION_FULL_DATE_EXTENSION.getCode())) {
                        try {
                            ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationFormRepository.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                            if (null != ilepEvaluationForm) {
                                getFormListResponseModel.setDateExtensionStatus(ilepEvaluationForm.getExtensionRequestedStatus());
                                getFormListResponseModel.setIsDateExtensionRequested(ilepEvaluationForm.getIsExtensionRequested());
                                getFormListResponseModel.setRequestedDate(ilepEvaluationForm.getExtensionRequestedDate());
                                getFormListResponseModel.setDateExtensionReason(ilepEvaluationForm.getReasonForExtension());
                            }
                        } catch (Exception e){

                        }
                    }
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                    if (conflictForm != null) {
                        if (conflictForm.getIlepConflictData() == null) {
                            if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                });
                                getFormListResponseModel.setConflictCount(institutionConflictData.size());
                            }
                        } else {
                            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                            });
                            if (!conflictForms.isEmpty()) {
                                getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                            }

                        }
                    }
                }

                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());
            }
            case APPLICATION_MANAGER -> {
                try {
                   /* if(formUniqueId<=0) {
                        institutionList = institutionFormRepository.getFormList(userId, plannedSubmissionDate, pageable);
                    } else {
                        institutionList = institutionFormRepository.getFormListByUniqueId(formUniqueId, ApplicationStatus.DFO_ADMIN_APPROVED.getCode(), plannedSubmissionDate, pageable);
                    }*/
                    institutionList = institutionFormRepository.getFormList(searchValue,userId, plannedSubmissionDate, pageable);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList = institutionList.stream().toList();
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    if (getFormListResponseModel.getDateExtensionType() == DateExtensionType.APCMGR_DATE_EXTENSION.getCode()) {
                        FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                        if (null != formApplicationManager) {
                            getFormListResponseModel.setDateExtensionStatus(formApplicationManager.getExtensionRequestedStatus());
                            getFormListResponseModel.setIsDateExtensionRequested(formApplicationManager.getIsExtensionRequested());
                            getFormListResponseModel.setRequestedDate(formApplicationManager.getExtensionRequestedDate());
                            getFormListResponseModel.setDateExtensionReason(formApplicationManager.getReasonForExtension());
                        }
                    } else if (getFormListResponseModel.getDateExtensionType().equals(DateExtensionType.CONDITION_FULL_DATE_EXTENSION.getCode())) {
                        try {
                            ILEPEvaluationForm ilepEvaluationForm = ilepEvaluationFormRepository.getByFormUniqueId(getFormListResponseModel.getFormUniqueId());
                            if (null != ilepEvaluationForm) {
                                getFormListResponseModel.setDateExtensionStatus(ilepEvaluationForm.getExtensionRequestedStatus());
                                getFormListResponseModel.setIsDateExtensionRequested(ilepEvaluationForm.getIsExtensionRequested());
                                getFormListResponseModel.setRequestedDate(ilepEvaluationForm.getExtensionRequestedDate());
                                getFormListResponseModel.setDateExtensionReason(ilepEvaluationForm.getReasonForExtension());
                            }
                        } catch (Exception e){

                        }

                    }
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                    if (conflictForm != null) {
                        if (conflictForm.getIlepConflictData() == null) {
                            if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                });
                                getFormListResponseModel.setConflictCount(institutionConflictData.size());
                            }
                        } else {
                            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                            });
                            if (!conflictForms.isEmpty()) {
                                getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                            }

                        }
                    }
                }

                setQPDateExtensionDetails(getFormListResponseModelList);

                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());
            }
            case ILEP_MEMBER -> {
                try {
                    institutionList = institutionFormRepository.getFormList(searchValue,ApplicationStatus.ACCESS_GRANT_ILEP_1.getCode(), plannedSubmissionDate, userId, pageable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList = institutionList.stream().toList();
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                    if (conflictForm != null) {
                        if (conflictForm.getIlepConflictData() == null) {
                            if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                });
                                getFormListResponseModel.setConflictCount(institutionConflictData.size());
                            }
                        } else {
                            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                            });
                            if (!conflictForms.isEmpty()) {
                                getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                            }

                        }
                    }
                }
                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());


            }
            case CHIEF, DIRECTOR, MCO,GDQ,OBSERVER-> {
                try {
                    institutionList = institutionFormRepository.getFormList(searchValue,pageable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList = institutionList.stream().toList();
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                    if (conflictForm != null) {
                        if (conflictForm.getIlepConflictData() == null) {
                            if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                });
                                getFormListResponseModel.setConflictCount(institutionConflictData.size());
                            }
                        } else {
                            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                            });
                            if (!conflictForms.isEmpty()) {
                                getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                            }

                        }
                    }
                }
                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());

            }
            default -> {
                try {
                    institutionList = institutionFormRepository.getFormList(searchValue,0L, 0, plannedSubmissionDate, pageable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<GetFormListResponseModel> getFormListResponseModelList = institutionList.stream().toList();
                for (GetFormListResponseModel getFormListResponseModel : getFormListResponseModelList) {
                    Long ilepCount = ilepPanelRepository.countByFormUniqueIdAndIsDfoApproved(getFormListResponseModel.getFormUniqueId(), BooleanEnum.TRUE.getCode());
                    getFormListResponseModel.setIlepCount(ilepCount.intValue());
                    ConflictForm conflictForm = conflictFormRepository.findByFormUniqueIdAndIsHistory(getFormListResponseModel.getFormUniqueId(), BooleanEnum.FALSE.getCode());
                    if (conflictForm != null) {
                        if (conflictForm.getIlepConflictData() == null) {
                            if (conflictForm.getInstitutionConflictData() != null && !conflictForm.getInstitutionConflictData().isEmpty()) {
                                List<InstitutionConflictData> institutionConflictData = objectMapper.readValue(conflictForm.getInstitutionConflictData(), new TypeReference<List<InstitutionConflictData>>() {
                                });
                                getFormListResponseModel.setConflictCount(institutionConflictData.size());
                            }
                        } else {
                            List<IlepConflictForm> conflictForms = objectMapper.readValue(conflictForm.getIlepConflictData(), new TypeReference<List<IlepConflictForm>>() {
                            });
                            if (!conflictForms.isEmpty()) {
                                getFormListResponseModel.setConflictCount(conflictForms.stream().filter(el -> el.getNoConflictBeingMember().equals(BooleanEnum.FALSE.getCode())).toList().size());
                            }

                        }
                    }
                }
                institutionList = new PageImpl<>(getFormListResponseModelList, pageable, institutionList.getTotalElements());

            }
        }


        return PageUtils.setPageResponse(institutionList);
    }

    private void setQPDateExtensionDetails(List<GetFormListResponseModel> list) {
        try {
            if (list != null && !list.isEmpty()) {
                for (GetFormListResponseModel item : list) {
                    List<DateExtension> dateExtensionList = getDateExtensions(item.getFormUniqueId(), DateExtensionStatus.REQUESTED.getCode());
                    if (dateExtensionList != null && !dateExtensionList.isEmpty()) {
                        Integer status = DateExtensionStatus.REQUESTED.getCode();
                        item.setDateExtensionStatus(status);
                        item.setDateExtensionFlagV2(status);
                        item.setDateExtensionList(dateExtensionList);
                    }
                }
            }
        } catch (Exception e){

        }

    }

    @Transactional
    private List<DateExtension> getDateExtensions(Long formUniqueId) {
        return dateExtensionRepository.getDateExtensions(formUniqueId);
    }

    @Transactional
    private List<DateExtension> getDateExtensions(Long formUniqueId, Integer status) {
        return dateExtensionRepository.getDateExtensions(formUniqueId, status);
    }

    @Transactional
    @Override
    public FormApplicationManager save(FormApplicationManager formApplicationManager) {
        return formApplicationManagerRepository.save(formApplicationManager);
    }

    @Override
    public InstitutionBranch save(InstitutionBranch institutionBranch) {
        return institutionBranchRepository.save(institutionBranch);
    }

    @Override
    public InstitutionBranch getInstitutionByFormUniqueIdBuildingRoadBlock(Long formUniqueId, String building, String road, String block) {
        return institutionBranchRepository.findByFormUniqueIdAndBuildingAndRoadAndBlock(formUniqueId, building, road, block);
    }

    @Override
    public List<GetBranchesResponseModel> getInstitutionBranches(Long formUniqueId) {
        return institutionBranchRepository.getInstitutionBranches(formUniqueId);
    }

    @Override
    public InstituteForm getByFormUniqueIdAndAssignedAppManager(Long formUniqueId, Long userId) {
        return institutionFormRepository.getByFormUniqueIdAndAssignedAppManager(formUniqueId, userId);
    }

    @Override
    public SiteVisitDateChange getSiteVisitDateChangeByFormUniqueId(Long formUniqueId) {
        return siteVisitDateChangeRepository.findByFormUniqueId(formUniqueId);
    }

    @Override
    public SiteVisitDateChange save(SiteVisitDateChange siteVisitDateChange) {
        return siteVisitDateChangeRepository.save(siteVisitDateChange);
    }

    @Override
    public AppManagerMapping getAppManagerByFormUniqueId(Long formUniqueId) {
        return appManagerMappingRepository.getByFormUniqueId(formUniqueId);
    }


}
