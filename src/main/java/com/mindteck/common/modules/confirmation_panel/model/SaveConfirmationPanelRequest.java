package com.mindteck.common.modules.confirmation_panel.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaveConfirmationPanelRequest {


    @ApiModelProperty(example = "1", required = true)
    @NotNull(message = "appId is required")
    private Long formUniqueId;

    private List<ConfirmationPanel> confirmationPanelList;

}
