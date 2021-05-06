package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultarLancamentos {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;

    public List<LancamentoDTO> executar() {
        return lancamentoRepositoryFacade.findAll().stream()
                .map(lancamento -> lancamentoBuilder.build(lancamento))
                .collect(Collectors.toList());
    }
}
