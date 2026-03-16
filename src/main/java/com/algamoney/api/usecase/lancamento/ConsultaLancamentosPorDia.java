package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorDiaDTO;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultaLancamentosPorDia {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;

    public List<LancamentoEstatisticaPorDiaDTO> executar(Pageable pageable, LocalDate mesReferencia) {
        List<LancamentoEstatisticaPorDiaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        predicate.and(QLancamento.lancamento.dataVencimento.between(primeiroDia, ultimoDia));

        Page<Lancamento> lancamentosPage = lancamentoRepositoryFacade.findAll(predicate, pageable);

        adicionaLancamentosPorTipo(list, lancamentosPage, TipoLancamento.DESPESA);
        adicionaLancamentosPorTipo(list, lancamentosPage, TipoLancamento.RECEITA);

        return list;
    }

    private void adicionaLancamentosPorTipo(List<LancamentoEstatisticaPorDiaDTO> list,
                                             Page<Lancamento> lancamentosPage,
                                             TipoLancamento tipo) {
        lancamentosPage.stream()
                .filter(lancamento -> lancamento.getValor() != null
                        && lancamento.getTipoLancamento().equals(tipo))
                .collect(Collectors.groupingBy(Lancamento::getDataVencimento))
                .forEach((dia, lancamentos) ->
                        list.add(LancamentoEstatisticaPorDiaDTO.builder()
                                .total(lancamentos.stream()
                                        .map(Lancamento::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .dia(dia)
                                .tipo(tipo)
                                .build()));
    }
}
