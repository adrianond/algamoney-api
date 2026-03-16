package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCategoriasTest {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @InjectMocks
    private ConsultaCategorias consultaCategorias;

    @Test
    public void deveRetornarListaDeTodasCategorias() {
        Categoria alimentacao = categoriaComId(1L, "Alimentação");
        Categoria transporte = categoriaComId(2L, "Transporte");

        when(categoriaRepositoryFacade.findAll()).thenReturn(Arrays.asList(alimentacao, transporte));

        List<CategoriaDTO> resultado = consultaCategorias.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Alimentação", resultado.get(0).getNome());
        assertEquals("Transporte", resultado.get(1).getNome());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaCategorias() {
        when(categoriaRepositoryFacade.findAll()).thenReturn(Collections.emptyList());

        List<CategoriaDTO> resultado = consultaCategorias.executar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveMappearIdENomeDeCadaCategoria() {
        Categoria categoria = categoriaComId(3L, "Saúde");
        when(categoriaRepositoryFacade.findAll()).thenReturn(Collections.singletonList(categoria));

        List<CategoriaDTO> resultado = consultaCategorias.executar();

        assertEquals(3L, resultado.get(0).getId().longValue());
        assertEquals("Saúde", resultado.get(0).getNome());
    }

    @Test
    public void deveChamarFindAllNoRepositorio() {
        when(categoriaRepositoryFacade.findAll()).thenReturn(Collections.emptyList());

        consultaCategorias.executar();

        verify(categoriaRepositoryFacade).findAll();
    }

    @Test
    public void deveLimparCacheSemErros() {
        // verifica que o método de limpeza de cache pode ser chamado sem exceções
        consultaCategorias.clearCache();
    }

    private Categoria categoriaComId(Long id, String nome) {
        Categoria categoria = new Categoria(nome);
        categoria.setId(id);
        return categoria;
    }
}
