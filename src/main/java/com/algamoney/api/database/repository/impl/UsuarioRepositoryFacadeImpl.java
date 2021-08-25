package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Usuario;
import com.algamoney.api.database.repository.UsuarioRepositoryFacade;
import com.algamoney.api.exception.UsuarioNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioRepositoryFacadeImpl implements UsuarioRepositoryFacade {
    private final UsuarioRepository repository;

    @Override
    public Usuario findByCodigo(Long codigo) {
        return repository.findByCodigo(codigo).orElseThrow(() -> new UsuarioNotFoundException(String.format("Usuario não encontrada para o codigo %s:", codigo)));
    }
}
