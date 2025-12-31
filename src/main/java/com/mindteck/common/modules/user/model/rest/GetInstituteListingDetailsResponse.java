package com.mindteck.common.modules.user.model.rest;


import com.mindteck.models_cas.ListedInstitute;
import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GetInstituteListingDetailsResponse extends AbstractView {
    private ListedInstitute data;
    private RegistrationRequest draftDetails;
}
