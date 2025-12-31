package com.mindteck.common.modules.user.model.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class GetPreSignedUrlResponse extends AbstractView {
    private GetPreSignedUrlModel data;
}
