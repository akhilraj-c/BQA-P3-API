package com.mindteck.common.modules.listing.controller;

import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ControllerException;
import com.mindteck.common.models.SwaggerHeads;
import com.mindteck.common.models.rest.Status;
import com.mindteck.common.modules.listing.service.ListingService;
import com.mindteck.common.utils.WebUtils;
import com.mindteck.common.modules.user.model.rest.GetInstituteListingDetailsRequest;
import com.mindteck.common.modules.user.model.rest.GetInstituteListingDetailsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.Arrays;

@RestController
@CrossOrigin
@Api(tags = {SwaggerHeads.PROGRAM_STRUCTURE_FLOW})
@Slf4j
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping(ApiUrls.GET_INSTITUTE_LISTING_DETAILS)
    @ApiOperation(value = "Get institute listing  details", tags = {SwaggerHeads.USER_API})
    public ResponseEntity<GetInstituteListingDetailsResponse> getInstituteListingDetails(@ModelAttribute @Valid GetInstituteListingDetailsRequest request, BindingResult bindingResult) {
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

            GetInstituteListingDetailsResponse response = listingService.getInstituteListingDetails(URLDecoder.decode(request.getEmail()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

}
