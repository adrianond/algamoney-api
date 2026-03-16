package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCategoriaTest {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @InjectMocks
    private ConsultaCategoria consultaCategoria;

    @Test
    public void deveRetornarCategoriaPorId() {
        Long id = 1L;
        Categoria categoria = new Categoria("Saúde");
        categoria.setId(id);

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoria);

        CategoriaDTO resultado = consultaCategoria.executar(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Saúde", resultado.getNome());
    }

    @Test
    public void deveChamarRepositorioComIdCorreto() {
        Long id = 10L;
        Categoria categoria = new Categoria("Lazer");
        categoria.setId(id);

        when(categoriaRepositoryFacade.findById(id)).thenReturn(categoria);

        consultaCategoria.executar(id);

        verify(categoriaRepositoryFacade).findById(id);
    }
}
