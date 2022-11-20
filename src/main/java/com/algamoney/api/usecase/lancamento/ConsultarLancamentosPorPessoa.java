package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorPessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
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
public class ConsultarLancamentosPorPessoa {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public List<LancamentoEstatisticaPorPessoaDTO> executarConsulta(Pageable pageable, LocalDate dataVencimentoDe, LocalDate dataVencimentoAte) {
        List<LancamentoEstatisticaPorPessoaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(QLancamento.lancamento.dataVencimento.between(dataVencimentoDe, dataVencimentoAte));

        Page<Lancamento> lancamentosPage = lancamentoRepositoryFacade.findAll(predicate, pageable);

        adicionaLancamentosDespesa(list, lancamentosPage);
        adicionaLancamentosReceita(list, lancamentosPage);

       return list;
    }

    public Page<LancamentoEstatisticaPorPessoaDTO> executar (Pageable pageable, LocalDate dataVencimentoDe, LocalDate dataVencimentoAte) {
        List<LancamentoEstatisticaPorPessoaDTO> list = executarConsulta(pageable,  dataVencimentoDe, dataVencimentoAte);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<LancamentoEstatisticaPorPessoaDTO> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return page;
    }

    private void adicionaLancamentosDespesa(List<LancamentoEstatisticaPorPessoaDTO> list, Page<Lancamento> lancamentosPage) {
        lancamentosPage.stream()
                .filter(lancamento -> lancamento.getValor() != null
                        && lancamento.getTipoLancamento().equals((TipoLancamento.DESPESA)))
                .collect(Collectors.groupingBy(lancamento -> lancamento.getPessoa().getId()))
                .forEach((key, lancamentos) ->
                        list.add(LancamentoEstatisticaPorPessoaDTO.builder()
                                .total(lancamentos.stream().filter(lancamento -> lancamento.getPessoa().getId().equals(key))
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .nome(pessoaBuilder.getNomePessoa(lancamentos.stream().filter(lancamento ->
                                        lancamento.getPessoa().getId().equals(key)).findFirst().get().getPessoa()))
                                .tipo(TipoLancamento.DESPESA)
                                .build()));
    }

    private void adicionaLancamentosReceita(List<LancamentoEstatisticaPorPessoaDTO> list, Page<Lancamento> lancamentosPage) {
        lancamentosPage.stream()
                .filter(lancamento -> lancamento.getValor() != null
                        && lancamento.getTipoLancamento().equals((TipoLancamento.RECEITA)))
                .collect(Collectors.groupingBy(lancamento -> lancamento.getPessoa().getId()))
                .forEach((key, lancamentos) ->
                        list.add(LancamentoEstatisticaPorPessoaDTO.builder()
                                .total(lancamentos.stream().filter(lancamento -> lancamento.getPessoa().getId().equals(key))
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .nome(pessoaBuilder.getNomePessoa(lancamentos.stream().filter(lancamento ->
                                        lancamento.getPessoa().getId().equals(key)).findFirst().get().getPessoa()))
                                .tipo(TipoLancamento.RECEITA)
                                .build()));
    }
}
