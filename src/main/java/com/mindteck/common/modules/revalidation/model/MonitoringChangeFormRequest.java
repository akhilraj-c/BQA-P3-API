package com.mindteck.common.modules.revalidation.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MonitoringChangeFormRequest {
    private int changeType;
    private String description;
    private String file;
}
