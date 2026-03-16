package com.algamoney.api.usecase.user;

import com.algamoney.api.database.entity.Usuario;
import com.algamoney.api.database.repository.UsuarioRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaPermissoesUsuario {
    private final UsuarioRepositoryFacade usuarioRepositoryFacade;

    public Usuario executar(Long codigo) {
        return usuarioRepositoryFacade.findByCodigo(codigo);
    }
}
