package com.mindteck.common.modules.archival.controller;

import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.archival.model.ArchivalFormApplyRequest;
import com.mindteck.common.modules.archival.model.ArchivalFormSaveResponse;
import com.mindteck.common.modules.archival.model.ArchivalFormUpdateStatusRequest;
import com.mindteck.common.modules.archival.model.ArchivalGetResponse;
import com.mindteck.common.modules.archival.service.ArchivalFormService;
import com.mindteck.common.utils.WebUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@RestController
public class ArchivalFormController {

    @Autowired
    ArchivalFormService archivalFormService;

    @PostMapping(ApiUrls.APPLY_FOR_ARCHIVAL)
    @ApiOperation(value = "Apply for archival")
    public ResponseEntity<ArchivalFormSaveResponse> applyForArchival(@RequestBody ArchivalFormApplyRequest request){
        Status status = WebUtils.getStatus();
        status.setApiId(136);
        WebUtils.setStatus(status);
        try{
            ArchivalFormSaveResponse response = archivalFormService.applyForArchival(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @PostMapping(ApiUrls.UPDATE_ARCHIVAL_STATUS)
    @ApiOperation(value = "update archival status")
    public ResponseEntity<ArchivalFormSaveResponse> updateArchivalStatus(@RequestBody ArchivalFormUpdateStatusRequest request){
        Status status = WebUtils.getStatus();
        status.setApiId(137);
        WebUtils.setStatus(status);
        try{
            ArchivalFormSaveResponse response = archivalFormService.updateArchivalStatus(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }

    @GetMapping(ApiUrls.GET_ARCHIVAL_FORM)
    @ApiOperation(value = "Get Archival form")
    public ResponseEntity<ArchivalGetResponse> getArchivalForm(@PathVariable Long formUniqueId){
        Status status = WebUtils.getStatus();
        status.setApiId(138);
        WebUtils.setStatus(status);
        try{
            ArchivalGetResponse response = archivalFormService.getArchival(formUniqueId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }

    }
}
