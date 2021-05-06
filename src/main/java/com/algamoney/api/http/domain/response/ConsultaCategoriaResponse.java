package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConsultaCategoriaResponse {
    private CategoriaDTO categoria;

    public ConsultaCategoriaResponse(CategoriaDTO categoria) {
        this.categoria = categoria;
    }
}
