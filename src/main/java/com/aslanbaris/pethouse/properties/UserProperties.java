package com.aslanbaris.pethouse.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "user")
@PropertySource("classpath:messages.properties")
public class UserProperties {

    private String confirmMessage;

}
