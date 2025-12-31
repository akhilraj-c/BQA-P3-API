package com.mindteck.common.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({ "/general" })
public class GeneralController {

    @GetMapping(value = "test-api")
    public String testApi() {
        return "Hello working";
    }
}
