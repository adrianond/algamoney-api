package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.*;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultarLancamentos {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;
    private final PessoaBuilder pessoaBuilder;

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

    public Page<?> executarPorCategoria(Pageable pageable, LocalDate mesReferencia) {
        List<LancamentoEstatisticaPorCategoriaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        predicate.and(QLancamento.lancamento.dataVencimento.between(primeiroDia, ultimoDia));

        lancamentoRepositoryFacade.findAll(predicate, pageable)
                .stream().collect(Collectors.groupingBy(lancamento -> lancamento.getCategoria().getId()))
                .forEach((key, lancamentos) ->
                        list.add(LancamentoEstatisticaPorCategoriaDTO.builder()
                                .total(lancamentos.stream().filter(lancamento -> lancamento.getCategoria().getId().equals(key))
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .categoriaDTO(buildCategoria(lancamentos.stream()
                                        .filter(lancamento ->
                                                lancamento.getCategoria().getId().equals(key))
                                        .findFirst().get().getCategoria()))
                                .build()));

        return buildListToPage(pageable, list);
    }

    public Page<?> executarPorDia(Pageable pageable, LocalDate mesReferencia) {
        List<LancamentoEstatisticaPorDiaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        predicate.and(QLancamento.lancamento.dataVencimento.between(primeiroDia, ultimoDia));

        Page<Lancamento> lancamentosPage = lancamentoRepositoryFacade.findAll(predicate, pageable);

        adicionaLancamentosTipoDespesa(list, lancamentosPage);
        adicionaLancamentosTipoReceita(list, lancamentosPage);

        return buildListToPage(pageable, list);
    }

    public Page<?> executarPorPessoa(Pageable pageable, LocalDate dataVencimentoDe, LocalDate dataVencimentoAte) {
        List<LancamentoEstatisticaPorPessoaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(QLancamento.lancamento.dataVencimento.between(dataVencimentoDe, dataVencimentoAte));

        Page<Lancamento> lancamentosPage = lancamentoRepositoryFacade.findAll(predicate, pageable);

        adicionaLancamentosDespesa(list, lancamentosPage);
        adicionaLancamentosReceita(list, lancamentosPage);

        return buildListToPage(pageable, list);
    }


    private Page<?> buildListToPage(Pageable pageable, List<?> list) {
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<?> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
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
                                .pessoaDTO(pessoaBuilder.buildPessoaDTO(lancamentos.stream().filter(lancamento ->
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
                                .pessoaDTO(pessoaBuilder.buildPessoaDTO(lancamentos.stream().filter(lancamento ->
                                        lancamento.getPessoa().getId().equals(key)).findFirst().get().getPessoa()))
                                .tipo(TipoLancamento.RECEITA)
                                .build()));
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

    private CategoriaDTO buildCategoria(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }


}
