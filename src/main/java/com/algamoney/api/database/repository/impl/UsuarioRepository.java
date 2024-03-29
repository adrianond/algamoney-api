package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByCodigo(Long codigo);

    List<Usuario> findByPermissoesDescricao(String permissaoDescricao);
}
