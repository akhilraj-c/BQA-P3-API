package com.mindteck.common.ErrorResponseBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mindteck.common.models.rest.AbstractView;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends AbstractView implements Serializable {

    private Data data;

}
