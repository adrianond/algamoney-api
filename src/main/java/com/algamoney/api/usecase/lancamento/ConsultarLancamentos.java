package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.ResumoLancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
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

    public Page<LancamentoDTO> executarPaginacao(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepositoryFacade.filtrar(lancamentoFilter, pageable);
    }

    public Page<ResumoLancamentoDTO> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepositoryFacade.resumir(lancamentoFilter, pageable);
    }

    public Page<LancamentoDTO> executarPaginacaoQueryDsl(Pageable pageable, LocalDate dataVencimentoDe,
                                                         LocalDate dataVencimentoAte, TipoLancamento tipoLancamento, String descricao) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (null != dataVencimentoDe && null != dataVencimentoAte)
            predicate.and(QLancamento.lancamento.dataVencimento.between(dataVencimentoDe, dataVencimentoAte));

        if (null != tipoLancamento)
            predicate.and(QLancamento.lancamento.tipoLancamento.eq(tipoLancamento));

        if (StringUtils.hasText(descricao))
            predicate.and(QLancamento.lancamento.descricao.containsIgnoreCase(descricao));

        return build(lancamentoRepositoryFacade.findAll(predicate, pageable));
    }

    private Page<LancamentoDTO> build(Page<Lancamento> lancamentos) {
        return lancamentos
                .map(this::buildDTO);
    }

    private LancamentoDTO buildDTO(Lancamento lancamento) {
        return lancamentoBuilder.build(lancamento);
    }
}
