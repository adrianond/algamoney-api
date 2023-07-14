package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ExcluiLancamento {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Transactional
    public void executar(Long id) {
        Lancamento lancamento = lancamentoRepositoryFacade.findById(id);
        lancamentoRepositoryFacade.delete(lancamento);
    }
}
