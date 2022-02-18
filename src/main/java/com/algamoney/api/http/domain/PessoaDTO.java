package com.algamoney.api.http.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PessoaDTO {
    private Long id;
    private String nome;
    private boolean ativo;
    private String dataCadastro;
    private String dataAtualizacao;
    private EnderecoDTO endereco;
    private List<TelefoneDTO> telefones;
}
