package com.mindteck.common.service;

import com.mindteck.common.constants.Enum.DocType;
import springfox.documentation.service.Tag;

import java.io.File;

public interface SwaggerUtilityService {

    Tag[] getAllSecuredApiTags();

    void generateSwaggerDocumentationFiles();

    File getSwaggerDocumentationFile(DocType en);
}
