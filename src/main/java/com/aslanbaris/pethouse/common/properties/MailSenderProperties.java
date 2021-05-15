package com.aslanbaris.pethouse.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "pethouse.mail-sender")
public class MailSenderProperties {

    private String apiUrl;
    private String apiUsername;
    private String apiKey;
    private String mailSender;

}
