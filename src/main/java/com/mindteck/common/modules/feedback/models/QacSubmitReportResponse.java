package com.mindteck.common.modules.feedback.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class QacSubmitReportResponse extends AbstractView {
    private QacSubmitReportResponseModel data;
}
