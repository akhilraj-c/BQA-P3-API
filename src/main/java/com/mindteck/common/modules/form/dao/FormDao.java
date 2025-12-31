package com.mindteck.common.modules.form.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.Enum.UserType;
import com.mindteck.common.models.AppManagerMapping;
import com.mindteck.common.models.FormApplicationManager;
import com.mindteck.common.models.InstituteForm;
import com.mindteck.common.models.InstitutionBranch;
import com.mindteck.common.models.SiteVisitDateChange;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.form.rest.GetBranchesResponseModel;
import com.mindteck.common.modules.form.rest.GetFormListResponseModel;
import com.mindteck.common.modules.user.model.rest.GetInstitutesHavingQualificationResponseModel;
import com.mindteck.common.modules.user.model.rest.InstituteFormInterface;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormDao {

    InstituteForm getInstitutionFormById(Long uniqueId);

    List<GetInstitutesHavingQualificationResponseModel> getDistinctInstitutionDetails();

    List<InstituteForm> getAllQualificationForArchival(Long userId, Integer archivalStatus);

    AppManagerMapping getAppMngMapByFormUniqueIdAndUserId(Long formUniqueId, Long userId);

    AppManagerMapping saveAppManagerMapping(AppManagerMapping appManagerMappedData);

    InstituteForm save(InstituteForm instituteForm);

    QualificationProfileData save(QualificationProfileData instituteForm);

    void deleteDraftForm(String email);

    void deleteQualificationProfileData(Long formUniqueId);
    void deleteQualificationProfileData(QualificationProfileData qualificationProfileData);

    InstituteFormInterface getInstituteFormDetails(Long uniqueId);

    PagedData<GetFormListResponseModel> getFormList(Long userId,Long formUniqueId, UserType userType, String plannedSubmissonDate, Integer page, Integer limit,String searchValue) throws JsonProcessingException;

    FormApplicationManager save(FormApplicationManager formApplicationManager);

    InstitutionBranch save(InstitutionBranch institutionBranch);

    InstitutionBranch getInstitutionByFormUniqueIdBuildingRoadBlock(Long formUniqueId, String building, String road, String block);

    List<GetBranchesResponseModel> getInstitutionBranches(Long formUniqueId);

    InstituteForm getByFormUniqueIdAndAssignedAppManager(Long formUniqueId, Long userId);

    SiteVisitDateChange getSiteVisitDateChangeByFormUniqueId(Long formUniqueId);

    SiteVisitDateChange save(SiteVisitDateChange siteVisitDateChange);

    AppManagerMapping getAppManagerByFormUniqueId(Long formUniqueId);


}
