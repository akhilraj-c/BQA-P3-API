package com.mindteck;

import com.mindteck.common.dao.UserLoginInfoDao;
import com.mindteck.common.filters.RequestBodyFilter;
import com.mindteck.common.filters.ResponseBodyFilter;
import com.mindteck.common.interceptors.CommonInterceptor;
import com.mindteck.common.interceptors.SwggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class MindTeckConfig implements WebMvcConfigurer {
    @Autowired
    private SwggerInterceptor swggerInterceptor;
    @Autowired
      private CommonInterceptor commonInterceptor;

    @Autowired
    private UserLoginInfoDao userLoginInfoDao;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(swggerInterceptor).addPathPatterns("/api-doc", "/swagger-ui.html*");
        registry.addInterceptor(commonInterceptor);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api-doc", "/swagger-ui.html");
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


   /* @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }*/

    @Bean
    public FilterRegistrationBean<RequestBodyFilter> requestBodyFilter() {
        FilterRegistrationBean<RequestBodyFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RequestBodyFilter(userLoginInfoDao));
        filterFilterRegistrationBean.addUrlPatterns("/api/*");
        filterFilterRegistrationBean.setOrder(2);
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<ResponseBodyFilter> responseBodyFilter() {
        FilterRegistrationBean<ResponseBodyFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new ResponseBodyFilter());
        filterFilterRegistrationBean.addUrlPatterns("/api/*");
        filterFilterRegistrationBean.setOrder(2);
        return filterFilterRegistrationBean;
    }
}
