package com.algamoney.api.http.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class LancamentoEstatisticaPorCategoriaDTO {
    private CategoriaDTO categoriaDTO;
    private BigDecimal total;
}
