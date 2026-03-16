package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlteraCategoriaTest {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @InjectMocks
    private AlteraCategoria alteraCategoria;

    @Test
    public void deveAlterarNomeDaCategoriaComSucesso() {
        Long id = 1L;
        Categoria categoriaExistente = new Categoria("Nome Antigo");
        categoriaExistente.setId(id);

        CategoriaDTO categoriaDTO = CategoriaDTO.builder().nome("Nome Novo").build();
        CategoriaRequest request = new CategoriaRequest();
        request.setCategoriaDTO(categoriaDTO);

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoriaExistente);

        alteraCategoria.executar(id, request);

        assertEquals("Nome Novo", categoriaExistente.getNome());
        verify(categoriaRepositoryFacade).findById(id);
    }

    @Test
    public void deveBuscarCategoriaPorIdAntesDeAlterar() {
        Long id = 5L;
        Categoria categoria = new Categoria("Original");
        categoria.setId(id);

        CategoriaRequest request = new CategoriaRequest();
        request.setCategoriaDTO(CategoriaDTO.builder().nome("Alterado").build());

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoria);

        alteraCategoria.executar(id, request);

        verify(categoriaRepositoryFacade).findById(id);
    }
}
