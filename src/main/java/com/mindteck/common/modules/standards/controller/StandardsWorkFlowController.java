package com.mindteck.common.modules.standards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.*;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.standards.model.SaveStandardWorkFlowResponse;
import com.mindteck.common.modules.standards.service.StandardsWorkFlowService;
import com.mindteck.common.modules.user.dao.MailTemplateDao;
import com.mindteck.common.modules.user.dao.UserDao;
import com.mindteck.common.modules.user.model.rest.GetApplicationDetailsRequest;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.utils.CommonUtils;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.evaluation.dao.EvaluationDao;
import com.mindteck.common.modules.form.dao.FormDao;
import com.mindteck.common.modules.standards.model.GetStandardWorkFlowResponse;
import com.mindteck.common.modules.standards.model.SaveStandardsRequest;
import com.mindteck.models_cas.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RestController
@CrossOrigin
@Api(tags = {SwaggerHeads.STANDARDS_API})
@Slf4j
public class StandardsWorkFlowController {

    final int TYPE_SAVE_STDS = 0;
    final int TYPE_EVALUATE_STDS = 1;

    @Autowired
    StandardsWorkFlowService standardsWorkFlowService;

    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Autowired
    private EvaluationDao evaluationDao;

    @Autowired
    private FormDao formDao;

    @Autowired
    private AwsService awsService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonUtils commonUtils;

    @GetMapping(ApiUrls.GET_STANDARDS_WORK_FLOW)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<GetStandardWorkFlowResponse> getStandardsWorkFLow(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {

        return getStandardWorkFlowResponseResponseEntity(request, StandardsApiType.DEFAULT);
    }

    @GetMapping(ApiUrls.GET_STANDARDS_WORK_FLOW_CF)
    @ApiOperation(value = "Get application data details", tags = {SwaggerHeads.STANDARDS_WORK_FLOW})
    public ResponseEntity<GetStandardWorkFlowResponse> getStandardsWorkFLowCf(@ModelAttribute @Valid GetApplicationDetailsRequest request, BindingResult
            bindingResult) throws JsonProcessingException {

        return getStandardWorkFlowResponseResponseEntity(request, StandardsApiType.CONDITIONAL_FULFILMENT);
    }


    @PostMapping(ApiUrls.SAVE_STANDARDS_WORK_FLOW)
    @ApiOperation(value = "Save Stds Work Flow")
    public ResponseEntity<SaveStandardWorkFlowResponse> saveStandards(
            @RequestBody @Valid SaveStandardsRequest request,
            BindingResult bindingResult) {
        return performStandsOperation(request, bindingResult, TYPE_SAVE_STDS, StandardsApiType.DEFAULT);
    }

    @PostMapping(ApiUrls.SAVE_STANDARDS_WORK_FLOW_CF)
    @ApiOperation(value = "Save Stds Work Flow")
    public ResponseEntity<SaveStandardWorkFlowResponse> saveStandardsCf(
            @RequestBody @Valid SaveStandardsRequest request,
            BindingResult bindingResult) {
        return performStandsOperation(request, bindingResult, TYPE_SAVE_STDS, StandardsApiType.CONDITIONAL_FULFILMENT);
    }

    @PostMapping(ApiUrls.EVALUATE_STANDARDS_WORK_FLOW)
    @ApiOperation(value = "Evaluate Stds Work Flow")
    public ResponseEntity<SaveStandardWorkFlowResponse> evaluateStandards(
            @RequestBody @Valid SaveStandardsRequest request,
            BindingResult bindingResult) {
        return performStandsOperation(request, bindingResult, TYPE_EVALUATE_STDS, StandardsApiType.DEFAULT);
    }

    private ResponseEntity<GetStandardWorkFlowResponse> getStandardWorkFlowResponseResponseEntity(GetApplicationDetailsRequest request, StandardsApiType apiType) {
        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            GetStandardWorkFlowResponse response = standardsWorkFlowService.getStandards(request.getFormUniqueId(),apiType);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private ResponseEntity<SaveStandardWorkFlowResponse> performStandsOperation(
            @RequestBody @Valid SaveStandardsRequest request,
            BindingResult bindingResult,
            int type, StandardsApiType apiType) {
        InstituteForm instituteForm = formDao.getInstitutionFormById(request.getFormUniqueId());

        Status status = WebUtils.getStatus();
        status.setApiId(28);
        try {

            SaveStandardWorkFlowResponse response;
            if (type == TYPE_EVALUATE_STDS) {
                response = standardsWorkFlowService.evaluate(request);
            } else {
                response = standardsWorkFlowService.saveStandards(request, apiType);
                MailTemplate mailTemplate = mailTemplateDao.getByTemplateCode("REPORT_PANEL_CONFIRMATION_CHIEF");
                if(Objects.nonNull(mailTemplate)){
                    List<IlepPanel> ilepList = evaluationDao.getILEPByFormUniqueId(instituteForm.getFormUniqueId());
                    String mailBody = mailTemplate.getTemplateBody();
                    List<String> ccAdresses = commonUtils.getMailCcMemebrs(mailTemplate, instituteForm);
                    User amUserDetails = userDao.getByUserId(instituteForm.getAssignedAppManager());
                    LocalDate twoDaysLater = LocalDate.now().plusDays(2);
                    for (IlepPanel ilepPanel : ilepList) {
                        User userDetail = userDao.getByUserId(ilepPanel.getIlepMemberId());
                        if(userDetail.getActive()==1){
                            if (mailBody !=null && !mailBody.isBlank()) {
                                mailBody = mailBody.replace("{userName}", userDetail.getUsername());
                                mailBody = mailBody.replace("{managerName}", amUserDetails.getUsername());
                                mailBody = mailBody.replace("{managerMail}", amUserDetails.getEmailId());
                                mailBody = mailBody.replace("{managerNumber}", amUserDetails.getContactNumber());
                                mailBody = mailBody.replace("{frstDate}", twoDaysLater.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                            }
                            Map<String, Object> templateModel = new HashMap<>();

                            templateModel.put("mailBody", mailBody);
                            String mailHtmlPath = "mail-template.html";
    //                    List<String> ccAdresses = new ArrayList<>();
                            awsService.sendMail(userDetail.getEmailId(), templateModel, mailHtmlPath, ccAdresses, mailTemplate.getTemplateSubject());
                            mailBody = mailBody.replace( userDetail.getUsername(),"{userName}");
                        }
                    }
                }

            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
/*
    @PostMapping(ApiUrls.ILEP_EVALUATE)
    @ApiOperation(value = "ILEP member evaluate The Application")
    public ResponseEntity<IlepEvaluateResponse> ilepEvaluateApplication(@RequestBody @Valid IlepEvaluateRequest request, BindingResult bindingResult) {
        Status status = WebUtils.getStatus();
        status.setApiId(6);
        WebUtils.setStatus(status);
        try {
            if (bindingResult.hasErrors()) {
                throw new ControllerException(
                        bindingResult,
                        ErrorCode.ILEP_EVALUATE_INPUT_VALIDATION_FAILED
                );
            }

            IlepEvaluateResponse response = ilepEvaluatioService.ilepEvaluate(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }*/

}
