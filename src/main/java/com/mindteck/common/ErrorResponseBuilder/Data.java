package com.mindteck.common.ErrorResponseBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data<T>  implements Serializable {

    private  Integer code;
    private String message;
    private T errorDetails;

}
