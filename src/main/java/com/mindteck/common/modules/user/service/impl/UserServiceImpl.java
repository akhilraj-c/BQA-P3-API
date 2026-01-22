package com.mindteck.common.modules.user.service.impl;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindteck.common.authentication.JwtTokenUtil;
import com.mindteck.common.constants.Enum.*;
import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.exceptionHandler.UserNotFoundException;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.PagedData;
import com.mindteck.common.models.rest.RSAKeys;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.user.model.rest.*;
import com.mindteck.common.modules.user.model.rest.formdata.*;
import com.mindteck.common.modules.user.model.rest.qp.ForeignQualification;
import com.mindteck.common.modules.user.model.rest.qp.NationalQualification;
import com.mindteck.common.modules.user.service.RegistrationType;
import com.mindteck.models_cas.*;
import com.mindteck.repository_cas.ApplicationRepository;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.service.LogService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.EncryptionUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.IlepEvaluationForm.dao.IlepEvaluationDao;
import com.mindteck.common.modules.confirmation_panel.model.ConfirmationPanel;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelRequest;
import com.mindteck.common.modules.confirmation_panel.model.SaveConfirmationPanelResponse;
import com.mindteck.common.modules.confirmation_panel.service.ConfirmationPanelService;
import com.mindteck.common.modules.date_extension.DateExtensionRepository;
import com.mindteck.common.modules.date_extension.model.DateExtension;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.ilepAssigin.dao.IlepAssignDao;
import com.mindteck.repository_cas.ListingRepository;
import com.mindteck.common.modules.mapping_panel.model.MappingPanel;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelRequest;
import com.mindteck.common.modules.mapping_panel.model.SaveMappingPanelResponse;
import com.mindteck.common.modules.mapping_panel.service.MappingPanelService;
import com.mindteck.common.modules.program_structure.model.ProgramStructure;
import com.mindteck.common.modules.program_structure.model.SaveProgramStructureRequest;
import com.mindteck.common.modules.program_structure.model.SaveProgramStructureResponse;
import com.mindteck.common.modules.program_structure.service.ProgramStructureService;
import com.mindteck.common.modules.program_structure_work_flow.model.ProgramStructureWorkFlow;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowRequest;
import com.mindteck.common.modules.program_structure_work_flow.model.SaveProgramStructureFlowResponse;
import com.mindteck.common.modules.program_structure_work_flow.service.ProgramStructureFlowService;
import com.mindteck.common.modules.standards.controller.StandardsApiType;
import com.mindteck.common.modules.standards.model.SaveStandardWorkFlowResponse;
import com.mindteck.common.modules.standards.model.SaveStandardsRequest;
import com.mindteck.common.modules.standards.model.StandardWorkFlow;
import com.mindteck.common.modules.standards.service.StandardsWorkFlowService;
import com.mindteck.common.modules.user.builder.UserResponseBuilder;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.service.UserService;
import com.mindteck.common.modules.user.util.UserUtils;
import com.mindteck.repository_cas.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserLoginInfoDao userLoginInfoDao;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserResponseBuilder responseBuilder;

    @Autowired
    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private AwsService awsService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private EvaluationDao evaluationDao;
    @Autowired
    private LogService logService;

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    IlepAssignDao ilepAssignDao;

    @Autowired
    StandardsWorkFlowService standardsWorkFlowService;

    @Autowired
    ProgramStructureService programStructureService;

    @Autowired
    ProgramStructureFlowService programStructureFlowService;

    @Autowired
    MappingPanelService mappingPanelService;

    @Autowired
    ConfirmationPanelService confirmationPanelService;

    @Autowired
    private IlepEvaluationDao ilepEvaluationDao;

    public UserServiceImpl(SpringTemplateEngine thymeleafTemplateEngine) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Value("${fromEmail}")
    private String fromEmail;

    @Value("${resetLink}")
    private String resetLink;

    @Value("${mindteck.aws.s3.bucket:bqap1-ter}")
    private String bucketName;

    @Value("${mindteck.aws.s3.pre.signed.url.expiry:15}")
    private Long urlExpiryInMinutes;

    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60 * 1000;

    @Autowired
    private FormDao formDao;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private DateExtensionRepository dateExtensionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getByUserEmail(username);
        if (user == null) {
            throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
        }
        Set<Role> roles = user.getRoles();
        if (roles.isEmpty()) {
            throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
        }
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    UserType userType = UserType.getByCode(role.getUserType(), role.getSubType());
                    if (userType == null) {
                        throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
                    }
                    return new SimpleGrantedAuthority(userType.getDescription());
                })
                .toList();
        return new org.springframework.security.core.userdetails.User(user.getEmailId(), user.getPassword(),
                authorities);
    }

    @Override
    @Transactional
    public LoginResponse userLogin(LoginRequest request) throws Exception {

        ClientRegistration appData = applicationRepository.getByAppId(request.getAppId());

        if (appData == null) {
            LOGGER.debug("APP not registered with id:{}", request.getAppId());
            throw new ServiceException(ErrorCode.LOGIN_APP_NOT_REGISTERED);
        }

        List<User> users = userDao.getUsersByEmailId(request.getUsername());
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
            if(user.getParentInst()!=null){
                user = userDao.getByUserId(user.getParentInst());
                if(user.getActive()!=1){
                    throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
                }
                request.setUsername(user.getEmailId());
            }
        }

        if (user.getRoles().isEmpty()) {
            LOGGER.debug("No role is assigned for user with id:{}", request.getUsername());
            throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
        }

//        if((user.getRoles().stream().anyMatch(role -> role.getUserType()==UserType.INSTITUTION.getCode()))){
//            ListedInstitute listedInstitute = listingRepository.getInstitute(user.getEmailId());
//            if(listedInstitute==null){
//                LOGGER.debug("user not found with id:{}", request.getUsername());
//                throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
//            }
//
//        }

        if (user == null) {
            LOGGER.debug("user not found with id:{}", request.getUsername());
            throw new ServiceException(ErrorCode.LOGIN_USER_NOT_FOUND);
        }

        if (user.getActive().equals(UserStatus.INACTIVE.getCode())) {
            LOGGER.debug("user :{} is disabled ", request.getUsername());
            throw new UserNotFoundException(ErrorCode.USER_INACTIVE);
        }

        String token = jwtTokenUtil.generateToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);
        List<InstituteForm> instituteForm = userDao.getFormsByMailId(user.getEmailId());

        UserLoginInfo userLoginInfo = userLoginInfoDao.getByUserIdAndAppId(user.getUserId(), request.getAppId());
        if (userLoginInfo == null) {
            userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(user.getUserId());
            userLoginInfo.setUserType(user.getRoles().stream().findFirst().map(Role::getUserType).orElse(null));
            userLoginInfo.setSubType(user.getRoles().stream().findFirst().map(Role::getSubType).orElse(null));
            userLoginInfo.setAppId(request.getAppId());
            userLoginInfo.setCreatedAppId(request.getAppId());
        }
        userLoginInfo.setToken(token);
        userLoginInfo.setRefreshToken(refreshToken);
        userLoginInfo.setExpiryTime(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
        userLoginInfo.setUpdatedAppId(request.getAppId());

        RSAKeys rsaKeys = EncryptionUtils.generateRSAKeys();
        String publicKey = String.valueOf(rsaKeys.getPublicKey());
        String privateKey = String.valueOf(rsaKeys.getPrivateKey());
        String aesKey = EncryptionUtils.generateAESKeyString();
        String encryptedAESKey = EncryptionUtils.encryptAESKey(aesKey, rsaKeys.getPrivateKey());
        if (null != request.getPublicKey() && !request.getPublicKey().isEmpty()) {
            encryptedAESKey = EncryptionUtils.encryptAESKey(aesKey, EncryptionUtils.getKey(request.getPublicKey()));
        }
        userLoginInfo.setPublicKey(publicKey);
        userLoginInfo.setPrivateKey(privateKey);
        userLoginInfo.setAesKey(aesKey);
        userLoginInfo.setAesKeyEncrypted(encryptedAESKey);

        userLoginInfo = userLoginInfoDao.saveUserLoginInfo(userLoginInfo);

        LoginResponseModel responseModel = new LoginResponseModel();
        responseModel.setToken(token);
        responseModel.setRoleDetails(user.getRoleIdNamePairs());
        responseModel.setRefreshToken(refreshToken);
        responseModel.setAppId(request.getAppId());
        responseModel.setUsername(request.getUsername());
        responseModel.setUserType(userLoginInfo.getUserType());
        responseModel.setUserId(user.getUserId());
        responseModel.setSubType(userLoginInfo.getSubType());
        if (instituteForm != null && instituteForm.size() > 0) {
            responseModel.setFormUniqueId(instituteForm.get(0).getFormParentUniqueId());
        }
        if((user.getRoles().stream().anyMatch(role -> role.getUserType()==UserType.INSTITUTION.getCode()))){
            responseModel.setInstitutionName(user.getUsername());
        }
        responseModel.setBqaap(publicKey);
        responseModel.setBqae(encryptedAESKey);

        return responseBuilder.buildLoginResponse(responseModel);
    }

    @Override
    @Transactional
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        ClientRegistration appData = applicationRepository.getByAppId(request.getAppId());

        if (appData == null) {
            LOGGER.debug("APP not registered with id:{}", request.getAppId());
            throw new ServiceException(ErrorCode.RESET_PASSWORD_APP_NOT_REGISTERED);
        }

        User user = userDao.getUserByEmailId(request.getEmailId());
        if (user == null) {
            throw new ServiceException(ErrorCode.RESET_PASSWORD_USER_NOT_FOUND);
        }

        ForgotPassword forgotPassword = userDao.getByEmailIdAndOtp(request.getEmailId(), request.getOtpCode());
        if (forgotPassword == null) {
            LOGGER.debug("Invalid otp given:{}", request.getOtpCode());
            throw new ServiceException(ErrorCode.RESET_PASSWORD_INVALID_OTP);
        }

        if (System.currentTimeMillis() > forgotPassword.getExpiryTime()) {
            LOGGER.debug("Given otp {} expired", request.getOtpCode());
            throw new ServiceException(ErrorCode.RESET_PASSWORD_EXPIRED_OTP);
        }

        user.setPassword(request.getPassword());
        user.setUpdatedAppId(request.getAppId());

        userDao.saveUser(user);
        userDao.deleteForgotPassword(forgotPassword);

        return responseBuilder.buildResetPasswordResponse("password reset successfully");

    }

    @Override
    @Transactional
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userDao.getUserByEmailId(request.getMailId());
        if(user==null || user.getActive()!=1){
            throw new ServiceException(ErrorCode.EMAIL_NOT_FOUND);
        }
        ForgotPassword forgotPassword = userDao.getByEmailId(request.getMailId());
        if (forgotPassword != null) {
            forgotPassword.setOtp(Long.toHexString(Double.doubleToLongBits(Math.random())));
            forgotPassword.setExpiryTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
            forgotPassword.setUpdatedIp(WebUtils.getClientIpAddress());
            forgotPassword.setUpdatedAppId(request.getAppId());
        } else {
            forgotPassword = new ForgotPassword();
            forgotPassword.setCreatedAppId(request.getAppId());
            forgotPassword.setUpdatedAppId(request.getAppId());
            forgotPassword.setEmailId(request.getMailId());
            forgotPassword.setCreatedIp(WebUtils.getClientIpAddress());
            forgotPassword.setUpdatedIp(WebUtils.getClientIpAddress());
            forgotPassword.setOtp(Long.toHexString(Double.doubleToLongBits(Math.random())));
            forgotPassword.setExpiryTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
        }
        userDao.save(forgotPassword);

        LOGGER.info("Set data for Forgot password mail");
        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("FORGOT_PASSWORD");
        if (Objects.nonNull(mailTemplate)) {
            String mailBody = mailTemplate.getTemplateBody();
            if (mailBody != null && !mailBody.isBlank()) {
                mailBody = mailBody.replace("{resetLink}", resetLink + forgotPassword.getOtp() + "/" + request.getMailId());
                mailBody = mailBody.replace("{userName}", user.getUsername());
            }

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("mailBody", mailBody);
            String mailHtmlPath = "mail-template.html";
            List<String> ccAdresses = new ArrayList<>();
            awsService.sendMail(request.getMailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
        }
        return responseBuilder.buildForgotPasswordResponse("Please check the Mail", forgotPassword.getOtp());
    }

    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        UserLoginInfo userLoginInfo = userLoginInfoDao.getByToken(request.getToken());
        if (userLoginInfo == null) {
            LOGGER.debug("Invalid token given:{}", request.getToken());
            throw new ServiceException(ErrorCode.REFRESH_TOKEN_NOT_VALID);
        }

        User userDetails = userDao.getByUserId(userLoginInfo.getUserId());
        String token = jwtTokenUtil.generateToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);

        userLoginInfo.setToken(token);
        userLoginInfo.setRefreshToken(refreshToken);
        userLoginInfo.setExpiryTime(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
        userLoginInfo.setUpdatedAppId(request.getAppId());
        userLoginInfo.setPublicKey(String.valueOf(new Random().nextInt(10000000) + 1000));
        userLoginInfo.setPrivateKey(String.valueOf(new Random().nextInt(10000000) + 1000));
        userLoginInfo = userLoginInfoDao.saveUserLoginInfo(userLoginInfo);

        return responseBuilder.buildRefreshTokenResponse(token, String.valueOf(new Random().nextInt(10000000) + 1000));
    }

    @Override
    @Transactional
    public RegistrationResponse userRegistration(RegistrationRequest request) {
//        List<RegistrationRequest.Qualification> qualifications = request.getQualifications();

        request.getQualifications().forEach(qualification -> {
            InstituteForm instituteForm = new InstituteForm();
            instituteForm.setApplicantOrganizationName(request.getApplicantOrganizationName());
            instituteForm.setInstitutionName(request.getApplicantOrganizationName());
            instituteForm.setQualificationTitle(qualification.getQualificationTitle());
            instituteForm.setProviders(qualification.getProviders() !=null ? qualification.getProviders().toString() : null);
            instituteForm.setAwardingBody(qualification.getAwardingBody());
            instituteForm.setLevel(qualification.getLevel());
            instituteForm.setCredit(qualification.getCredit());
            instituteForm.setNumberOfUnitsCoursesModules(qualification.getNumberOfUnitsCoursesModules());
            instituteForm.setExpectedSubmissionDate(qualification.getExpectedSubmissionDate());
            instituteForm.setPlannedSubDate(qualification.getExpectedSubmissionDate()!=null? qualification.getExpectedSubmissionDate().toString() : null);
            instituteForm.setIncludedInOther(request.getQualificationIncludedOtherFramework());
            instituteForm.setQualificationFramework(request.getQualificationFramework());
            instituteForm.setContactPersonNumber(request.getContactNumber());
            instituteForm.setContactPersonEmail(request.getContactEmail());
            instituteForm.setContactPersonTitle(request.getContactPosition());
            instituteForm.setContactPersonName(request.getContactName());
            instituteForm.setFormUniqueId(userUtils.generateUniqueId());
            instituteForm.setQualificationType(request.getQualificationType());
            instituteForm.setStatus(ApplicationStatus.INSTITUTION_REGISTERED.getCode());
            instituteForm.setInstitutionId(WebUtils.getUserId());
            userDao.saveInstitutionDetails(instituteForm);
        });
//        Long returnId = 0L;
//        ApplicationStatus currentStatus = ApplicationStatus.INITIAL_STATUS;
//        if (request.getRegistrationType() == RegistrationType.LISTING.ordinal()) {
//            InstituteForm alreadyRegisteredCheck = userDao.getByMailId(request.getEmailId());
//            if (alreadyRegisteredCheck != null) {
//                LOGGER.error("Already registered with given emailId:{}", request.getEmailId());
//                throw new ServiceException(ErrorCode.LOGIN_USER_ALREADY_EXIST);
//            }
//            InstituteForm institutionDetails = getInstituteForm(request);
//            institutionDetails = userDao.saveInstitutionDetails(institutionDetails);
//            returnId = institutionDetails.getFormId();
//            logService.writeLogToDatabase(0L, currentStatus, ApplicationStatus.getByCode(institutionDetails.getStatus()), institutionDetails.getFormUniqueId());
//        } else if (request.getRegistrationType() == RegistrationType.QUALIFICATION_PLACEMENTS.ordinal()) {
//            long parentUniqueId = userUtils.generateUniqueId();
//            formDao.deleteDraftForm(request.getEmailId());
//            for (NationalQualification nq : request.nationalQualifications) {
//                InstituteForm institutionDetails = getInstituteForm(request);
//                institutionDetails.setListingId(request.getListingId());
//                institutionDetails.setFormParentUniqueId(parentUniqueId);
//                institutionDetails.setQualificationType(1);
//                institutionDetails.setPlannedSubDate(nq.getPlannedSubmissionDate().toString());
//                institutionDetails.setQualificationTitle(nq.getQualificationTitle());
//                institutionDetails.setQualificationSize(nq.getQualificationSize());
//                institutionDetails = userDao.saveInstitutionDetails(institutionDetails);
//                logService.writeLogToDatabase(0L, currentStatus, ApplicationStatus.getByCode(institutionDetails.getStatus()), institutionDetails.getFormUniqueId());
//            }
//
//            for (ForeignQualification fq : request.foreignQualifications) {
//                InstituteForm institutionDetails = getInstituteForm(request);
//                institutionDetails.setListingId(request.getListingId());
//                institutionDetails.setFormParentUniqueId(parentUniqueId);
//                institutionDetails.setQualificationType(2);
//                institutionDetails.setPlannedSubDate(fq.getPlannedSubmissionDate().toString());
//                institutionDetails.setQualificationTitle(fq.getQualificationTitle());
//                institutionDetails.setQualificationSize(fq.getQualificationSize());
//                institutionDetails.setAwardingBody(fq.getAwardingBody());
//                institutionDetails.setIncludedInOther(fq.getIncludedInOther());
//                institutionDetails = userDao.saveInstitutionDetails(institutionDetails);
//                logService.writeLogToDatabase(0L, currentStatus, ApplicationStatus.getByCode(institutionDetails.getStatus()), institutionDetails.getFormUniqueId());
//            }
//            returnId = parentUniqueId;
//        } else {
//            throw new ServiceException(ErrorCode.REGISTRATION_TYPE_NOT_VALID);
//        }
//
//        if (request.getMailFlag() >= 0 && request.getIsDraft()==0) {
//            /*writing application status log to database*/
//            LOGGER.info("Set data for Institution Registration mail");
//            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTE_REGISTRATION");
//            if (Objects.nonNull(mailTemplate)) {
//                String mailBody = mailTemplate.getTemplateBody();
//                List<User> dfoAdminList = userDao.getUsersByTypeAndSubType(UserType.DFO_ADMIN.getCode(), UserSubType.ADMIN.getCode());
//                List<User> cheifList = userDao.getUsersByTypeAndSubType(UserType.CHIEF.getCode(), UserSubType.ADMIN.getCode());
//                List<User> directorList = userDao.getUsersByTypeAndSubType(UserType.DIRECTOR.getCode(), UserSubType.ADMIN.getCode());
////            mailBody = mailBody.replace("{licenseName}", getLicenceTypeName(request.getLicenseType()));
//                if (mailBody != null && !mailBody.isBlank()) {
//                    mailBody = mailBody.replace("{userName}", request.getContactName());
//                }
//
//                Map<String, Object> templateModel = new HashMap<>();
//                templateModel.put("mailBody", mailBody);
//                String mailHtmlPath = "mail-template.html";
//                List<String> ccAdresses = new ArrayList<>();
//                dfoAdminList.forEach(dfoUser -> {
//                    if(dfoUser.getActive()==1){
//                        ccAdresses.add(dfoUser.getEmailId());
//                    }
//                });
//                cheifList.forEach(cheif -> {
//                    if(cheif.getActive()==1){
//                        ccAdresses.add(cheif.getEmailId());
//                    }
//                });
//                directorList.forEach(director -> {
//                    if(director.getActive()==1){
//                        ccAdresses.add(director.getEmailId());
//                    }
//                });
//                awsService.sendMail(request.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
//            }
//        }
        return responseBuilder.buildRegistrationResponse(2001, "Successfully registered", 0L, 0L);
    }

