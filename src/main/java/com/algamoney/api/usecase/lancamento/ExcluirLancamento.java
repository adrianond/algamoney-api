package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.LancamentoQueryDslRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ExcluirLancamento {
    private final LancamentoQueryDslRepositoryFacade lancamentoQueryDslRepositoryFacade;

    @Transactional
    public void executar(Long id) {
        Lancamento lancamento = lancamentoQueryDslRepositoryFacade.findById(id);
        lancamentoQueryDslRepositoryFacade.delete(lancamento);
    }
}
