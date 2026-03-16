package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExcluiPessoaTest {

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @InjectMocks
    private ExcluiPessoa excluiPessoa;

    @Test
    public void deveExcluirPessoaComSucesso() {
        Long id = 1L;
        Pessoa pessoa = pessoaComId(id, "João");

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);

        excluiPessoa.executar(id);

        verify(pessoaRepositoryFacade).findById(id);
        verify(pessoaRepositoryFacade).delete(pessoa);
    }

    @Test
    public void deveBuscarPessoaPorIdAntesDeExcluir() {
        Long id = 2L;
        Pessoa pessoa = pessoaComId(id, "Maria");

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);

        excluiPessoa.executar(id);

        verify(pessoaRepositoryFacade).findById(id);
    }

    private Pessoa pessoaComId(Long id, String nome) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        return p;
    }
}
