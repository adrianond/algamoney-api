package com.algamoney.api.http.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PessoaRequest {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private boolean ativo;
    private EnderecoRequest endereco;
    private List<TelefoneRequest> telefones = new ArrayList<>();
}
