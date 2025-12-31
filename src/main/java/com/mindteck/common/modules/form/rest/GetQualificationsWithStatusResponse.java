package com.mindteck.common.modules.form.rest;

import com.mindteck.common.models.StaticQualificationDetails;
import com.mindteck.common.models.rest.AbstractView;
import com.mindteck.common.modules.form.rest.formdata.GetQualificationsWithStatusResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GetQualificationsWithStatusResponse extends AbstractView {
    List<GetQualificationsWithStatusResponseModel> qp;
    List<StaticQualificationDetails> qpMasters;
}
