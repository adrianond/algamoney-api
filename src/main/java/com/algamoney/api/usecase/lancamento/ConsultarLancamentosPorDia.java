package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorDiaDTO;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultarLancamentosPorDia {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;

    public Page<LancamentoEstatisticaPorDiaDTO> executar(Pageable pageable, LocalDate mesReferencia) {
        List<LancamentoEstatisticaPorDiaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        predicate.and(QLancamento.lancamento.dataVencimento.between(primeiroDia, ultimoDia));

        Page<Lancamento> lancamentosPage = lancamentoRepositoryFacade.findAll(predicate, pageable);

        adicionaLancamentosTipoDespesa(list, lancamentosPage);
        adicionaLancamentosTipoReceita(list, lancamentosPage);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<LancamentoEstatisticaPorDiaDTO> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return page;
    }

    private void adicionaLancamentosTipoDespesa(List<LancamentoEstatisticaPorDiaDTO> list, Page<Lancamento> lancamentosPage) {
        lancamentosPage.stream()
                .filter(lancamento -> lancamento.getValor() != null
                        && lancamento.getTipoLancamento().equals((TipoLancamento.DESPESA)))
                .collect(Collectors.groupingBy(lancamento -> lancamento.getDataVencimento()))
                .forEach((key, lancamentos) ->
                        list.add(LancamentoEstatisticaPorDiaDTO.builder()
                                .total(lancamentos.stream().filter(lancamento -> lancamento.getDataVencimento().equals(key))
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .dia(key)
                                .tipo(TipoLancamento.DESPESA)
                                .build()));
    }

    private void adicionaLancamentosTipoReceita(List<LancamentoEstatisticaPorDiaDTO> list, Page<Lancamento> lancamentosPage) {
        lancamentosPage.stream()
                .filter(lancamento -> lancamento.getValor() != null
                        && lancamento.getTipoLancamento().equals((TipoLancamento.RECEITA)))
                .collect(Collectors.groupingBy(lancamento -> lancamento.getDataVencimento()))
                .forEach((key, lancamentos) ->
                        list.add(LancamentoEstatisticaPorDiaDTO.builder()
                                .total(lancamentos.stream().filter(lancamento -> lancamento.getDataVencimento().equals(key))
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .dia(key)
                                .tipo(TipoLancamento.RECEITA)
                                .build()));
    }
}
