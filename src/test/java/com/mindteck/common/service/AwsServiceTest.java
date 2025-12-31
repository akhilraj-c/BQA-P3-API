package com.mindteck.common.service;

import com.mindteck.MindteckApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MindteckApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@Slf4j
class AwsServiceTest {

//
//    @Autowired
//    private AwsService awsService;
//
//    @Test
//    void sendSimpleEmail() {
//        LOGGER.debug("Mail Sending ....");
//        awsService.sendSimpleEmail(
//                "javatest60@gmail.com",
//                "BQA-support@ternstech.in",
//                "This is test mail" ,
//                "Testing the mail service");
//    }
//
//    @Test
//    void sendEmailAttachment() {
//        LOGGER.debug("Mail Sending ....");
//        awsService.sendEmailAttachment(
//                "javatest60@gmail.com",
//                "BQA-support@ternstech.in",
//                "classpath:static/img/logo.png",
//                "logo.png",
//                "This is test mail" ,
//                "Testing the mail service");
//    }
//
//    @Test
//    void s3Upload() throws IOException {
//        File file = ResourceUtils.getFile("classpath:static/img/logo.png");
//        MultipartFile multipartFile = new MockMultipartFile(
//                "test.yml",
//                 file.getName() ,
//                URLConnection.guessContentTypeFromName(file.getName()),
//                IOUtils.toByteArray(new FileInputStream(file)));
//       String url =  awsService.s3Upload(
//                "demo-teck",
//                "users/test/",
//                multipartFile
//        );
//       System.out.println(url);
//    }
}