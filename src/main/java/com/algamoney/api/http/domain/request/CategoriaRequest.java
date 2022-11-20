package com.algamoney.api.http.domain.request;

import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoriaRequest {
   @Valid
   @NotNull (message = "Categoria é obrigatório")
   private CategoriaDTO categoriaDTO;
}
