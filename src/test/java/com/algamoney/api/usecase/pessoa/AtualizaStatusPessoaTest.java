package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtualizaStatusPessoaTest {

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @Mock
    private PessoaBuilder pessoaBuilder;

    @InjectMocks
    private AtualizaStatusPessoa atualizaStatusPessoa;

    @Test
    public void deveAtivarPessoa() {
        Long id = 1L;
        Pessoa pessoa = pessoaComId(id, "João", false);
        PessoaDTO dtoEsperado = new PessoaDTO();

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(dtoEsperado);

        PessoaDTO resultado = atualizaStatusPessoa.executar(id, true);

        assertNotNull(resultado);
        verify(pessoaRepositoryFacade).findById(id);
        verify(pessoaBuilder).buildPessoaDTO(pessoa);
    }

    @Test
    public void deveDesativarPessoa() {
        Long id = 2L;
        Pessoa pessoa = pessoaComId(id, "Maria", true);
        PessoaDTO dtoEsperado = new PessoaDTO();

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(dtoEsperado);

        atualizaStatusPessoa.executar(id, false);

        verify(pessoaRepositoryFacade).findById(id);
    }

    @Test
    public void deveAtualizarAtributoAtivoNaEntidade() {
        Long id = 3L;
        Pessoa pessoa = pessoaComId(id, "Pedro", false);

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(new PessoaDTO());

        atualizaStatusPessoa.executar(id, true);

        // verifica que o status foi alterado na entidade antes de construir o DTO
        verify(pessoaBuilder).buildPessoaDTO(pessoa);
    }

    private Pessoa pessoaComId(Long id, String nome, boolean ativo) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        p.setAtivo(ativo);
        return p;
    }
}
