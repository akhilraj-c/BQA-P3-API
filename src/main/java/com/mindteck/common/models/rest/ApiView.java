package com.mindteck.common.models.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ApiView {

    private Integer slNo;
    private String httpMethod;
    private String url;
    private String name;

    public ApiView(String httpMethod , String url , String name)
    {
        this.httpMethod = httpMethod;
        this.url = url;
        this.name = name;
    }

}
