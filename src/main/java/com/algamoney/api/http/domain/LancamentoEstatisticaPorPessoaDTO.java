package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class LancamentoEstatisticaPorPessoaDTO {
    private TipoLancamento tipo;
    private String nome;
    private BigDecimal total;
}
