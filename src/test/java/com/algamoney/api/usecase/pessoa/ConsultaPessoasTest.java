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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaPessoasTest {

    @Mock
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    @Mock
    private PessoaBuilder pessoaBuilder;

    @InjectMocks
    private ConsultaPessoas consultaPessoas;

    @Test
    public void deveRetornarTodasAsPessoas() {
        Pessoa p1 = pessoaComId(1L, "João");
        Pessoa p2 = pessoaComId(2L, "Maria");

        PessoaDTO dto1 = new PessoaDTO();
        PessoaDTO dto2 = new PessoaDTO();

        when(pessoaRepositoryFacade.findAll()).thenReturn(Arrays.asList(p1, p2));
        when(pessoaBuilder.buildPessoaDTO(p1)).thenReturn(dto1);
        when(pessoaBuilder.buildPessoaDTO(p2)).thenReturn(dto2);

        List<PessoaDTO> resultado = consultaPessoas.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pessoaRepositoryFacade).findAll();
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaPessoas() {
        when(pessoaRepositoryFacade.findAll()).thenReturn(Collections.emptyList());

        List<PessoaDTO> resultado = consultaPessoas.executar();

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarPaginacaoComFiltroDeNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Pessoa pessoa = pessoaComId(1L, "Carlos");
        PessoaDTO dto = new PessoaDTO();

        Page<Pessoa> pagePessoas = new PageImpl<>(Collections.singletonList(pessoa));
        when(pessoaRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(pagePessoas);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(dto);

        Page<PessoaDTO> resultado = consultaPessoas.executarComPaginacao(pageable, "Carlos");

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    public void deveRetornarPaginacaoSemFiltroDeNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Pessoa pessoa = pessoaComId(1L, "Ana");
        PessoaDTO dto = new PessoaDTO();

        Page<Pessoa> pagePessoas = new PageImpl<>(Collections.singletonList(pessoa));
        when(pessoaRepositoryFacade.findAll(any(), any(Pageable.class))).thenReturn(pagePessoas);
        when(pessoaBuilder.buildPessoaDTO(pessoa)).thenReturn(dto);

        // nome vazio: não aplica filtro de nome
        Page<PessoaDTO> resultado = consultaPessoas.executarComPaginacao(pageable, "");

        assertNotNull(resultado);
    }

    private Pessoa pessoaComId(Long id, String nome) {
        Pessoa p = new Pessoa();
        p.setId(id);
        p.setNome(nome);
        return p;
    }
}
