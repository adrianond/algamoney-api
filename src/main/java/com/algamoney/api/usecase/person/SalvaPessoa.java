package com.algamoney.api.usecase.person;

import com.algamoney.api.config.AlgamoneyApiProperties;
import com.algamoney.api.config.AlgamoneyBeanConfiguration;
import com.algamoney.api.http.domain.request.PersonRequest;
import com.algamoney.api.http.domain.response.PersonResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class SalvaPessoa {
    private final AlgamoneyApiProperties algamoneyApiProperties;
    private final AlgamoneyBeanConfiguration algamoneyBeanConfiguration;
    private static final String API_GATEWAY_CREATE_PERSON = "/person";

    public PersonResponse executar(PersonRequest personRequest) {
        String url = algamoneyApiProperties.getPerson().getUrl() + API_GATEWAY_CREATE_PERSON;
        HttpEntity<PersonRequest> entity = new HttpEntity<>(personRequest);
        return executePost(url, entity);
    }

    private PersonResponse executePost(String url, HttpEntity<PersonRequest> entity) {
        PersonResponse response;
        RestTemplate restTemplate = algamoneyBeanConfiguration.restTemplate();
        response = restTemplate.postForEntity(url, entity, PersonResponse.class).getBody();
        return response != null ? response : null;
    }
}
