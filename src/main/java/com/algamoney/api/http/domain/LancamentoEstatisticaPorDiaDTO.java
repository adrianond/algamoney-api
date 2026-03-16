package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class LancamentoEstatisticaPorDiaDTO {
    private TipoLancamento tipo;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dia;
    private BigDecimal total;
}
