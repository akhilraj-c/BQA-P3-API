package com.mindteck.common.modules.feedback.models;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class NACSubmitFeedbackResponse extends AbstractView {
    private NACSubmitFeedbackResponseModel data;
}
