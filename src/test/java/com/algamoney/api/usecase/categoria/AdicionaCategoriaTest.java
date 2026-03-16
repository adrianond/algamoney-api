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
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdicionaCategoriaTest {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @InjectMocks
    private AdicionaCategoria adicionaCategoria;

    @Test
    public void deveAdicionarCategoriaComSucesso() {
        CategoriaDTO categoriaDTO = CategoriaDTO.builder().nome("Alimentação").build();
        CategoriaRequest request = new CategoriaRequest();
        request.setCategoriaDTO(categoriaDTO);

        Categoria categoriaSalva = new Categoria("Alimentação");
        categoriaSalva.setId(1L);

        when(categoriaRepositoryFacade.save(any(Categoria.class))).thenReturn(categoriaSalva);

        CategoriaDTO resultado = adicionaCategoria.executar(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        assertEquals("Alimentação", resultado.getNome());
        verify(categoriaRepositoryFacade).save(any(Categoria.class));
    }

    @Test
    public void deveSalvarCategoriaSomenteComONomeFornecido() {
        CategoriaDTO categoriaDTO = CategoriaDTO.builder().nome("Transporte").build();
        CategoriaRequest request = new CategoriaRequest();
        request.setCategoriaDTO(categoriaDTO);

        Categoria categoriaSalva = new Categoria("Transporte");
        categoriaSalva.setId(2L);

        when(categoriaRepositoryFacade.save(any(Categoria.class))).thenReturn(categoriaSalva);

        CategoriaDTO resultado = adicionaCategoria.executar(request);

        assertEquals("Transporte", resultado.getNome());
    }
}
