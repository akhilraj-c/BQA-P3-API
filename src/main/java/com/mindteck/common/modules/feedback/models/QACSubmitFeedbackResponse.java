package com.mindteck.common.modules.feedback.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class QACSubmitFeedbackResponse extends AbstractView {

    QACSubmitFeedbackResponseModel data;
}
