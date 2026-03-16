package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaLancamentoTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Mock
    private LancamentoBuilder lancamentoBuilder;

    @InjectMocks
    private ConsultaLancamento consultaLancamento;

    @Test
    public void deveRetornarLancamentoPorId() {
        Long id = 1L;
        Lancamento lancamento = lancamentoComDados(id);
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(id);
        dto.setDescricao("Conta de luz");

        when(lancamentoRepositoryFacade.findById(id)).thenReturn(lancamento);
        when(lancamentoBuilder.build(lancamento)).thenReturn(dto);

        LancamentoDTO resultado = consultaLancamento.executar(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Conta de luz", resultado.getDescricao());
    }

    @Test
    public void deveChamarRepositorioEBuilderNaOrdemCorreta() {
        Long id = 2L;
        Lancamento lancamento = lancamentoComDados(id);
        LancamentoDTO dto = new LancamentoDTO();

        when(lancamentoRepositoryFacade.findById(id)).thenReturn(lancamento);
        when(lancamentoBuilder.build(lancamento)).thenReturn(dto);

        consultaLancamento.executar(id);

        verify(lancamentoRepositoryFacade).findById(id);
        verify(lancamentoBuilder).build(lancamento);
    }

    private Lancamento lancamentoComDados(Long id) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        lancamento.setDescricao("Conta de luz");
        lancamento.setValor(new BigDecimal("150.00"));
        lancamento.setTipoLancamento(TipoLancamento.DESPESA);
        lancamento.setDataVencimento(LocalDate.now());

        Categoria categoria = new Categoria("Moradia");
        categoria.setId(1L);
        lancamento.setCategoria(categoria);

        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João");
        lancamento.setPessoa(pessoa);

        return lancamento;
    }
}
