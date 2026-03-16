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
public class ConsultaPessoaTest {

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @Mock
    private PessoaBuilder pessoaBuilder;

    @InjectMocks
    private ConsultaPessoa consultaPessoa;

    @Test
    public void deveRetornarPessoaPorId() {
        Long id = 1L;
        Pessoa pessoa = pessoaComId(id, "João");
        PessoaDTO dtoEsperado = new PessoaDTO();

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(dtoEsperado);

        PessoaDTO resultado = consultaPessoa.executar(id);

        assertNotNull(resultado);
        verify(pessoaRepositoryFacade).findById(id);
        verify(pessoaBuilder).buildPessoaDTO(pessoa);
    }

    @Test
    public void deveDelegarABuilderAConstrucaoDoDTO() {
        Long id = 2L;
        Pessoa pessoa = pessoaComId(id, "Maria");

        when(pessoaRepositoryFacade.findById(id)).thenReturn(pessoa);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(new PessoaDTO());

        consultaPessoa.executar(id);

        verify(pessoaBuilder).buildPessoaDTO(pessoa);
    }

    private Pessoa pessoaComId(Long id, String nome) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        return p;
    }
}
