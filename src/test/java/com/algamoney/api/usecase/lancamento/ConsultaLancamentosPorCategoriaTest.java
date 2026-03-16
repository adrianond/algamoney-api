package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorCategoriaDTO;
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
public class ConsultaLancamentosPorCategoriaTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @InjectMocks
    private ConsultaLancamentosPorCategoria consultaLancamentosPorCategoria;

    @Test
    public void deveAgruparLancamentosPorCategoria() {
        LocalDate mesReferencia = LocalDate.of(2024, 1, 1);

        Categoria alimentacao = categoriaComId(1L, "Alimentação");
        Lancamento l1 = lancamento(1L, new BigDecimal("100.00"), alimentacao);
        Lancamento l2 = lancamento(2L, new BigDecimal("50.00"), alimentacao);

        Page<Lancamento> page = new PageImpl<>(Arrays.asList(l1, l2));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);

        List<LancamentoEstatisticaPorCategoriaDTO> resultado =
                consultaLancamentosPorCategoria.executar(Pageable.unpaged(), mesReferencia);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(new BigDecimal("150.00"), resultado.get(0).getTotal());
        assertEquals("Alimentação", resultado.get(0).getCategoriaDTO().getNome());
    }

    @Test
    public void deveAgruparLancamentosEmCategoriasDistintas() {
        LocalDate mesReferencia = LocalDate.of(2024, 2, 1);

        Categoria alimentacao = categoriaComId(1L, "Alimentação");
        Categoria transporte = categoriaComId(2L, "Transporte");

        Lancamento l1 = lancamento(1L, new BigDecimal("200.00"), alimentacao);
        Lancamento l2 = lancamento(2L, new BigDecimal("80.00"), transporte);

        Page<Lancamento> page = new PageImpl<>(Arrays.asList(l1, l2));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);

        List<LancamentoEstatisticaPorCategoriaDTO> resultado =
                consultaLancamentosPorCategoria.executar(Pageable.unpaged(), mesReferencia);

        assertEquals(2, resultado.size());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaLancamentos() {
        LocalDate mesReferencia = LocalDate.of(2024, 3, 1);
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        List<LancamentoEstatisticaPorCategoriaDTO> resultado =
                consultaLancamentosPorCategoria.executar(Pageable.unpaged(), mesReferencia);

        assertTrue(resultado.isEmpty());
    }

    private Lancamento lancamento(Long id, BigDecimal valor, Categoria categoria) {
        Lancamento l = new Lancamento();
        l.setId(id);
        l.setValor(valor);
        l.setTipoLancamento(TipoLancamento.DESPESA);
        l.setDataVencimento(LocalDate.of(2024, 1, 15));
        l.setCategoria(categoria);
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        l.setPessoa(pessoa);
        return l;
    }

    private Categoria categoriaComId(Long id, String nome) {
        Categoria categoria = new Categoria(nome);
        categoria.setId(id);
        return categoria;
    }
}
