package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.exception.CategoriaNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaRepositoryFacadeImpl implements CategoriaRepositoryFacade {
    private final CategoriaRepository repository;

    @Override
    public Categoria findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(String.format("Categoria não encontrada para o id %s:", id)));
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Categoria categoria) {
        repository.delete(categoria);
    }
}
