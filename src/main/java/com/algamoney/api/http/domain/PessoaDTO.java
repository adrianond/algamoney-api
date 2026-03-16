package com.algamoney.api.http.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
