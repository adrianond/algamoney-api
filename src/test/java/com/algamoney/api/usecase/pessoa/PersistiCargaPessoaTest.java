package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.CargaPessoaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PersistiCargaPessoaTest {

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @InjectMocks
    private PersistiCargaPessoa persistiCargaPessoa;

    @Test
    public void deveSalvarTodasAsPessoasDaCarga() {
        CargaPessoaDTO dto1 = new CargaPessoaDTO("João", true, "Rua A", "10", null, "Centro", "01000-000", "São Paulo", "SP");
        CargaPessoaDTO dto2 = new CargaPessoaDTO("Maria", false, "Av B", "20", "Ap 1", "Jardins", "02000-000", "Rio de Janeiro", "RJ");

        persistiCargaPessoa.executar(Arrays.asList(dto1, dto2));

        ArgumentCaptor<List<Pessoa>> captor = ArgumentCaptor.forClass(List.class);
        verify(pessoaRepositoryFacade).saveAll(captor.capture());
        assertEquals(2, captor.getValue().size());
    }

    @Test
    public void deveDefinirDataCadastroParaCadaPessoa() {
        CargaPessoaDTO dto = new CargaPessoaDTO("Pedro", true, "Rua C", "5", null, "Vila", "03000-000", "Curitiba", "PR");

        persistiCargaPessoa.executar(Collections.singletonList(dto));

        ArgumentCaptor<List<Pessoa>> captor = ArgumentCaptor.forClass(List.class);
        verify(pessoaRepositoryFacade).saveAll(captor.capture());

        Pessoa pessoaSalva = captor.getValue().get(0);
        assertEquals("Pedro", pessoaSalva.getNome());
        // dataCadastro deve ter sido preenchida no método buildListPessoa
        org.junit.Assert.assertNotNull(pessoaSalva.getDataCadastro());
    }

    @Test
    public void deveChamarSaveAllComListaVaziaQuandoEntradaVazia() {
        persistiCargaPessoa.executar(Collections.emptyList());

        verify(pessoaRepositoryFacade).saveAll(anyList());
    }
}
