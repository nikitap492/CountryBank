package com.cbank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Podshivalov N.A.
 * @since 22.12.2017.
 */
@Data
@ConfigurationProperties("spring.mail")
public class MailProperties {
    private Boolean enable = Boolean.FALSE;
    private String username;
}
