package com.mindteck.common.modules.user.builder;

import com.mindteck.common.constants.Enum.DateExtensionStatus;
import com.mindteck.common.constants.Enum.StatusEnum;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleResponse;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleResponseModel;
import com.mindteck.common.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class UserResponseBuilder {

    public LoginResponse buildLoginResponse(LoginResponseModel data) {

        return LoginResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(data)
                .build();
    }

    public RefreshTokenResponse buildRefreshTokenResponse(String token, String publicKey) {

        return RefreshTokenResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(RefreshTokenResponseModel.builder().token(token).publicKey(publicKey).build())
                .build();
    }

    public ResetPasswordResponse buildResetPasswordResponse(String message) {

        return ResetPasswordResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(ResetPasswordResponseModel.builder().code(2001).message(message).build())
                .build();
    }

    public ForgotPasswordResponse buildForgotPasswordResponse(String message, String otp) {
        return ForgotPasswordResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(ForgotPasswordResponseModel.builder()
                        .message(message)
                        .otp(otp)
                        .build())
                .build();
    }

    public RegistrationResponse buildRegistrationResponse(Integer code, String message, Long registeredId , Long formId) {
        return RegistrationResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(RegistrationResponseModel.builder()
                        .message(message)
                        .code(code)
                        .formUniqueId(registeredId)
                        .formId(formId)
                        .build())
                .build();
    }

    public ApplicationManagerEvaluationResponse buildApplicationManagerEvaluationResponse(String message , Long formUniqueId) {
        return ApplicationManagerEvaluationResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(ApplicationManagerEvaluationResponseModel.builder()
                        .message(message)
                        .formUniqueId(formUniqueId)
                        .build())
                .build();
    }

    public AssignApplicationManagerResponse buildAssignApplicationManagerResponse(String message) {
        return AssignApplicationManagerResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(AssignApplicationManagerResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public GetApplicationDetailsResponse buildGetApplicationDetailsInstituteFormResponse(GetApplicationDetailsResponseModel responseModel) {
        return GetApplicationDetailsResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(responseModel)
                .build();
    }

    public GetApplicationDetailsResponse buildGetApplicationDetailsFormApplicationResponse(GetApplicationDetailsResponseModel responseModel) {
        return GetApplicationDetailsResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .data(responseModel)
                .build();
    }

    public GetInstitutesHavingQualificationResponse buildGetDistingtInstituteResponse(List<GetInstitutesHavingQualificationResponseModel> responseModel,Long startTime) {
        return GetInstitutesHavingQualificationResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder()
                        .statusCode(StatusEnum.SUCCESS.getCode())
                        .startTime(startTime)
                        .endTime(System.currentTimeMillis())
                        .apiId(141)
                        .build())
                .data(responseModel)
                .build();
    }

    public LogoutResponse buildLogoutResponse() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return LogoutResponse.builder()
                .status(status)
                .data(Logout.builder()
                        .message("logged out success")
                        .build())
                .build();
    }

    public DateExtensionApprovalResponse buildDateExtensionApprovalResponse(Integer dateExtensionStatus, String log) {
        String message = null;
        switch (dateExtensionStatus) {
            case 2:
                message = DateExtensionStatus.APPROVED.getDescription();
                break;
            case 3:
                message = DateExtensionStatus.REJECTED.getDescription();
                break;
        }
        return DateExtensionApprovalResponse.builder()
                .status(com.mindteck.common.models.rest.Status.builder().statusCode(StatusEnum.SUCCESS.getCode()).build())
                .log(log)
                .data(DateExtensionApprovalResponseModel.builder()
                        .message(message)
                        .build())
                .build();
    }

    public GetSubmissionDateListResponse buildGetSubmissionDateListResponse(PagedData<GetSubmissionDateListResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetSubmissionDateListResponse.builder().status(status)
                .data(data)
                .build();
    }

    public GetApplicationManagerListResponse buildGetApplicationManagerListResponse(PagedData<GetApplicationManagerListResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetApplicationManagerListResponse.builder()
                .status(status)
                .data(data)
                .build();
    }


    public UserEditResponse userEditResponseBuilder(Integer success, Long userId) {
        return UserEditResponse.builder()
                .status(getStatus())
                .data(UserEditResponseModel.builder()
                        .success(success)
                        .userId(userId)
                        .build())
                .build();


    }

    public Status getStatus() {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return status;
    }

    public CreateUserResponse buildCreateUserResponse(Long userId, String message) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return CreateUserResponse.builder()
                .status(status)
                .data(CreateUserResponseModel.builder().userId(userId).message(message).build())
                .build();

    }

    public GetUserListResponse getUserListResponseBuilder(PagedData<GetUserDataResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetUserListResponse.builder()
                .status(status)
                .data(data)
                .build();
    }
    public GetInstituteUserListResponse getInstituteUserListResponseBuilder(PagedData<GetInstituteUserDataResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetInstituteUserListResponse.builder()
                .status(status)
                .data(data)
                .build();
    }

    public GetUsersResponse getUsersResponseBuilder(PagedData<GetUsersResponseModel> data) {
        return GetUsersResponse.builder()
                .status(getStatus())
                .data(data)
                .build();

    }

    public GetDueDateResponse getDueDateResponseBuilder(PagedData<GetDueDateResponseModel> data) {
        return GetDueDateResponse.builder()
                .status(getStatus())
                .data(data)
                .build();
    }

    public SetDueDatesResponse setDueDateResponseBuilder(Integer success) {
        return SetDueDatesResponse.builder()
                .status(getStatus())
                .data(SetDueDatesResponseModel.builder().success(success).build())
                .build();
    }

    public SetMailTemplateResponse buildSetMailTemplateResponse(Integer success) {
        return SetMailTemplateResponse.builder()
                .status(getStatus())
                .data(SetMailTemplateResponseModel.builder().success(success).build())
                .build();
    }

    public GetMailTemplateResponse buildGetMailTemplateResponse(GetMailTemplateResponseModel responseModel) {
        return GetMailTemplateResponse.builder()
                .status(getStatus())
                .data(responseModel)
                .build();
    }

    public GetMailTemplateListResponse GetMailTemplateListResponse(PagedData<GetMailTemplateListResponseModel> data) {

        return GetMailTemplateListResponse.builder()
                .status(getStatus())
                .data(data)
                .build();
    }

    public AddRegistrationDateResponse buildAddRegistrationDateResponse(Integer success, Long dateId) {
        return AddRegistrationDateResponse.builder()
                .status(getStatus())
                .data(AddRegistrationDateResponseModel.builder().success(success).dateId(dateId).build())
                .build();
    }

    public DeleteRegistrationDateResponse buildDeleteRegistrationDateResponse(Integer success) {
        return DeleteRegistrationDateResponse.builder()
                .status(getStatus())
                .data(DeleteRegistrationDateResponseModel.builder().success(success).build())
                .build();
    }

    public GetStatusResponse buildGetStatusResponse(GetStatusResponseModel data) {
        return GetStatusResponse.builder()
                .status(getStatus())
                .data(data)
                .build();
    }

    public EditStatusResponse buildEditStatusResponse(Integer success) {
        return EditStatusResponse.builder()
                .status(getStatus())
                .data(EditStatusResponseModel.builder().success(success).build())
                .build();
    }

    public GetStatusListResponse buildGetStatusListResponse(PagedData<GetStatusListResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetStatusListResponse.builder().status(status)
                .data(data)
                .build();
    }

    public ChangePasswordResponse changePasswordResponseBuilder (Integer success) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return ChangePasswordResponse.builder()
                .status(status)
                .data(ChangePasswordResponseModel.builder().success(success).build())
                .build();
    }

    public GetPreSignedUrlResponse getPreSignedUrlResponseBuilder (String url) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetPreSignedUrlResponse.builder()
                .status(status)
                .data(GetPreSignedUrlModel.builder().preSignedUrl(url).build())
                .build();
    }

    public SwitchRoleResponse buildSwitchRoleResponse(SwitchRoleResponseModel data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return SwitchRoleResponse.builder()
                .status(status)
                .data(data)
                .build();
    }

    public GetRoleListResponse buildGetRoleResponse(List<GetRoleResponseModel> data) {
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());

        return GetRoleListResponse.builder()
                .status(status)
                .data(data)
                .build();
    }

}
