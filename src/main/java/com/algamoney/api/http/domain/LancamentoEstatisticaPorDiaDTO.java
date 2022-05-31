package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class LancamentoEstatisticaPorDiaDTO {
    private TipoLancamento tipo;
    private LocalDate dia;
    private BigDecimal total;
}
