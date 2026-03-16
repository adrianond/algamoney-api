package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExcluiCategoriaTest {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @InjectMocks
    private ExcluiCategoria excluiCategoria;

    @Test
    public void deveExcluirCategoriaComSucesso() {
        Long id = 1L;
        Categoria categoria = new Categoria("Alimentação");
        categoria.setId(id);

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoria);

        excluiCategoria.executar(id);

        verify(categoriaRepositoryFacade).findById(id);
        verify(categoriaRepositoryFacade).delete(categoria);
    }

    @Test
    public void deveBuscarCategoriaPorIdAntesDeExcluir() {
        Long id = 2L;
        Categoria categoria = new Categoria("Transporte");
        categoria.setId(id);

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoria);

        excluiCategoria.executar(id);

        verify(categoriaRepositoryFacade).findById(id);
    }
}
