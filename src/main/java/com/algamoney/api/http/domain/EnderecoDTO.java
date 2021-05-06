package com.algamoney.api.http.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EnderecoDTO {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
}
