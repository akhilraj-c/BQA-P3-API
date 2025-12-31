package com.mindteck.common.controllers;

import com.mindteck.MindteckApplication;
import com.mindteck.common.constants.ApiUrls;
import com.mindteck.common.models.rest.AppRegRequest;
import com.mindteck.common.models.rest.AppRegResponse;
import com.mindteck.common.service.AppService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = MindteckApplication.class)
class AppControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AppService appService;

//    @Test
//    void appRegistrationValidData() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getJson(appRegRequest))).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.appId").value("5"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationInvalidValidData() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "\"deviceInfo\":\"deviceInfo\",\n" +
//                                "\"osInfo\":\"osInfo\",\n" +
//                                "\"appVersion\":8\n" +
//                                "}")).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationInvalidValidJson() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "\"deviceId\" :\"aaa\",\n" +
//                                "\"deviceToken\":\"deviceToken\",\n" +
//                                "\"deviceInfo\":\"deviceInfo\",\n" +
//                                "\"osInfo\":\"osInfo\",\n" +
//                                "\"appVersion\":8\n" +
//                                "")).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Unable to process request at this time"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationEmptyInput() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("")).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Unable to process request at this time"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationInvalidContentType() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_XML)
//                        .content("")).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Unable to process request at this time"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationWithDeviceIdNull() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest(null,
//                "deviceInfo",
//                "deviceToken",
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getJson(appRegRequest))).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationWithDeviceTokenNull() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                null,
//                "osInfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getJson(appRegRequest))).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//    @Test
//    void appRegistrationWithOsInfoNull() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                "deviceInfo",
//                "sdfjdlaf;",
//                null, "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getJson(appRegRequest))).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//    @Test
//    void appRegistrationWithDeviceInfoNull() throws Exception {
//
//        AppRegRequest appRegRequest = getAppRegRequest("deviceId",
//                null,
//                "sdfjdlaf;",
//                "osinfo", "1.2");
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(appRegRequest)).thenReturn(appRegResponse);
//
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(getJson(appRegRequest))).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//    @Test
//    void appRegistrationWithoutDeviceId() throws Exception {
//        String json = "{\n" +
//                "  \"appVersion\": 8,\n" +
//                "  \"deviceInfo\": \"abcdkfladxxxxafldasd\",\n" +
//                "  \"deviceToken\": \"thga3i943459vgfgakjflgkj__935_fhdjsahewjhrtq fhdfkej_fcm\",\n" +
//                "  \"osInfo\": \"Window 8\"\n" +
//                "}";
//        appRegistrationWithOutData(json);
//    }
//
//    @Test
//    void appRegistrationWithoutDeviceToken() throws Exception {
//        String json = "{\n" +
//                "  \"appVersion\": 8,\n" +
//                "  \"deviceId\": \"NOKIAXXXXX520XP\",\n" +
//                "  \"deviceInfo\": \"abcdkfladxxxxafldasd\",\n" +
//                "  \"osInfo\": \"Window 8\"\n" +
//                "}";
//        appRegistrationWithOutData(json);
//    }
//
//    @Test
//    void appRegistrationWithoutDeviceInfo() throws Exception {
//        String json = "{\n" +
//                "  \"appVersion\": 8,\n" +
//                "  \"deviceId\": \"NOKIAXXXXX520XP\",\n" +
//                "  \"deviceToken\": \"thga3i943459vgfgakjflgkj__935_fhdjsahewjhrtq fhdfkej_fcm\",\n" +
//                "  \"osInfo\": \"Window 8\"\n" +
//                "}";
//        appRegistrationWithOutData(json);
//    }
//    @Test
//    void appRegistrationWithoutOsInfo() throws Exception {
//        String json = "{\n" +
//                "  \"appVersion\": 8,\n" +
//                "  \"deviceId\": \"NOKIAXXXXX520XP\",\n" +
//                "  \"deviceInfo\": \"abcdkfladxxxxafldasd\",\n" +
//                "  \"deviceToken\": \"thga3i943459vgfgakjflgkj__935_fhdjsahewjhrtq fhdfkej_fcm\"\n" +
//                "}";
//        appRegistrationWithOutData(json);
//    }
//
//
//
//
//
//    void appRegistrationWithOutData(String json) throws Exception {
//
//        AppRegResponse appRegResponse = getAppRegResponse();
//
//        Mockito.when(appService.saveAppData(Mockito.any())).thenReturn(appRegResponse);
//        mockMvc.perform(MockMvcRequestBuilders.post(ApiUrls.APP_REGISTRATION)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)).andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message")
//                        .value("Input validation failed"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
}