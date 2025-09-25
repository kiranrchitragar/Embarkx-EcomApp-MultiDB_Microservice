package com.ecommerce.order_microservice.configuration;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptorConfig.class);

    private final ReadAllPropertiesAtOnce readAllPropertiesAtOnce;
    private final String activeProfile;
    public RequestInterceptorConfig(
            ReadAllPropertiesAtOnce readAllPropertiesAtOnce,
            @Value("${spring.profiles.active}") String activeProfile) {
        this.readAllPropertiesAtOnce = readAllPropertiesAtOnce;
        this.activeProfile = activeProfile;
    }


    // called at the time of application startup
    // Register an interceptor with the registry, Interceptor name : RequestInterceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("Active Profile Set is :: Interceptor :: {}", activeProfile);
        logger.info("readAllPropertiesAtOnce:: Id: " + readAllPropertiesAtOnce.getId() + "Name : " + readAllPropertiesAtOnce.getName());
        registry.addInterceptor(new RequestInterceptor());
    }
    //* We can register any number of interceptors with our spring application context
}