package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Categoria;

import java.util.List;

public interface CategoriaRepositoryFacade {
    Categoria findById(Long id);
    Categoria save(Categoria categoria);
    List<Categoria> findAll();
    void delete(Categoria categoria);
}
