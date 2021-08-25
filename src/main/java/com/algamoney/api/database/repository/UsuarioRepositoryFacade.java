package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Usuario;

public interface UsuarioRepositoryFacade {
    Usuario findByCodigo(Long codigo);
}
