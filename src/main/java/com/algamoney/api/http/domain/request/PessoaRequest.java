package com.algamoney.api.http.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PessoaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private boolean ativo;
    private EnderecoRequest endereco;
}
