package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Pessoa;

public interface PessoaRepositoryFacade {
    Pessoa add(Pessoa pessoa);

    Pessoa findById(Long id);

    void delete(Pessoa pessoa);
}
