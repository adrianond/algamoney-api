package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.QLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorCategoriaDTO;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
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

    public List<LancamentoEstatisticaPorCategoriaDTO> executar(Pageable pageable, LocalDate mesReferencia) {
        List<LancamentoEstatisticaPorCategoriaDTO> list = new ArrayList<>();
        BooleanBuilder predicate = new BooleanBuilder();

        LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
        LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

        predicate.and(QLancamento.lancamento.dataVencimento.between(primeiroDia, ultimoDia));

        lancamentoRepositoryFacade.findAll(predicate, pageable)
                .stream()
                .collect(Collectors.groupingBy(lancamento -> lancamento.getCategoria().getId()))
                .forEach((categoriaId, lancamentos) ->
                        list.add(LancamentoEstatisticaPorCategoriaDTO.builder()
                                .total(lancamentos.stream()
                                        .map(lancamento -> lancamento.getValor())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                .categoriaDTO(buildCategoria(lancamentos.get(0).getCategoria()))
                                .build()));

        return list;
    }

    private CategoriaDTO buildCategoria(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }
}
