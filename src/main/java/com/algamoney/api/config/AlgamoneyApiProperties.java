package com.algamoney.api.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(
        prefix = "app.algamoney"
)
public class AlgamoneyApiProperties {

    private Seguranca seguranca;
    private Person person;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Seguranca {

        private boolean enableHttps;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {

        private String url;
    }
}
