package com.mindteck.common.modules.user.dao.impl;

import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.QualificationProfileData;
import com.mindteck.common.repository.*;
import com.mindteck.common.utils.PageUtils;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.repository.ForgotPasswordRepository;
import com.mindteck.common.repository.FormApplicationManagerRepository;
import com.mindteck.common.repository.InstitutionFormRepository;
import com.mindteck.models_cas.User;
import com.mindteck.repository_cas.ListingRepository;
import com.mindteck.repository_cas.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private InstitutionFormRepository institutionFormRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private QualificationProfileApplicationManagerRepository qpApplicationManagerRepository;


    @Autowired
    private FormApplicationManagerRepository formApplicationManagerRepository;

    @Autowired
    private PlannedSubmissionDateRepository plannedSubmissionDateRepository;
    @Autowired
    private DueDatesRepository dueDatesRepository;
    @Autowired
    private DueCountRepository dueCountRepository;

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Override
    public User getByUserEmail(String username) {
        return userRepository.getByEmailId(username);
    }

    @Override
    public List<User> getUsersByEmailId(String emailId) {
        return userRepository.getUsersByEmailId(emailId);
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepository.getByEmailId(emailId);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ForgotPassword getByEmailId(String mailId) {
        return forgotPasswordRepository.getByEmailId(mailId);
    }

    @Override
    public ForgotPassword save(ForgotPassword forgotPassword) {
        return forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public ForgotPassword getByEmailIdAndOtp(String emailId, String otp) {
        return forgotPasswordRepository.getByEmailIdAndOtp(emailId, otp);
    }

    @Override
    public void deleteForgotPassword(ForgotPassword forgotPassword) {
        forgotPasswordRepository.delete(forgotPassword);
    }

    @Override
    public User getByUserId(Long userId) {
        return userRepository.getByUserId(userId);
    }

    @Override
    public InstituteForm saveInstitutionDetails(InstituteForm instituteForm) {
        return institutionFormRepository.save(instituteForm);
    }


    @Override
    public InstituteForm getByMailId(String mailId) {
        return institutionFormRepository.getByContactPersonEmail(mailId);
    }

    @Override
    public List<InstituteForm> getFormsByMailId(String mailId) {
        return institutionFormRepository.getFormsByContactPersonEmail(mailId);
    }


    @Override
    public InstituteForm getByLicenseAndPlannedSubDate(String license, String submissionDate) {
        return institutionFormRepository.findOneByInstAppLicNoAndPlannedSubDate(license, submissionDate);
    }

    @Override
    public FormApplicationManager save(FormApplicationManager formApplicationManager) {
        return formApplicationManagerRepository.save(formApplicationManager);
    }

    @Override
    public FormApplicationManager getByFormUniqueId(Long formUniqueId) {
        return formApplicationManagerRepository.getByFormUniqueId(formUniqueId);
    }

    @Override
    public QualificationProfileData getQualificationProfileDataByFormUniqueId(Long formUniqueId) {
        return qpApplicationManagerRepository.getQualificationProfileDataByFormUniqueId(formUniqueId);
    }

    @Override
    public PagedData<GetSubmissionDateListResponseModel> getSubmissionDateList(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetSubmissionDateListResponseModel> submissionDateList = plannedSubmissionDateRepository.getSubmissionDateList(pageable);

        return PageUtils.setPageResponse(submissionDateList);
    }

    @Override
    public PagedData<GetApplicationManagerListResponseModel> getApplicationManagerList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<GetApplicationManagerListResponseModel> applicationManagerList = userRepository.getApplicationManager(pageable);
        return PageUtils.setPageResponse(applicationManagerList);
    }

    @Override
    public PagedData<GetUserDataResponseModel> getUserList(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetUserDataResponseModel> userList = userRepository.getUserDetails(pageable)
                .map(GetUserDataResponseModel::new);
        return PageUtils.setPageResponse(userList);
    }

    @Override
    public PagedData<GetInstituteUserDataResponseModel> getInstituteUserList(Integer page, Integer limit, String searchValue) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetInstituteUserDataResponseModel> InstUserList = userRepository.getInstituteUserDetails(pageable, searchValue)
                .map(user -> {
                    String institutionName = listingRepository.findInstitutionNameByUserId(user.getUserId());
                    return new GetInstituteUserDataResponseModel(user, institutionName);
                });
        return PageUtils.setPageResponse(InstUserList);
    }

    @Override
    public List<User> getUserList(List<Long> userIds) {
        return userRepository.getUserByUserId(userIds);
    }

    @Override
    public PagedData<GetUsersResponseModel> getUsersByTypeAndSubType(Integer type, Integer subType, Integer page, Integer limit, Integer active) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetUsersResponseModel> usersResponseModels = userRepository.getUsersByTypeAndSubType(type, subType, active, pageable)
                .map(GetUsersResponseModel::new);
        return PageUtils.setPageResponse(usersResponseModels);
    }

    @Override
    public List<User> getUsersByTypeAndSubType(Integer type, Integer subType) {
        return userRepository.getByUserTypeAndSubType(type, subType);
    }

    @Override
    public PagedData<GetDueDateResponseModel> getDueDates(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetDueDateResponseModel> dueDates = dueDatesRepository.getDueDates(pageable);
        return PageUtils.setPageResponse(dueDates);
    }

    @Override
    public DueDates saveDueDates(DueDates dueDates) {
        return dueDatesRepository.save(dueDates);
    }

    @Override
    public DueDates findByActionAndType(Integer action, Integer type) {
        return dueDatesRepository.findByActionAndType(action, type);
    }

    @Override
    public void saveAllDueDates(List<DueDates> dueDates) {
        dueDatesRepository.saveAll(dueDates);
    }

    @Override
    public PlannedSubmissionDate savePlannedSubmissionDate(PlannedSubmissionDate submissionDate) {
        return plannedSubmissionDateRepository.save(submissionDate);
    }

    @Override
    public PlannedSubmissionDate getByRegistrationDateId(Long dateId) {
        return plannedSubmissionDateRepository.getByDateId(dateId);
    }

    @Override
    public void deletePlannedSubmissionDate(PlannedSubmissionDate submissionDate) {
        plannedSubmissionDateRepository.delete(submissionDate);
    }

    @Override
    public DueCount findDueCountByFormUniqueIdAndStatus(Long formUniqueId, Integer status) {
        return dueCountRepository.findByFormUniqueIdAndStatus(formUniqueId, status);
    }

    @Override
    public AppStatus getByStatusId(Long statusId) {
        return applicationStatusRepository.getByStatusId(statusId);
    }

    @Override
    public AppStatus saveAppStatus(AppStatus appStatus) {
        return applicationStatusRepository.save(appStatus);
    }

    @Override
    public PagedData<GetStatusListResponseModel> getStatusList(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetStatusListResponseModel> statusList = applicationStatusRepository.getStatusList(pageable);
        return PageUtils.setPageResponse(statusList);
    }

    @Override
    public InstituteForm findOneByContactPersonEmailAndPlannedSubDate(String contactPersonEmail, String plannedSubDate) {
        return institutionFormRepository.findOneByContactPersonEmailAndPlannedSubDate(contactPersonEmail, plannedSubDate);
    }

