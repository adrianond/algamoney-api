package com.algamoney.api.http.domain.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AtualizarPessoaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotNull(message = "Ativo é obrigatório")
    private boolean ativo;
    @NotNull(message = "Endereço da pessoa é obrigatório")
    private EnderecoRequest endereco;
}
