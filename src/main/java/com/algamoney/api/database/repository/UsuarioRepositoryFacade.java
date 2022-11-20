package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Usuario;

import java.util.List;

public interface UsuarioRepositoryFacade {
    Usuario findByCodigo(Long codigo);
    List<Usuario> findByPermissoesDescricao(String permissao);
}
