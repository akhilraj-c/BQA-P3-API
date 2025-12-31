package com.mindteck.common.modules.confirmation_panel.model;

import com.mindteck.common.models.rest.AbstractView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ConfirmationPanelResponse extends AbstractView {

    private List<ConfirmationPanel> confirmationPanelList;
    private Long formUniqueId;
}