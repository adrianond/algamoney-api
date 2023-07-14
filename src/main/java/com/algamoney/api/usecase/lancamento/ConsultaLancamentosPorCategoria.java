package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorCategoriaDTO;
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
public class ConsultaLancamentosPorCategoria {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;

    public Page<LancamentoEstatisticaPorCategoriaDTO> executar(Pageable pageable, LocalDate mesReferencia) {
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


        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<LancamentoEstatisticaPorCategoriaDTO> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
        return page;
    }

    private CategoriaDTO buildCategoria(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }
}
