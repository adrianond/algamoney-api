package com.algamoney.api.http.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PessoaDTO {
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @ApiModelProperty(
            value = "Nome da pessoa",
            required = true,
            dataType = "String",
            example = "Jose Paulo"
    )
    private String nome;

    @ApiModelProperty(
            value = "Identificador se pessoa esta ativo",
            required = true,
            dataType = "boolean",
            example = "true"
    )
    private boolean ativo;

    @ApiModelProperty(
            value = "Data de cadastro",
            hidden = true
    )
    private LocalDateTime dataCadastro;

    @ApiModelProperty(
            value = "Data de atualização",
            hidden = true
    )
    private LocalDateTime dataAtualizacao;

    private EnderecoDTO enderecoDTO;
    private List<TelefoneDTO> telefones;
    private List<ContatoDTO> contatos;
}
