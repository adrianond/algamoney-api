package com.algamoney.api.http.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class ContatoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;
}
