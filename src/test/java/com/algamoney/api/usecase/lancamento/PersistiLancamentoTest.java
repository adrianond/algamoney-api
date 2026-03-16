package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersistiLancamentoTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @Mock
    private LancamentoBuilder lancamentoBuilder;

    @Mock
    private LancamentoRequest request;

    @InjectMocks
    private PersistiLancamento persistiLancamento;

    @Test
    public void devePersistirNovoLancamento() {
        Categoria categoria = categoriaComId(1L, "Alimentação");
        Pessoa pessoa = pessoaComId(1L, "João");
        Lancamento lancamentoSalvo = lancamentoSalvo(1L);

        LancamentoDTO dtoEsperado = new LancamentoDTO();
        dtoEsperado.setId(1L);

        when(request.getDescricao()).thenReturn("Supermercado");
        when(request.getValor()).thenReturn(new BigDecimal("250.00"));
        when(request.getTipo()).thenReturn(TipoLancamento.DESPESA);
        when(request.getDataVencimento()).thenReturn(LocalDate.now());
        when(request.getDataRecebimentoPagamento()).thenReturn(null);
        when(request.getObservacao()).thenReturn(null);
        when(request.getIdCategoria()).thenReturn(1L);
        when(request.getIdPessoa()).thenReturn(1L);

        when(categoriaRepositoryFacade.findById(1L)).thenReturn(categoria);
        when(pessoaRepositoryFacade.findById(1L)).thenReturn(pessoa);
        when(lancamentoRepositoryFacade.save(any(Lancamento.class))).thenReturn(lancamentoSalvo);
        when(lancamentoBuilder.build(lancamentoSalvo)).thenReturn(dtoEsperado);

        LancamentoDTO resultado = persistiLancamento.executar(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        verify(lancamentoRepositoryFacade).save(any(Lancamento.class));
        verify(categoriaRepositoryFacade).findById(1L);
        verify(pessoaRepositoryFacade).findById(1L);
    }

    @Test
    public void deveAtualizarLancamentoExistente() {
        Long idExistente = 5L;
        Lancamento lancamentoExistente = lancamentoSalvo(idExistente);
        Categoria categoria = categoriaComId(2L, "Transporte");
        Pessoa pessoa = pessoaComId(2L, "Maria");

        when(request.getDescricao()).thenReturn("Uber");
        when(request.getValor()).thenReturn(new BigDecimal("35.00"));
        when(request.getTipo()).thenReturn(TipoLancamento.DESPESA);
        when(request.getDataVencimento()).thenReturn(LocalDate.now());
        when(request.getDataRecebimentoPagamento()).thenReturn(null);
        when(request.getObservacao()).thenReturn("Viagem ao trabalho");
        when(request.getIdCategoria()).thenReturn(2L);
        when(request.getIdPessoa()).thenReturn(2L);

        when(lancamentoRepositoryFacade.findById(idExistente)).thenReturn(lancamentoExistente);
        when(categoriaRepositoryFacade.findById(2L)).thenReturn(categoria);
        when(pessoaRepositoryFacade.findById(2L)).thenReturn(pessoa);

        persistiLancamento.executar(idExistente, request);

        verify(lancamentoRepositoryFacade).findById(idExistente);
    }

    private Lancamento lancamentoSalvo(Long id) {
        Lancamento l = new Lancamento();
        l.setId(id);
        Categoria cat = new Categoria("Moradia");
        cat.setId(1L);
        l.setCategoria(cat);
        Pessoa p = new Pessoa();
        p.setId(1L);
        l.setPessoa(p);
        return l;
    }

    private Categoria categoriaComId(Long id, String nome) {
        Categoria c = new Categoria(nome);
        c.setId(id);
        return c;
    }

    private Pessoa pessoaComId(Long id, String nome) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        return p;
    }
}
