package com.mindteck.common.modules.listing.service;

import com.mindteck.common.modules.user.model.rest.GetInstituteListingDetailsResponse;
import org.springframework.stereotype.Service;

@Service
public interface ListingService {

    GetInstituteListingDetailsResponse getInstituteListingDetails(String email);
}