//    @Override
//    public List<InstituteForm> getByContactPersonEmailAndInstAppLicNo(String contactPersonEmail, String instAppLicNo) {
//        return institutionFormRepository.getByContactPersonEmailAndInstAppLicNo(contactPersonEmail, instAppLicNo);
//    }

    @Override
    public List<InstituteForm> getByPlannedSubDateAndInstAppLicNo(String plannedSubDate, String instAppLicNo) {
        return institutionFormRepository.getByPlannedSubDateAndInstAppLicNo(plannedSubDate, instAppLicNo);
    }

    @Override
    public DueDates findByAction(Integer status) {
        return dueDatesRepository.findByAction(status);
    }

    @Override
    public PagedData<GetUserDataResponseModel> getLimitedUserList(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetUserDataResponseModel> userList = userRepository.getLimitedUserDetails(pageable)
                .map(GetUserDataResponseModel::new);
        return PageUtils.setPageResponse(userList);
    }

    @Override
    public PagedData<GetUserDataResponseModel> getILEPUserList(Integer page, Integer limit,Integer active) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<GetUserDataResponseModel> userList = userRepository.getILEPUserDetails(active,pageable)
                .map(GetUserDataResponseModel::new);
        return PageUtils.setPageResponse(userList);
    }

    @Override
    public PagedData<GetUserDataResponseModel> getNonAdminNonInstituteUsers(GetDashboardUserListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());
        Page<GetUserDataResponseModel> userList = userRepository.findNonAdminNonInstituteUsers(request.getActiveStatus(),pageable,request.getSearchValue())
                .map(GetUserDataResponseModel::new);
        return PageUtils.setPageResponse(userList);
    }

}
