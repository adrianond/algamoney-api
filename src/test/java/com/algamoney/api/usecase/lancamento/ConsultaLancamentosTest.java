package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.LancamentoFilter;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLancamentosTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Mock
    private LancamentoBuilder lancamentoBuilder;

    @InjectMocks
    private ConsultaLancamentos consultaLancamentos;

    @Test
    public void deveRetornarTodosOsLancamentos() {
        Lancamento l1 = lancamento(1L, "Conta de luz");
        Lancamento l2 = lancamento(2L, "Aluguel");

        LancamentoDTO dto1 = dtoComId(1L);
        LancamentoDTO dto2 = dtoComId(2L);

        when(lancamentoRepositoryFacade.findAll()).thenReturn(Arrays.asList(l1, l2));
        when(lancamentoBuilder.build(l1)).thenReturn(dto1);
        when(lancamentoBuilder.build(l2)).thenReturn(dto2);

        List<LancamentoDTO> resultado = consultaLancamentos.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaLancamentos() {
        when(lancamentoRepositoryFacade.findAll()).thenReturn(Collections.emptyList());

        List<LancamentoDTO> resultado = consultaLancamentos.executar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarPaginaDeLancamentosComFiltro() {
        LancamentoFilter filtro = new LancamentoFilter();
        Pageable pageable = PageRequest.of(0, 10);
        Page<LancamentoDTO> pageMock = new PageImpl<>(Collections.singletonList(dtoComId(1L)));

        when(lancamentoRepositoryFacade.filtrar(filtro, pageable)).thenReturn(pageMock);

        Page<LancamentoDTO> resultado = consultaLancamentos.executarPaginacao(filtro, pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(lancamentoRepositoryFacade).filtrar(filtro, pageable);
    }

    @Test
    public void deveRetornarPaginacaoComQueryDsl() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);

        Lancamento lancamento = lancamento(1L, "Despesa");
        LancamentoDTO dto = dtoComId(1L);
        Page<Lancamento> pageEntidade = new PageImpl<>(Collections.singletonList(lancamento));

        when(lancamentoRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(pageEntidade);
        when(lancamentoBuilder.build(lancamento)).thenReturn(dto);

        Page<LancamentoDTO> resultado = consultaLancamentos.executarPaginacaoQueryDsl(
                pageable, de, ate, TipoLancamento.DESPESA, "Despesa");

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }

    private Lancamento lancamento(Long id, String descricao) {
        Lancamento l = new Lancamento();
        l.setId(id);
        l.setDescricao(descricao);
        l.setValor(new BigDecimal("100.00"));
        l.setTipoLancamento(TipoLancamento.DESPESA);
        l.setDataVencimento(LocalDate.now());
        Categoria cat = new Categoria("Moradia");
        cat.setId(1L);
        l.setCategoria(cat);
        Pessoa p = new Pessoa();
        p.setId(1L);
        p.setNome("Maria");
        l.setPessoa(p);
        return l;
    }

    private LancamentoDTO dtoComId(Long id) {
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(id);
        return dto;
    }
}
