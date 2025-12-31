package com.mindteck.common.modules.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleRequest;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.InvocationTargetException;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    LoginResponse userLogin(LoginRequest request) throws Exception;

    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    RegistrationResponse userRegistration(RegistrationRequest request);

    GetUserListResponse getLimitedUserDataList(GetUserDataListRequest request);

    ApplicationManagerEvaluationResponse evaluateApplication(ApplicationManagerEvaluationRequest request);
    ApplicationManagerEvaluationResponse submitApplication(SubmitFormRequest request);
    ApplicationManagerEvaluationResponse forceChangeStatus(ForceChangeStatusRequest request);
    public GetOverallAdminCheckDetailsResponse getOverallAdminCheckDetails(long formUniqueID);
    AssignApplicationManagerResponse assignApplicationManager(AssignApplicationManagerRequest request);

    GetApplicationDetailsResponse getApplicationDetails(GetApplicationDetailsRequest request) throws JsonProcessingException;
    GetInstitutesHavingQualificationResponse getInstitutesHavingQualificationDetails();


    LogoutResponse logout(String token);


    GetSubmissionDateListResponse getSubmissionDateList(GetSubmissionDateListRequest request);

    GetApplicationManagerListResponse getApplicationManagerList();


    UserEditResponse editUser(UserEditRequest request,int isIlepEdit);

    UserEditResponse editInstituteUser(InstituteUserEditRequest request);

    CreateUserResponse createUser(CreateUserResponseRequest request,int isIlepCreation);

    GetUserListResponse getUserDataList(GetUserDataListRequest request);

    GetInstituteUserListResponse getInstituteUserDataList(GetInstituteUserDataListRequest request);

    GetUsersResponse getUsersByTypeAndSubType(GetUsersRequest request);

    GetDueDateResponse getDueDates(GetDueDateRequest request);

    SetDueDatesResponse setDueDates(SetDueDateRequest request);

    SetMailTemplateResponse setMailTemplate(SetMailTemplateRequest request);

    GetMailTemplateResponse getMailTemplate(Long mailId) throws InvocationTargetException, IllegalAccessException;

    GetMailTemplateListResponse getMailTemplateList(GetMailTemplateListRequest request);

    AddRegistrationDateResponse addRegistrationDates(AddRegistrationDateRequest request);

    DeleteRegistrationDateResponse deleteRegistrationDate(Long dateId);

    GetStatusResponse getStatusDetails(Long statusId);

    EditStatusResponse editStatusDetails(EditStatusRequest request);

    GetStatusListResponse getStatusList(GetStatusListRequest request);

    ChangePasswordResponse changePassword(ChangePasswordRequest request);

    GetPreSignedUrlResponse generatePreSignedUrl(GetPreSignedUrlRequest request);

    GetRoleListResponse getRoles();

    public SwitchRoleResponse switchRole(SwitchRoleRequest request);

    GetUserListResponse getILEPUserDataList(GetDashboardUserListRequest request);

    GetUserListResponse getNonAdminNonInstituteUsers(GetDashboardUserListRequest request);
}
