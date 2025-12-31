package com.mindteck.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AwsService {

    void sendSimpleEmail(String mailTo, String mailedBy, String message, String subject);

    void sendEmailAttachment(String mailTo, String mailedBy, String path, String fileName, String message, String subject);

    String s3Upload(String bucketName, String filePath, MultipartFile file);

    void sendMail(String toMail, Map<String, Object> templateModel, String mailHtmlPath, List<String> ccAdresses, String subect);

    String generateS3PreSignedUrl(String bucketName, String fileName, String method, Long expiry);
}
