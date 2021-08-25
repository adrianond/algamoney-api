package com.algamoney.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.gateway.person")
@Data
public class AlgamoneyProperties {
    private String url;
}
