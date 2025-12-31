package com.mindteck.common.modules.revalidation.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringFormGetResponse extends AbstractView {
    private MonitoringFormGetResponseModel data;
}
