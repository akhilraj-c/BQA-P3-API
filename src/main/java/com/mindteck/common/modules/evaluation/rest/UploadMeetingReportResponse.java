package com.mindteck.common.modules.evaluation.rest;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UploadMeetingReportResponse extends AbstractView {

        UploadMeetingReportResponseModel data;
}
