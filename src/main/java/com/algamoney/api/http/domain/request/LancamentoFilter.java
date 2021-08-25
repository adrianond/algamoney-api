package com.algamoney.api.http.domain.request;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LancamentoFilter {
    private TipoLancamento tipoLancamento;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoDe;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoAte;
}
