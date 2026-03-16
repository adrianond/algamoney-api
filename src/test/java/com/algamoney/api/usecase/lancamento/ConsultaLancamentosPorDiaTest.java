package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorDiaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLancamentosPorDiaTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @InjectMocks
    private ConsultaLancamentosPorDia consultaLancamentosPorDia;

    @Test
    public void deveRetornarEstatisticasDespesaEReceita() {
        LocalDate mesReferencia = LocalDate.of(2024, 1, 1);
        LocalDate dia = LocalDate.of(2024, 1, 15);

        Lancamento despesa = lancamento(1L, new BigDecimal("100.00"), TipoLancamento.DESPESA, dia);
        Lancamento receita = lancamento(2L, new BigDecimal("200.00"), TipoLancamento.RECEITA, dia);

        Page<Lancamento> page = new PageImpl<>(Arrays.asList(despesa, receita));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);

        List<LancamentoEstatisticaPorDiaDTO> resultado =
                consultaLancamentosPorDia.executar(Pageable.unpaged(), mesReferencia);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void deveSomarValoresDespesasDoMesmoDia() {
        LocalDate mesReferencia = LocalDate.of(2024, 1, 1);
        LocalDate dia = LocalDate.of(2024, 1, 10);

        Lancamento d1 = lancamento(1L, new BigDecimal("50.00"), TipoLancamento.DESPESA, dia);
        Lancamento d2 = lancamento(2L, new BigDecimal("30.00"), TipoLancamento.DESPESA, dia);

        Page<Lancamento> page = new PageImpl<>(Arrays.asList(d1, d2));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);

        List<LancamentoEstatisticaPorDiaDTO> resultado =
                consultaLancamentosPorDia.executar(Pageable.unpaged(), mesReferencia);

        assertEquals(1, resultado.size());
        assertEquals(new BigDecimal("80.00"), resultado.get(0).getTotal());
        assertEquals(TipoLancamento.DESPESA, resultado.get(0).getTipo());
        assertEquals(dia, resultado.get(0).getDia());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaLancamentos() {
        LocalDate mesReferencia = LocalDate.of(2024, 3, 1);
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        List<LancamentoEstatisticaPorDiaDTO> resultado =
                consultaLancamentosPorDia.executar(Pageable.unpaged(), mesReferencia);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveIgnorarLancamentosComValorNulo() {
        LocalDate mesReferencia = LocalDate.of(2024, 1, 1);
        LocalDate dia = LocalDate.of(2024, 1, 5);

        Lancamento semValor = new Lancamento();
        semValor.setId(1L);
        semValor.setValor(null);
        semValor.setTipoLancamento(TipoLancamento.DESPESA);
        semValor.setDataVencimento(dia);

        Page<Lancamento> page = new PageImpl<>(Collections.singletonList(semValor));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);

        List<LancamentoEstatisticaPorDiaDTO> resultado =
                consultaLancamentosPorDia.executar(Pageable.unpaged(), mesReferencia);

        assertTrue(resultado.isEmpty());
    }

    private Lancamento lancamento(Long id, BigDecimal valor, TipoLancamento tipo, LocalDate dataVencimento) {
        Lancamento l = new Lancamento();
        l.setId(id);
        l.setValor(valor);
        l.setTipoLancamento(tipo);
        l.setDataVencimento(dataVencimento);
        Categoria cat = new Categoria("Moradia");
        cat.setId(1L);
        l.setCategoria(cat);
        Pessoa p = new Pessoa();
        p.setId(1L);
        p.setNome("Ana");
        l.setPessoa(p);
        return l;
    }
}
