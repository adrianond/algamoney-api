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
    private Mail mail;
    private S3 s3;

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

    @Data
    @NoArgsConstructor
    public static class Mail {

        private String host;
        private Integer port;
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class S3 {

        private String accessKeyId;
        private String secretAccessKey;
        private String bucket = "aw-algamoney-files-dantas";
    }

}
