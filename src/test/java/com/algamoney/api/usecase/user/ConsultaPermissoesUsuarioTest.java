package com.algamoney.api.usecase.user;

import com.algamoney.api.database.entity.Usuario;
import com.algamoney.api.database.repository.UsuarioRepositoryFacade;
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
public class ConsultaPermissoesUsuarioTest {

    @Mock
    private UsuarioRepositoryFacade usuarioRepositoryFacade;

    @InjectMocks
    private ConsultaPermissoesUsuario consultaPermissoesUsuario;

    @Test
    public void deveRetornarUsuarioPorCodigo() {
        Long codigo = 1L;
        Usuario usuario = new Usuario();
        usuario.setCodigo(codigo);
        usuario.setEmail("admin@email.com");

        when(usuarioRepositoryFacade.findByCodigo(codigo)).thenReturn(usuario);

        Usuario resultado = consultaPermissoesUsuario.executar(codigo);

        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigo());
        assertEquals("admin@email.com", resultado.getEmail());
        verify(usuarioRepositoryFacade).findByCodigo(codigo);
    }

    @Test
    public void deveDelegarABuscaAoRepositorio() {
        Long codigo = 10L;
        when(usuarioRepositoryFacade.findByCodigo(codigo)).thenReturn(new Usuario());

        consultaPermissoesUsuario.executar(codigo);

        verify(usuarioRepositoryFacade).findByCodigo(codigo);
    }
}
