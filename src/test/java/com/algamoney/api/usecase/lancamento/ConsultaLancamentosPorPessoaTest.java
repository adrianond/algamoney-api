package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoEstatisticaPorPessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
public class ConsultaLancamentosPorPessoaTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Mock
    private PessoaBuilder pessoaBuilder;

    @InjectMocks
    private ConsultaLancamentosPorPessoa consultaLancamentosPorPessoa;

    @Test
    public void deveRetornarEstatisticasDespesaPorPessoa() {
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);

        Pessoa pessoa = pessoaComId(1L, "João");
        Lancamento despesa = lancamento(1L, new BigDecimal("300.00"), TipoLancamento.DESPESA, pessoa);

        Page<Lancamento> page = new PageImpl<>(Collections.singletonList(despesa));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(pessoaBuilder.getNomePessoa(pessoa)).thenReturn("João");

        List<LancamentoEstatisticaPorPessoaDTO> resultado =
                consultaLancamentosPorPessoa.executarConsulta(Pageable.unpaged(), de, ate);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(TipoLancamento.DESPESA, resultado.get(0).getTipo());
    }

    @Test
    public void deveRetornarEstatisticasReceitaPorPessoa() {
        LocalDate de = LocalDate.of(2024, 2, 1);
        LocalDate ate = LocalDate.of(2024, 2, 28);

        Pessoa pessoa = pessoaComId(2L, "Maria");
        Lancamento receita = lancamento(1L, new BigDecimal("500.00"), TipoLancamento.RECEITA, pessoa);

        Page<Lancamento> page = new PageImpl<>(Collections.singletonList(receita));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(pessoaBuilder.getNomePessoa(pessoa)).thenReturn("Maria");

        List<LancamentoEstatisticaPorPessoaDTO> resultado =
                consultaLancamentosPorPessoa.executarConsulta(Pageable.unpaged(), de, ate);

        assertEquals(1, resultado.size());
        assertEquals(TipoLancamento.RECEITA, resultado.get(0).getTipo());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaLancamentos() {
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);

        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        List<LancamentoEstatisticaPorPessoaDTO> resultado =
                consultaLancamentosPorPessoa.executarConsulta(Pageable.unpaged(), de, ate);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarPaginadoComOffsetCorreto() {
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);
        Pageable pageable = PageRequest.of(0, 10);

        Pessoa pessoa = pessoaComId(1L, "Pedro");
        Lancamento despesa = lancamento(1L, new BigDecimal("100.00"), TipoLancamento.DESPESA, pessoa);

        Page<Lancamento> page = new PageImpl<>(Collections.singletonList(despesa));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(pessoaBuilder.getNomePessoa(pessoa)).thenReturn("Pedro");

        Page<LancamentoEstatisticaPorPessoaDTO> resultado =
                consultaLancamentosPorPessoa.executar(pageable, de, ate);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    public void deveSomarValoresDespesasDaMesmaPessoa() {
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);

        Pessoa pessoa = pessoaComId(1L, "Ana");
        Lancamento d1 = lancamento(1L, new BigDecimal("100.00"), TipoLancamento.DESPESA, pessoa);
        Lancamento d2 = lancamento(2L, new BigDecimal("200.00"), TipoLancamento.DESPESA, pessoa);

        Page<Lancamento> page = new PageImpl<>(Arrays.asList(d1, d2));
        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(pessoaBuilder.getNomePessoa(pessoa)).thenReturn("Ana");

        List<LancamentoEstatisticaPorPessoaDTO> resultado =
                consultaLancamentosPorPessoa.executarConsulta(Pageable.unpaged(), de, ate);

        assertEquals(1, resultado.size());
        assertEquals(new BigDecimal("300.00"), resultado.get(0).getTotal());
    }

    private Lancamento lancamento(Long id, BigDecimal valor, TipoLancamento tipo, Pessoa pessoa) {
        Lancamento l = new Lancamento();
        l.setId(id);
        l.setValor(valor);
        l.setTipoLancamento(tipo);
        l.setDataVencimento(LocalDate.now());
        Categoria cat = new Categoria("Moradia");
        cat.setId(1L);
        l.setCategoria(cat);
        l.setPessoa(pessoa);
        return l;
    }

    private Pessoa pessoaComId(Long id, String nome) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        return p;
    }
}