//    private InstituteForm getInstituteForm(RegistrationRequest request) {
//        InstituteForm institutionDetails = new InstituteForm();
//        institutionDetails.setFormUniqueId(userUtils.generateUniqueId());
//        int isDraft = 0;
//        if (!Objects.isNull(request.getIsDraft())) {
//            isDraft = request.getIsDraft();
//        }
//        institutionDetails.setIsDraft(isDraft);
//        institutionDetails.setInstitutionName(request.getInstitutionName());
//        institutionDetails.setInstAppLicNo(request.getInstAppLicNo());
//        institutionDetails.setLicenseType(request.getLicenseType());
//       /* if (institutionDetails.getLicenseType() == LicenseType.OTHERS.getCode()) {
//            institutionDetails.setOthersData(request.getRegulatoryOthersData());
//        }*/
//        institutionDetails.setNotApplicable(request.getExpiryDateNotApplicable());
//        institutionDetails.setApprovalDocFile(request.getApprovalDocFile());
//        institutionDetails.setIssueDate(request.getIssueDate());
//        institutionDetails.setExpDate(request.getExpiryDate());
//        institutionDetails.setIsBqaReviewed(request.getIsBqaReviewed());
//        institutionDetails.setIsOfferingNanLocCourseQa(request.getIsOfferingNanLocCourseQa());
//        institutionDetails.setRandomDate(request.getPlannedSubmissionDate());
//        institutionDetails.setStatus(ApplicationStatus.INSTITUTION_REGISTERED.getCode());
//        if (request.getIsBqaReviewed() == 1) {
//            institutionDetails.setReviewIssueDate(request.getBqaReviewIssueDate());
//            institutionDetails.setReviewJudResult(request.getBqaReviewResult());
//        }
//        if (request.getIsOfferingNanLocCourseQa() == 1) {
//            institutionDetails.setOfferingDescription(request.getOfferingDescription());
//        }
//
//        institutionDetails.setContactPersonName(request.getContactName());
//        institutionDetails.setContactPersonTitle(request.getPositionTitle());
//        institutionDetails.setContactPersonNumber(request.getContactNo());
//        institutionDetails.setContactPersonEmail(request.getEmailId());
//
//        //Set Institution Id If Its exists
//        User userDetails = userDao.getByUserEmail(request.getEmailId());
//        if(userDetails!=null){
//            institutionDetails.setInstitutionId(userDetails.getUserId());
//        }
//
//        institutionDetails.setInstitutionTypeOtherData(request.getInstitutionTypeOtherData());
//        institutionDetails.setFieldOtherData(request.getFieldOtherData());
//        institutionDetails.setRegulatoryOthersData(request.getRegulatoryOthersData());
//        institutionDetails.setLicencedByOthersData(request.getLicencedByOthersData());
//        Integer draft = request.getIsDraft();
//        if (Objects.isNull(draft)) {
//            draft = 0;
//        }
//        institutionDetails.setIsDraft(draft);
//        return institutionDetails;
//    }

    @Override
    public ApplicationManagerEvaluationResponse forceChangeStatus(ForceChangeStatusRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND, " FormId : " + instituteForm.getFormUniqueId() + " Application status : " + instituteForm.getStatus());
        }
        if(request.getSubStatus() > 0 ) {
            instituteForm.setSubStatus(request.getSubStatus());
        }
        instituteForm.setStatus(request.getStatus());
        formDao.save(instituteForm);
        return responseBuilder.buildApplicationManagerEvaluationResponse("Application status forcefully changed", instituteForm.getFormUniqueId());
    }

    @Override
    public ApplicationManagerEvaluationResponse submitApplication(SubmitFormRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteForm)) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND, " FormId : " + instituteForm.getFormUniqueId() + " Application status : " + instituteForm.getStatus());
        }
        if(request.getQpId()!=null){
            instituteForm.setQpId(request.getQpId());
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        saveOverAllStatus(request, instituteForm);
        saveStandardsWorkFlow(request);
        saveProgramStructures(request);
        saveProgramStructureFlow(request);
        saveMappingPanel(request);
        saveConfirmationPanel(request);
        //Timeline
        OverallAdminCheckDetails statusData = request.getOverallAdminCheckDetails();
        Integer userType = WebUtils.getUserType();
        if (!Objects.isNull(request.getDraft()) && request.getDraft() == 0 && !Objects.isNull(statusData) && !Objects.isNull(statusData.getRegistrationStatus())) {

            ApplicationStatus newStatus = ApplicationStatus.getByCode(statusData.getRegistrationStatus());
            if (!currentStatus.equals(newStatus) && statusData.getVerificationFlag()==1 && statusData.getEvaluationFlag()==1 ) {
                logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_ADMINISTRATIVE_CHECK_COMPLETED");
                if (Objects.nonNull(mailTemplate)) {
                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                    String mailBody = mailTemplate.getTemplateBody();
                    Map<String, Object> templateModel = new HashMap<>();
                    User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
                    User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                    if (mailBody != null && !mailBody.isBlank()) {
                        mailBody = mailBody.replace("{userName}", userData.getUsername());
                        mailBody = mailBody.replace("{instituteName}", instituteForm.getQualificationTitle());
                        mailBody = mailBody.replace("{applicationId}", instituteForm.getQpId().toString());
                        mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                        mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                        mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                    }
                    String mailHtmlPath = "mail-template.html";
                    templateModel.put("mailBody", mailBody);
                    awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                }
            }
            else{
                if(userType == UserType.INSTITUTION.getCode()){
                    if(request.getIsEvaluation()==1){
                        logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2001), instituteForm.getFormUniqueId());
                        if (currentStatus.getCode().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
                            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTION_RESUBMITTED");
                            if (Objects.nonNull(mailTemplate)) {
                                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                                String mailBody = mailTemplate.getTemplateBody();
                                //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                                if (mailBody !=null && !mailBody.isBlank()) {
                                    mailBody = mailBody.replace("{userName}", instituteForm.getQualificationTitle());
                                }
                                Map<String, Object> templateModel = new HashMap<>();
                                String mailHtmlPath = "mail-template.html";
                                templateModel.put("mailBody", mailBody);
//                    List<String> ccAdresses = new ArrayList<>();
                                awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            }

                        }
                    }
                    if(request.getIsVerification()==1){
                        logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2002), instituteForm.getFormUniqueId());
                        if (currentStatus.getCode().equals(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode())) {
                            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("INSTITUTION_RESUBMITTED_VERIFICATION");
                            if (Objects.nonNull(mailTemplate)) {
                                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                                String mailBody = mailTemplate.getTemplateBody();
                                //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                                if (mailBody !=null && !mailBody.isBlank()) {
                                    mailBody = mailBody.replace("{userName}", instituteForm.getQualificationTitle());
                                }
                                Map<String, Object> templateModel = new HashMap<>();
                                String mailHtmlPath = "mail-template.html";
                                templateModel.put("mailBody", mailBody);
//                    List<String> ccAdresses = new ArrayList<>();
                                awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            }

                        }
                    }
                }
                else{
                    Map<String, Object> templateModel = new HashMap<>();
                    if(request.getIsEvaluation()==1){
                        if(statusData.getEvaluationFlag()== 1){
                            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2003), instituteForm.getFormUniqueId());
                        }
                        else if(statusData.getEvaluationFlag()== -1){
                            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2004), instituteForm.getFormUniqueId());
                            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_ADMINISTRATIVE_CHECK_EVALUATION_NOT_COMPLETED");
                            if (Objects.nonNull(mailTemplate)) {
                                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                                String mailBody = mailTemplate.getTemplateBody();
                                //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                                if (mailBody !=null && !mailBody.isBlank()) {
                                    mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                                    mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                                    Instant instant = Instant.ofEpochMilli( statusData.getEvaluationDeadLine());
                                    LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    String formattedDate = deadlineDate.format(formatter);
                                    mailBody = mailBody.replace("{deadline}", formattedDate);
                                }
                                String mailHtmlPath = "mail-template.html";
                                templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
                                awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            }
                        }
                    }
                    if(request.getIsVerification()==1){
                        if(statusData.getVerificationFlag()==1){
                            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2000), instituteForm.getFormUniqueId());
                        }
                        else if(statusData.getVerificationFlag()== -1){
                            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, ApplicationStatus.getByCode(2005), instituteForm.getFormUniqueId());
                            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_VERIFICATION_NOT_COMPLETED");
                            if (Objects.nonNull(mailTemplate)) {
                                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                                String mailBody = mailTemplate.getTemplateBody();
                                //  User instituteUser = userDao.getUserByEmailId(instituteForm.getContactPersonEmail());
                                if (mailBody !=null && !mailBody.isBlank()) {
                                    mailBody = mailBody.replace("{userName}", instituteForm.getContactPersonName());
                                    mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                                    Instant instant = Instant.ofEpochMilli( statusData.getVerificationDeadLine());
                                    LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    String formattedDate = deadlineDate.format(formatter);
                                    mailBody = mailBody.replace("{deadline}", formattedDate);
                                }
                                String mailHtmlPath = "mail-template.html";
                                templateModel.put("mailBody", mailBody);

//                    List<String> ccAdresses = new ArrayList<>();
                                awsService.sendMail(instituteForm.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            }
                        }
                    }
                }
            }
        }

        return responseBuilder.buildApplicationManagerEvaluationResponse("Application Submitted Successfully", request.getFormUniqueId());
    }

    @Override
    public GetOverallAdminCheckDetailsResponse getOverallAdminCheckDetails(long formUniqueID) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(formUniqueID);
        if (instituteForm == null) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND, " FormId : " + instituteForm.getFormUniqueId() + " Application status : " + instituteForm.getStatus());
        }

        OverallAdminCheckDetails overallAdminCheckDetails = getOverAllStatus(instituteForm);
        Status status = WebUtils.getStatus();
        status.setEndTime(System.currentTimeMillis());
        status.setStatusCode(StatusEnum.SUCCESS.getCode());
        return GetOverallAdminCheckDetailsResponse.builder().status(status)
                .overallAdminCheckDetails(overallAdminCheckDetails)
                .formUniqueId(formUniqueID)
                .build();
    }

    private OverallAdminCheckDetails getOverAllStatus(InstituteForm instituteForm) {
        if (!Objects.isNull(instituteForm)) {
            OverallAdminCheckDetails statusData = new OverallAdminCheckDetails();
            statusData.setOverAllEvaluationStatus(instituteForm.getOverAllEvaluationStatus());
            statusData.setOverAllEvaluationStatusComment(instituteForm.getOverAllEvaluationStatusComment());
            statusData.setEvaluationDeadLine(instituteForm.getEvaluationDeadLine());
            statusData.setOverAllVerificationStatus(instituteForm.getOverAllVerificationStatus());
            statusData.setOverAllVerificationStatusComment(instituteForm.getOverAllVerificationStatusComment());
            statusData.setVerificationDeadLine(instituteForm.getVerificationDeadLine());
            statusData.setRegistrationStatus(instituteForm.getStatus());
            statusData.setVerificationFlag(instituteForm.getVerificationFlag());
            statusData.setEvaluationFlag(instituteForm.getEvaluationFlag());
            statusData.setVerificationRejectionCount(instituteForm.getVerificationRejectionCount());
            statusData.setEvaluationRejectionCount(instituteForm.getEvaluationRejectionCount());
            return statusData;
        }
        return null;
    }

    private void saveOverAllStatus(SubmitFormRequest request, InstituteForm instituteForm) {
        if (!Objects.isNull(request.getDraft()) && request.getDraft() == 0) {
            OverallAdminCheckDetails statusData = request.getOverallAdminCheckDetails();
            if (!Objects.isNull(statusData)) {
                if (!Objects.isNull(statusData.getOverAllEvaluationStatus())) {
                    instituteForm.setOverAllEvaluationStatus(statusData.getOverAllEvaluationStatus());
                }
                if (!Objects.isNull(statusData.getOverAllEvaluationStatusComment())) {
                    instituteForm.setOverAllEvaluationStatusComment(statusData.getOverAllEvaluationStatusComment());
                }
                if (!Objects.isNull(statusData.getEvaluationDeadLine()) && statusData.getEvaluationDeadLine() > 0) {
                    instituteForm.setEvaluationDeadLine(statusData.getEvaluationDeadLine());
                }
                if (!Objects.isNull(statusData.getOverAllVerificationStatus())) {
                    instituteForm.setOverAllVerificationStatus(statusData.getOverAllVerificationStatus());
                }
                if (!Objects.isNull(statusData.getOverAllVerificationStatusComment())) {
                    instituteForm.setOverAllVerificationStatusComment(statusData.getOverAllVerificationStatusComment());
                }
                if (!Objects.isNull(statusData.getVerificationDeadLine()) && statusData.getVerificationDeadLine() > 0) {
                    instituteForm.setVerificationDeadLine(statusData.getVerificationDeadLine());
                }
                if (!Objects.isNull(statusData.getRegistrationStatus())) {
//                    if(statusData.getRegistrationStatus()==ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode()){
//                        String qpId = userUtils.generateUniqueQpId();
//                        instituteForm.setQpId(qpId);
//                    }
                    instituteForm.setStatus(statusData.getRegistrationStatus());
                }
                if (!Objects.isNull(statusData.getEvaluationFlag())) {
                    instituteForm.setEvaluationFlag(statusData.getEvaluationFlag());
                }
                if (!Objects.isNull(statusData.getVerificationFlag())) {
                    instituteForm.setVerificationFlag(statusData.getVerificationFlag());
                }
                if (!Objects.isNull(statusData.getVerificationRejectionCount())) {
                    instituteForm.setVerificationRejectionCount(statusData.getVerificationRejectionCount());
                }
                if (!Objects.isNull(statusData.getEvaluationRejectionCount())) {
                    instituteForm.setEvaluationRejectionCount(statusData.getEvaluationRejectionCount());
                }
                formDao.save(instituteForm);

            }
        }
    }

    private boolean saveConfirmationPanel(SubmitFormRequest request) {
        List<ConfirmationPanel> confirmationPanelList = request.getConfirmationPanelList();
        SaveConfirmationPanelResponse confirmationPanelResponse = null;
        if (!Objects.isNull(confirmationPanelList) && !confirmationPanelList.isEmpty()) {
            SaveConfirmationPanelRequest saveMappingPanelRequest = new SaveConfirmationPanelRequest();
            saveMappingPanelRequest.setFormUniqueId(request.getFormUniqueId());
            saveMappingPanelRequest.setConfirmationPanelList(confirmationPanelList);
            confirmationPanelResponse = confirmationPanelService.savePanels(saveMappingPanelRequest);
            if (confirmationPanelResponse != null && confirmationPanelResponse.getSaved() > 0) {
                boolean isSuccess = confirmationPanelResponse.getFormUniqueId().longValue() == request.getFormUniqueId().longValue() &&
                        confirmationPanelResponse.getSaved().intValue() > 0;
                if (!isSuccess) {
                    throw new ServiceException(ErrorCode.FORM_SUBMISSION_FAILED, "Not able to save mappingPanelList");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean saveMappingPanel(SubmitFormRequest request) {
        List<MappingPanel> mappingPanelList = request.getMappingPanelList();
        SaveMappingPanelResponse mappingPanelResponse = null;
        if (!Objects.isNull(mappingPanelList) && !mappingPanelList.isEmpty()) {
            SaveMappingPanelRequest saveMappingPanelRequest = new SaveMappingPanelRequest();
            saveMappingPanelRequest.setFormUniqueId(request.getFormUniqueId());
            saveMappingPanelRequest.setMappingPanelList(mappingPanelList);
            mappingPanelResponse = mappingPanelService.saveMappingPanels(saveMappingPanelRequest);
            if (mappingPanelResponse != null && mappingPanelResponse.getSaved() > 0) {
                boolean isSuccess = mappingPanelResponse.getFormUniqueId().longValue() == request.getFormUniqueId().longValue() &&
                        mappingPanelResponse.getSaved().intValue() > 0;
                if (!isSuccess) {
                    throw new ServiceException(ErrorCode.FORM_SUBMISSION_FAILED, "Not able to save mappingPanelList");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean saveProgramStructureFlow(SubmitFormRequest request) {
        List<ProgramStructureWorkFlow> programStructureWorkFlowList = request.getProgramStructureFlowList();
        SaveProgramStructureFlowResponse programStructureFlowResponse = null;
        if (!Objects.isNull(programStructureWorkFlowList) && !programStructureWorkFlowList.isEmpty()) {
            SaveProgramStructureFlowRequest saveProgramStructureFlowRequest = new SaveProgramStructureFlowRequest();
            saveProgramStructureFlowRequest.setFormUniqueId(request.getFormUniqueId());
            saveProgramStructureFlowRequest.setDeleteAll(request.getDeleteAll());
            saveProgramStructureFlowRequest.setProgramStructureFlowList(programStructureWorkFlowList);
            programStructureFlowResponse = programStructureFlowService.saveProgramStructureFlow(saveProgramStructureFlowRequest);
            if (programStructureFlowResponse != null && programStructureFlowResponse.getSaved() > 0) {
                boolean isSuccess = programStructureFlowResponse.getFormUniqueId().longValue() == request.getFormUniqueId().longValue() &&
                        programStructureFlowResponse.getSaved().intValue() > 0;
                if (!isSuccess) {
                    throw new ServiceException(ErrorCode.FORM_SUBMISSION_FAILED, "Not able to save programStructureFlowList");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean saveProgramStructures(SubmitFormRequest request) {
        List<ProgramStructure> programStructureList = request.getProgramStructureList();
        SaveProgramStructureResponse saveProgramStructureResponse = null;
        if (!Objects.isNull(programStructureList) && !programStructureList.isEmpty()) {
            SaveProgramStructureRequest saveProgramStructureRequest = new SaveProgramStructureRequest();
            saveProgramStructureRequest.setFormUniqueId(request.getFormUniqueId());
            saveProgramStructureRequest.setDeleteAll(request.getDeleteAll());
            saveProgramStructureRequest.setProgramStructureList(programStructureList);
            saveProgramStructureResponse = programStructureService.saveProgramStructure(saveProgramStructureRequest);
            if (saveProgramStructureResponse != null && saveProgramStructureResponse.getSaved() > 0) {
                boolean isSuccess = saveProgramStructureResponse.getFormUniqueId().longValue() == request.getFormUniqueId().longValue() &&
                        saveProgramStructureResponse.getSaved().intValue() > 0;
                if (!isSuccess) {
                    throw new ServiceException(ErrorCode.FORM_SUBMISSION_FAILED, "Not able to save programStructureList");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    private boolean saveStandardsWorkFlow(SubmitFormRequest request) {
        List<StandardWorkFlow> standardWorkFlowList = request.getStandardWorkFlowsList();
        SaveStandardWorkFlowResponse standardWorkFlowResponse = null;
        if (!Objects.isNull(standardWorkFlowList) && !standardWorkFlowList.isEmpty()) {
            SaveStandardsRequest saveStandardsRequest = new SaveStandardsRequest();
            saveStandardsRequest.setFormUniqueId(request.getFormUniqueId());
            saveStandardsRequest.setDeleteAll(request.getDeleteAll());
            saveStandardsRequest.setStandardWorkFlowsList(standardWorkFlowList);
            standardWorkFlowResponse = standardsWorkFlowService.saveStandards(saveStandardsRequest, StandardsApiType.DEFAULT);
            if (standardWorkFlowResponse != null && standardWorkFlowResponse.getSaved() > 0) {
                boolean isSuccess = standardWorkFlowResponse.getFormUniqueId().longValue() == request.getFormUniqueId().longValue() &&
                        standardWorkFlowResponse.getSaved().intValue() > 0;
                if (!isSuccess) {
                    throw new ServiceException(ErrorCode.FORM_SUBMISSION_FAILED, "Not able to save standardWorkFlowList");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public ApplicationManagerEvaluationResponse evaluateApplication(ApplicationManagerEvaluationRequest request) {


        if (request.getRequestType() < 21) {
            throw new ServiceException(ErrorCode.BAD_REQUEST, " Request type should be 21");
        }
        if (request.getRequestType() >= FormApplicationUpdateType.QUALIFICATION_PROFILE_DATA.ordinal()) {
            return evaluateQualificationProfile(request);
        }
        FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(request.getFormUniqueId());
        if (formApplicationManager == null) {
            LOGGER.error("Application Not found:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        LOGGER.debug("Status for InstitutionProfileStatus");
        setInstitutionProfileStatus(formApplicationManager, request);

        LOGGER.debug("Status for QualityAssuranceStatus");
        setQualityAssuranceStatus(formApplicationManager, request);

        LOGGER.info("Status for AccessTransferProgressionStatus ");
        setAccessTransferProgressionStatus(formApplicationManager, request);

        LOGGER.debug("Status for QualificationDevelopmentApprovalReviewStatus");
        setQualificationDevelopmentApprovalReviewStatus(formApplicationManager, request);

        LOGGER.debug("Status for AssessmentDesignAndModerationStatus");
        setAssessmentDesignAndModerationStatus(formApplicationManager, request);

        LOGGER.debug("Status for CertificationStatus");
        setCertificationStatus(formApplicationManager, request);

        LOGGER.debug("Status for SustainabilityAndContinuousQualityImprovementStatus");
        setSustainabilityAndContinuousQualityImprovementStatus(formApplicationManager, request);

        if (request.getOverAllEvaluationStatus() == 0) {
            formApplicationManager.setAdditionalDataSubDate(request.getEvaluationDeadLine());
        }
        formApplicationManager.setOverallStatus(request.getOverAllEvaluationStatus());

        InstituteForm instituteForm = formDao.getInstitutionFormById(formApplicationManager.getFormUniqueId());
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        List<Integer> allowedStatus = List.of(
                ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode(),
                ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode(),
                ApplicationStatus.INSTITUTION_RESUBMITTED_DOCUMENT_FOR_ADMINISTRATIVE_CHECK.getCode(),
                ApplicationStatus.AM_FACTUAL_ACCURACY_COMPLETED_WITH_LISTED.getCode()
        );
        if (!allowedStatus.contains(instituteForm.getStatus())) {
            throw new ServiceException(ErrorCode.ACTION_CANNOT_BE_PERFORMED, " FormId : " + instituteForm.getFormUniqueId() + " Application status : " + instituteForm.getStatus());
        }
        if (instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode())) {
            instituteForm.setStatus(ApplicationStatus.AM_COMPLETE_ADMIN_CHECK_FOR_SUBMITTED_DOCUMENTS.getCode());
        } else {
            if (request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode())) {
                if (request.getOverAllEvaluationStatus().equals(0)) {
                    //TODO remove static values
                    LOGGER.info("0 :-> additional data required");
                    instituteForm.setStatus(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode());
                } else if (request.getOverAllEvaluationStatus().equals(1)) {
                    LOGGER.info("1 :-> adminitrative check completed");
                    instituteForm.setStatus(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode());
//                    if(instituteForm.getQpId()==null){
//                        String qpId = userUtils.generateUniqueQpId();
//                        instituteForm.setQpId(qpId);
//                    }
                } else if (request.getOverAllEvaluationStatus().equals(2)) {
                    LOGGER.info("2 :-> Bqa admin rejected");
                    instituteForm.setStatus(ApplicationStatus.DFO_ADMIN_REJECTED.getCode());
                }
            }
        }


        formDao.save(instituteForm);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        formApplicationManager.setOverallStatusComment(request.getOverAllEvaluationStatusComment());
        userDao.save(formApplicationManager);

        sendEvaluationEmail(request, instituteForm);

        return responseBuilder.buildApplicationManagerEvaluationResponse("Application Evaluated Successfully", instituteForm.getFormUniqueId());
    }

    private void sendEvaluationEmail(ApplicationManagerEvaluationRequest request, InstituteForm instituteForm) {
        if (!(instituteForm.getStatus().equals(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode()))) {
            if (request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode())) {
                if (request.getOverAllEvaluationStatus().equals(0)) {
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_ADMINISTRATIVE_CHECK_NOT_COMPLETED");
                    if (Objects.nonNull(mailTemplate)) {
                        List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                        String mailBody = mailTemplate.getTemplateBody();

//                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                        Map<String, Object> templateModel = new HashMap<>();

                        User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
                        if (mailBody != null && !mailBody.isBlank()) {
                            Date date = new Date(request.getEvaluationDeadLine());
                            DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                            format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                            Instant instant = Instant.ofEpochMilli(request.getEvaluationDeadLine());
                            LocalDateTime deadlineDate = LocalDateTime.ofInstant(instant, ZoneId.of("Asia/Bahrain"));
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            String formattedDate = deadlineDate.format(formatter);
                            mailBody = mailBody.replace("{userName}", userData.getUsername());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                            mailBody = mailBody.replace("{proposedDate}", formattedDate);
                            mailBody = mailBody.replace("{QualificationTitle}", instituteForm.getQualificationTitle());
                        }
                        String mailHtmlPath = "mail-template.html";
                        templateModel.put("mailBody", mailBody);

//                        List<String> ccAdresses = new ArrayList<>();
                        awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }
                } else if (request.getOverAllEvaluationStatus().equals(1)) {
                    MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("AM_ADMINISTRATIVE_CHECK_COMPLETED");
                    if (Objects.nonNull(mailTemplate)) {
                        List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                        String mailBody = mailTemplate.getTemplateBody();

//                        mailBody = mailBody.replace("{applicationId}", request.getFormUniqueId().toString());
                        Map<String, Object> templateModel = new HashMap<>();

                        User userData = userDao.getByUserEmail(instituteForm.getContactPersonEmail());
                        User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                        if (mailBody != null && !mailBody.isBlank()) {
                            mailBody = mailBody.replace("{userName}", userData.getUsername());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                            mailBody = mailBody.replace("{applicationId}", instituteForm.getFormUniqueId().toString());
                            mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                            mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                            mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                        }
                        String mailHtmlPath = "mail-template.html";
                        templateModel.put("mailBody", mailBody);
                        awsService.sendMail(userData.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }
                }
            }
        } else {
            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("CONDITION_FULFILMENT_ILEP_DEADLINE");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);

                DueDates dueDates = userDao.findByAction(ApplicationStatus.INSTITUTION_RE_SUBMITS_THE_REQUIRED_DOCUMENTS.getCode());
                long turnAroundTime = 3;
                if (!Objects.isNull(dueDates)) {
                    turnAroundTime = dueDates.getNoOfDays();
                }
                ILEPEvaluationForm evaluationForm = ilepEvaluationDao.getByFormUniqueId(instituteForm.getFormUniqueId());
                long dueDate = evaluationForm.getPartialMetDate() + TimeUnit.DAYS.toMillis(turnAroundTime);
                Map<String, Object> templateModel = new HashMap<>();
                List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                User amDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                ilepList.forEach(ilepPanel -> {
                    User ilepMember = userDao.getByUserId(ilepPanel.getIlepMemberId());
                    if(ilepMember.getActive()==1){
                        String mailBody = mailTemplate.getTemplateBody();
                        if (mailBody != null && !mailBody.isBlank()) {
                            Date date = new Date(dueDate);
                            DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                            format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                            mailBody = mailBody.replace("{userName}", ilepMember.getUsername());
                            mailBody = mailBody.replace("{instituteName}", instituteForm.getInstitutionName());
                            mailBody = mailBody.replace("{proposedDate}", format.format(date));
                            mailBody = mailBody.replace("{managerName}", amDetails.getUsername());
                            mailBody = mailBody.replace("{managerMail}", amDetails.getEmailId());
                            mailBody = mailBody.replace("{managerNumber}", amDetails.getContactNumber());
                        }
                        String mailHtmlPath = "mail-template.html";
                        templateModel.put("mailBody", mailBody);
                        awsService.sendMail(ilepMember.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                    }
                });
            }
        }
    }

    private ApplicationManagerEvaluationResponse evaluateQualificationProfile(ApplicationManagerEvaluationRequest request) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (instituteForm == null) {
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND, " FormId : " + instituteForm.getFormUniqueId() + " Application status : " + instituteForm.getStatus());
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteForm.getStatus());

        if (true || request.getSubmissionStatus().equals(SubmissionStatus.SUBMIT.getCode())) {
            if (request.getOverAllEvaluationStatus().equals(0)) {
                //TODO remove static values
                LOGGER.info("0 :-> additional data required");
                instituteForm.setStatus(ApplicationStatus.REQUIRED_ADDITION_DATA.getCode());
            } else if (request.getOverAllEvaluationStatus().equals(1)) {
                LOGGER.info("1 :-> adminitrative check completed");
                instituteForm.setStatus(ApplicationStatus.ADMINISTRATIVE_CHECk_COMPLETED.getCode());
//                if(instituteForm.getQpId()==null){
//                    String qpId = userUtils.generateUniqueQpId();
//                    instituteForm.setQpId(qpId);
//                }
            } else if (request.getOverAllEvaluationStatus().equals(2)) {
                LOGGER.info("2 :-> Bqa admin rejected");
                instituteForm.setStatus(ApplicationStatus.DFO_ADMIN_REJECTED.getCode());
            }
        }
        instituteForm.setOverAllEvaluationStatus(request.getOverAllEvaluationStatus());
        instituteForm.setOverAllEvaluationStatusComment(request.getOverAllEvaluationStatusComment());
        instituteForm.setEvaluationDeadLine(request.getEvaluationDeadLine());

        instituteForm.setOverAllVerificationStatus(request.getOverAllVerificationStatus());
        instituteForm.setOverAllVerificationStatusComment(request.getOverAllVerificationStatusComment());
        instituteForm.setVerificationDeadLine(request.getVerificationDeadLine());

        formDao.save(instituteForm);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteForm.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteForm.getFormUniqueId());
        }
        sendEvaluationEmail(request, instituteForm);
        return responseBuilder.buildApplicationManagerEvaluationResponse("Application Evaluated Successfully", instituteForm.getFormUniqueId());
    }

    @Override
    @Transactional
    public AssignApplicationManagerResponse assignApplicationManager(AssignApplicationManagerRequest request) {

        InstituteForm instituteRegDat = formDao.getInstitutionFormById(request.getFormUniqueId());
        if (Objects.isNull(instituteRegDat)) {
            LOGGER.error("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.ASSIGN_APPLICATION_MANAGER_INSTITUTE_NOT_FOUND);
        }
        ApplicationStatus currentStatus = ApplicationStatus.getByCode(instituteRegDat.getStatus());
        User user = userDao.getByUserId(request.getAmUserId());
        if (Objects.isNull(user)) {
            LOGGER.error("Application manager not found with id:{}", request.getAmUserId());
            throw new ServiceException(ErrorCode.ASSIGN_APPLICATION_MANAGER_NOT_FOUND);
        }


        /*        AppManagerMapping appManagerMappedData = formDao.getAppMngMapByFormUniqueIdAndUserId(request.getFormUniqueId(), request.getAmUserId());*/
        AppManagerMapping appManagerMappedData = formDao.getAppManagerByFormUniqueId(request.getFormUniqueId());
        AppManagerMapping appManagerMappedData2 = appManagerMappedData;
        if (Objects.isNull(appManagerMappedData)) {
            /**
             * checking status of application only if am not created initially
             * */
            if (!instituteRegDat.getStatus().equals(ApplicationStatus.INSTITUTION_SUBMITTED.getCode())) {
                throw new ServiceException(ErrorCode.ASSIGN_APPLICATION_MANAGER_INSTITUTE_FORM_NOT_SUBMITTED);
            }
            appManagerMappedData = new AppManagerMapping();

            appManagerMappedData.setFormUniqueId(request.getFormUniqueId());
            appManagerMappedData.setCreatedBy(user.getUserId());
            appManagerMappedData.setCreatedAppId(request.getAppId());
/*            LOGGER.error("Institute record already assigned to application manager");
            throw new ServiceException(ErrorCode.ASSIGN_APPLICATION_MANAGER_ALREADY_ASSIGNED);*/
            instituteRegDat.setStatus(ApplicationStatus.ASSIGNED_APPLICATION_MANAGER.getCode());
        }
        appManagerMappedData.setUserId(request.getAmUserId());
        appManagerMappedData.setUpdatedAppId(request.getAppId());
        appManagerMappedData = formDao.saveAppManagerMapping(appManagerMappedData);
        instituteRegDat.setAssignedAppManager(request.getAmUserId());

        formDao.save(instituteRegDat);
        ApplicationStatus newStatus = ApplicationStatus.getByCode(instituteRegDat.getStatus());
        /*Writing Application status log to database */
        if (!currentStatus.equals(newStatus)) {
            logService.writeLogToDatabase(WebUtils.getUserId(), currentStatus, newStatus, instituteRegDat.getFormUniqueId());
        }

        /*FormApplicationManager formApplicationManager = userDao.getByFormUniqueId(instituteRegDat.getFormUniqueId());
        formApplicationManager.setApplicationContactEmail(user.getEmailId());
        formApplicationManager.setApplicationContactName(user.getUsername());
        formApplicationManager.setApplicationContactNumber(user.getContactNumber());
        formApplicationManager.setApplicationContactPosition(user.getPosition());
        formDao.save(formApplicationManager);*/

        if (Objects.isNull(appManagerMappedData2)) {
            LOGGER.info("Before initializing variables for institution notification mail regarding AM assignee");

            MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("DFO_ASSIGN_AM");
            if (Objects.nonNull(mailTemplate)) {
                List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteRegDat);
                String mailBody = mailTemplate.getTemplateBody();
                Date date = new Date(instituteRegDat.getLastUpdatedTime());
                DateFormat format = new SimpleDateFormat("E, MMM dd yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                if (mailBody != null && !mailBody.isBlank()) {
                    mailBody = mailBody.replace("{userName}", instituteRegDat.getContactPersonName());
//                mailBody = mailBody.replace("{applicationId}", instituteRegDat.getFormUniqueId().toString());
                    mailBody = mailBody.replace("{managerName}", user.getUsername());
                    mailBody = mailBody.replace("{managerContactNumber}", user.getContactNumber());
                    mailBody = mailBody.replace("{managerEmail}", user.getEmailId());
                    mailBody = mailBody.replace("{QualificationTitle}", instituteRegDat.getQualificationTitle());
//                mailBody = mailBody.replace("{Time}", format.format(date));
                }

                Map<String, Object> templateModel = new HashMap<>();

                templateModel.put("mailBody", mailBody);

                String mailHtmlPath = "mail-template.html";
              /*  List<String> ccAdresses = new ArrayList<>();
                ccAdresses.add(user.getEmailId());*/
                awsService.sendMail(instituteRegDat.getContactPersonEmail(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
            }
        }

        return responseBuilder.buildAssignApplicationManagerResponse("Successfully assigned AM");
    }

    @Override
    public GetInstitutesHavingQualificationResponse getInstitutesHavingQualificationDetails(){
        Long startTime=System.currentTimeMillis();
        List<GetInstitutesHavingQualificationResponseModel> finalResponse = formDao.getDistinctInstitutionDetails();
        return responseBuilder.buildGetDistingtInstituteResponse(finalResponse,startTime);
    }

    @Override
    public GetApplicationDetailsResponse getApplicationDetails(GetApplicationDetailsRequest request) throws JsonProcessingException {

        FormApplicationUpdateType type = FormApplicationUpdateType.getByCode(request.getRequestType());
        GetApplicationDetailsResponseModel responseModel = new GetApplicationDetailsResponseModel();
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());
        responseModel.setIsPaid(instituteForm.getIsPaid());
        if (Objects.isNull(instituteForm)) {
            LOGGER.error("Institute record not found with id:{}", request.getFormUniqueId());
            throw new ServiceException(ErrorCode.APPLICATION_DETAILS_INSTITUTE_NOT_FOUND);
        }
        List<Integer> completedStatus = new ArrayList<>();

        Integer registrationStatus = instituteForm.getStatus();
        ObjectMapper objectMapper = new ObjectMapper();
        if (instituteForm.getCompletedStatus() != null) {
            completedStatus = objectMapper.readValue(instituteForm.getCompletedStatus(), new TypeReference<List<Integer>>() {
            });
        }
        responseModel.setCompletedStatus(completedStatus);
        Integer isDateExtensionRequested = null;
        String dateExtensionReason = null;
        Long requestedExtensionDate = null;
        Integer dateExtensionStatus = null;
        Long submissionDate = instituteForm.getRandomDate();

        if (ApplicationStatus.DFO_ADMIN_APPROVED.getCode().equals(registrationStatus)) {
            isDateExtensionRequested = instituteForm.getIsDateExtensionRequested();
            dateExtensionReason = instituteForm.getDateExtensionReason();
            requestedExtensionDate = instituteForm.getRequestedExtensionDate();
            dateExtensionStatus = instituteForm.getDateExtensionStatus();
            submissionDate = Long.valueOf(instituteForm.getPlannedSubDate());
        }

        FormApplicationManager applicationFormData = new FormApplicationManager();

        if (setNewBornFormData(request, responseModel, instituteForm, registrationStatus, isDateExtensionRequested, dateExtensionReason, requestedExtensionDate, dateExtensionStatus, submissionDate))
            return responseBuilder.buildGetApplicationDetailsInstituteFormResponse(responseModel);

        FormApplicationResponseModel formApplicationResponseModel = new FormApplicationResponseModel();
        switch (type) {
            case QUALIFICATION_PROFILE_DATA:
                QualificationProfileData qualificationProfileData = userDao.getQualificationProfileDataByFormUniqueId(request.getFormUniqueId());
                try {/**/
                    qualificationProfileData.setName(instituteForm.getContactPersonName());
                    qualificationProfileData.setEmail(instituteForm.getContactPersonEmail());
                    qualificationProfileData.setMobileNo(instituteForm.getContactPersonNumber());
                    qualificationProfileData.setPosition(instituteForm.getContactPersonTitle());
                    qualificationProfileData.setSubmissionDate(Long.parseLong(instituteForm.getPlannedSubDate()));
                    qualificationProfileData.setOriginalPlacementDate(instituteForm.getOriginalPlacementDate());
                } catch (Exception e) {

                }
                responseModel.setQualificationProfileData(qualificationProfileData);
                break;
            case INSTITUTION_PROFILE_DATA:
                InstitutionProfileData institutionProfileData = setInstitutionProfileData(applicationFormData);
                institutionProfileData.setSubmissionDate(instituteForm.getPlannedSubDate());
                formApplicationResponseModel.setInstitutionProfileData(institutionProfileData);
                break;
            case QUALITY_ASSURANCE_SYSTEM:
                QualityAssuranceSystemOverviewData qualityAssuranceSystemOverviewData = setQualityAssuranceSystemOverviewData(applicationFormData);
                formApplicationResponseModel.setQualityAssuranceSystemOverviewData(qualityAssuranceSystemOverviewData);
                break;
            case STANDARD:
                Standard1Data standard1Data = setStandard1Data(applicationFormData);
                Standard2Data standard2Data = setStandard2Data(applicationFormData);
                Standard3Data standard3Data = setStandard3Data(applicationFormData);
                Standard4Data standard4Data = setStandard4Data(applicationFormData);
                Standard5Data standard5Data = setStandard5Data(applicationFormData);
                formApplicationResponseModel.setStandard1Data(standard1Data);
                formApplicationResponseModel.setStandard2Data(standard2Data);
                formApplicationResponseModel.setStandard3Data(standard3Data);
                formApplicationResponseModel.setStandard4Data(standard4Data);
                formApplicationResponseModel.setStandard5Data(standard5Data);
                break;
            case APPLICATION_CONTACT:
                ApplicationContactData applicationContactData = setApplicationContactData(applicationFormData, instituteForm.getAssignedAppManager(), instituteForm);
                formApplicationResponseModel.setApplicationContactData(applicationContactData);
        }

        if (registrationStatus >= ApplicationStatus.INSTITUTION_SUBMITTED.getCode()) {
            isDateExtensionRequested = applicationFormData.getIsExtensionRequested();
            dateExtensionReason = applicationFormData.getReasonForExtension();
            requestedExtensionDate = applicationFormData.getExtensionRequestedDate();
            dateExtensionStatus = applicationFormData.getExtensionRequestedStatus();
            submissionDate = applicationFormData.getAdditionalDataSubDate();
        }
        responseModel.setRegistrationStatus(registrationStatus);
        responseModel.setFormApplicationData(formApplicationResponseModel);
        responseModel.setIsDateExtensionRequested(isDateExtensionRequested);
        responseModel.setDateExtensionReason(dateExtensionReason);
        responseModel.setRequestedExtensionDate(requestedExtensionDate);
        responseModel.setDateExtensionStatus(dateExtensionStatus);
        responseModel.setSubmissionDate(submissionDate);
        responseModel.setOverAllStatus(applicationFormData.getOverallStatus());
        responseModel.setOverallStatusComment(applicationFormData.getOverallStatusComment());
        responseModel.setFormId(instituteForm.getFormId());
        responseModel.setFormUniqueId(instituteForm.getFormUniqueId());
        responseModel.setSubStatus(instituteForm.getSubStatus());
        responseModel.setFilePathParam(applicationFormData.getFilePathParameter());
        responseModel.setIsVerificationPanelRequired(instituteForm.getIsVerificationPanelRequired());
        responseModel.setIsRevalidation(instituteForm.getIsRevalidation());
        responseModel = setQPAdditionalData(responseModel, instituteForm);
        responseModel = setQPDateExtensionDetails(responseModel);
        responseModel.setQpId(instituteForm.getQpId());
        List<IlepPanel> ilepPanels = evaluationDao.getILEPByFormUniqueId(request.getFormUniqueId());
        responseModel.setIlepCount(ilepPanels.size());
        GetApplicationDetailsResponseModel finalResponseModel = responseModel;
        ilepPanels.forEach(el -> {
            if (BooleanEnum.TRUE.getCode().equals(el.getGrandAccess())) {
                finalResponseModel.getIlepsGotGrandAccess().add(el.getIlepMemberId());
            }
        });

        return responseBuilder.buildGetApplicationDetailsFormApplicationResponse(finalResponseModel);
    }

    private GetApplicationDetailsResponseModel setQPAdditionalData(GetApplicationDetailsResponseModel responseModel, InstituteForm instituteForm) {

        try {
            responseModel.setNoOfModules(instituteForm.getNoOfModules());

            responseModel.setAwardingBody(instituteForm.getAwardingBody());
            responseModel.setQualificationSize(instituteForm.getQualificationSize());
            responseModel.setIncludedInOther(instituteForm.getIncludedInOther());
            responseModel.setQualificationType(instituteForm.getQualificationType());
            responseModel.setQualificationTitle(instituteForm.getQualificationTitle());

            responseModel.setPlannedSubmissionDate(instituteForm.getPlannedSubDate());

            responseModel.setOverAllEvaluationStatus(instituteForm.getOverAllEvaluationStatus());
            responseModel.setOverAllEvaluationStatusComment(instituteForm.getOverAllEvaluationStatusComment());
            responseModel.setOverAllVerificationStatus(instituteForm.getOverAllVerificationStatus());
            responseModel.setOverAllVerificationStatusComment(instituteForm.getOverAllVerificationStatusComment());
            responseModel.setEvaluationDeadLine(instituteForm.getEvaluationDeadLine());
            responseModel.setVerificationDeadLine(instituteForm.getVerificationDeadLine());

            if (!Objects.isNull(instituteForm.getEvaluationFlag())) {
                responseModel.setEvaluationFlag(instituteForm.getEvaluationFlag());
            } else {
                responseModel.setEvaluationFlag(0);
            }
            if (!Objects.isNull(instituteForm.getEvaluationFlag())) {
                responseModel.setVerificationFlag(instituteForm.getVerificationFlag());
            } else {
                responseModel.setVerificationFlag(0);
            }
            if (!Objects.isNull(instituteForm.getEvaluationFlag())) {
                responseModel.setEvaluationRejectionCount(instituteForm.getEvaluationRejectionCount());
            } else {
                responseModel.setEvaluationRejectionCount(0);
            }
            if (!Objects.isNull(instituteForm.getEvaluationFlag())) {
                responseModel.setVerificationRejectionCount(instituteForm.getVerificationRejectionCount());
            } else {
                responseModel.setVerificationRejectionCount(0);
            }

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseModel;
    }

    private boolean setNewBornFormData(GetApplicationDetailsRequest request, GetApplicationDetailsResponseModel responseModel, InstituteForm instituteForm, Integer registrationStatus, Integer isDateExtensionRequested, String dateExtensionReason, Long requestedExtensionDate, Integer dateExtensionStatus, Long submissionDate) {
        if (request.getRequestType() < FormApplicationUpdateType.QUALIFICATION_PROFILE_DATA.ordinal()) {
            FormApplicationManager applicationFormData = userDao.getByFormUniqueId(request.getFormUniqueId());

            if (Objects.isNull(applicationFormData)) {
                LOGGER.info("User has completed only basic registration");
                InstituteFormInterface instituteFormDetails = formDao.getInstituteFormDetails(request.getFormUniqueId());
                ObjectMapper mapper = new ObjectMapper();
                InstituteFormResponseModel instituteFormResponse = mapper.convertValue(instituteFormDetails, InstituteFormResponseModel.class);
                instituteFormResponse.setSubmissionDate(submissionDate.toString());
                responseModel.setRegistrationStatus(registrationStatus);
                responseModel.setInstituteFormData(instituteFormResponse);
                responseModel.setIsDateExtensionRequested(isDateExtensionRequested);
                responseModel.setDateExtensionReason(dateExtensionReason);
                responseModel.setRequestedExtensionDate(requestedExtensionDate);
                responseModel.setDateExtensionStatus(dateExtensionStatus);
                responseModel.setSubmissionDate(submissionDate);
                responseModel.setFormId(instituteForm.getFormId());
                responseModel.setFormUniqueId(instituteForm.getFormUniqueId());
                responseModel.setSubStatus(instituteForm.getSubStatus());
                return true;
            }
        } else {
            QualificationProfileData qualificationProfileData = userDao.getQualificationProfileDataByFormUniqueId(request.getFormUniqueId());
            if (Objects.isNull(qualificationProfileData)) {
                qualificationProfileData = new QualificationProfileData();
                qualificationProfileData.setInstitutionNameEnglish(instituteForm.getInstitutionName());
                qualificationProfileData.setFormUniqueId(instituteForm.getFormUniqueId());
                qualificationProfileData.setListingId(instituteForm.getListingId());
                qualificationProfileData.setExpiryDate(instituteForm.getExpDate());
                qualificationProfileData.setIssueDate(instituteForm.getIssueDate());
                qualificationProfileData.setQualificationNameEnglish(instituteForm.getQualificationTitle());

                responseModel.setRegistrationStatus(registrationStatus);
                responseModel.setQualificationProfileData(qualificationProfileData);
                responseModel.setIsDateExtensionRequested(isDateExtensionRequested);
                responseModel.setDateExtensionReason(dateExtensionReason);
                responseModel.setRequestedExtensionDate(requestedExtensionDate);
                responseModel.setDateExtensionStatus(dateExtensionStatus);
                responseModel.setSubmissionDate(submissionDate);
                responseModel.setFormId(instituteForm.getFormId());
                responseModel.setFormUniqueId(instituteForm.getFormUniqueId());
                responseModel.setSubStatus(instituteForm.getSubStatus());
                responseModel.setIsRevalidation(instituteForm.getIsRevalidation());
                //qualificationProfileData.setStartDate(instituteForm.getSt());

                responseModel = setQPAdditionalData(responseModel, instituteForm);

                try {/**/
                    qualificationProfileData.setName(instituteForm.getContactPersonName());
                    qualificationProfileData.setEmail(instituteForm.getContactPersonEmail());
                    qualificationProfileData.setMobileNo(instituteForm.getContactPersonNumber());
                    qualificationProfileData.setPosition(instituteForm.getContactPersonTitle());

                    qualificationProfileData.setSubmissionDate(Long.parseLong(instituteForm.getPlannedSubDate()));
                } catch (Exception e) {

                }
                responseModel.setQualificationProfileData(qualificationProfileData);
                responseModel = setQPDateExtensionDetails(responseModel);

                return true;
            }
        }
        return false;
    }

    private GetApplicationDetailsResponseModel setQPDateExtensionDetails(GetApplicationDetailsResponseModel responseModel) {
        if (!Objects.isNull(responseModel.getFormUniqueId())) {
            List<DateExtension> dateExtensionList = getDateExtensions(responseModel.getFormUniqueId());
            if (dateExtensionList != null && !dateExtensionList.isEmpty()) {
                responseModel.setDateExtensionList(dateExtensionList);
            }
        }
        return responseModel;
    }

    @Transactional
    public List<DateExtension> getDateExtensions(Long formUniqueId, Integer status) {
        return dateExtensionRepository.getDateExtensions(formUniqueId, status);
    }

    @Transactional
    public List<DateExtension> getDateExtensions(Long formUniqueId) {
        return dateExtensionRepository.getDateExtensions(formUniqueId);
    }

    @Override
    public LogoutResponse logout(String token) {
        UserLoginInfo userLoginInfo = userLoginInfoDao.getByToken(token);
        LOGGER.info("data :" + userLoginInfo);
        if (null != userLoginInfo) {
            /*
            ArchiveUserLoginInfo archiveUserLoginInfo = new ArchiveUserLoginInfo();
            BeanUtils.copyProperties(userLoginInfo, archiveUserLoginInfo);
            archiveUserLoginInfo = archiveUserLoginInfoDao.save(archiveUserLoginInfo);
            LOGGER.info("data1 :" + archiveUserLoginInfo);
            if (archiveUserLoginInfo != null) {
                userLoginInfoDao.delete(userLoginInfo);
            }
             */
            userLoginInfoDao.delete(userLoginInfo);
        }
        return responseBuilder.buildLogoutResponse();
    }

    @Override
    public GetApplicationManagerListResponse getApplicationManagerList() {
        PagedData<GetApplicationManagerListResponseModel> pageData = userDao.getApplicationManagerList();
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.buildGetApplicationManagerListResponse(pageData);
    }

    @Override
    @Transactional
    public UserEditResponse editUser(UserEditRequest request,int isIlepUserEdit) {
        Integer userType = WebUtils.getUserType();
        if (!(userType == UserType.SUPER_ADMIN.getCode() || userType == UserType.DIRECTOR.getCode())) {
            throw new ServiceException(ErrorCode.USER_EDIT_ACTION_NOT_PERMITTED);
        }

        User user = userDao.getByUserId(request.getUserId());
        if (user == null) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }

        user.setAccessRight(request.getAccessRight());
        user.setReportAccess(request.getReportAccess());
        user.setUsername(request.getName());
        user.setPosition(request.getPosition());
        user.setContactNumber(request.getContactNo());
        user.setActive(request.getActive());
        user.setBio(request.getBio());
        if (null != request.getPassword())
            user.setPassword(request.getPassword());

        if(isIlepUserEdit!=1 && !request.getRoleIds().isEmpty()){
            Set<Role> newRoles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
            user.setRoles(newRoles);
        }

        userDao.saveUser(user);
        userLoginInfoDao.deleteByUserId(request.getUserId());
        return responseBuilder.userEditResponseBuilder(StatusCode.SUCCESS.getCode(), user.getUserId());
    }

    @Override
    @Transactional
    public UserEditResponse editInstituteUser(InstituteUserEditRequest request) {
        User user = userDao.getByUserId(request.getUserId());
        ListedInstitute listedInstitute = listingRepository.getByUserId(user.getUserId());
        if (user == null || listedInstitute == null) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }

        boolean hasInstituteRole = user.getRoles().stream().anyMatch(role -> role.getUserType()==UserType.INSTITUTION.getCode()&&role.getSubType()==UserSubType.ADMIN.getCode());
        if(!hasInstituteRole ||  user.getRoles().isEmpty() ){
            throw new ServiceException(ErrorCode.GET_USER_BY_TYPE_INVALID_USER_TYPE);
        }
        User existingUser = userDao.getUserByEmailId(request.getEmail());
        if (existingUser != null && !existingUser.getUserId().equals(user.getUserId())) {
            throw new ServiceException(ErrorCode.LOGIN_USER_ALREADY_EXIST_WITH_SAME_EMAIL_AND_LICENSE);
        }
        user.setEmailId(request.getEmail());
        user.setActive(request.getActive());
        user.setContactNumber(request.getContactNumber());
        userDao.saveUser(user);

        //Update institution email Id in listed Institute table
        listedInstitute.setEmail(user.getEmailId());
        listingRepository.save(listedInstitute);

        userLoginInfoDao.deleteByUserId(request.getUserId());
        return responseBuilder.userEditResponseBuilder(StatusCode.SUCCESS.getCode(), user.getUserId());

    }

    @Transactional
    public CreateUserResponse createUser(CreateUserResponseRequest request,int isIlepCreation) {

        User user = userDao.getByUserEmail(request.getMailId());
        if (Objects.nonNull(user)) {
            LOGGER.debug("User already exist with mailId:{}", request.getMailId());
            throw new ServiceException(ErrorCode.CREATE_USER_ALREADY_EXIST);
        }
        Set<Role> roles = new HashSet<>();
        if( isIlepCreation==1 ){
            // Assign ilep role
            List<Role> ilepRolesList = roleRepository.findByUserTypeAndSubType(UserType.ILEP_MEMBER.getCode(),UserType.ILEP_MEMBER.getSubType().getCode());
            Set<Long> ilepRole = ilepRolesList.stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());

            request.setRoleIds(ilepRole);
        }


        for (Long roleId : request.getRoleIds()) {
            Role role = roleRepository.getById(roleId);
            if(role==null){
                throw new ServiceException(ErrorCode.ROLE_NOT_FOUND);
            }
            try{
                roles.add(role);
            }catch (Exception  e){
                e.printStackTrace();
            }

        }
        user = new User();
        user.setUsername(request.getUsername());
        user.setEmailId(request.getMailId());
        user.setPassword(request.getPassword());
        user.setRoles(roles);
        user.setReportAccess(request.getReport_access());
        user.setAccessRight(request.getAccess_right());
        user.setCreatedAppId(request.getAppId());
        user.setUpdatedAppId(request.getAppId());
        user.setContactNumber(request.getContactNo());
        user.setPosition(request.getPosition());
        user.setActive(UserStatus.ACTIVE.getCode());
        user.setBio(request.getBio());
        userDao.saveUser(user);
        return responseBuilder.buildCreateUserResponse(user.getUserId(), "User created successfully");

    }

    @Override
    public GetUserListResponse getUserDataList(GetUserDataListRequest request) {

        PagedData<GetUserDataResponseModel> pageData = userDao.getUserList(request.getPage(), request.getLimit());
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getUserListResponseBuilder(pageData);
    }

    @Override
    public GetInstituteUserListResponse getInstituteUserDataList(GetInstituteUserDataListRequest request) {
        if(request.getSearchValue() != null){
            String searchValue = URLDecoder.decode(request.getSearchValue(), StandardCharsets.UTF_8);
            request.setSearchValue(searchValue);
        }
        PagedData<GetInstituteUserDataResponseModel> pageData = userDao.getInstituteUserList(request.getPage(), request.getLimit(),request.getSearchValue());
        return responseBuilder.getInstituteUserListResponseBuilder(pageData);
    }

    @Override
    public GetUsersResponse getUsersByTypeAndSubType(GetUsersRequest request) {
        UserType userType = UserType.getByCode(request.getUserType(), request.getUserSubType());
        if (userType == null) {
            throw new ServiceException(ErrorCode.GET_USER_BY_TYPE_INVALID_USER_TYPE);
        }
        PagedData<GetUsersResponseModel> pagedData = userDao.getUsersByTypeAndSubType(request.getUserType(), request.getUserSubType()
                , request.getPage(), request.getLimit(), UserStatus.ACTIVE.getCode());

        if (pagedData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getUsersResponseBuilder(pagedData);
    }

    @Override
    public GetDueDateResponse getDueDates(GetDueDateRequest request) {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.GET_DUE_DATES_USER_NOT_PERMITTED);
        }
        PagedData<GetDueDateResponseModel> pagedData = userDao.getDueDates(request.getPage(), request.getLimit());
        if (pagedData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getDueDateResponseBuilder(pagedData);
    }

    @Override
    public SetDueDatesResponse setDueDates(SetDueDateRequest request) {
        Long userId = WebUtils.getUserId();
        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.SET_DUE_DATES_USER_NOT_PERMITTED);
        }
        List<SetDueDate> setDueDateList = request.getSetDueDateList();
        if (setDueDateList.isEmpty()) {
            throw new ServiceException(ErrorCode.SET_DUE_DATES_INPUT_VALIDATION_FAILED);
        }
        List<DueDates> dueDates = new ArrayList<>();
        for (SetDueDate setDueDate : setDueDateList) {
            DueDates dueDate = userDao.findByActionAndType(setDueDate.getAction(), setDueDate.getType());
            if (null != dueDate) {
                dueDate.setNoOfDays(setDueDate.getNoOfDays());
                dueDates.add(dueDate);
            } else {
                LOGGER.error("Type and action not found : Action {} , Type :{}", setDueDate.getAction(), setDueDate.getType());
                throw new ServiceException(ErrorCode.SET_DUE_DATES_TYPE_ACTION_NOT_FOUND);
            }
        }
        userDao.saveAllDueDates(dueDates);
        return responseBuilder.setDueDateResponseBuilder(StatusCode.SUCCESS.getCode());
    }

    @Override
    @Transactional
    public SetMailTemplateResponse setMailTemplate(SetMailTemplateRequest request) {

        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.SET_MAIL_TEMPLATE_USER_NOT_PERMITTED);
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode(request.getTemplateCode());
        if (Objects.isNull(mailTemplate)) {
            LOGGER.error("Template not found with code:{}", request.getTemplateCode());
            throw new ServiceException(ErrorCode.SET_MAIL_TEMPLATE_NOT_FOUND);
        }

        mailTemplate.setTemplateBody(request.getMailBody());
        if (!Objects.isNull(request.getTemplateSubject()) && !request.getTemplateSubject().isBlank()) {
            mailTemplate.setTemplateSubject(request.getTemplateSubject());
        }
        mailTemplate.setInstituteCc(request.getInstituteCc());
        mailTemplate.setDfoAdminCc(request.getDfoAdminCc());
        mailTemplate.setAmCc(request.getAmCc());
        mailTemplate.setDfoMemberCc(request.getDfoMemberCc());
        mailTemplate.setChiefCc(request.getChiefCc());
        mailTemplate.setDirectorCc(request.getDirectorCc());
        mailTemplate.setIlepCc(request.getIlepCc());
        mailTemplateDao.save(mailTemplate);
        return responseBuilder.buildSetMailTemplateResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    public GetMailTemplateResponse getMailTemplate(Long mailId) throws InvocationTargetException, IllegalAccessException {

        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.GET_MAIL_TEMPLATE_USER_NOT_PERMITTED);
        }

        MailTemplate mailTemplate = mailTemplateDao.getByTemplateId(mailId);
        if (Objects.isNull(mailTemplate)) {
            LOGGER.error("Template not found with id:{}", mailId);
            throw new ServiceException(ErrorCode.GET_MAIL_TEMPLATE_NOT_FOUND);
        }

        GetMailTemplateResponseModel responseModel = new GetMailTemplateResponseModel();
        BeanUtils.copyProperties(responseModel, mailTemplate);
        return responseBuilder.buildGetMailTemplateResponse(responseModel);
    }

    @Override
    public GetMailTemplateListResponse getMailTemplateList(GetMailTemplateListRequest request) {

        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.LIST_MAIL_TEMPLATE_USER_NOT_PERMITTED);
        }

        PagedData<GetMailTemplateListResponseModel> pageData = mailTemplateDao.getMailTemplateList(request.getPage(), request.getLimit());
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.GetMailTemplateListResponse(pageData);
    }

    @Override
    public GetSubmissionDateListResponse getSubmissionDateList(GetSubmissionDateListRequest request) {

     /*   Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.GET_REGISTRATION_DATES_USER_NOT_PERMITTED);
        }*/

        PagedData<GetSubmissionDateListResponseModel> plannedSubmissionDateList = userDao.getSubmissionDateList(request.getPage(), request.getLimit());
        if (plannedSubmissionDateList.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.GET_SUBMISSION_DATES_INPUT_DATA_NOT_FOUND);
        }
        return responseBuilder.buildGetSubmissionDateListResponse(plannedSubmissionDateList);
    }

    @Override
    @Transactional
    public AddRegistrationDateResponse addRegistrationDates(AddRegistrationDateRequest request) {
        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.ADD_REGISTRATION_DATES_USER_NOT_PERMITTED);
        }

        PlannedSubmissionDate plannedSubmissionDate = new PlannedSubmissionDate();
        plannedSubmissionDate.setSubmissionDate(request.getSubmissionDate());
        plannedSubmissionDate.setSubmissionEndDate(request.getSubmissionEndDate());
        userDao.savePlannedSubmissionDate(plannedSubmissionDate);
        return responseBuilder.buildAddRegistrationDateResponse(StatusCode.SUCCESS.getCode(), plannedSubmissionDate.getDateId());
    }

    @Override
    @Transactional
    public DeleteRegistrationDateResponse deleteRegistrationDate(Long dateId) {
        Integer userType = WebUtils.getUserType();
        if (!userType.equals(UserType.SUPER_ADMIN.getCode())) {
            throw new ServiceException(ErrorCode.DELETE_REGISTRATION_DATES_USER_NOT_PERMITTED);
        }

        PlannedSubmissionDate submissionDate = userDao.getByRegistrationDateId(dateId);
        if (Objects.isNull(submissionDate)) {
            LOGGER.error("Date not found:{}", dateId);
            throw new ServiceException(ErrorCode.DELETE_REGISTRATION_DATES_NOT_FOUND);
        }
        userDao.deletePlannedSubmissionDate(submissionDate);
        return responseBuilder.buildDeleteRegistrationDateResponse(StatusCode.SUCCESS.getCode());
    }

    @Override
    public GetStatusResponse getStatusDetails(Long statusId) {

        AppStatus appStatus = userDao.getByStatusId(statusId);

        GetStatusResponseModel response = new GetStatusResponseModel();
        if (Objects.isNull(appStatus)) {
            throw new ServiceException(ErrorCode.GET_STATUS_NOT_FOUND);
        }
        response.setEnglishText(appStatus.getEnglishText());
        response.setArabText(appStatus.getArabText());
        response.setStatusNumber(appStatus.getStatusNumber());

        return responseBuilder.buildGetStatusResponse(response);
    }

    @Override
    @Transactional
    public EditStatusResponse editStatusDetails(EditStatusRequest request) {

        AppStatus appStatus = userDao.getByStatusId(request.getStatusId());
        if (Objects.isNull(appStatus)) {
            throw new ServiceException(ErrorCode.EDIT_APP_STATUS_NOT_FOUND);
        }

        appStatus.setArabText(request.getArabText());
        appStatus.setEnglishText(request.getEnglishText());
        userDao.saveAppStatus(appStatus);

        return responseBuilder.buildEditStatusResponse(BooleanEnum.TRUE.getCode());
    }

    @Override
    public GetStatusListResponse getStatusList(GetStatusListRequest request) {
        PagedData<GetStatusListResponseModel> statusList = userDao.getStatusList(request.getPage(), request.getLimit());
        if (statusList.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.GET_STATUS_LIST_INPUT_DATA_NOT_FOUND);
        }
        return responseBuilder.buildGetStatusListResponse(statusList);
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        Long userId = WebUtils.getUserId();
        User user = userDao.getByUserId(userId);
        if (null == user) {
            throw new ServiceException(ErrorCode.CHANGE_PASSWORD_USER_NOT_FOUND);
        }
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new ServiceException(ErrorCode.CHANGE_PASSWORD_WRONG_PASSWORD);
        }

        user.setPassword(request.getNewPassword());
        userDao.saveUser(user);
        return responseBuilder.changePasswordResponseBuilder(StatusCode.SUCCESS.getCode());
    }


    public String getLicenceTypeName(Integer type) {
        String licenceName = null;
        switch (type) {
            case 1:
                licenceName = LicenseType.MINISTRY_OF_EDUCATION.getLicenceName();
                break;
            case 2:
                licenceName = LicenseType.MINISTRY_OF_LABOUR.getLicenceName();
                break;
            case 3:
                licenceName = LicenseType.HIGHER_EDUCATION_COUNCIL.getLicenceName();
                break;
            case 4:
                licenceName = LicenseType.DECREE.getLicenceName();
                break;
            default:
                licenceName = LicenseType.OTHERS.getLicenceName();

        }
        return licenceName;
    }


    public InstitutionProfileData setInstitutionProfileData(FormApplicationManager applicationFormData) {
        InstitutionProfileData institutionProfileData = new InstitutionProfileData();

        institutionProfileData.setInstitutionNameEnglish(applicationFormData.getInstitutionNameEnglish());
        institutionProfileData.setInstitutionNameArabic(applicationFormData.getInstitutionNameArabic());
        institutionProfileData.setInstitutionDetailRegulatedBy(applicationFormData.getInstitutionDetailRegulatedBy());
        institutionProfileData.setInstitutionDetailLicensedBy(applicationFormData.getInstitutionDetailLicensedBy());
        institutionProfileData.setInstitutionApprovalNumber(applicationFormData.getInstitutionApprovalNumber());
        institutionProfileData.setInstitutionIssueDate(applicationFormData.getInstitutionIssueDate());
        institutionProfileData.setInstitutionExpiryDate(applicationFormData.getInstitutionExpiryDate());
        institutionProfileData.setInstitutionDetailType(applicationFormData.getInstitutionDetailType());
        institutionProfileData.setInstitutionDetailDomain(applicationFormData.getInstitutionDetailDomain());
        institutionProfileData.setInstitutionDetailField(applicationFormData.getInstitutionDetailField());
        institutionProfileData.setInstitutionDetailDoc(applicationFormData.getInstitutionDetailDoc());
        institutionProfileData.setAddressDetailBuilding(applicationFormData.getAddressDetailBuilding());
        institutionProfileData.setAddressDetailRoad(applicationFormData.getAddressDetailRoad());
        institutionProfileData.setAddressDetailBlock(applicationFormData.getAddressDetailBlock());
        institutionProfileData.setContactDetailNumber(applicationFormData.getContactDetailNumber());
        institutionProfileData.setContactDetailEmailId(applicationFormData.getContactDetailEmailId());
        institutionProfileData.setContactDetailWebSite(applicationFormData.getContactDetailWebSite());
        institutionProfileData.setHeadOfInstitutionName(applicationFormData.getHeadOfInstitutionName());
        institutionProfileData.setHeadOfInstitutionPosition(applicationFormData.getHeadOfInstitutionPosition());
        institutionProfileData.setHeadOfInstitutionContactNumber(applicationFormData.getHeadOfInstitutionContactNumber());
        institutionProfileData.setHeadOfInstitutionEmailId(applicationFormData.getHeadOfInstitutionEmailId());
        institutionProfileData.setHeadOfQualityName(applicationFormData.getHeadOfQualityName());
        institutionProfileData.setHeadOfQualityPosition(applicationFormData.getHeadOfQualityPosition());
        institutionProfileData.setHeadOfQualityContactNumber(applicationFormData.getHeadOfQualityContactNumber());
        institutionProfileData.setHeadOfQualityEmailId(applicationFormData.getHeadOfQualityEmailId());

        //setting status and comment fields for institutionProfileData
        institutionProfileData.setInstitutionNameEnglishStatus(applicationFormData.getInstitutionNameEnglishStatus());
        institutionProfileData.setInstitutionNameEnglishStatusComment(applicationFormData.getInstitutionNameEnglishStatusComment());
        institutionProfileData.setInstitutionNameArabicStatus(applicationFormData.getInstitutionNameArabicStatus());
        institutionProfileData.setInstitutionNameArabicStatusComment(applicationFormData.getInstitutionNameArabicStatusComment());
        institutionProfileData.setInstitutionDetailRegulatedByStatus(applicationFormData.getInstitutionDetailRegulatedByStatus());
        institutionProfileData.setInstitutionDetailRegulatedByStatusComment(applicationFormData.getInstitutionDetailRegulatedByStatusComment());
        institutionProfileData.setInstitutionDetailLicensedByStatus(applicationFormData.getInstitutionDetailLicensedByStatus());
        institutionProfileData.setInstitutionDetailLicensedByStatusComment(applicationFormData.getInstitutionDetailLicensedByStatusComment());
        institutionProfileData.setInstitutionApprovalNumberStatus(applicationFormData.getInstitutionApprovalNumberStatus());
        institutionProfileData.setInstitutionApprovalNumberStatusComment(applicationFormData.getInstitutionApprovalNumberStatusComment());
        institutionProfileData.setInstitutionDetailTypeStatus(applicationFormData.getInstitutionDetailTypeStatus());
        institutionProfileData.setInstitutionDetailTypeStatusComment(applicationFormData.getInstitutionDetailTypeStatusComment());
        institutionProfileData.setInstitutionDetailDomainStatus(applicationFormData.getInstitutionDetailDomainStatus());
        institutionProfileData.setInstitutionDetailDomainStatusComment(applicationFormData.getInstitutionDetailDomainStatusComment());
        institutionProfileData.setInstitutionDetailFieldStatus(applicationFormData.getInstitutionDetailFieldStatus());
        institutionProfileData.setInstitutionDetailFieldStatusComment(applicationFormData.getInstitutionDetailFieldStatusComment());
        institutionProfileData.setAddressDetailStatus(applicationFormData.getAddressDetailStatus());
        institutionProfileData.setAddressDetailStatusComment(applicationFormData.getAddressDetailStatusComment());
        institutionProfileData.setContactDetailStatus(applicationFormData.getContactDetailStatus());
        institutionProfileData.setContactDetailStatusComment(applicationFormData.getContactDetailStatusComment());
        institutionProfileData.setHeadOfInstitutionStatus(applicationFormData.getHeadOfInstitutionStatus());
        institutionProfileData.setHeadOfInstitutionStatusComment(applicationFormData.getHeadOfInstitutionStatusComment());
        institutionProfileData.setHeadOfQualityStatus(applicationFormData.getHeadOfQualityStatus());
        institutionProfileData.setHeadOfQualityStatusComment(applicationFormData.getHeadOfQualityStatusComment());

        institutionProfileData.setRegulatoryOthersData(applicationFormData.getRegulatoryOthersData());
        institutionProfileData.setInstitutionTypeOtherData(applicationFormData.getInstitutionTypeOtherData());
        institutionProfileData.setLicencedByOthersData(applicationFormData.getLicencedByOthersData());
        institutionProfileData.setFieldOtherData(applicationFormData.getFieldOtherData());

        return institutionProfileData;
    }

    public QualityAssuranceSystemOverviewData setQualityAssuranceSystemOverviewData(FormApplicationManager applicationFormData) {
        QualityAssuranceSystemOverviewData qualityAssuranceSystemOverviewData = new QualityAssuranceSystemOverviewData();

        qualityAssuranceSystemOverviewData.setQualityAssuranceDescription(applicationFormData.getQualityAssuranceDescription());
        qualityAssuranceSystemOverviewData.setQualityDepartmentDoc(applicationFormData.getQualityDepartmentDoc());
        qualityAssuranceSystemOverviewData.setQualityReviewDate(applicationFormData.getQualityReviewDate());
        qualityAssuranceSystemOverviewData.setQualityAssuranceReport(applicationFormData.getQualityAssuranceReport());

        //setting status and comment fields for qualityAssuranceSystemOverviewData
        qualityAssuranceSystemOverviewData.setQualityAssuranceDescriptionStatus(applicationFormData.getQualityAssuranceDescriptionStatus());
        qualityAssuranceSystemOverviewData.setQualityAssuranceDescriptionStatusComment(applicationFormData.getQualityAssuranceDescriptionStatusComment());
        qualityAssuranceSystemOverviewData.setQualityAssuranceSystemOverviewStatus(applicationFormData.getQualityAssuranceSystemOverviewStatus());
        qualityAssuranceSystemOverviewData.setQualityAssuranceSystemOverviewStatusComment(applicationFormData.getQualityAssuranceSystemOverviewStatusComment());
        qualityAssuranceSystemOverviewData.setQualityReviewDateStatus(applicationFormData.getQualityReviewDateStatus());
        qualityAssuranceSystemOverviewData.setQualityReviewDateStatusComment(applicationFormData.getQualityReviewDateStatusComment());
        qualityAssuranceSystemOverviewData.setLastInstitutionQualityAssuranceReviewStatus(applicationFormData.getLastInstitutionQualityAssuranceReviewStatus());
        qualityAssuranceSystemOverviewData.setLastInstitutionQualityAssuranceReviewStatusComment(applicationFormData.getLastInstitutionQualityAssuranceReviewStatusComment());

        return qualityAssuranceSystemOverviewData;
    }

    public Standard1Data setStandard1Data(FormApplicationManager applicationFormData) {
        Standard1Data standard1Data = new Standard1Data();

        standard1Data.setAccessAndAdmissionDesc(applicationFormData.getAccessAndAdmissionDesc());
        standard1Data.setAccessAndAdmissionFile(applicationFormData.getAccessAndAdmissionFile());
        standard1Data.setCreditAccumulationDesc(applicationFormData.getCreditAccumulationDesc());
        standard1Data.setCreditAccumulationFile(applicationFormData.getCreditAccumulationFile());
        standard1Data.setInternalAndExternalCreditTransferDesc(applicationFormData.getInternalAndExternalCreditTransferDesc());
        standard1Data.setInternalAndExternalCreditTransferFile(applicationFormData.getInternalAndExternalCreditTransferFile());
        standard1Data.setCareerProgressionAndLearningPathwaysDesc(applicationFormData.getCareerProgressionAndLearningPathwaysDesc());
        standard1Data.setCareerProgressionAndLearningPathwaysFile(applicationFormData.getCareerProgressionAndLearningPathwaysFile());
        standard1Data.setRecognitionOfPrioLearningDesc(applicationFormData.getRecognitionOfPrioLearningDesc());
        standard1Data.setRecognitionOfPrioLearningFile(applicationFormData.getRecognitionOfPrioLearningFile());
        standard1Data.setAppealAgainstAccessAndTransferDesc(applicationFormData.getAppealAgainstAccessAndTransferDesc());
        standard1Data.setAppealAgainstAccessAndTransferFile(applicationFormData.getAppealAgainstAccessAndTransferFile());

        //setting status and comment fields for standard1Data
        standard1Data.setAccessAndAdmissionStatus(applicationFormData.getAccessAndAdmissionStatus());
        standard1Data.setAccessAndAdmissionStatusComment(applicationFormData.getAccessAndAdmissionStatusComment());
        standard1Data.setCreditAccumulationStatus(applicationFormData.getCreditAccumulationStatus());
        standard1Data.setCreditAccumulationStatusComment(applicationFormData.getCreditAccumulationStatusComment());
        standard1Data.setInternalAndExternalCreditTransferStatus(applicationFormData.getInternalAndExternalCreditTransferStatus());
        standard1Data.setInternalAndExternalCreditTransferStatusComment(applicationFormData.getInternalAndExternalCreditTransferStatusComment());
        standard1Data.setCareerProgressionAndLearningPathwaysStatus(applicationFormData.getCareerProgressionAndLearningPathwaysStatus());
        standard1Data.setCareerProgressionAndLearningPathwaysStatusComment(applicationFormData.getCareerProgressionAndLearningPathwaysStatusComment());
        standard1Data.setRecognitionOfPrioLearningStatus(applicationFormData.getRecognitionOfPrioLearningStatus());
        standard1Data.setRecognitionOfPrioLearningStatusComment(applicationFormData.getRecognitionOfPrioLearningStatusComment());
        standard1Data.setAppealAgainstAccessAndTransferStatus(applicationFormData.getAppealAgainstAccessAndTransferStatus());
        standard1Data.setAppealAgainstAccessAndTransferStatusComment(applicationFormData.getAppealAgainstAccessAndTransferStatusComment());


        return standard1Data;
    }

    public Standard2Data setStandard2Data(FormApplicationManager applicationFormData) {
        Standard2Data standard2Data = new Standard2Data();

        standard2Data.setJustificationOfNeedDesc(applicationFormData.getJustificationOfNeedDesc());
        standard2Data.setJustificationOfNeedFile(applicationFormData.getJustificationOfNeedFile());
        standard2Data.setQualificationDesignDesc(applicationFormData.getQualificationDesignDesc());
        standard2Data.setQualificationDesignFile(applicationFormData.getQualificationDesignFile());
        standard2Data.setQualificationComplianceDesc(applicationFormData.getQualificationComplianceDesc());
        standard2Data.setQualificationComplianceFile(applicationFormData.getQualificationComplianceFile());
        standard2Data.setLearningRecoursesAndLearnersSupportDesc(applicationFormData.getLearningRecoursesAndLearnersSupportDesc());
        standard2Data.setLearningRecoursesAndLearnersSupportFile(applicationFormData.getLearningRecoursesAndLearnersSupportFile());
        standard2Data.setQualificationInternalApprovalDesc(applicationFormData.getQualificationInternalApprovalDesc());
        standard2Data.setQualificationInternalApprovalFile(applicationFormData.getQualificationInternalApprovalFile());
        standard2Data.setQualificationInternalAndExternalEvaluationAndReviewDesc(applicationFormData.getQualificationInternalAndExternalEvaluationAndReviewDesc());
        standard2Data.setQualificationInternalAndExternalEvaluationAndReviewFile(applicationFormData.getQualificationInternalAndExternalEvaluationAndReviewFile());

        //setting status and comment fields for standard2Data
        standard2Data.setJustificationOfNeedStatus(applicationFormData.getJustificationOfNeedStatus());
        standard2Data.setJustificationOfNeedStatusComment(applicationFormData.getJustificationOfNeedStatusComment());
        standard2Data.setQualificationDesignStatus(applicationFormData.getQualificationDesignStatus());
        standard2Data.setQualificationDesignStatusComment(applicationFormData.getQualificationDesignStatusComment());
        standard2Data.setQualificationComplianceStatus(applicationFormData.getQualificationComplianceStatus());
        standard2Data.setQualificationComplianceStatusComment(applicationFormData.getQualificationComplianceStatusComment());
        standard2Data.setLearningRecoursesAndLearnersSupportStatus(applicationFormData.getLearningRecoursesAndLearnersSupportStatus());
        standard2Data.setLearningRecoursesAndLearnersSupportStatusComment(applicationFormData.getLearningRecoursesAndLearnersSupportStatusComment());
        standard2Data.setQualificationInternalApprovalStatus(applicationFormData.getQualificationInternalApprovalStatus());
        standard2Data.setQualificationInternalApprovalStatusComment(applicationFormData.getQualificationInternalApprovalStatusComment());
        standard2Data.setQualificationInternalAndExternalEvaluationAndReviewStatus(applicationFormData.getQualificationInternalAndExternalEvaluationAndReviewStatus());
        standard2Data.setQualificationInternalAndExternalEvaluationAndReviewStatusComment(applicationFormData.getQualificationInternalAndExternalEvaluationAndReviewStatusComment());

        return standard2Data;
    }

    public Standard3Data setStandard3Data(FormApplicationManager applicationFormData) {
        Standard3Data standard3Data = new Standard3Data();

        standard3Data.setAssessmentDesignDesc(applicationFormData.getAssessmentDesignDesc());
        standard3Data.setAssessmentDesignFile(applicationFormData.getAssessmentDesignFile());
        standard3Data.setInternalAndExternalVerificationAndModerationOfAssessmentDesc(applicationFormData.getInternalAndExternalVerificationAndModerationOfAssessmentDesc());
        standard3Data.setInternalAndExternalVerificationAndModerationOfAssessmentFile(applicationFormData.getInternalAndExternalVerificationAndModerationOfAssessmentFile());
        standard3Data.setMarkingCriteriaDesc(applicationFormData.getMarkingCriteriaDesc());
        standard3Data.setMarkingCriteriaFile(applicationFormData.getMarkingCriteriaFile());
        standard3Data.setMeasuringTheAchievementOfLearningOutcomesDesc(applicationFormData.getMeasuringTheAchievementOfLearningOutcomesDesc());
        standard3Data.setMeasuringTheAchievementOfLearningOutcomesFile(applicationFormData.getMeasuringTheAchievementOfLearningOutcomesFile());
        standard3Data.setFeedbackToLearnersDesc(applicationFormData.getFeedbackToLearnersDesc());
        standard3Data.setFeedbackToLearnersFile(applicationFormData.getFeedbackToLearnersFile());
        standard3Data.setApprovalOfAssessmentResultsDesc(applicationFormData.getApprovalOfAssessmentResultsDesc());
        standard3Data.setApprovalOfAssessmentResultsFile(applicationFormData.getApprovalOfAssessmentResultsFile());
        standard3Data.setAppealAgainstAssessmentResultsDesc(applicationFormData.getAppealAgainstAssessmentResultsDesc());
        standard3Data.setAppealAgainstAssessmentResultsFile(applicationFormData.getAppealAgainstAssessmentResultsFile());
        standard3Data.setIntegrityOfAssessmentDesc(applicationFormData.getIntegrityOfAssessmentDesc());
        standard3Data.setIntegrityOfAssessmentFile(applicationFormData.getIntegrityOfAssessmentFile());
        standard3Data.setSecurityOfAssessmentDocumentsAndRecordsDesc(applicationFormData.getSecurityOfAssessmentDocumentsAndRecordsDesc());
        standard3Data.setSecurityOfAssessmentDocumentsAndRecordsFile(applicationFormData.getSecurityOfAssessmentDocumentsAndRecordsFile());

        //setting status and comment fields for standard3Data
        standard3Data.setAssessmentDesignStatus(applicationFormData.getAssessmentDesignStatus());
        standard3Data.setAssessmentDesignStatusComment(applicationFormData.getAssessmentDesignStatusComment());
        standard3Data.setInternalAndExternalVerificationAndModerationOfAssessmentStatus(applicationFormData.getInternalAndExternalVerificationAndModerationOfAssessmentStatus());
        standard3Data.setInternalAndExternalVerificationAndModerationOfAssessmentStatusComment(applicationFormData.getInternalAndExternalVerificationAndModerationOfAssessmentStatusComment());
        standard3Data.setMarkingCriteriaStatus(applicationFormData.getMarkingCriteriaStatus());
        standard3Data.setMarkingCriteriaStatusComment(applicationFormData.getMarkingCriteriaStatusComment());
        standard3Data.setMeasuringTheAchievementOfLearningOutcomesStatus(applicationFormData.getMeasuringTheAchievementOfLearningOutcomesStatus());
        standard3Data.setMeasuringTheAchievementOfLearningOutcomesStatusComment(applicationFormData.getMeasuringTheAchievementOfLearningOutcomesStatusComment());
        standard3Data.setFeedbackToLearnersStatus(applicationFormData.getFeedbackToLearnersStatus());
        standard3Data.setFeedbackToLearnersStatusComment(applicationFormData.getFeedbackToLearnersStatusComment());
        standard3Data.setApprovalOfAssessmentResultsStatus(applicationFormData.getApprovalOfAssessmentResultsStatus());
        standard3Data.setApprovalOfAssessmentResultsStatusComment(applicationFormData.getApprovalOfAssessmentResultsStatusComment());
        standard3Data.setAppealAgainstAssessmentResultsStatus(applicationFormData.getAppealAgainstAssessmentResultsStatus());
        standard3Data.setAppealAgainstAssessmentResultsStatusComment(applicationFormData.getAppealAgainstAssessmentResultsStatusComment());
        standard3Data.setIntegrityOfAssessmentStatus(applicationFormData.getIntegrityOfAssessmentStatus());
        standard3Data.setIntegrityOfAssessmentStatusComment(applicationFormData.getIntegrityOfAssessmentStatusComment());
        standard3Data.setSecurityOfAssessmentDocumentsAndRecordsStatus(applicationFormData.getSecurityOfAssessmentDocumentsAndRecordsStatus());
        standard3Data.setSecurityOfAssessmentDocumentsAndRecordsStatusComment(applicationFormData.getSecurityOfAssessmentDocumentsAndRecordsStatusComment());
        return standard3Data;
    }

    public Standard4Data setStandard4Data(FormApplicationManager applicationFormData) {
        Standard4Data standard4Data = new Standard4Data();

        standard4Data.setCertificateIssuanceDesc(applicationFormData.getCertificateIssuanceDesc());
        standard4Data.setCertificateIssuanceFile(applicationFormData.getCertificateIssuanceFile());
        standard4Data.setCertificateAuthenticationDesc(applicationFormData.getCertificateAuthenticationDesc());
        standard4Data.setCertificateAuthenticationFile(applicationFormData.getCertificateAuthenticationFile());
        standard4Data.setRecordsOfCertificationDesc(applicationFormData.getRecordsOfCertificationDesc());
        standard4Data.setRecordsOfCertificationFile(applicationFormData.getRecordsOfCertificationFile());

        //setting status and comment fields for standard4Data
        standard4Data.setCertificateIssuanceStatus(applicationFormData.getCertificateIssuanceStatus());
        standard4Data.setCertificateIssuanceStatusComment(applicationFormData.getCertificateIssuanceStatusComment());
        standard4Data.setCertificateAuthenticationStatus(applicationFormData.getCertificateAuthenticationStatus());
        standard4Data.setCertificateAuthenticationStatusComment(applicationFormData.getCertificateAuthenticationStatusComment());
        standard4Data.setRecordsOfCertificationStatus(applicationFormData.getRecordsOfCertificationStatus());
        standard4Data.setRecordsOfCertificationStatusComment(applicationFormData.getRecordsOfCertificationStatusComment());

        return standard4Data;
    }

    public Standard5Data setStandard5Data(FormApplicationManager applicationFormData) {
        Standard5Data standard5Data = new Standard5Data();

        standard5Data.setInstitutionQualityAssuranceSystemDesc(applicationFormData.getInstitutionQualityAssuranceSystemDesc());
        standard5Data.setInstitutionQualityAssuranceSystemFile(applicationFormData.getInstitutionQualityAssuranceSystemFile());
        standard5Data.setContinuousImprovementOfInstitutionQualityAssuranceSystemDesc(applicationFormData.getContinuousImprovementOfInstitutionQualityAssuranceSystemDesc());
        standard5Data.setContinuousImprovementOfInstitutionQualityAssuranceSystemFile(applicationFormData.getContinuousImprovementOfInstitutionQualityAssuranceSystemFile());
        standard5Data.setRiskAndCrisisManagementDesc(applicationFormData.getRiskAndCrisisManagementDesc());
        standard5Data.setRiskAndCrisisManagementFile(applicationFormData.getRiskAndCrisisManagementFile());

        //setting status and comment fields for standard5Data
        standard5Data.setInstitutionQualityAssuranceSystemStatus(applicationFormData.getInstitutionQualityAssuranceSystemStatus());
        standard5Data.setInstitutionQualityAssuranceSystemStatusComment(applicationFormData.getInstitutionQualityAssuranceSystemStatusComment());
        standard5Data.setContinuousImprovementOfInstitutionQualityAssuranceSystemStatus(applicationFormData.getContinuousImprovementOfInstitutionQualityAssuranceSystemStatus());
        standard5Data.setContinuousImprovementOfInstitutionQualityAssuranceSystemStatusComment(applicationFormData.getContinuousImprovementOfInstitutionQualityAssuranceSystemStatusComment());
        standard5Data.setRiskAndCrisisManagementStatus(applicationFormData.getRiskAndCrisisManagementStatus());
        standard5Data.setRiskAndCrisisManagementStatusComment(applicationFormData.getRiskAndCrisisManagementStatusComment());

        return standard5Data;
    }

    public ApplicationContactData setApplicationContactData(FormApplicationManager applicationFormData, Long managerId, InstituteForm instituteForm) {
        ApplicationContactData applicationContactData = new ApplicationContactData();
        User user = userDao.getByUserId(managerId);
        if (null == user) {
            applicationContactData.setApplicationContactName(applicationFormData.getApplicationContactName());
            applicationContactData.setApplicationContactEmail(applicationFormData.getApplicationContactEmail());
            applicationContactData.setApplicationContactPosition(applicationFormData.getApplicationContactPosition());
            applicationContactData.setApplicationContactNumber(applicationFormData.getApplicationContactNumber());
        } else {
            applicationContactData.setApplicationContactName(user.getUsername());
            applicationContactData.setApplicationContactEmail(user.getEmailId());
            applicationContactData.setApplicationContactPosition(user.getPosition());
            applicationContactData.setApplicationContactNumber(user.getContactNumber());
        }
        InstitutionContactData institutionContactData = setInstitutionContactData(instituteForm);
        applicationContactData.setInstitutionContactData(institutionContactData);

        return applicationContactData;
    }

    public InstitutionContactData setInstitutionContactData(InstituteForm form) {
        InstitutionContactData institutionData = new InstitutionContactData();
        institutionData.setName(form.getContactPersonName());
        institutionData.setEmailId(form.getContactPersonEmail());
        institutionData.setContactNo(form.getContactPersonNumber());

        return institutionData;
    }

    public FormApplicationManager setInstitutionProfileStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for InstitutionProfileStatus");
        //TODO institution_background_status :: no place to update
        //TODO institution_Status :: on which ground

        formApplicationManager.setInstitutionNameEnglishStatus(request.getInstitutionProfileStatus().getInstitutionNameEnglish());
        formApplicationManager.setInstitutionNameEnglishStatusComment(request.getInstitutionProfileStatus().getInstitutionNameEnglishComment());

        formApplicationManager.setInstitutionNameArabicStatus(request.getInstitutionProfileStatus().getInstitutionNameArabic());
        formApplicationManager.setInstitutionNameArabicStatusComment(request.getInstitutionProfileStatus().getInstitutionNameArabicComment());

        formApplicationManager.setInstitutionDetailRegulatedByStatus(request.getInstitutionProfileStatus().getRegulatedBy());
        formApplicationManager.setInstitutionDetailRegulatedByStatusComment(request.getInstitutionProfileStatus().getRegulatedByComment());

        formApplicationManager.setInstitutionDetailLicensedByStatus(request.getInstitutionProfileStatus().getLicencedBy());
        formApplicationManager.setInstitutionDetailLicensedByStatusComment(request.getInstitutionProfileStatus().getLicencedByComment());

        formApplicationManager.setInstitutionApprovalNumberStatus(request.getInstitutionProfileStatus().getInstitutionApprovalNumber());
        formApplicationManager.setInstitutionApprovalNumberStatusComment(request.getInstitutionProfileStatus().getInstitutionApprovalNumberComment());

        formApplicationManager.setInstitutionDetailTypeStatus(request.getInstitutionProfileStatus().getInstitutionType());
        formApplicationManager.setInstitutionDetailTypeStatusComment(request.getInstitutionProfileStatus().getInstitutionTypeComment());

        formApplicationManager.setInstitutionDetailDomainStatus(request.getInstitutionProfileStatus().getInstitutionDomain());
        formApplicationManager.setInstitutionDetailDomainStatusComment(request.getInstitutionProfileStatus().getInstitutionDomainComment());

        formApplicationManager.setInstitutionDetailFieldStatus(request.getInstitutionProfileStatus().getField());
        formApplicationManager.setInstitutionDetailFieldStatusComment(request.getInstitutionProfileStatus().getFieldComment());

        formApplicationManager.setHeadOfInstitutionStatus(request.getInstitutionProfileStatus().getInstitutionDetails());
        formApplicationManager.setHeadOfInstitutionStatusComment(request.getInstitutionProfileStatus().getInstitutionDetailsComment());

        return formApplicationManager;
    }

    public FormApplicationManager setQualityAssuranceStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for QualityAssuranceStatus");
        //TODO both the data is not valid or not proper column in db
        //TODO Updated the above commented as required :: need to check
        formApplicationManager.setQualityAssuranceSystemOverviewStatus(request.getQualityAssuranceSystemStatus().getQualityAssuranceSystemOverview());
        formApplicationManager.setQualityAssuranceSystemOverviewStatusComment(request.getQualityAssuranceSystemStatus().getQualityAssuranceSystemOverviewComment());

        formApplicationManager.setLastInstitutionQualityAssuranceReviewStatus(request.getQualityAssuranceSystemStatus().getLastInstitutionQualityAssuranceReview());
        formApplicationManager.setLastInstitutionQualityAssuranceReviewStatusComment(request.getQualityAssuranceSystemStatus().getLastInstitutionQualityAssuranceReviewComment());
        return formApplicationManager;
    }

    public FormApplicationManager setAccessTransferProgressionStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.info("Status for AccessTransferProgressionStatus ");
        //TODO
        formApplicationManager.setAccessAndAdmissionStatus(request.getAccessTransferProgressionStatus().getAccessAndAdmission());
        formApplicationManager.setAccessAndAdmissionStatusComment(request.getAccessTransferProgressionStatus().getAccessAndAdmissionComment());

        formApplicationManager.setCreditAccumulationStatus(request.getAccessTransferProgressionStatus().getCreditAccumulation());
        formApplicationManager.setCreditAccumulationStatusComment(request.getAccessTransferProgressionStatus().getCreditAccumulationComment());

        formApplicationManager.setInternalAndExternalCreditTransferStatus(request.getAccessTransferProgressionStatus().getInternalAndExternalCreditTransfer());
        formApplicationManager.setInternalAndExternalCreditTransferStatusComment(request.getAccessTransferProgressionStatus().getInternalAndExternalCreditTransferComment());

        formApplicationManager.setCareerProgressionAndLearningPathwaysStatus(request.getAccessTransferProgressionStatus().getCareerProgressionAndLearningPathways());
        formApplicationManager.setCareerProgressionAndLearningPathwaysStatusComment(request.getAccessTransferProgressionStatus().getCareerProgressionAndLearningPathwaysComment());

        formApplicationManager.setRecognitionOfPrioLearningStatus(request.getAccessTransferProgressionStatus().getRecognitionOfPriorLearning());
        formApplicationManager.setRecognitionOfPrioLearningStatusComment(request.getAccessTransferProgressionStatus().getRecognitionOfPriorLearningComment());

        formApplicationManager.setAppealAgainstAccessAndTransferStatus(request.getAccessTransferProgressionStatus().getAppealAgainstAccessAndTransfer());
        formApplicationManager.setAppealAgainstAccessAndTransferStatusComment(request.getAccessTransferProgressionStatus().getAppealAgainstAccessAndTransferComment());
        return formApplicationManager;
    }

    public FormApplicationManager setQualificationDevelopmentApprovalReviewStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for QualificationDevelopmentApprovalReviewStatus");
        //TODO
        formApplicationManager.setJustificationOfNeedStatus(request.getQualificationDevelopmentApprovalReviewStatus().getJustificationOfNeed());
        formApplicationManager.setJustificationOfNeedStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getJustificationOfNeedComment());

        formApplicationManager.setQualificationComplianceStatus(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationCompliance());
        formApplicationManager.setQualificationComplianceStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationComplianceComment());

        formApplicationManager.setQualificationDesignStatus(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationDesign());
        formApplicationManager.setQualificationDesignStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationDesignComment());

        formApplicationManager.setQualificationInternalApprovalStatus(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationInternalApproval());
        formApplicationManager.setQualificationInternalApprovalStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationInternalApprovalComment());

        formApplicationManager.setQualificationInternalAndExternalEvaluationAndReviewStatus(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationInternalAndExternalEvaluationReview());
        formApplicationManager.setQualificationInternalAndExternalEvaluationAndReviewStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getQualificationInternalAndExternalEvaluationReviewComment());

        formApplicationManager.setLearningRecoursesAndLearnersSupportStatus(request.getQualificationDevelopmentApprovalReviewStatus().getLearningRecoursesAndLearnersSupport());
        formApplicationManager.setLearningRecoursesAndLearnersSupportStatusComment(request.getQualificationDevelopmentApprovalReviewStatus().getLearningRecoursesAndLearnersSupportComment());
        return formApplicationManager;
    }

    public FormApplicationManager setAssessmentDesignAndModerationStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for AssessmentDesignAndModerationStatus");
        //TODO
        formApplicationManager.setAssessmentDesignStatus(request.getAssessmentDesignAndModerationStatus().getAssessmentDesign());
        formApplicationManager.setAssessmentDesignStatusComment(request.getAssessmentDesignAndModerationStatus().getAssessmentDesignComment());

        formApplicationManager.setInternalAndExternalVerificationAndModerationOfAssessmentStatus(request.getAssessmentDesignAndModerationStatus().getInternalAndExternalVerificationAndModerationAssessment());
        formApplicationManager.setInternalAndExternalVerificationAndModerationOfAssessmentStatusComment(request.getAssessmentDesignAndModerationStatus().getInternalAndExternalVerificationAndModerationAssessmentComment());

        formApplicationManager.setMarkingCriteriaStatus(request.getAssessmentDesignAndModerationStatus().getMarkingCriteria());
        formApplicationManager.setMarkingCriteriaStatusComment(request.getAssessmentDesignAndModerationStatus().getMarkingCriteriaComment());

        formApplicationManager.setMeasuringTheAchievementOfLearningOutcomesStatus(request.getAssessmentDesignAndModerationStatus().getMeasuringTheAchievementOfLearningOutcomes());
        formApplicationManager.setMeasuringTheAchievementOfLearningOutcomesStatusComment(request.getAssessmentDesignAndModerationStatus().getMeasuringTheAchievementOfLearningOutcomesComment());

        formApplicationManager.setFeedbackToLearnersStatus(request.getAssessmentDesignAndModerationStatus().getFeedbackToLearners());
        formApplicationManager.setFeedbackToLearnersStatusComment(request.getAssessmentDesignAndModerationStatus().getFeedbackToLearnersComment());

        formApplicationManager.setApprovalOfAssessmentResultsStatus(request.getAssessmentDesignAndModerationStatus().getApprovalOfAssessmentResults());
        formApplicationManager.setApprovalOfAssessmentResultsStatusComment(request.getAssessmentDesignAndModerationStatus().getApprovalOfAssessmentResultsComment());

        formApplicationManager.setAppealAgainstAssessmentResultsStatus(request.getAssessmentDesignAndModerationStatus().getAppealAgainstAssessmentResults());
        formApplicationManager.setAppealAgainstAssessmentResultsStatusComment(request.getAssessmentDesignAndModerationStatus().getAppealAgainstAssessmentResultsComment());

        formApplicationManager.setIntegrityOfAssessmentStatus(request.getAssessmentDesignAndModerationStatus().getIntegrityOfAssessment());
        formApplicationManager.setIntegrityOfAssessmentStatusComment(request.getAssessmentDesignAndModerationStatus().getIntegrityOfAssessmentComment());

        formApplicationManager.setSecurityOfAssessmentDocumentsAndRecordsStatus(request.getAssessmentDesignAndModerationStatus().getSecurityOfAssessmentDocumentsAndRecords());
        formApplicationManager.setSecurityOfAssessmentDocumentsAndRecordsStatusComment(request.getAssessmentDesignAndModerationStatus().getSecurityOfAssessmentDocumentsAndRecordsComment());
        return formApplicationManager;
    }

    public FormApplicationManager setCertificationStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for CertificationStatus");
        //TODO
        formApplicationManager.setCertificateIssuanceStatus(request.getCertificationStatus().getCertificateIssuance());
        formApplicationManager.setCertificateIssuanceStatusComment(request.getCertificationStatus().getCertificateIssuanceComment());

        formApplicationManager.setCertificateAuthenticationStatus(request.getCertificationStatus().getCertificateAuthentication());
        formApplicationManager.setCertificateAuthenticationStatusComment(request.getCertificationStatus().getCertificateAuthenticationComment());

        formApplicationManager.setRecordsOfCertificationStatus(request.getCertificationStatus().getRecordsOfCertification());
        formApplicationManager.setRecordsOfCertificationStatusComment(request.getCertificationStatus().getRecordsOfCertificationComment());
        return formApplicationManager;
    }

    public FormApplicationManager setSustainabilityAndContinuousQualityImprovementStatus(FormApplicationManager formApplicationManager, ApplicationManagerEvaluationRequest request) {
        LOGGER.debug("Status for SustainabilityAndContinuousQualityImprovementStatus");
        //TODO
        formApplicationManager.setInstitutionQualityAssuranceSystemStatus(request.getSustainabilityAndContinuousQualityImprovementStatus().getInstitutionQualityAssuranceSystem());
        formApplicationManager.setInstitutionQualityAssuranceSystemStatusComment(request.getSustainabilityAndContinuousQualityImprovementStatus().getInstitutionQualityAssuranceSystemComment());

        formApplicationManager.setContinuousImprovementOfInstitutionQualityAssuranceSystemStatus(request.getSustainabilityAndContinuousQualityImprovementStatus().getContinuousImprovementOfInstitutionQualityAssuranceSystem());
        formApplicationManager.setContinuousImprovementOfInstitutionQualityAssuranceSystemStatusComment(request.getSustainabilityAndContinuousQualityImprovementStatus().getContinuousImprovementOfInstitutionQualityAssuranceSystemComment());

        formApplicationManager.setRiskAndCrisisManagementStatus(request.getSustainabilityAndContinuousQualityImprovementStatus().getRiskAndCrisisManagement());
        formApplicationManager.setRiskAndCrisisManagementStatusComment(request.getSustainabilityAndContinuousQualityImprovementStatus().getRiskAndCrisisManagementComment());
        return formApplicationManager;
    }
    @Override
    public GetUserListResponse getLimitedUserDataList(GetUserDataListRequest request) {

        PagedData<GetUserDataResponseModel> pageData = userDao.getLimitedUserList(request.getPage(), request.getLimit());
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getUserListResponseBuilder(pageData);
    }

    @Override
    public GetPreSignedUrlResponse generatePreSignedUrl(GetPreSignedUrlRequest request) {
        if (request.getBucketName() == null || request.getBucketName().isEmpty() || request.getBucketName().isBlank()) {
            request.setBucketName(bucketName);
        }

        String url = awsService.generateS3PreSignedUrl(request.getBucketName(), request.getFileName(),request.getMethod(), urlExpiryInMinutes);
        return responseBuilder.getPreSignedUrlResponseBuilder(url);
    }

    @Override
    @Transactional
    public SwitchRoleResponse switchRole(SwitchRoleRequest request){
        UserLoginInfo userLoginInfo = userLoginInfoDao.getByUserIdAndAppId(request.getUserId(),request.getAppId());
        if(userLoginInfo==null){
            throw new ServiceException(ErrorCode.APPLICATION_NOT_FOUND);
        }
        userLoginInfo.setUserType(request.getUserType());
        userLoginInfo.setSubType(request.getSubType());
        userLoginInfoDao.saveUserLoginInfo(userLoginInfo);
        SwitchRoleResponseModel data = new SwitchRoleResponseModel(request.getUserType(),request.getSubType());
        return responseBuilder.buildSwitchRoleResponse(data);
    }

    @Override
    public GetRoleListResponse getRoles(){
        List<GetRoleResponseModel> roles = roleRepository.getActiveRoles();
        roles.forEach(role->{
            if (role.getUserType()==UserType.ILEP_MEMBER.getCode()&&role.getSubType()==UserSubType.ADMIN.getCode()){
                role.setName("PANEL_MEMBER");
            }
        });
        return responseBuilder.buildGetRoleResponse(roles);
    }

    @Override
    public GetUserListResponse getILEPUserDataList(GetDashboardUserListRequest request) {

        PagedData<GetUserDataResponseModel> pageData = userDao.getILEPUserList(request.getPage(), request.getLimit(),request.getActiveStatus());
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getUserListResponseBuilder(pageData);
    }

    @Override
    public GetUserListResponse getNonAdminNonInstituteUsers(GetDashboardUserListRequest request) {
        if(request.getSearchValue() != null){
            String searchValue = URLDecoder.decode(request.getSearchValue(), StandardCharsets.UTF_8);
            request.setSearchValue(searchValue);
        }

        PagedData<GetUserDataResponseModel> pageData = userDao.getNonAdminNonInstituteUsers(request);
        if (pageData.getList().isEmpty()) {
            throw new ServiceException(ErrorCode.DATA_NOT_FOUND);
        }
        return responseBuilder.getUserListResponseBuilder(pageData);
    }
}
