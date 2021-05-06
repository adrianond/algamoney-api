package com.algamoney.api.http.domain.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategoriaRequest {
    private Long id;
    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;
}
