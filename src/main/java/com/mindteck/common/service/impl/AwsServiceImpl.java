package com.mindteck.common.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.exceptionHandler.ServiceException;
import com.mindteck.common.service.AwsService;
import com.mindteck.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AwsServiceImpl implements AwsService {

    @Autowired
    @Qualifier("mailSender")
    private MailSender mailSender;

    @Autowired
    @Qualifier("javaMailSender")
    private JavaMailSender javaMailSender;


    @Autowired
    @Qualifier("amazonS3Client")
    private AmazonS3 amazonS3Client;

    @Value("${mindteck.aws.email.enabled:false}")
    private Boolean isEmailEnabled;

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public AwsServiceImpl(SpringTemplateEngine thymeleafTemplateEngine) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Value("${fromEmail}")
    private String fromEmail;


    @Override
    public void sendSimpleEmail(String mailTo, String mailedBy, String message, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailedBy);
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setSentDate(new Date());
        mailSender.send(simpleMailMessage);

    }

    @Override
    public void sendEmailAttachment(String mailTo, String mailedBy, String path, String fileName, String message, String subject) {
        this.javaMailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.addTo(mailTo);
                helper.setFrom(mailedBy);
                InputStreamSource data = new ByteArrayResource(path.getBytes());
                helper.addAttachment(fileName, data);
                helper.setSentDate(new Date());
                helper.setSubject(subject);
                helper.setText(message);
            }
        });
    }

    @Override
    public String s3Upload(String bucketName, String filePath, MultipartFile multipartFile) {
        final String fileName = filePath + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        try {
            File file = FileUtils.convertMultiPartFileToFile(multipartFile);
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
            final AccessControlList accessControlList = new AccessControlList();
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            putObjectRequest.setAccessControlList(accessControlList);
            amazonS3Client.putObject(putObjectRequest);
            final URL s3Url = amazonS3Client.getUrl(bucketName, fileName);
            file.delete();
            return s3Url.toString();

        } catch (Exception ex) {
            LOGGER.debug("s3 Upload failed for bucketName :{} , fileName : {}", bucketName, fileName);
            throw ex;
        }

    }

    @Override
    public void sendMail(String toMail, Map<String, Object> templateModel, String mailHtmlPath, List<String> ccAdresses, String subect) {
        try {
            Context thymeleafContext = new Context();
            String mailBody = null;
            if (subect == null) {
                subect = "BQA status";
            }
            thymeleafContext.setVariables(templateModel);
            mailBody = thymeleafTemplateEngine.process(mailHtmlPath, thymeleafContext);
            if (ccAdresses == null) {
                sendMessage1(toMail, subect, mailBody);
            } else if (ccAdresses.isEmpty()) {
                sendMessage1(toMail, subect, mailBody);
            } else {
                sendMessage(toMail, subect, mailBody, ccAdresses);
            }
        } catch (Exception ignore) {

        }
    }

    public int sendMessage(String toEmail, String subject, String body, List<String> ccAdresses) {
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.AP_SOUTH_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(toEmail).withCcAddresses(ccAdresses))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(body))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(body)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(fromEmail);
            // Comment or remove the next line if you are not using a
            // configuration set
//                .withConfigurationSetName("configset");
            if (isEmailEnabled) {
                amazonSimpleEmailService.sendEmail(request);
                System.out.println("Email  sent!");
            } else {
                System.out.println("Email not  sent!");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int sendMessage1(String toEmail, String subject, String body) {
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.AP_SOUTH_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(toEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(body))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(body)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(fromEmail);
            // Comment or remove the next line if you are not using a
            // configuration set
//                .withConfigurationSetName("configset");
            if (isEmailEnabled) {
                amazonSimpleEmailService.sendEmail(request);
                System.out.println("Email  sent!");
            } else {
                System.out.println("Email not  sent!");
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String generateS3PreSignedUrl(String bucketName, String fileName, String method, Long expiry) {
        if(!List.of("PUT", "GET","DELETE").contains(method)) {
            throw new ServiceException(ErrorCode.GENERATE_PRE_SIGNED_URL_INVALID_METHOD);
        }
        Date urlExpirationDate = new Date(System.currentTimeMillis() + (expiry * 60 * 1000));
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName,
                HttpMethod.valueOf(method.toUpperCase()));
        generatePresignedUrlRequest.setExpiration(urlExpirationDate);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }
}
