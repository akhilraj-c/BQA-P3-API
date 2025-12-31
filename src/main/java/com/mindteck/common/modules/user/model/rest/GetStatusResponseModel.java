package com.mindteck.common.modules.user.model.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetStatusResponseModel {

    @ApiModelProperty(name = "statusNumber", example = "12", value = "status number", dataType = "Long"
            , position = 1 )
    private Long statusNumber;

    @ApiModelProperty(name = "englishText", example = "Hello", value = "status desc in eng", dataType = "String",
            position = 3)
    private String englishText;

    @ApiModelProperty(name = "arabText", example = "صباح الخير.", value = "status desc in arab", dataType = "String",
            position = 4)
    private String arabText;
}
