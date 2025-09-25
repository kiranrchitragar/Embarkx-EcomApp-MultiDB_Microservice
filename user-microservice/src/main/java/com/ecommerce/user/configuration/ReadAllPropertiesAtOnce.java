package com.ecommerce.user.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "build") // this reads all the properties that starts with build
@Data
public class ReadAllPropertiesAtOnce {
    private String id;
    private String name;
}
