package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdicionaCategoriaResponse {
    private CategoriaDTO categoria;

    public AdicionaCategoriaResponse(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
