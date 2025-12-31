package com.mindteck.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
@Slf4j
public class FileUtils {


    public static  File convertMultiPartFileToFile(MultipartFile file) {
        final File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (Exception e) {
            LOGGER.error("Failed to upload file to server");
            //throw new ServiceException(ErrorCodeEnum.USER_REGISTRATION_FILE_UPLOAD_FAILED);
        }
        return convertedFile;
    }
}
