package com.mindteck.common.config;

import com.mindteck.common.constants.Enum.DocType;
import com.mindteck.common.service.SwaggerUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Autowired
    private SwaggerUtilityService swaggerUtilityService;

    @Value("${mindteck.server.protocol}")
    String protocol;

    @Value("${mindteck.server.host}")
    private String host;

    @Value("${mindteck.web.url}")
    String webUrl;

    @Value("${server.debug}")
    String debugStatus;

    @Value("${server.basePath}")
    private String basePath;

    @Value("${server.servlet.contextPath}")
    private String contextPath;

    @Bean
    public Docket productApi() {


        Set<String> mediaTypes = new HashSet<>();
        mediaTypes.add("application/json");
        Tag[] tags = swaggerUtilityService.getAllSecuredApiTags();

        List<RequestParameter> aParameters = new ArrayList<>();
//        aParameters.add(getAuthHeaderParameter());
//        aParameters.add(versionParameterBuilder.build());
        aParameters.add(getDebugHeaderParameter());
        aParameters.add(getAuthorizationHeaderParameter());
        aParameters.add(getEncryptionHeaderParameter());


        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .paths(PathSelectors.regex("^((?!/public/).)*$"))
                .build()
                .apiInfo(metaData())
                .tags(tags[0], tags)
                .protocols(new HashSet<String>(List.of("http", "https")))
                .produces(mediaTypes)
                .globalRequestParameters(aParameters)
                .useDefaultResponseMessages(false);
    }


    private ApiInfo metaData() {
        String docUrl = getSwaggerCustomDoc();
        return new ApiInfo("Mindteck API Specification",
                "All API's are protected by token. The token should be passed as <b>'Authorization" +
                        "'</b> header in the following format <b>'bearer &lt;token&gt;'</b>" + docUrl,
                "1.0",
                webUrl,
                new Contact("Mindteck team", webUrl, "admin@mindteck.com"),
                "Licensed under mindteck policy",
                webUrl,
                Collections.emptyList());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .defaultModelRendering(ModelRendering.MODEL)
                .defaultModelsExpandDepth(-1)
                .displayRequestDuration(true)
                .displayOperationId(false)
                .filter(true)
                .deepLinking(true).build();
    }

    private List<RequestParameter> getGlobalOperationParams() {
        /*List<String> versionList = new ArrayList<>();
        versionList.add(DEFAULT_VERSION);
        AllowableListValues versionValues = new AllowableListValues(versionList, STRING_TYPE);

        ParameterBuilder versionParameterBuilder = new ParameterBuilder();
        versionParameterBuilder.name("version")
                .modelRef(new ModelRef(STRING_TYPE)).parameterType("path")
                .defaultValue(DEFAULT_VERSION)
                .allowableValues(versionValues)
                .required(true)
                .build();*/

        List<RequestParameter> aParameters = new ArrayList<>();
//        aParameters.add(getAuthHeaderParameter());
//        aParameters.add(versionParameterBuilder.build());
        aParameters.add(getDebugHeaderParameter());
        return aParameters;

    }

    private RequestParameter getDebugHeaderParameter() {
        RequestParameterBuilder debugHeaderBuilder = new RequestParameterBuilder();
        debugHeaderBuilder.name("debug")
                .in(ParameterType.HEADER)
                .query(param ->
                        param.allowEmptyValue(false)
                                .model(model -> model.scalarModel(ScalarType.STRING))
                                .defaultValue("true")
                                .enumerationFacet(e -> e.allowedValues(
                                        new AllowableListValues(
                                                List.of("true" , "false"),"string")
                                )))
                .required(false)
                .build();
        return debugHeaderBuilder.build();
    }

    private RequestParameter getEncryptionHeaderParameter(){
        RequestParameterBuilder encryptHeaderBuilder = new RequestParameterBuilder();
        encryptHeaderBuilder.name("enc")
                .in(ParameterType.HEADER)
                .query(q ->
                        q.allowEmptyValue(false)
                                .model(m -> m.scalarModel(ScalarType.STRING))
                                .defaultValue("false")
                                .enumerationFacet(e -> e.allowedValues(new AllowableListValues(List.of("true","false") , "string")
                                )))
                .required(false)
                ;
        return encryptHeaderBuilder.build();
    }

    private RequestParameter getAuthorizationHeaderParameter() {

        RequestParameterBuilder debugHeaderBuilder = new RequestParameterBuilder();
        debugHeaderBuilder.name("Authorization")
                .in(ParameterType.HEADER)
                .query(param ->
                        param.allowEmptyValue(false)
                                .model(model -> model.scalarModel(ScalarType.STRING))
                                .defaultValue("Bearer <token>"))
                .required(false)
                .build();
        return debugHeaderBuilder.build();
    }

    private String getSwaggerCustomDoc() {
        StringBuilder apiLink = new StringBuilder();
        int counter = 0;
        for (DocType en : DocType.values()) {
            String newLine = "";
            if (counter % 4 == 0) {
                newLine = "\n <br>";
            }
            counter++;
            apiLink.append(newLine).append("<a target = \"_blank\" href = \"")
                    .append(protocol).append("://")
                    .append(host + (contextPath.isEmpty() ? basePath : ""))
                    .append("/public/document?docType=")
                    .append(en.getCode())
                    .append("\">")
                    .append(en.getLinkInfo())
                    .append("</a>").append("&nbsp;&nbsp;&nbsp;&nbsp;");
        }

        return apiLink.toString();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}