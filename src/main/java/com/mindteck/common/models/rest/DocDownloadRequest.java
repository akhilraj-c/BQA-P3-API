package com.mindteck.common.models.rest;

import com.mindteck.common.models.AbstractModel;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocDownloadRequest extends AbstractModel {

    @NotNull
    Integer docType;
}
