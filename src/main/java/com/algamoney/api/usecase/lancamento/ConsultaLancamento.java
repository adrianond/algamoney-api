package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaLancamento {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;

    public LancamentoDTO executar(Long id) {
        Lancamento lancamento = lancamentoRepositoryFacade.findById(id);
        return lancamentoBuilder.build(lancamento);
    }
}
