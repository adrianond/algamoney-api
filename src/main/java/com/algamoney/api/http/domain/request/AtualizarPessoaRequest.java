package com.algamoney.api.http.domain.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AtualizarPessoaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    @NotNull(message = "Ativo é obrigatório")
    private boolean ativo;
    @NotNull(message = "Endereço da pessoa é obrigatório")
    private EnderecoRequest endereco;
    @Size(min = 1)
    List<TelefoneRequest> telefones = new ArrayList<>();
}
