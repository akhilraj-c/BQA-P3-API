package com.mindteck.common.service.impl;

import com.mindteck.common.constants.Enum.DocType;
import com.mindteck.common.constants.Enum.ErrorCode;
import com.mindteck.common.models.SecureApiTag;
import com.mindteck.common.service.SwaggerUtilityService;
import com.mindteck.common.utils.SwaggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import springfox.documentation.service.Tag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Service
@Slf4j
public class SwaggerUtilityServiceImpl implements SwaggerUtilityService {

    @Value("${mindteck.swagger.documentation.files.root:./generated/swagger-docs/}")
    private String swaggerDocumentationFileBasePath;
    @Autowired
    SpringTemplateEngine springTemplateEngine;
    @Override
    public Tag[] getAllSecuredApiTags() {
        return SecureApiTag.getAll();
    }

    @Override
    public void generateSwaggerDocumentationFiles() {
        File filePath = new File(swaggerDocumentationFileBasePath);
        if(!filePath.exists())
            filePath.mkdirs();

        Context context = null;
        for(DocType en : DocType.values()) {
            String fileName = swaggerDocumentationFileBasePath + en.getTemplateName();
            LOGGER.info("writing swagger document named : {}", fileName);
            context = getThymeleafContext(en);
            try(Writer writer = new FileWriter(fileName)) {
                writer.write(springTemplateEngine.process(en.getTemplateName(), context));
            } catch (IOException e) {
                LOGGER.error("IOException while writing swagger document with tryWithThymeleaf: {}, {}",
                        fileName, e);
            }
        }

    }

    private Context getThymeleafContext(DocType en) {
        Context context = new Context();
        if(DocType.RESPONSE_CODE.equals(en)) {
            context.setVariable("ecodes", ErrorCode.getVisibleErrorCodes());
        } else  if(DocType.API_LIST.equals(en)) {
            context.setVariable("views", SwaggerUtils.getApiList());
        } else {
            throw new UnsupportedOperationException();
        }
        return context;
    }

    @Override
    public File getSwaggerDocumentationFile(DocType en) {
        return new File(swaggerDocumentationFileBasePath + en.getTemplateName());
    }

}
