package com.algamoney.api.http.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequest {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
}
