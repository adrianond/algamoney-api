package com.algamoney.api.http.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlgamoneyFilterRequest {

    private String nome;
    private String logradouro;
    private String descricao;
    private String  bairro;
    private String tipoLancamento;
    private String  nomeCategoria;
}
