package com.mindteck.common.modules.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.authentication.JwtTokenUtil;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.constants.Enum.FormApplicationUpdateType;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleRequest;
import com.mindteck.common.modules.user.model.rest.formdata.SwitchRoleResponse;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@CrossOrigin
@Api(tags = {SwaggerHeads.AUTH_API})
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LogService logService;

    @Autowired
    FormDao formDao;

    @PostMapping(ApiUrls.LOGIN)
    public ResponseEntity<LoginResponse> userLogin(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) throws Exception {

        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);

        try {

            if (bindingResult.hasErrors()) {
                status.setEndTime(System.currentTimeMillis());
                LOGGER.debug("Input validation failed");
                throw new ControllerException(status, bindingResult, ErrorCode.LOGIN_INPUT_VALIDATION);
            }

            authenticate(request.getUsername(), request.getPassword(), status);

            LoginResponse response = userService.userLogin(request);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.FORGOT_PASSWORD)
    @ApiOperation(value = "Api for forgot password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request,
                                                                 BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(3);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                status.setEndTime(System.currentTimeMillis());
                LOGGER.debug("Input validation failed");
                throw new ControllerException(status, bindingResult, ErrorCode.FORGOT_PASSWORD_VALIDATION_FAILED);
            }

            ForgotPasswordResponse apiResponse = userService.forgotPassword(request);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private void authenticate(String username, String password, Status status) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new ServiceException(ErrorCode.LOGIN_INVALID_CREDENTIALS);
        }
    }

    @PostMapping(ApiUrls.RESET_PASSWORD)
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(4);
        WebUtils.setStatus(status);
        WebUtils.setAppId(request.getAppId());
        try {

            if (bindingResult.hasErrors()) {
                status.setEndTime(System.currentTimeMillis());
                LOGGER.debug("Input validation failed");
                throw new ControllerException(status, bindingResult, ErrorCode.RESET_PASSWORD_INPUT_VALIDATION);
            }
            ResetPasswordResponse response = userService.resetPassword(request);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.REFRESH_TOKEN)
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(5);
        WebUtils.setStatus(status);
        try {

            if (bindingResult.hasErrors()) {
                status.setEndTime(System.currentTimeMillis());
                LOGGER.debug("Input validation failed");
                throw new ControllerException(status, bindingResult, ErrorCode.REFRESH_TOKEN_VALIDATION_FAILED);
            }
            RefreshTokenResponse response = userService.refreshToken(request);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.USER_REGISTRATION)
    @ApiOperation(value = "To register using the form", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<RegistrationResponse> userRegistration(
            @Valid @RequestBody RegistrationRequest request,
            BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_REGISTRATION_INPUT_VALIDATION_FAILED
                );
            }
            final RegistrationResponse response = userService.userRegistration(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.USER_QP_REGISTRATION)
    @ApiOperation(value = "To register using the form", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<RegistrationResponse> userRegistrationP2(
            @Valid @RequestBody RegistrationRequest request,
            BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_REGISTRATION_INPUT_VALIDATION_FAILED
                );
            }
            final RegistrationResponse response = userService.userRegistration(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.APPLICATION_MANAGER_EVALUATE_FORM)
    @ApiOperation(value = "To Evaluate form by Application Manager", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<ApplicationManagerEvaluationResponse> evaluateFormSubmission(@Valid @RequestBody ApplicationManagerEvaluationRequest request,
                                                                                       BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_MANAGER_INPUT_VALIDATION_FAILED
                );
            }
            final ApplicationManagerEvaluationResponse response = userService.evaluateApplication(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.SUBMIT_FORM)
    @ApiOperation(value = "To Evaluate form by Application Manager", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<ApplicationManagerEvaluationResponse> submitForm(@Valid @RequestBody SubmitFormRequest request,
                                                                           BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_MANAGER_INPUT_VALIDATION_FAILED
                );
            }
            final ApplicationManagerEvaluationResponse response = userService.submitApplication(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.OVERALL_ADMIN_CHECK_DETAILS)
    @ApiOperation(value = "Get over all admin check details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<GetOverallAdminCheckDetailsResponse> getOverallAdminCheckDetails(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_MANAGER_INPUT_VALIDATION_FAILED
                );
            }
            final GetOverallAdminCheckDetailsResponse response = userService.getOverallAdminCheckDetails(request.getFormUniqueId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.FORCE_CHANGE_STATUS)
    @ApiOperation(value = "To Evaluate form by Application Manager", tags = {SwaggerHeads.AUTH_API})
    public ResponseEntity<ApplicationManagerEvaluationResponse> forceChangeStatus(@Valid @RequestBody ForceChangeStatusRequest request,
                                                                                  BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(2);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_MANAGER_INPUT_VALIDATION_FAILED
                );
            }
            final ApplicationManagerEvaluationResponse response = userService.forceChangeStatus(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.ASSIGN_APPLICATION_MANAGER)
    @ApiOperation(value = "Assign application manager for institution", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<AssignApplicationManagerResponse> assignApplicationManager(@RequestBody @Valid AssignApplicationManagerRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(9);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ASSIGN_APPLICATION_MANAGER_VALIDATION_FAILED
                );
            }
            final AssignApplicationManagerResponse response = userService.assignApplicationManager(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.APPLICATION_DETAILS)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetApplicationDetailsResponse> getApplicationDetails(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult bindingResult) throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(6);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_DETAILS_INPUT_VALIDATION_FAILED
                );
            }

            if (!Arrays.stream(FormApplicationUpdateType.values()).anyMatch((x) -> x.getCode().equals(request.getRequestType()))) {
                LOGGER.error("Invalid request type given:{}", request.getRequestType());
                throw new ServiceException(ErrorCode.APPLICATION_DETAILS_INVALID_REQUEST_TYPE);
            }

            GetApplicationDetailsResponse response = userService.getApplicationDetails(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @GetMapping(ApiUrls.GET_APPLICATION_MANAGER_LIST)
    @ApiOperation(value = "API for get all Application Manager")
    public ResponseEntity<GetApplicationManagerListResponse> getFormList(@ModelAttribute @Valid GetApplicationManagerListRequest request,
                                                                         BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(11);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.APPLICATION_MANAGER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetApplicationManagerListResponse response = userService.getApplicationManagerList();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.GET_INSTITUTES_HAVING_QUALIFICATIONS)
    @ApiOperation(value = "Get Institutes having qualification data", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetInstitutesHavingQualificationResponse> getInstitutesHavingQualificationDetails() throws JsonProcessingException {
        Status status = WebUtils.getStatus();
        status.setApiId(141);
        WebUtils.setStatus(status);
        try{
            GetInstitutesHavingQualificationResponse response = userService.getInstitutesHavingQualificationDetails();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PutMapping(ApiUrls.USER_EDIT)
    @ApiOperation(value = "API for edit a user", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<UserEditResponse> editUser(@RequestBody UserEditRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(14);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_EDIT_INPUT_VALIDATION_FAILED
                );
            }
            UserEditResponse response = userService.editUser(request,0);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    //institute user edit

    @PutMapping(ApiUrls.INSTITUTE_USER_EDIT)
    @ApiOperation(value = "API for edit a user", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<UserEditResponse> editInstituteUser(@RequestBody InstituteUserEditRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(148);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_EDIT_INPUT_VALIDATION_FAILED
                );
            }
            UserEditResponse response = userService.editInstituteUser(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PutMapping(ApiUrls.DIRECTOR_USER_EDIT)
    @ApiOperation(value = "API for edit a user", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<UserEditResponse> editILEPUser(@RequestBody UserEditRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(146);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_EDIT_INPUT_VALIDATION_FAILED
                );
            }
            UserEditResponse response = userService.editUser(request,1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.CREATE_USER)
    @ApiOperation(value = "To create users", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserResponseRequest request, BindingResult bindingResult) {

        Status status = WebUtils.getStatus();
        status.setApiId(13);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CREATE_USER_INPUT_VALIDATION_FAILED
                );
            }

            CreateUserResponse response = userService.createUser(request,0);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_USER_LIST)
    @ApiOperation(value = "API for get list of all users")
    public ResponseEntity<GetUserListResponse> getUserList(@ModelAttribute @Valid GetUserDataListRequest request,
                                                           BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(11);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetUserListResponse response = userService.getUserDataList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    //institute users
    @GetMapping(ApiUrls.GET_INSTITUTE_USER_LIST)
    @ApiOperation(value = "API for get list of all users")
    public ResponseEntity<GetInstituteUserListResponse> getInstituteUserList(@ModelAttribute @Valid GetInstituteUserDataListRequest request,
                                                                             BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(149);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetInstituteUserListResponse response = userService.getInstituteUserDataList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.GET_USER_BY_TYPE)
    @ApiOperation(value = "API for get list of  users by type and subtype", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetUsersResponse> getUsersByTypeAndSubType(@ModelAttribute @Valid GetUsersRequest request,
                                                                     BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(31);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.GET_USER_BY_TYPE_INPUT_VALIDATION_FAILED
                );
            }

            GetUsersResponse response = userService.getUsersByTypeAndSubType(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.BQA_ADMIN_DUE_DATES)
    @ApiOperation(value = "API for get list of  due dates", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetDueDateResponse> getDueDates(@ModelAttribute @Valid GetDueDateRequest request,
                                                          BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(55);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.GET_DUE_DATES_INPUT_VALIDATION_FAILED
                );
            }

            GetDueDateResponse response = userService.getDueDates(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.BQA_ADMIN_SET_DUE_DATES)
    @ApiOperation(value = "API for set the list of  due date", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<SetDueDatesResponse> setDueDates(@RequestBody @Valid SetDueDateRequest request,
                                                           BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(56);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.SET_DUE_DATES_INPUT_VALIDATION_FAILED
                );
            }

            SetDueDatesResponse response = userService.setDueDates(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.SET_MAIL_TEMPLATE)
    @ApiOperation(value = "API to set mail body for the template", tags = {SwaggerHeads.MAIL_TEMPLATE})
    public ResponseEntity<SetMailTemplateResponse> SetMailTemplate(@RequestBody @Valid SetMailTemplateRequest request,
                                                                   BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(60);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.SET_MAIL_TEMPLATE_INPUT_VALIDATION_FAILED
                );
            }

            SetMailTemplateResponse response = userService.setMailTemplate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.GET_MAIL_TEMPLATE)
    @ApiOperation(value = "To get mail template details", tags = {SwaggerHeads.MAIL_TEMPLATE})
    public ResponseEntity<GetMailTemplateResponse> getMailTemplate(@PathVariable(value = "mailId") Long mailId) throws InvocationTargetException, IllegalAccessException {
        Status status = WebUtils.getStatus();
        status.setApiId(61);
        WebUtils.setStatus(status);
        try {
            GetMailTemplateResponse response = userService.getMailTemplate(mailId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.LIST_MAIL_TEMPLATES)
    @ApiOperation(value = "To list all mail templates", tags = {SwaggerHeads.MAIL_TEMPLATE})
    public ResponseEntity<GetMailTemplateListResponse> getMailTemplateList(@ModelAttribute @Valid GetMailTemplateListRequest request,
                                                                           BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(62);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.LIST_MAIL_TEMPLATE_INPUT_VALIDATION_FAILED
                );
            }

            GetMailTemplateListResponse response = userService.getMailTemplateList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.GET_REGISTRATION_DATES)
    @ApiOperation(value = "Get submission dates list", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetSubmissionDateListResponse> getSubmissionDates(@ModelAttribute GetSubmissionDateListRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(18);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.GET_REGISTRATION_DATE_INPUT_VALIDATION_FAILED
                );
            }

            GetSubmissionDateListResponse response = userService.getSubmissionDateList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.ADD_REGISTRTION_DATE)
    @ApiOperation(value = "To add bqa registration dates", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<AddRegistrationDateResponse> addRegistrationDates(@RequestBody @Valid AddRegistrationDateRequest request,
                                                                            BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(63);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ADD_REGISTRATION_DATES_INPUT_VALIDATION_FAILED
                );
            }

            AddRegistrationDateResponse response = userService.addRegistrationDates(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @DeleteMapping(ApiUrls.DELETE_REGISTRTION_DATE)
    @ApiOperation(value = "To delete added registration dates", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<DeleteRegistrationDateResponse> deleteRegistrationDate(@PathVariable(value = "dateId") Long dateId) {
        Status status = WebUtils.getStatus();
        status.setApiId(64);
        WebUtils.setStatus(status);

        try {

            DeleteRegistrationDateResponse response = userService.deleteRegistrationDate(dateId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_STATUS)
    @ApiOperation(value = "To get status desc details")
    public ResponseEntity<GetStatusResponse> getStatusDetails(@PathVariable(value = "statusId") Long statusId) {
        Status status = WebUtils.getStatus();
        status.setApiId(108);
        WebUtils.setStatus(status);

        try {

            GetStatusResponse response = userService.getStatusDetails(statusId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.EDIT_STATUS)
    @ApiOperation(value = "To edit status desc details")
    public ResponseEntity<EditStatusResponse> editStatusDetails(@RequestBody @Valid EditStatusRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(109);
        WebUtils.setStatus(status);

        try {

            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.EDIT_APP_STATUS_INPUT_VALIDATION_FAILED
                );
            }
            EditStatusResponse response = userService.editStatusDetails(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_STATUS_LIST)
    @ApiOperation(value = "API for get all status details")
    public ResponseEntity<GetStatusListResponse> getStatusList(@ModelAttribute @Valid GetStatusListRequest request,
                                                               BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(107);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.GET_STATUS_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetStatusListResponse response = userService.getStatusList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.CHANGE_PASSWORD)
    @ApiOperation(value = "API for change password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody @Valid ChangePasswordRequest request,
                                                                 BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(120);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CHANGE_PASSWORD_INPUT_VALIDATION_FAILED
                );
            }

            ChangePasswordResponse response = userService.changePassword(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.GENERATE_PRE_SIGNED_URL)
    @ApiOperation(value = "Api for generating pre signed URLs for s3 access" , tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetPreSignedUrlResponse> generatePreSignedUrl(@ModelAttribute @Valid GetPreSignedUrlRequest request,
                                                                        BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(122);
        WebUtils.setStatus(status);
        String decodedFileName = URLDecoder.decode(request.getFileName(), StandardCharsets.UTF_8);
        request.setFileName(decodedFileName);

        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.GENERATE_PRE_SIGNED_URL_INPUT_VALIDATION_FAILED
                );
            }

            GetPreSignedUrlResponse response = userService.generatePreSignedUrl(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_LIMITED_USER_LIST)
    @ApiOperation(value = "API for get  ilep and application manager list of all users" , tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetUserListResponse> getLimitedUserList(@ModelAttribute @Valid GetUserDataListRequest request,
                                                                  BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(11);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetUserListResponse response = userService.getLimitedUserDataList(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @PostMapping(ApiUrls.SWITCH_ROLE)
    public ResponseEntity<SwitchRoleResponse> switchRole(@RequestBody  @Valid SwitchRoleRequest request,
                                                         BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(143);
        WebUtils.setStatus(status);

        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            SwitchRoleResponse response = userService.switchRole(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_ROLES)
    @ApiOperation(value = "API for get active roles" , tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetRoleListResponse> getRoles() {
        Status status = WebUtils.getStatus();
        status.setApiId(144);
        WebUtils.setStatus(status);
        try {

            GetRoleListResponse response = userService.getRoles();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }
    @PostMapping(ApiUrls.DIRECTOR_CREATE_USER)
    @ApiOperation(value = "To create users", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<CreateUserResponse> DirectorcreateUser(@RequestBody @Valid CreateUserResponseRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(13);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.CREATE_USER_INPUT_VALIDATION_FAILED
                );
            }
            CreateUserResponse response = userService.createUser(request,1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }


    @GetMapping(ApiUrls.GET_ILEP_USER)
    @ApiOperation(value = "API for get list of all ilep users")
    public ResponseEntity<GetUserListResponse> getILEPUserList(@ModelAttribute @Valid GetDashboardUserListRequest request,
                                                           BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(145);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetUserListResponse response = userService.getILEPUserDataList(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @GetMapping(ApiUrls.GET_NON_ADMIN_NON_INSTITUTE_USER)
    @ApiOperation(value = "API for get list of all non admin non institute users")
    public ResponseEntity<GetUserListResponse> getNonAdminNonInstituteUsers(@ModelAttribute @Valid GetDashboardUserListRequest request,
                                                               BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(146);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.USER_LIST_INPUT_VALIDATION_FAILED
                );
            }

            GetUserListResponse response = userService.getNonAdminNonInstituteUsers(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

}
