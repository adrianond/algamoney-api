package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExcluiLancamentoTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @InjectMocks
    private ExcluiLancamento excluiLancamento;

    @Test
    public void deveExcluirLancamentoComSucesso() {
        Long id = 1L;
        Lancamento lancamento = lancamento(id);

        when(lancamentoRepositoryFacade.findById(id)).thenReturn(lancamento);

        excluiLancamento.executar(id);

        verify(lancamentoRepositoryFacade).findById(id);
        verify(lancamentoRepositoryFacade).delete(lancamento);
    }

    @Test
    public void deveBuscarLancamentoPorIdAntesDeExcluir() {
        Long id = 2L;
        Lancamento lancamento = lancamento(id);

        when(lancamentoRepositoryFacade.findById(id)).thenReturn(lancamento);

        excluiLancamento.executar(id);

        verify(lancamentoRepositoryFacade).findById(id);
    }

    private Lancamento lancamento(Long id) {
        Lancamento l = new Lancamento();
        l.setId(id);
        l.setDescricao("Conta de água");
        l.setValor(new BigDecimal("80.00"));
        l.setTipoLancamento(TipoLancamento.DESPESA);
        l.setDataVencimento(LocalDate.now());
        Categoria cat = new Categoria("Moradia");
        cat.setId(1L);
        l.setCategoria(cat);
        Pessoa p = new Pessoa();
        p.setId(1L);
        l.setPessoa(p);
        return l;
    }
}
