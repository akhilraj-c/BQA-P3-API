package com.mindteck.common.modules.user.dao;

import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import com.mindteck.models_cas.User;

import java.util.List;

public interface UserDao {

    User getByUserEmail(String username);

    User getUserByEmailId(String emailId);

    List<User> getUsersByEmailId(String emailId);

    User saveUser(User user);

    ForgotPassword getByEmailId(String mailId);

    ForgotPassword save(ForgotPassword forgotPassword);

    ForgotPassword getByEmailIdAndOtp(String emailId, String otp);

    void deleteForgotPassword(ForgotPassword forgotPassword);

    User getByUserId(Long userId);

    InstituteForm saveInstitutionDetails(InstituteForm instituteForm);

    InstituteForm getByMailId(String mailId);
    List<InstituteForm> getFormsByMailId(String mailId);

    InstituteForm getByLicenseAndPlannedSubDate(String license , String submissionDate);

    FormApplicationManager save(FormApplicationManager formApplicationManager);

    FormApplicationManager getByFormUniqueId(Long formUniqueId);

    QualificationProfileData getQualificationProfileDataByFormUniqueId(Long formUniqueId);


    PagedData<GetSubmissionDateListResponseModel> getSubmissionDateList(Integer page, Integer limit);

    PagedData<GetApplicationManagerListResponseModel> getApplicationManagerList();

    PagedData<GetUserDataResponseModel> getUserList(Integer page, Integer limit);

    PagedData<GetInstituteUserDataResponseModel> getInstituteUserList(Integer page, Integer limit, String searchValue);

    List<User> getUserList(List<Long> userIds);

    PagedData<GetUsersResponseModel> getUsersByTypeAndSubType(Integer type , Integer subType , Integer page , Integer limit, Integer active);

    List<User> getUsersByTypeAndSubType(Integer type , Integer subType);


    PagedData<GetDueDateResponseModel> getDueDates(Integer page, Integer limit);

    DueDates saveDueDates(DueDates dueDates);

    DueDates findByActionAndType(Integer action , Integer type);

    void saveAllDueDates(List<DueDates> dueDates);

    PlannedSubmissionDate savePlannedSubmissionDate(PlannedSubmissionDate submissionDate);

    PlannedSubmissionDate getByRegistrationDateId(Long dateId);

    void deletePlannedSubmissionDate(PlannedSubmissionDate submissionDate);

    DueCount findDueCountByFormUniqueIdAndStatus(Long formUniqueId , Integer status);

    AppStatus getByStatusId(Long statusId);

    AppStatus saveAppStatus(AppStatus appStatus);

    PagedData<GetStatusListResponseModel> getStatusList(Integer page, Integer limit);

    InstituteForm findOneByContactPersonEmailAndPlannedSubDate(String contactPersonEmail, String plannedSubDate);

    List<InstituteForm> getByPlannedSubDateAndInstAppLicNo(String plannedSubDate, String instAppLicNo);

    DueDates findByAction(Integer status);

    PagedData<GetUserDataResponseModel> getLimitedUserList(Integer page, Integer limit);

    PagedData<GetUserDataResponseModel> getILEPUserList(Integer page, Integer limit,Integer active);

    PagedData<GetUserDataResponseModel> getNonAdminNonInstituteUsers(GetDashboardUserListRequest request);

}
