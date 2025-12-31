package com.mindteck.common.config;


import com.mindteck.common.models.rest.ApiView;
import com.mindteck.common.utils.SwaggerUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Optional;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;


/**
 * This class is used for getting the ApiOperation annotation value and set it in ApiView class which is used for
 * creating the additional file name Api-list.html.
 *
 * @author Hari Sankar
 *
 */



@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER+1)
@Slf4j
public class OperationNotesResourcesReader implements OperationBuilderPlugin {
    @Override
    public void apply(OperationContext operationContext) {
        try{
            Optional<ApiOperation> apiOperation = operationContext.findAnnotation(ApiOperation.class);

            ApiView apiView = new ApiView();
            apiView.setHttpMethod(operationContext.httpMethod().name());
            apiView.setName(apiOperation.isPresent() && StringUtils.hasText(apiOperation.get().value()) ? apiOperation.get().value() : "");
            apiView.setUrl(getUrl(operationContext));
            SwaggerUtils.addToList(apiView);

        } catch (Exception e) {
            LOGGER.error("Error occured while creating swagger documentation" + e);
        }

    }

    private String getUrl(OperationContext operationContext)
    {
       HttpMethod httpMethod = operationContext.httpMethod();
        String apiUrl = switch (httpMethod.toString()) {
            case "GET" -> operationContext.findAnnotation(GetMapping.class).get().value()[0];
            case "POST" -> operationContext.findAnnotation(PostMapping.class).get().value()[0];
            case "PUT" -> operationContext.findAnnotation(PutMapping.class).get().value()[0];
            case "DELETE" -> operationContext.findAnnotation(DeleteMapping.class).get().value()[0];
            default -> null;
        };
        return apiUrl;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return SwaggerPluginSupport.pluginDoesApply(documentationType);
    }
}
